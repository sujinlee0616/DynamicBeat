// [작업 요약] 커스텀(디자인된) menuBar 생성 
package dynamic_beat_4;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {
	
	private Image screenImage;
	private Graphics screenGraphic;
	
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	
	
	private Image introBackground = new ImageIcon(Main.class.getResource("../images/introBackground(Title2).jpg")).getImage();
	// memnuBar를 이미지로 변경 
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private JButton exitButton = new JButton(exitButtonBasicImage);
	
	private int mouseX, mouseY;
	
	
	public DynamicBeat() {
		setUndecorated(true); // 기본 제공 menubBar 보이지 않도록 
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0,0,0,0)); // paintComponent를 했을 때 배경이 하얀색이 되도록 
		setLayout(null); 
		
		// exitButton 먼저 코딩해야 exitButton이 memuBar 보다 위에 올라옴 
		exitButton.setBounds(1245, 0, 30, 30); 
		
		// JButton이 기본적으로 제공하는 모양들을 없앰 
		exitButton.setBorderPainted(false); // 외곽선 없앰 
		exitButton.setContentAreaFilled(false); // 영역 채우기 없앰 
		exitButton.setFocusPainted(false); // JButton이 선택되었을 때 생기는 테두리 없앰 
		
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage); // exitButton의 이미지를 변경 
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false); //false: 무한반복X. 한 번만 재생.
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) { 
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3",false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitButton);
		
		menuBar.setBounds(0,0,1280,30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { // 마우스 눌렀을 때 
				mouseX = e.getX(); 
				mouseY = e.getY();
				// 마우스 눌렀을 때의 x좌표와 y좌표 값을 받아옴 
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { //드래그 이벤트가 발생했을 때 
				int x = e.getXOnScreen(); // 현재 스크린의 X좌표 
				int y = e.getYOnScreen(); // 현재 스크린의 Y좌표 
				setLocation(x - mouseX, y - mouseY); // 현재 게임창의 위치를 바꿔줌 
			}
		});
		// 즉, 메뉴바를 드래그해서 이동시키면 게임창 위치가 변경되도록 
		
		add(menuBar);
				
		Music introMusic = new Music("introMusic.mp3", true);
		introMusic.start();
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(introBackground, 0, 0, null);
		// background같이 단순한 이미지들은 g.drawImamge로 그려줌 
		paintComponents(g);
		// paintComponent : 이미지를 단순히 해당 screenImage라는 변수 안에 그려주는 것 이외에, 
		// 따로 JFrame안에 추가한 것들(ex. 여기서는 menuBar, JLabel)을 그려줌.
		// 항상 고정되어 있는 요소는 paintComponents 사용 
		this.repaint();
	}

}
