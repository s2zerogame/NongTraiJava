package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Xin chào");
		System.out.println("Bạn đang dùng Code của nhóm 06(T4 - t789) - NNT");
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Nông trại vui vẻ");
		
		//Phần khởi động hiển thị trò chơi
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		//Load các cấu hình trước đó của người chơi
		gamePanel.config.loadConfig();
		if(gamePanel.fullScreenOn == true) {
			window.setUndecorated(true);
		}
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGane();
		gamePanel.startGameThread();
		//20/09/2023
		//Nội dung
	}

}
