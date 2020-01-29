// Note : 하나의 떨어지는 note를 클래스화해서 다루기 위한 것.
package dynamic_beat_13;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread { // 각각의 note 또한 하나의 부분적인 기능으로써 떨어지는 역할을 수행해야 하므로 Thread로 만든다. 
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x;
	private int y = 580 - 1000 / Main.SLEEP_TIME * Main.NOTE_SPEED; 
	/*
	 * 1. note가 1초에 움직이는 거리 
	 *  - SLEEP_TIME : 쓰레드를 이 시간동안 실행 중지시킴 
	 *                => 쓰레드는 일시중지했다가 SLEEP_TIME 만큼의 시간이 지나면 다시 실행됨.
	 *  - NOTE_SPEED : 쓰레드가 멈췄다가 다음에 실행될때까지 내려오는 y값 (픽셀) 
	 *  - note는 SLEEP_TIME(ms) 동안 NOTE_SPEED(px) 만큼 내려간다 
	 *    ==> note의 속도는 NOTE_SPEED(px)/SLEEP_TIME(ms) 
	 * 	   				= NOTE_SPEED*1000/SLEEP_TIME (px/s) 
	 *   ==> note는 1초에 NOTE_SPEED*1000/SLEEP_TIME 만큼 내려간다. 
	 * 
	 *  2. note가 생성되고 1초 후에 판정 라인에 닿도록 y 초기값 설정
	 *  - note가 생성되고 y초기좌표에서 1초동안 NOTE_SPEED*1000/SLEEP_TIME 만큼 이동하는데 이게 판정라인 y좌표와 같아야 함. 
	 *  - y초기좌표 + NOTE_SPEED*1000/SLEEP_TIME = 580 (판정라인 y좌표) 
	 *    ==> y 초기좌표 = 580 - NOTE_SPEED*1000/SLEEP_TIME
	*/
	
	private String noteType;
	
	// <생성자> - 변수 초기화 
	public Note(int x, String noteType) { 
		this.x = x; // x : note가 만들어질 위치  
		// 생성될 때 y는 고정이므로 생성자 만들어줄 필요 X
		this.noteType = noteType;
	}
	
	// <note 이미지 그리는 함수> 
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("short")) {
			g.drawImage(noteBasicImage, x, y, null);
		}
		else if(noteType.equals("long")) {
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x+100, y, null);
		}
	}
	
	// <note 떨어뜨리는 함수>
	public void drop(){
		y += Main.NOTE_SPEED;
	}
	
	// <스레드 실행 함수>
	@Override
	public void run() { 
		try {
			while(true) { // 무한 반복해서 note 떨어뜨림 
				drop();
				Thread.sleep(Main.SLEEP_TIME); 
				// 한 번 note를 떨어뜨리고 SLEEP_TIME만큼 쉬고 그 다음 노트 떨어뜨린다. 
				// sleep은 ms(0.001초)를 기준으로 함 
				// ex) 만약 SLEEP_TIME을 10, NOTE_SPEED=7로 준다면 
				// ==> 0.01초에 7px 만큼 내려옴 ==> note는 1초에 700px 만큼 아래로 내려오게됨
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
