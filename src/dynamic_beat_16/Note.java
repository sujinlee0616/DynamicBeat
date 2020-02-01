// Note 클래스 : 하나의 떨어지는 note를 클래스화해서 다루기 위한 것.
package dynamic_beat_16;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread { // 각각의 note 또한 하나의 부분적인 기능으로써 떨어지는 역할을 수행해야 하므로 Thread로 만든다. 
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x,y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) *Main.REACH_TIME; 
	/*
	 * y 초기값 : note의 속도와 note가 떨어지는 주기, 노트가 생성된 이후 판정바에 도달하는 시간을 고려하여 y값을 잡아줘야한다. 
	 * y 초기값을 상수로 잡지 않고 수식으로 만들어 놓는 이유는, 
	 * 이렇게 해야 곡/난이도에 따라 REACH_TIME, NOTE_SPPED, SLEEP_TIME을 변경해도 y초기값에 자동 반영되기 때문이다. 
	 */
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
	 * 2. note가 REACH_TIME(s)동안 움직이는 거리 
	 *    = (NOTE_SPEED*1000/SLEEP_TIME) * REACH_TIME
	 *   
	 * 3. note가 생성되고 REACH_TIME(s)후에 판정 라인에 닿도록 y 초기값 설정
	 *  - note가 생성되고 y초기좌표에서 REACH_TIME동안 이동한 거리가 판정라인 y좌표와 같아야
	 *    ==> y초기좌표 + (NOTE_SPEED*1000/SLEEP_TIME) * REACH_TIME = 580 (판정라인 y좌표) 
	 *    ==> y 초기좌표 = 580 - (NOTE_SPEED*1000/SLEEP_TIME) * REACH_TIME
	*/	
	private String noteType;
	private boolean proceeded = true; //현재 note의 진행여부 체크 
	
	// 노트 타입을 받아온다. 
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	// <생성자> - 변수 초기화 
	public Note(String noteType) {
		// note 종류(S,D,F,...)에 따른 x좌표 초기화
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
	
	// <note 이미지 그리는 함수> 
	public void screenDraw(Graphics2D g) {
		if(!noteType.equals("Space")) { //space가 아니면 한 번만 그리면 되는데 
			g.drawImage(noteBasicImage, x, y, null);
		}else { //space면 길게 그려야 하니까 이렇게 두 번 그려줌 
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x+100, y, null);
		}
	}
	
	// <note 떨어뜨리는 함수>
	public void drop(){ 
		y += Main.NOTE_SPEED;
		if(y>620) { // y값이 판정바 아래로 내려갔다면 
			System.out.println("Miss"); // Miss라고 판정 
			close();
		}
	}
	
	// <스레드 실행 함수>
	@Override
	public void run() { 
		try {
			while(true) { // 무한 반복해서 note 떨어뜨림 
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
					// 한 번 note를 떨어뜨리고 SLEEP_TIME만큼 쉬고 그 다음 노트 떨어뜨린다. 
					// sleep은 ms(0.001초)를 기준으로 함 
					// ex) 만약 SLEEP_TIME을 10, NOTE_SPEED=7로 준다면 
					// ==> 0.01초에 7px 만큼 내려옴 ==> note는 1초에 700px 만큼 아래로 내려오게됨
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
	
	//<판정함수>
	public String judge() {
		if(y>=613) {
			System.out.println("Late");
			close();
			return "Late";
		}
		else if(y>=600) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if(y>=587) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if(y>=573) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if(y>=565) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if(y>=550) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if(y>=535) {
			System.out.println("Early");
			close();
			return "Early";
		}
		return "None";
	}
	
	public int getY() { //현재의 y좌표 반환
		return y; 
	}

}
