package dynamic_beat_13;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread { // 스레드로 만드는 이유? 각각의 노트 또한 하나의 부분적인 기능으로써, 떨어지는 역할을 수행해야 하므로  
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x,y = 580 - 1000 / Main.SLEEP_TIME * Main.NOTE_SPEED; // 노트가 생성되고 1초 뒤에 판정라인(y=580)에 도착하도록
	private String noteType;
	
	public Note(int x, String noteType) { //생성될 때 y는 고정이므로 생성자 만들어줄 필요 X
		this.x = x;
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("short")) {
			g.drawImage(noteBasicImage, x, y, null);
		}else if(noteType.equals("long")) {
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x+100, y, null);
		}
	}
	
	public void drop(){ //노트가 떨어지는 함수 
		y += Main.NOTE_SPEED;
	}
	
	@Override
	public void run() { //스레드가 실행되는 함수 
		try {
			while(true) { //무한정 반복해서 노트 떨어뜨리도록 
				drop();
				Thread.sleep(Main.SLEEP_TIME); 
				// 한 번 note를 떨어뜨리고 0.01초만큼 쉬고 그 다음 노트 떨어뜨린다. 
				// sleep은 0.001초를 기준으로 함 ==> 우리는 final SLEEP_TIME을 10, NOTE_SPEED=7로 줬음  
				// ==> 0.01초에 7 만큼 내려옴 ==> 노트는 1초에 700 pixel 만큼 아래로 내려오게됨
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
