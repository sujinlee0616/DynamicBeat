// [작업 요약] 곡 선택 기능 추가 
package dynamic_beat_7;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;

	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../images/startButtonEntered.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../images/rightButtonEntered.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));

	private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title2).jpg"))
			.getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);

	private int mouseX, mouseY;

	private boolean isMainScreen = false;

	// ArrayList : 어떠한 변수들을 담을 수 있는, 하나의 이미 만들어진 배열
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	// 곡 선택 관련 변수 
	private Image titleImage;
	// 선택한 곡의 이미지/음악  ==> selectTrack 메서드(함수) 통해서 컨트롤 하겠음. ==> 코드의 함수화. 
	private Image selectedImage;
	private Music selectedMusic;
	
	private int	nowSelected = 0; // 현재 선택된 트랙의 번호  
	
	public DynamicBeat() {
		setUndecorated(true);
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		// 게임 켜면 introMusic 바로 나오게 
		Music introMusic = new Music("introMusic.mp3", true);
		introMusic.start();
		
		// <곡 리스트> - trackList에 add ==> 각각의 곡들이 순서대로 ArrayList에 담긴다. 
		trackList.add(new Track("Mighty Love Title Image.png", 
				"Mighty Love Start Image.png", "Mighty Love Game Image.png", 
				"Mighty Love Selected.mp3", "Joakim Karud - Mighty Love.mp3"));
		trackList.add(new Track("Wild Flower Title Image.png", 
				"Wild Flower Start Image.png", "Wild Flower Game Image.png", 
				"Wild Flower Selected.mp3", "Wild Flower - Joakim Karud.mp3"));
		trackList.add(new Track("Energy Title Image.png", 
				"Energy Start Image.png", "Energy Game Image.png", 
				"Energy Selected.mp3", "Bensound - Energy.mp3"));

		// exitButton
		exitButton.setBounds(1245, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitButton);

		// startButton
		startButton.setBounds(40, 200, 300, 75);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// <시작 버튼 이벤트> - 인트로 화면 → 메인화면  
				introMusic.close(); // intro 음악 중지 
				selectTrack(0); // track을 0으로 설정 ==> 맨 첫 곡 실행되게끔. 
				// 인트로 화면에 있던 start/quit 버튼 안 보이게 만들고 
				startButton.setVisible(false); 
				quitButton.setVisible(false);
				// 메인 화면에서 좌/우 이동 버튼 보이게 
				leftButton.setVisible(true);
				rightButton.setVisible(true);
				// 메인화면 배경 이미지로 변경하고 isMain을 true로 변경 
				background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
				isMainScreen = true;
			}
		});
		add(startButton);

		// quitButton
		quitButton.setBounds(40, 300, 300, 75);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton);

		// leftButton
		leftButton.setVisible(false);
		leftButton.setBounds(140, 310, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 왼쪽 버튼 이벤트 
				selectLeft(); // selectLeft 함수(메서드) 실행 ==> 객체지향
			}
		});
		add(leftButton);
		
		// rightButton
		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 60, 60);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 오른쪽 버튼 이벤트 
				selectRight(); //selectRight 메서드(함수) 실행 ==> 객체지향 
			}
		});
		add(rightButton);

		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);


	}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if (isMainScreen) {
			g.drawImage(selectedImage, 340, 100, null);
			g.drawImage(titleImage, 340, 110, null);
		}
		paintComponents(g);
		this.repaint();
	}
	
	// 
	// 위에서 정의한 selectedImage와 selectedMusic을 컨트롤 
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null)
			selectedMusic.close(); // 이미 어떤 곡이 실행되고 있었다면 실행되고 있던 곡을 종료시킴 
		// 선택한 곡 번호를 받아서 그 곡의 정보를 trackList라는 ArrayList에서 받아온다.
		// trackList는 Track 클래스 파일에서 알 수 있듯이 titleImage, startImage, gameImage, startMusic, gameMusic를 가지고 있음. 
		titleImage = new ImageIcon(Main.class.getResource("../images/"+trackList.get(nowSelected).getTitleImage()))
				.getImage(); // 타이틀 이미지를 선택한 곡 껄로 변경  
		selectedImage = new ImageIcon(Main.class.getResource("../images/"+trackList.get(nowSelected).getStartImage()))
				.getImage(); // 선택된 이미지 변경
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(),true); //true : 음악 무한반복 
		selectedMusic.start();  // 선택한 음악 재생 
	}
	
	// leftButton 클릭 시 이벤트 처리 
	public void selectLeft() {
		// 0번째 곡일때 왼쪽 버튼을 누르면 가장 마지막 곡이 나오도록.  
		if(nowSelected == 0)
			nowSelected = trackList.size() -1;
		else 
			nowSelected --;
		selectTrack(nowSelected);
	}
	
	// leftButton 클릭 시  이벤트 처리 
		public void selectRight() {
			// 제일 마지막 곡일 때 오른쪽 버튼을 누르면 가장 첫번째 곡이 나오도록. 
			if(nowSelected == trackList.size()-1)
				nowSelected = 0;
			else 
				nowSelected ++;
			selectTrack(nowSelected);
		}

}


