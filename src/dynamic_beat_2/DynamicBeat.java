package dynamic_beat_2;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DynamicBeat extends JFrame {
	/*
	 * 자바에서 제공하는, 단순히 이미지를 화면에 띄우는 방식을 이용하면 버퍼링이 심함 ==> '더블 버퍼링(double buffering)' 기법 사용. 
	 * <Double Buffering이란?> 
	 * - 현재 프로그램이 전체 화면의 크기에 맞는 이미지를 매 순간마다 생성해서 원하는 컴포넌트만 화면에 출력하는 방식.
	 * - 버퍼에 이미지를 담아서, 매 순간마다 이미지를 갱신해줌 
	*/
	// 아래의 screenIamge, screenGraphics : 더블 버퍼링을 위해서 전체 화면에 대한 이미지를 담는 인스턴스. 
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image introBackground;
	
	// 생성자
	public DynamicBeat() {
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		introBackground = new ImageIcon(Main.class.getResource("../images/introBackground(Title2).jpg")).getImage();
		// main class의 위치를 기반으로 해서 저 파일을 얻어온 다음, 그것의 이미지 인스턴스를 introBackground 변수에 넣어서 초기화.
	}
	
	// 메소드 
	// paint : JFrame을 상속받은 GUI 프로그램에서 가장 첫번째로 화면을 그려주는 함수.
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		//프로그램 화면 크기만큼 이미지를 생성해서, 그 이미지에 우리가 원하는 내용을 그려줌
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		//screenDraw 함수를 이용해서 그려줌. 
		g.drawImage(screenImage, 0, 0, null);
		// 게임창에 스크린 이미지가 그려짐 
	}
	// 
	public void screenDraw(Graphics g) {
		g.drawImage(introBackground, 0, 0, null);
		this.repaint();
	}

}
