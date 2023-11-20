package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_HatGiong1 extends Entity{

	GamePanel gp;
	int consum = 1;
	
	public OBJ_HatGiong1(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		type = type_plant1;
		name = "Bap";
		down1 = setup("/HatGiong/HatGiong1");
		collision = true;
		price = 10;
		stackable = true; // cho phép lưu trữ nhiều
		
		daysToGrow = 5; //số ngày tối thiểu để thu hoạch
		daysGrown = 0; // Số ngày phát triển
		description = "[" + name +"] \n Bắp cần trả qua 5"
				+ "\n giai đoạn để thu "
				+ "\n hoạch"
				+ "\n Số ngày cần: "+ daysToGrow;
		//Set up phạm vi của công cụ
		targetArea.width = 36;
		targetArea.height = 36;
	}
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialouge = "Bạn đã dùng "+ name+"!";
		//Thêm hiệu ứng gieo hạt
		gp.playSE(24);
	}
}
