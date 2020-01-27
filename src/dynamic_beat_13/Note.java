package dynamic_beat_13;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread { // 각각의 note 또한 하나의 부분적인 기능으로써 떨어지는 역할을 수행해야 하므로 Thread로 만든다. 
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x;
	private int y = 580 - 1000 / Main.SLEEP_TIME * Main.NOTE_SPEED; 
	// note가 만들어진 지 1초 후에 판정라인에 닿도록. 
	// note speed=7, slee_time=10ms ==> note는 10ms에 7px만큼 이동 ==> note는 1초에 700px만큼 이동 
	// (변수명을 note_speed라고 주는게 부적절 한 것 같은데...)
	// note가 만들어진 지 1초 후에 판정라인에 닿게 하려면 판정라인(y=580)에서 1초에 이동하는 거리 700px을 빼서 y=-120에서 처음 생성되게 하자. 
	// ==> int y = 580 - 1000/10*7 = -120 
	
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
