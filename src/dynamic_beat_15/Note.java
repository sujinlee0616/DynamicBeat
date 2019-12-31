package dynamic_beat_15;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread { // 스레드로 만드는 이유? 각각의 노트 또한 하나의 부분적인 기능으로써, 떨어지는 역할을 수행해야 하므로  
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x,y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) *Main.REACH_TIME; // 노트가 생성되고 1초 뒤에 판정라인(y=580)에 도착하도록
	private String noteType;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProcessed() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	public Note(String noteType) { //생성될 때 y는 고정이므로 생성자 만들어줄 필요 X
		if(noteType.equals("S")) {
			x=228;
		}
		else if(noteType.equals("D")) {
			x=332;
		}
		else if(noteType.equals("F")) {
			x=436;
		}
		else if(noteType.equals("Space")) {
			x=540;
		}
		else if(noteType.equals("J")) {
			x=744;
		}
		else if(noteType.equals("K")) {
			x=848;
		}
		else if(noteType.equals("L")) {
			x=952;
		}
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if(!noteType.equals("Space")) {
			g.drawImage(noteBasicImage, x, y, null);
		}else {
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x+100, y, null);
		}
	}
	
	public void drop(){ //노트가 떨어지는 함수 
		y += Main.NOTE_SPEED;
		if(y>620) {
			System.out.println("Miss");
			close();
		}
	}
	
	@Override
	public void run() { //스레드가 실행되는 함수 
		try {
			while(true) { //무한정 반복해서 노트 떨어뜨리도록 
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
					// 한 번 note를 떨어뜨리고 0.01초만큼 쉬고 그 다음 노트 떨어뜨린다. 
					// sleep은 0.001초를 기준으로 함 ==> 우리는 final SLEEP_TIME을 10, NOTE_SPEED=7로 줬음  
					// ==> 0.01초에 7 만큼 내려옴 ==> 노트는 1초에 700 pixel 만큼 아래로 내려오게됨
				}
				else {
					interrupt();
					break;
				}				
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void judge() {
		if(y>=613) {
			System.out.println("Late");
			close();
		}
		else if(y>=600) {
			System.out.println("Good");
			close();
		}
		else if(y>=587) {
			System.out.println("Great");
			close();
		}
		else if(y>=573) {
			System.out.println("Perfect");
			close();
		}
		else if(y>=565) {
			System.out.println("Great");
			close();
		}
		else if(y>=550) {
			System.out.println("Good");
			close();
		}
		else if(y>=535) {
			System.out.println("Early");
			close();
		}
	}

}
