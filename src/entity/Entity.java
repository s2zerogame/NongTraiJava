package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	public BufferedImage up1,up2,up3,up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
	//Thiết lập hoạt ảnh animation cho nhân vật
	public BufferedImage animaTionUp1, animaTionUp2, animaTionDown1, animaTionDown2, animaTionLeft1,animaTionLeft2, animaTionRight1,animaTionRight2;
	
	//State
	public int worldX,worldY;
	
	public String direction = "down", direction1;
	public int spriteCounter =0, spriteCounter1 =0;
	public int spriteNum = 1, spriteNum1 = 1;
	//Lệnh gọi đến animation cho nhân vật
	boolean playerAnimation = false;
	
	//Thiết lập định hình cho nhân vật không thể đi qua các vật cản
	//B1: Thiết lập khối hình khối bên trong nhân vật
	public Rectangle solidArea = new Rectangle(0,0,48,48) ; // Thiết lập giá trị mặc định cho tất cả NPC
	//Tạo một vùng tác động bởi hành động đào đất của nhân vật
	public Rectangle targetArea = new Rectangle(0,0,0,0); // Các con số bên trong sẽ tùy thuộc vào dụng cụ của nhân vật
	//Khi xác định dụng cụ sẽ ghi đè lên
	
	//Thiết lập các khối set up để phát hiện va chạm 
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
	//Phạm vị bộ đếm khóa hành động
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	
	//Phạm vi của đoạn hội thoại danh cho mỗi NPC
	String dialogue[] = new String[20];
	int dialogưeIndex = 0;
	
	//Khai báo siêu đối tượng
	//khai báo số lượng hình ảnh
	public BufferedImage image,image1,image2;
	public String name;
	public boolean collision = false;
	
	
	//Thông tin về nhân vật
	public int speed; //Tốc độ di chuyển
	public int maxLife; // Mạng tối đa
	public int life; //Mạng hiện tại
	public int level; 
	public int coins; // Số tiền 
	public int exps; // Kinh nghiệm
	public int nextLevel;
	//public int days;
	public Entity currentCongCu;
	public Entity currentShield; // Khiên lá chắn
	
	//Type
	public int type; // 0 - player , 1 - npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_axe = 3;
	public final int type_pickaxe = 4;
	public final int type_watering = 5;
	public final int type_plant1 = 6;
	public final int type_plant2 = 7;
	public final int type_consumable = 8;
	public final int type_hook = 9;
	
	public final int type_nothing = 99;
	
	//Mô tả item 
	//Khai báo danh túi đồ
	public ArrayList<Entity> inventory = new ArrayList<Entity>();
	public final int maxInventorySize = 20;
	public int attackValue;
	public String description ="";
	public int daysToGrow;
	public int daysGrown;
	public int valueConsumable;
	public int maxValueConsum;
	public int price; //Giá tiền
	public boolean stackable = false; //Số lượng lưu trữ của item
	public int amount = 1;
		
	//Check NPC có đc direction hay không 
	boolean check = false;
	//Bảng điều khiển trò chơi
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	//Tự động ghi đè trong mỗi đối tượng
	//Thiết lập animation cho NPC
	public void setAction() {
		//AI tự động điều khiển NPC di chuyển
	}
	public void speak() {}
	public void use(Entity entity) {}
	public void update() {
		
		setAction();
		//kiểm tra chiongws ngại vật
		collisionOn = false;
		gp.cChecker.checkTile(this);
		//Kiểm tra đối tượng thứ 2
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this,gp.npc);
		gp.cChecker.checkEntity(this,gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.objDig);
		//Kiểm tra đối tượng người chơi
		boolean contactPlaer = gp.cChecker.checkPlayer(this);
		
		//Khi người chơi và cả monster lại gần thì nhân vật sẽ bị trừ máu
		if(this.type == 2 && contactPlaer == true) {
			if(gp.player.invincible == false) {
				//Người chơi bị giảm máu
				gp.player.life -=1 ;
				gp.player.invincible = true;
			}
		}
		
		//Nếu vật thẩ xuyên được là false , người chơi có thể di chuyển
		if(collisionOn == false) {
			switch (direction) {
			case "up": worldY 	-= speed; break;
			case "down": worldY += speed;break;
			case "left":worldX 	-= speed; break;
			case "right":worldX += speed;break;
			}
		}
		
		//Sprite sẽ được tăng lên khi người dùng nhấn vào các nút di chuyển
		spriteCounter++;
		//Với mỗi khung hình được lặp đi lặp lại cho tới khi lớn hơn 12 sẽ thực hiện animation
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		if(invincible == true) {
			invincibleCounter ++;
			if(invincibleCounter >40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	//Vẽ nhân vật
	public void draw (Graphics2D g2) {
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX; 
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		//Stop moving the camera at the edge
		if(gp.player.worldX < gp.player.screenX) {
			screenX = worldX;
		}
		if(gp.player.worldY < gp.player.screenY) {
			screenY = worldY;
		}
		int rightOffset = gp.screenWidth - gp.player.screenX;
		if(rightOffset > gp.worldWidth - gp.player.worldX) {
			screenX = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - gp.player.screenY;
		if(bottomOffset > gp.worldHeight - gp.player.worldY) {
			screenY = gp.screenHeight - (gp.worldHeight - worldY);
		}
		
		switch (direction) {
		case "up": {
			
			//Kiểm tra check 
			if (check == false) {
				//Hành động đang đứng thở
				if(spriteNum == 1) {
					image = up1;
				}
				else if (spriteNum == 2) {
					image = up2;
				}
			}
			else {
				//Hành động bước đi
				if(spriteNum == 1) {
					image = up3;
				}
				else if (spriteNum == 2) {
					image = up4;
				}
			}
			break;
		}
		case "down": {
			
			if (check == false) {
				//Hành động đang đứng thở
				if(spriteNum == 1) {
					image = down1;
				}
				else if(spriteNum == 2) {
					image = down2;
				}
			}
			else {
				//Hành động bước đi
				if(spriteNum == 1) {
					image = down3;
				}
				else if(spriteNum == 2) {
					image = down4;
				}
			}
			break;
		}
		case "left": {
			
			if(check == false) {
				//Hành động thở
				if(spriteNum == 1) {
					image = left1;
				}
				else if(spriteNum == 2) {
					image = left2;
				}
			}
			else {
				//Hành động bước đi
				if(spriteNum == 1) {
					image = left3;
				}
				else if(spriteNum == 2) {
					image = left4;
				}
			}			
			break;
		}
		case "right": {
			
			if(check == false) {
				//Hành động thở
				if(spriteNum == 1) {
					image = right1;
				}
				else if(spriteNum == 2) {
					image = right2;
				}
			}
			else {
				//Hành động bước đi
				if(spriteNum == 1) {
					image = right3;
				}
				else if(spriteNum == 2) {
					image = right4;
				}
			}
			break;
		}}
		
		
		
		if(worldX + gp.titleSize > gp.player.worldX - gp.player.screenX && 
		   worldX - gp.titleSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.titleSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.titleSize < gp.player.worldY + gp.player.screenY) {				
			//System.out.println("Bạn đang : "+ spriteNum);

			
			
			if(invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
			}

			g2.drawImage(image,screenX,screenY, gp.titleSize,gp.titleSize,null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		else if (gp.player.worldX < gp.player.screenX ||
				gp.player.worldY < gp.player.screenY ||
				rightOffset > gp.worldWidth - gp.player.worldX ||
				bottomOffset > gp.worldHeight - gp.player.worldY) {
			g2.drawImage(image,screenX,screenY, gp.titleSize,gp.titleSize,null);
		}
		
	}
	//Thiết lập phần vẽ cho các nhân vật thông qua setup
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image, gp.titleSize, gp.titleSize);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return image;
	}
	//Thiết lập phần vẽ dành riêng cho animtion cho nhân vật 
	public BufferedImage setupAnimation(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image, width, height);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return image;
	}
	//Thiết lập phần vẽ cho khung giao diện dành cho đối tượng muốn tùy chỉnh kích cỡ
	public BufferedImage setupOption (String imagePath,int width,int height) {
			
			UtilityTool uTool = new UtilityTool();
			BufferedImage image = null;
			
			try {
				
				image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
				image = uTool.scaleImage(image, gp.titleSize + width, gp.titleSize + height);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return image;
		}
}
