// Game 클래스 : Game 클래스에서 만들어진 instance 변수를 이용해서 게임을 컨트롤. 
package dynamic_beat_custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	// Thread : 하나의 프로그램 안에서 실행되는 작은 프로그램.
	// 즉, 전반적인 게임 틀 안에서 하나의 게임이 하나의 단위로써 동작 ==> Thread 이용 
	// 2. note 경로 구분선 이미지 
	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("../images/noteRouteLine.png")).getImage();
	// 3. 게임정보 및 4. 판정선 이미지
	private Image judgementLineImage = new ImageIcon(Main.class.getResource("../images/judgementLine.png")).getImage();
	private Image gameInfoImage = new ImageIcon(Main.class.getResource("../images/gameInfo.png")).getImage();
	// 1. note 경로 이미지 
	private Image noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// 8. 판정 이미지 - 판정 결과(Miss, Late, Good, Great, Perfect, Early) 및 판정결과 아래의 노란 불꽃 이미지 
	private Image yellowFlareImage;
	private Image judgeImage;
	// 9. 각 키별 키패드 눌렀을 때(빨간색으로), 뗐을 때 이미지 변경 (원래대로)
	private Image keyPadSImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadDImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadFImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadSpace1Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadSpace2Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadJImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadKImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadLImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	// 7. 곡정보 - 1) 곡 제목 , 3) 난이도 
	private String titleName;
	private String difficulty;
	private String musicTitle;
	// 게임 음악 
	private Music gameMusic;
	private int totalScore=0;
	
	ArrayList<Note> noteList = new ArrayList<Note>();
	
	//비트 찍어주려고 ..
	private boolean beatChecker=true;
	
	// <생성자> - 초기화 
	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);		
	}
	
	
	// 게임 화면에서 그려줘야 하는 요소들 
	public void screenDraw(Graphics2D g) {
		// 1. note 경로 이미지
		g.drawImage(noteRouteSImage, 228, 30, null);
		g.drawImage(noteRouteDImage, 332, 30, null);
		g.drawImage(noteRouteFImage, 436, 30, null);
		g.drawImage(noteRouteSpace1Image, 540, 30, null);
		g.drawImage(noteRouteSpace2Image, 640, 30, null);
		g.drawImage(noteRouteJImage, 744, 30, null);
		g.drawImage(noteRouteKImage, 848, 30, null);
		g.drawImage(noteRouteLImage, 952, 30, null);
		// 2. note 경로 구분선 이미지 
		g.drawImage(noteRouteLineImage, 224, 30, null);
		g.drawImage(noteRouteLineImage, 328, 30, null);
		g.drawImage(noteRouteLineImage, 432, 30, null);
		g.drawImage(noteRouteLineImage, 536, 30, null);
		g.drawImage(noteRouteLineImage, 740, 30, null);
		g.drawImage(noteRouteLineImage, 844, 30, null);
		g.drawImage(noteRouteLineImage, 948, 30, null);
		g.drawImage(noteRouteLineImage, 1052, 30, null);
		// 3. 게임정보 및  4. 판정선 이미지
		g.drawImage(gameInfoImage, 0, 660, null); // 게임 정보 이미지 - 가수, 곡명, 점수 등이 나오는 반투명 검정색 영역
		g.drawImage(judgementLineImage, 0, 580, null); // 판정선(빨간색 위아래 두 줄) 이미지
		// 5. note 이미지 - noteList(ArrayList)
		for(int i=0; i<noteList.size(); i++){  
			Note note = noteList.get(i);
			// Note.java에서 close가 이루어진 note는  더 이상 그려줄 필요가 없음
			// ==> note가 작동하고 있는 상태가 아니라면 noteList에서 지우고 i--해준다. 
			if(note.getY()>620) {
				judgeImage = new ImageIcon(Main.class.getResource("../images/judgeMiss.png")).getImage();
			}
			if(!note.isProceeded()) {
				noteList.remove(i);
				i--;
			}
			else {
				note.screenDraw(g);
			}
			// 노트들을 그려준다    
			// 나중에 그릴수록 레이어가 더 위에 올라옴 ==> 판정라인보다 노트가 더 위에 있어야 하니까 노트를 더 뒤에서 그려줌 
		}
		g.setColor(Color.white);
		// Antialiasing - 글자 안 깨지게.
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// 7. 곡정보 - 1) 곡 제목 
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(titleName, 20, 702);
		// 7. 곡정보 - 3) 난이도 
		g.drawString(difficulty, 1190, 702);
		// 6. 각각의 note가 키보드에서 어떤 키인지 알려주는 문구  
		g.setFont(new Font("Arial", Font.PLAIN, 26));
		g.setColor(Color.DARK_GRAY);
		g.drawString("S", 270, 609);
		g.drawString("D", 374, 609);
		g.drawString("F", 478, 609);
		g.drawString("Spcae Bar", 580, 609);
		g.drawString("J", 784, 609);
		g.drawString("K", 889, 609);
		g.drawString("L", 993, 609);
		// 7. 곡정보 - 2) 점수 
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(Integer.toString(totalScore), 600, 702);
		// 8. 판정 이미지 - 판정 결과(Miss, Late, Good, Great, Perfect, Early) 및 판정결과 아래의 노란 불꽃 이미지 
		g.drawImage(yellowFlareImage, 335, 330, null);
		g.drawImage(judgeImage, 460, 420, null);
		// 9. 각 키별 키패드 영역 - 디폴트 이미지는 투명. 아래 press함수에서 각 키가 눌러지면 keyPadPressed(빨간색)으로 변경한다.
		g.drawImage(keyPadSImage, 228, 580, null);
		g.drawImage(keyPadDImage, 332, 580, null);
		g.drawImage(keyPadFImage, 436, 580, null);
		g.drawImage(keyPadSpace1Image, 540, 580, null);
		g.drawImage(keyPadSpace2Image, 640, 580, null);
		g.drawImage(keyPadJImage, 744, 580, null);
		g.drawImage(keyPadKImage, 848, 580, null);
		g.drawImage(keyPadLImage, 952, 580, null);
	}
	
	public void pressS() { // S 키 눌렀을 때의 처리
		judge("S");
		score("S");
		noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// 효과음 삽입 
		new Music("drumSmall1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"S"+"\"),");
		}
	}
	public void releaseS() { // S 키 뗐을 때의 처리 
		noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}
	
	public void pressD() { 
		judge("D");
		score("D");
		noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		new Music("drumSmall1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"D"+"\"),");
		}
	}
	public void releaseD() { 
		noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}
	
	public void pressF() { 
		judge("F");
		score("F");
		noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		new Music("drumSmall1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"F"+"\"),");
		}
	}
	public void releaseF() {  
		noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}
	
	public void pressSpace() { 
		judge("Space");
		score("Space");
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		new Music("drumBig1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"Space"+"\"),");
		}
	}
	public void releaseSpace() { 
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}
	public void pressJ() { 
		judge("J");
		score("J");
		noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		new Music("drumSmall1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"J"+"\"),");
		}
	}
	public void releaseJ() { 
		noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressK() { 
		judge("K");
		score("K");
		noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		new Music("drumSmall1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"K"+"\"),");
		}
	}
	public void releaseK() { 
		noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}
	
	public void pressL() {  
		judge("L");
		score("L");
		noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		new Music("drumSmall1.mp3",false).start();
		if (beatChecker==true) {
			System.out.println("new Beat(startTime+gap*"+(gameMusic.getTime()-2000)/125+",\""+"L"+"\"),");
		}
	}
	public void releaseL() { 
		noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}
	
	// <run 함수> Game 클래스에서 만든 instance 변수는 run 메소드 안에 있는 내용을 실행한다. 
	@Override
	public void run() {
		dropNotes(this.titleName);
	}
	
	// <Game 쓰레드 종료 함수> 
	public void close() { 
		gameMusic.close();
		this.interrupt();
	}
	
	// <note 떨어뜨리는 함수>
	public void dropNotes(String titleName) { 
		Beat[] beats = null;
		/*
		 * Beat[] beats = { 
		 * new Beat(1000, "S"), // 1초에 S 떨어지게 
		 * new Beat(2000, "D"), //2초에 D 떨어지게 
		 * new Beat(3000, "F"), // 3초에 F 떨어지게 
		 * };
		 */
		// Beat 생성 - 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡1-1) Easy 
		if(titleName.equals("Joakim Karud - Mighty Love") && difficulty.equals("Easy") ) {
			int startTime = 4460 - Main.REACH_TIME*1000; 
			int gap = 125; // 125/1000 = 1/8 ==> 박자 계산을 위해서..
			beats = new Beat[] { // 배열 초기화 ==> 곡에 따라 비트 다르니까..
					new Beat(startTime+gap*1, "S"),
					new Beat(startTime+gap*3, "D"),
					new Beat(startTime+gap*5, "S"),
					new Beat(startTime+gap*7, "D"),
					new Beat(startTime+gap*9, "S"),
					new Beat(startTime+gap*11, "D"),
					new Beat(startTime+gap*13, "S"),
					new Beat(startTime+gap*15, "D"),
					new Beat(startTime+gap*18, "J"),
					new Beat(startTime+gap*20, "K"),
					new Beat(startTime+gap*22, "J"),
					new Beat(startTime+gap*24, "K"),
					new Beat(startTime+gap*26, "J"),
					new Beat(startTime+gap*28, "K"),
					new Beat(startTime+gap*30, "J"),
					new Beat(startTime+gap*32, "K"),
					new Beat(startTime+gap*35, "S"),
					new Beat(startTime+gap*37, "D"),
					new Beat(startTime+gap*39, "S"),
					new Beat(startTime+gap*41, "D"),
					new Beat(startTime+gap*43, "S"),
					new Beat(startTime+gap*45, "D"),
					new Beat(startTime+gap*48, "J"),
					new Beat(startTime+gap*49, "K"),
					new Beat(startTime+gap*50, "L"),
					new Beat(startTime+gap*52, "F"),
					new Beat(startTime+gap*52, "Space"),
					new Beat(startTime+gap*52, "J"),
					new Beat(startTime+gap*54, "S"),
					new Beat(startTime+gap*56, "D"),
					new Beat(startTime+gap*59, "F"),
					new Beat(startTime+gap*59, "Space"),
					new Beat(startTime+gap*59, "J"),
					new Beat(startTime+gap*61, "L"),
					new Beat(startTime+gap*63, "K"),
					new Beat(startTime+gap*65, "F"),
					new Beat(startTime+gap*65, "Space"),
					new Beat(startTime+gap*65, "J"),
					new Beat(startTime+gap*70, "S"),
					new Beat(startTime+gap*72, "S"),
					new Beat(startTime+gap*74, "S"),
					new Beat(startTime+gap*78, "J"),
					new Beat(startTime+gap*79, "K"),
					new Beat(startTime+gap*80, "L"),
					new Beat(startTime+gap*83, "Space"),
					new Beat(startTime+gap*85, "S"),
					new Beat(startTime+gap*87, "D"),
					new Beat(startTime+gap*89, "S"),
					new Beat(startTime+gap*91, "D"),
					new Beat(startTime+gap*93, "F"),
					new Beat(startTime+gap*96, "Space"),
					new Beat(startTime+gap*98, "L"),
					new Beat(startTime+gap*100, "Space"),
					new Beat(startTime+gap*102, "S"),
					new Beat(startTime+gap*104, "D"),
					new Beat(startTime+gap*106, "Space"),
					new Beat(startTime+gap*109, "Space"),
					new Beat(startTime+gap*112, "Space"),
					new Beat(startTime+gap*118, "S"),
					new Beat(startTime+gap*119, "D"),
					new Beat(startTime+gap*120, "F"),
					new Beat(startTime+gap*123, "S"),
					new Beat(startTime+gap*124, "D"),
					new Beat(startTime+gap*125, "F"),
					new Beat(startTime+gap*126, "J"),
					new Beat(startTime+gap*127, "K"),
					new Beat(startTime+gap*130, "J"),
					new Beat(startTime+gap*133, "K"),
					new Beat(startTime+gap*136, "L"),
					new Beat(startTime+gap*138, "S"),
					new Beat(startTime+gap*140, "Space"),
					new Beat(startTime+gap*142, "S"),
					new Beat(startTime+gap*144, "Space"),
					new Beat(startTime+gap*146, "Space"),
					new Beat(startTime+gap*150, "Space"),
					new Beat(startTime+gap*152, "Space"),
					new Beat(startTime+gap*157, "J"),
					new Beat(startTime+gap*161, "K"),
					new Beat(startTime+gap*165, "L"),
					new Beat(startTime+gap*167, "S"),
					new Beat(startTime+gap*169, "D"),
					new Beat(startTime+gap*171, "F"),
					new Beat(startTime+gap*174, "S"),
					new Beat(startTime+gap*176, "D"),
					new Beat(startTime+gap*178, "F"),
					new Beat(startTime+gap*180, "Space"),
					new Beat(startTime+gap*181, "L"),
					new Beat(startTime+gap*184, "Space"),
					new Beat(startTime+gap*187, "L"),
					new Beat(startTime+gap*188, "K"),
					new Beat(startTime+gap*189, "J"),
					new Beat(startTime+gap*192, "S"),
					new Beat(startTime+gap*192, "Space"),
					new Beat(startTime+gap*196, "D"),
					new Beat(startTime+gap*196, "Space"),
					new Beat(startTime+gap*200, "S"),
					new Beat(startTime+gap*200, "Space"),
					new Beat(startTime+gap*207, "J"),
					new Beat(startTime+gap*207, "Space"),
					new Beat(startTime+gap*211, "K"),
					new Beat(startTime+gap*211, "Space"),
					new Beat(startTime+gap*216, "L"),
					new Beat(startTime+gap*216, "Space"),
					new Beat(startTime+gap*218, "Space"),
					new Beat(startTime+gap*221, "J"),
					new Beat(startTime+gap*223, "K"),
					new Beat(startTime+gap*225, "L"),
					new Beat(startTime+gap*227, "S"),
					new Beat(startTime+gap*227, "Space"),
					new Beat(startTime+gap*231, "D"),
					new Beat(startTime+gap*231, "Space"),
					new Beat(startTime+gap*235, "S"),
					new Beat(startTime+gap*235, "Space"),
					new Beat(startTime+gap*242, "S"),
					new Beat(startTime+gap*242, "Space"),
					new Beat(startTime+gap*242, "L"),
					new Beat(startTime+gap*246, "D"),
					new Beat(startTime+gap*246, "Space"),
					new Beat(startTime+gap*246, "K"),
					new Beat(startTime+gap*250, "F"),
					new Beat(startTime+gap*250, "Space"),
					new Beat(startTime+gap*250, "J"),
					new Beat(startTime+gap*255, "J"),
					new Beat(startTime+gap*257, "K"),
					new Beat(startTime+gap*259, "K"),
					new Beat(startTime+gap*262, "S"),
					new Beat(startTime+gap*262, "Space"),
					new Beat(startTime+gap*266, "D"),
					new Beat(startTime+gap*266, "Space"),
					new Beat(startTime+gap*270, "S"),
					new Beat(startTime+gap*270, "Space"),
					new Beat(startTime+gap*275, "J"),
					new Beat(startTime+gap*277, "K"),
					new Beat(startTime+gap*279, "L"),
					new Beat(startTime+gap*282, "J"),
					new Beat(startTime+gap*284, "K"),
					new Beat(startTime+gap*286, "L"),
					new Beat(startTime+gap*289, "J"),
					new Beat(startTime+gap*291, "K"),
					new Beat(startTime+gap*293, "L"),
					new Beat(startTime+gap*295, "J"),
					new Beat(startTime+gap*297, "F"),
					new Beat(startTime+gap*299, "D"),
					new Beat(startTime+gap*301, "S"),
					new Beat(startTime+gap*304, "F"),
					new Beat(startTime+gap*306, "D"),
					new Beat(startTime+gap*308, "S"),
					new Beat(startTime+gap*310, "F"),
					new Beat(startTime+gap*312, "D"),
					new Beat(startTime+gap*314, "S"),
					new Beat(startTime+gap*317, "F"),
					new Beat(startTime+gap*319, "D"),
					new Beat(startTime+gap*321, "S"),
					new Beat(startTime+gap*323, "F"),
					new Beat(startTime+gap*325, "D"),
					new Beat(startTime+gap*327, "S"),
					new Beat(startTime+gap*330, "F"),
					new Beat(startTime+gap*332, "S"),
					new Beat(startTime+gap*332, "Space"),
					new Beat(startTime+gap*336, "D"),
					new Beat(startTime+gap*336, "Space"),
					new Beat(startTime+gap*340, "S"),
					new Beat(startTime+gap*340, "Space")					
			};
		}
		// 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡1-2) Hard
		else if(titleName.equals("Joakim Karud - Mighty Love") && difficulty.equals("Hard")) {
			// 첫 음 시작 시간 : 4460ms. REACH_TIME은 s니까 ms로 바꿔주기 위해서 *1000했음.
			int startTime = 4460 - Main.REACH_TIME*1000;
			int gap = 125;
			beats = new Beat[] {
					new Beat(startTime+gap*1, "S"),
					new Beat(startTime+gap*3, "D"),
					new Beat(startTime+gap*5, "S"),
					new Beat(startTime+gap*7, "D"),
					new Beat(startTime+gap*9, "S"),
					new Beat(startTime+gap*11, "D"),
					new Beat(startTime+gap*13, "S"),
					new Beat(startTime+gap*15, "D"),
					new Beat(startTime+gap*18, "J"),
					new Beat(startTime+gap*20, "K"),
					new Beat(startTime+gap*22, "J"),
					new Beat(startTime+gap*24, "K"),
					new Beat(startTime+gap*26, "J"),
					new Beat(startTime+gap*28, "K"),
					new Beat(startTime+gap*30, "J"),
					new Beat(startTime+gap*32, "K"),
					new Beat(startTime+gap*35, "S"),
					new Beat(startTime+gap*37, "D"),
					new Beat(startTime+gap*39, "S"),
					new Beat(startTime+gap*41, "D"),
					new Beat(startTime+gap*43, "S"),
					new Beat(startTime+gap*45, "D"),
					new Beat(startTime+gap*48, "J"),
					new Beat(startTime+gap*49, "K"),
					new Beat(startTime+gap*50, "L"),
					new Beat(startTime+gap*52, "F"),
					new Beat(startTime+gap*52, "Space"),
					new Beat(startTime+gap*52, "J"),
					new Beat(startTime+gap*54, "S"),
					new Beat(startTime+gap*56, "D"),
					new Beat(startTime+gap*59, "F"),
					new Beat(startTime+gap*59, "Space"),
					new Beat(startTime+gap*59, "J"),
					new Beat(startTime+gap*61, "L"),
					new Beat(startTime+gap*63, "K"),
					new Beat(startTime+gap*65, "F"),
					new Beat(startTime+gap*65, "Space"),
					new Beat(startTime+gap*65, "J"),
					new Beat(startTime+gap*70, "S"),
					new Beat(startTime+gap*72, "S"),
					new Beat(startTime+gap*74, "S"),
					new Beat(startTime+gap*78, "J"),
					new Beat(startTime+gap*79, "K"),
					new Beat(startTime+gap*80, "L"),
					new Beat(startTime+gap*83, "Space"),
					new Beat(startTime+gap*85, "S"),
					new Beat(startTime+gap*87, "D"),
					new Beat(startTime+gap*89, "S"),
					new Beat(startTime+gap*91, "D"),
					new Beat(startTime+gap*93, "F"),
					new Beat(startTime+gap*96, "Space"),
					new Beat(startTime+gap*98, "L"),
					new Beat(startTime+gap*100, "Space"),
					new Beat(startTime+gap*102, "S"),
					new Beat(startTime+gap*104, "D"),
					new Beat(startTime+gap*106, "Space"),
					new Beat(startTime+gap*109, "Space"),
					new Beat(startTime+gap*112, "Space"),
					new Beat(startTime+gap*118, "S"),
					new Beat(startTime+gap*119, "D"),
					new Beat(startTime+gap*120, "F"),
					new Beat(startTime+gap*123, "S"),
					new Beat(startTime+gap*124, "D"),
					new Beat(startTime+gap*125, "F"),
					new Beat(startTime+gap*126, "J"),
					new Beat(startTime+gap*127, "K"),
					new Beat(startTime+gap*130, "J"),
					new Beat(startTime+gap*133, "K"),
					new Beat(startTime+gap*136, "L"),
					new Beat(startTime+gap*138, "S"),
					new Beat(startTime+gap*140, "Space"),
					new Beat(startTime+gap*142, "S"),
					new Beat(startTime+gap*144, "Space"),
					new Beat(startTime+gap*146, "Space"),
					new Beat(startTime+gap*150, "Space"),
					new Beat(startTime+gap*152, "Space"),
					new Beat(startTime+gap*157, "J"),
					new Beat(startTime+gap*161, "K"),
					new Beat(startTime+gap*165, "L"),
					new Beat(startTime+gap*167, "S"),
					new Beat(startTime+gap*169, "D"),
					new Beat(startTime+gap*171, "F"),
					new Beat(startTime+gap*174, "S"),
					new Beat(startTime+gap*176, "D"),
					new Beat(startTime+gap*178, "F"),
					new Beat(startTime+gap*180, "Space"),
					new Beat(startTime+gap*181, "L"),
					new Beat(startTime+gap*184, "Space"),
					new Beat(startTime+gap*187, "L"),
					new Beat(startTime+gap*188, "K"),
					new Beat(startTime+gap*189, "J"),
					new Beat(startTime+gap*192, "S"),
					new Beat(startTime+gap*192, "Space"),
					new Beat(startTime+gap*196, "D"),
					new Beat(startTime+gap*196, "Space"),
					new Beat(startTime+gap*200, "S"),
					new Beat(startTime+gap*200, "Space"),
					new Beat(startTime+gap*207, "J"),
					new Beat(startTime+gap*207, "Space"),
					new Beat(startTime+gap*211, "K"),
					new Beat(startTime+gap*211, "Space"),
					new Beat(startTime+gap*216, "L"),
					new Beat(startTime+gap*216, "Space"),
					new Beat(startTime+gap*218, "Space"),
					new Beat(startTime+gap*221, "J"),
					new Beat(startTime+gap*223, "K"),
					new Beat(startTime+gap*225, "L"),
					new Beat(startTime+gap*227, "S"),
					new Beat(startTime+gap*227, "Space"),
					new Beat(startTime+gap*231, "D"),
					new Beat(startTime+gap*231, "Space"),
					new Beat(startTime+gap*235, "S"),
					new Beat(startTime+gap*235, "Space"),
					new Beat(startTime+gap*242, "S"),
					new Beat(startTime+gap*242, "Space"),
					new Beat(startTime+gap*242, "L"),
					new Beat(startTime+gap*246, "D"),
					new Beat(startTime+gap*246, "Space"),
					new Beat(startTime+gap*246, "K"),
					new Beat(startTime+gap*250, "F"),
					new Beat(startTime+gap*250, "Space"),
					new Beat(startTime+gap*250, "J"),
					new Beat(startTime+gap*255, "J"),
					new Beat(startTime+gap*257, "K"),
					new Beat(startTime+gap*259, "K"),
					new Beat(startTime+gap*262, "S"),
					new Beat(startTime+gap*262, "Space"),
					new Beat(startTime+gap*266, "D"),
					new Beat(startTime+gap*266, "Space"),
					new Beat(startTime+gap*270, "S"),
					new Beat(startTime+gap*270, "Space"),
					new Beat(startTime+gap*275, "J"),
					new Beat(startTime+gap*277, "K"),
					new Beat(startTime+gap*279, "L"),
					new Beat(startTime+gap*282, "J"),
					new Beat(startTime+gap*284, "K"),
					new Beat(startTime+gap*286, "L"),
					new Beat(startTime+gap*289, "J"),
					new Beat(startTime+gap*291, "K"),
					new Beat(startTime+gap*293, "L"),
					new Beat(startTime+gap*295, "J"),
					new Beat(startTime+gap*297, "F"),
					new Beat(startTime+gap*299, "D"),
					new Beat(startTime+gap*301, "S"),
					new Beat(startTime+gap*304, "F"),
					new Beat(startTime+gap*306, "D"),
					new Beat(startTime+gap*308, "S"),
					new Beat(startTime+gap*310, "F"),
					new Beat(startTime+gap*312, "D"),
					new Beat(startTime+gap*314, "S"),
					new Beat(startTime+gap*317, "F"),
					new Beat(startTime+gap*319, "D"),
					new Beat(startTime+gap*321, "S"),
					new Beat(startTime+gap*323, "F"),
					new Beat(startTime+gap*325, "D"),
					new Beat(startTime+gap*327, "S"),
					new Beat(startTime+gap*330, "F"),
					new Beat(startTime+gap*332, "S"),
					new Beat(startTime+gap*332, "Space"),
					new Beat(startTime+gap*336, "D"),
					new Beat(startTime+gap*336, "Space"),
					new Beat(startTime+gap*340, "S"),
					new Beat(startTime+gap*340, "Space")
			};
		}
		// 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡2-1) Easy
		else if(titleName.equals("Bensound - Energy") && difficulty.equals("Easy")) {
			// 첫 음 시작 시간 : 2000ms
			int startTime = 2000 - Main.REACH_TIME*1000;
			System.out.println("startTime="+startTime);
			int gap = 125;
			beats = new Beat[] { // pattern: +4,+6,+2,+1,+3
					new Beat(startTime+gap*3,"J"), // 1
					new Beat(startTime+gap*7,"K"),
					new Beat(startTime+gap*13,"K"),
					new Beat(startTime+gap*15,"K"),
					new Beat(startTime+gap*16,"K"), 
					new Beat(startTime+gap*19,"J"), //2
					new Beat(startTime+gap*23,"K"),
					new Beat(startTime+gap*29,"K"),
					new Beat(startTime+gap*31,"K"),
					new Beat(startTime+gap*32,"K"), 
					new Beat(startTime+gap*35,"J"), // 3
					new Beat(startTime+gap*39,"K"),
					new Beat(startTime+gap*45,"K"),
					new Beat(startTime+gap*47,"K"),
					new Beat(startTime+gap*48,"K"), 
					new Beat(startTime+gap*51,"J"), // 4
					new Beat(startTime+gap*55,"K"),
					new Beat(startTime+gap*61,"K"),
					new Beat(startTime+gap*63,"K"),
					new Beat(startTime+gap*64,"K"), 
					new Beat(startTime+gap*67,"J"), // 5 - 새로운 음 섞임 (왼손)
					new Beat(startTime+gap*67,"S"), 
					new Beat(startTime+gap*71,"K"),
					new Beat(startTime+gap*71,"D"),
					new Beat(startTime+gap*77,"K"),
					new Beat(startTime+gap*77,"D"),
					new Beat(startTime+gap*79,"K"),
					new Beat(startTime+gap*79,"D"),
					new Beat(startTime+gap*80,"K"), 
					new Beat(startTime+gap*80,"D"), 
					new Beat(startTime+gap*83,"J"), // 6 - 새로운 음 섞임 (왼손) 
					new Beat(startTime+gap*83,"S"),
					new Beat(startTime+gap*87,"K"),
					new Beat(startTime+gap*87,"D"),
					new Beat(startTime+gap*93,"K"),
					new Beat(startTime+gap*93,"D"),
					new Beat(startTime+gap*95,"K"),
					new Beat(startTime+gap*95,"D"),
					new Beat(startTime+gap*96,"K"), 
					new Beat(startTime+gap*96,"D"), 
					new Beat(startTime+gap*99,"J"), // 7 
					new Beat(startTime+gap*103,"K"),
					new Beat(startTime+gap*109,"K"),
					new Beat(startTime+gap*111,"K"),
					new Beat(startTime+gap*112,"K"),
					new Beat(startTime+gap*115,"J"), // 8
					new Beat(startTime+gap*119,"K"),
					new Beat(startTime+gap*125,"K"),
					new Beat(startTime+gap*127,"K"),
					new Beat(startTime+gap*128,"K"),
					new Beat(startTime+gap*131,"J"), // 9
					new Beat(startTime+gap*135,"K"),
					new Beat(startTime+gap*141,"K"),
					new Beat(startTime+gap*143,"K"),
					new Beat(startTime+gap*144,"K"),
					new Beat(startTime+gap*147,"J"), // 10
					new Beat(startTime+gap*151,"K"),
					new Beat(startTime+gap*157,"K"),
					new Beat(startTime+gap*159,"K"),
					new Beat(startTime+gap*160,"K"),
					new Beat(startTime+gap*163,"J"), // 11
					new Beat(startTime+gap*167,"K"),
					new Beat(startTime+gap*173,"K"),
					new Beat(startTime+gap*175,"K"),
					new Beat(startTime+gap*176,"K"),
					new Beat(startTime+gap*179,"J"), // 12
					new Beat(startTime+gap*183,"K"),
					new Beat(startTime+gap*189,"K"),
					new Beat(startTime+gap*191,"K"),
					new Beat(startTime+gap*192,"K"),
					new Beat(startTime+gap*195,"J"), // 13 - 새로운 음 섞임 (왼손) - 여기부터 손봐야..
					new Beat(startTime+gap*197,"D"), 	//왼1
					new Beat(startTime+gap*199,"K"),
					new Beat(startTime+gap*200,"S"), 	//왼2
					new Beat(startTime+gap*205,"K"),
					new Beat(startTime+gap*207,"K"),
					new Beat(startTime+gap*207,"D"), 	//왼3
					new Beat(startTime+gap*208,"K"), 
					new Beat(startTime+gap*209,"D"), 	//왼4
					new Beat(startTime+gap*210,"D"), 	//왼5
					new Beat(startTime+gap*211,"J"), // 14
					new Beat(startTime+gap*213,"D"),	//왼1
					new Beat(startTime+gap*215,"K"),
					new Beat(startTime+gap*216,"S"),	//왼2
					new Beat(startTime+gap*221,"K"),
					new Beat(startTime+gap*223,"K"),
					new Beat(startTime+gap*223,"D"), 	//왼3
					new Beat(startTime+gap*224,"K"), 
					new Beat(startTime+gap*225,"D"),	//왼4
					new Beat(startTime+gap*226,"D"),	//왼5
					new Beat(startTime+gap*227,"J"), // 15
					new Beat(startTime+gap*228,"S"),	//왼1
					new Beat(startTime+gap*231,"K"),
					new Beat(startTime+gap*232,"S"),	//왼2
					new Beat(startTime+gap*233,"D"),	//왼3
					new Beat(startTime+gap*237,"K"),
					new Beat(startTime+gap*239,"K"),
					new Beat(startTime+gap*239,"D"),	//왼4
					new Beat(startTime+gap*240,"K"), 
					new Beat(startTime+gap*241,"D"),	//왼5
					new Beat(startTime+gap*242,"D"),	//왼6
					new Beat(startTime+gap*246,"D"),	//왼7
					new Beat(startTime+gap*249,"J"), // 16 
					new Beat(startTime+gap*249,"S"),	//왼1
					new Beat(startTime+gap*251,"Space"),
					new Beat(startTime+gap*256,"S"),	//왼2
					new Beat(startTime+gap*257,"S"),	//왼3
					new Beat(startTime+gap*257,"J"),
					new Beat(startTime+gap*259,"S"),	//왼4
					new Beat(startTime+gap*262,"K"),
					new Beat(startTime+gap*262,"F"),	//왼5
					new Beat(startTime+gap*265,"J"), // 17
					new Beat(startTime+gap*265,"D"),	//왼1
					new Beat(startTime+gap*272,"K"),
					new Beat(startTime+gap*273,"K"),
					new Beat(startTime+gap*274,"K"),
					new Beat(startTime+gap*277,"K"),
					new Beat(startTime+gap*281,"J"), // 18
					new Beat(startTime+gap*288,"K"),
					new Beat(startTime+gap*289,"K"),
					new Beat(startTime+gap*290,"K"),
					new Beat(startTime+gap*294,"J"),
					new Beat(startTime+gap*297,"K"),
					new Beat(startTime+gap*304,"K"),
					new Beat(startTime+gap*305,"K"),
					new Beat(startTime+gap*306,"K"),
					new Beat(startTime+gap*310,"K"),
					new Beat(startTime+gap*313,"J"),
					new Beat(startTime+gap*315,"Space"),
					new Beat(startTime+gap*320,"J"),
					new Beat(startTime+gap*326,"K"),
					new Beat(startTime+gap*330,"J"),
					new Beat(startTime+gap*336,"K"),
					new Beat(startTime+gap*337,"K"),
					new Beat(startTime+gap*338,"K"),
					new Beat(startTime+gap*342,"K"),
					new Beat(startTime+gap*346,"J"),
					new Beat(startTime+gap*352,"K"),
					new Beat(startTime+gap*354,"K"),
					new Beat(startTime+gap*354,"K"),
					new Beat(startTime+gap*357,"J"),
					new Beat(startTime+gap*362,"K"),
					new Beat(startTime+gap*369,"K"),
					new Beat(startTime+gap*370,"K"),
					new Beat(startTime+gap*371,"K"),
					new Beat(startTime+gap*374,"J"),
					new Beat(startTime+gap*378,"Space"),
					new Beat(startTime+gap*385,"K"),
					new Beat(startTime+gap*387,"K"),
					new Beat(startTime+gap*391,"K"),
					new Beat(startTime+gap*394,"J"),
					new Beat(startTime+gap*401,"K"),
					new Beat(startTime+gap*402,"K"),
					new Beat(startTime+gap*403,"K"),
					new Beat(startTime+gap*406,"K"),
					new Beat(startTime+gap*410,"J"),
					new Beat(startTime+gap*417,"K"),
					new Beat(startTime+gap*418,"K"),
					new Beat(startTime+gap*419,"K"),
					new Beat(startTime+gap*422,"J"),
					new Beat(startTime+gap*426,"K"),
					new Beat(startTime+gap*433,"K"),
					new Beat(startTime+gap*434,"K"),
					new Beat(startTime+gap*438,"K"),
					new Beat(startTime+gap*442,"J"),
					new Beat(startTime+gap*444,"Space"),
					new Beat(startTime+gap*449,"J"),
					new Beat(startTime+gap*455,"K"),
					new Beat(startTime+gap*459,"S"),
					new Beat(startTime+gap*465,"K"),
					new Beat(startTime+gap*466,"K"),
					new Beat(startTime+gap*468,"K"),
					new Beat(startTime+gap*471,"J"),
					new Beat(startTime+gap*475,"D"),
					new Beat(startTime+gap*481,"K"),
					new Beat(startTime+gap*482,"K"),
					new Beat(startTime+gap*483,"K"),
					new Beat(startTime+gap*487,"D"),
					new Beat(startTime+gap*491,"J"),
					new Beat(startTime+gap*498,"K"),
					new Beat(startTime+gap*499,"K"),
					new Beat(startTime+gap*500,"K"),
					new Beat(startTime+gap*504,"K"),
					new Beat(startTime+gap*507,"J"),
					new Beat(startTime+gap*509,"Space"),
					new Beat(startTime+gap*513,"J"),
					new Beat(startTime+gap*519,"S"),
					new Beat(startTime+gap*519,"K"),
					new Beat(startTime+gap*523,"J"),
					new Beat(startTime+gap*529,"K"),
					new Beat(startTime+gap*530,"K"),
					new Beat(startTime+gap*532,"K"),
					new Beat(startTime+gap*535,"K"),
					new Beat(startTime+gap*539,"J"),
					new Beat(startTime+gap*545,"K"),
					new Beat(startTime+gap*547,"K"),
					new Beat(startTime+gap*548,"K"),
					new Beat(startTime+gap*552,"S"),
					new Beat(startTime+gap*552,"K"),
					new Beat(startTime+gap*556,"K"),
					new Beat(startTime+gap*562,"K"),
					new Beat(startTime+gap*563,"K"),
					new Beat(startTime+gap*567,"K"),
					new Beat(startTime+gap*572,"J"),
					new Beat(startTime+gap*574,"Space"),
					new Beat(startTime+gap*578,"J"),
						
			};
		}
		// 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡2-2) Hard
		else if(titleName.equals("Bensound - Energy") && difficulty.equals("Hard")) {
			int startTime = 1000 - Main.REACH_TIME*1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
			};
		}
		// 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡2-1) Easy
		
		// 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡3-1) Easy
		else if(titleName.equals("Joakim Karud - Wild Flower") && difficulty.equals("Easy")) {
			int startTime = 1000 - Main.REACH_TIME*1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
			};
		}
		// 곡별로 음표와 시간 설정 (like 악보 그리기) - 곡3-2) Hard
		else if(titleName.equals("Joakim Karud - Wild Flower") && difficulty.equals("Hard")) {
			int startTime = 1000 - Main.REACH_TIME*1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
			};
		}
		/*
		 * Beat[] beats = { new Beat(1000, "S"), // 1초에 S 떨어지게 new Beat(2000, "D"), //
		 * 2초에 D 떨어지게 new Beat(3000, "F"), // 3초에 F 떨어지게 };
		 */
		// note 생성
		int i=0;
		gameMusic.start();
		while(i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if(beats[i].getTime() <= gameMusic.getTime()) { // 비트가 떨어지는 시간대가 gameMusic의 시간보다 작다면 (곡이 진행되고 있다면)  
				Note note = new Note(beats[i].getNoteName());  // 노트 만든다
				note.start();
				noteList.add(note);
				i++;
			}
			if(!dropped) {
				try {
					Thread.sleep(5); //5ms 쉬도록. 
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// note가 떨어지지 않은 경우에는 무한정 반복하는 것이 아니라, 텀을 두면서 note를 떨어뜨림
		// ==> 더 효율적. 자원의 낭비를 줄일 수 있음. 애니메이션이 더 매끄럽게 나온다. 
	}
	
	//<판정 함수> - 유저가 입력한 키가 실제 note의 키와 동일하면 Note.java의 judge함수에서 값을 받아서 judgeEvent 함수에 넘긴다. 
	public void judge(String input) {
		for(int i=0; i<noteList.size(); i++) {
			Note note = noteList.get(i);
			if(input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
		}
	}
	
	//<판정 이벤트 함수> - Note.java의 judge결과에 따라 이미지를 노출한다. 
	public void judgeEvent(String judge) {
		if(!judge.equals("None")) { // 판정 결과가 none이 아니라면 노란불꽃 이미지 넣어준다 
			yellowFlareImage = new ImageIcon(Main.class.getResource("../images/yellowFlare.png")).getImage();
		}
		if(judge.equals("Miss")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeMiss.png")).getImage();
		}
		else if(judge.equals("Late")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeLate.png")).getImage();
		}
		else if(judge.equals("Good")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeGood.png")).getImage();
		}
		else if(judge.equals("Great")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeGreat.png")).getImage();
		}
		else if(judge.equals("Perfect")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgePerfect.png")).getImage();
		}
		else if(judge.equals("Early")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeEarly.png")).getImage();
		}		
	}
	
	//<채점 함수> - 유저가 입력한 키가 실제 note의 키와 동일하면 Note.java의 score함수에서 값을 받아서 scoreEvent 함수에 넘긴다. 
	public void score(String input) {
		for(int i=0; i<noteList.size(); i++) {
			Note note = noteList.get(i);
			if(input.equals(note.getNoteType())) {
				scoreEvent(note.score());
				break;
			}
		}
	}
	
	//<채점 이벤트 함수> - Note.java의 judge결과에 따라 이미지를 노출한다. 
		public void scoreEvent(int score) {
			if(score==10) {
				totalScore+=10;
				//System.out.println("Total score="+totalScore);
			}
			else if(score==20) {
				totalScore+=20;
				//System.out.println("Total score="+totalScore);
			}
			else if(score==30) {
				totalScore+=30;
				//System.out.println("Total score="+totalScore);
			}
			else if(score==40) {
				totalScore+=40;
				//System.out.println("Total score="+totalScore);
			}
		}

}
