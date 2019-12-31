package dynamic_beat_13;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread { // ������� ����� ����? ������ ��Ʈ ���� �ϳ��� �κ����� ������ν�, �������� ������ �����ؾ� �ϹǷ�  
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x,y = 580 - 1000 / Main.SLEEP_TIME * Main.NOTE_SPEED; // ��Ʈ�� �����ǰ� 1�� �ڿ� ��������(y=580)�� �����ϵ���
	private String noteType;
	
	public Note(int x, String noteType) { //������ �� y�� �����̹Ƿ� ������ ������� �ʿ� X
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
	
	public void drop(){ //��Ʈ�� �������� �Լ� 
		y += Main.NOTE_SPEED;
	}
	
	@Override
	public void run() { //�����尡 ����Ǵ� �Լ� 
		try {
			while(true) { //������ �ݺ��ؼ� ��Ʈ ����߸����� 
				drop();
				Thread.sleep(Main.SLEEP_TIME); 
				// �� �� note�� ����߸��� 0.01�ʸ�ŭ ���� �� ���� ��Ʈ ����߸���. 
				// sleep�� 0.001�ʸ� �������� �� ==> �츮�� final SLEEP_TIME�� 10, NOTE_SPEED=7�� ����  
				// ==> 0.01�ʿ� 7 ��ŭ ������ ==> ��Ʈ�� 1�ʿ� 700 pixel ��ŭ �Ʒ��� �������Ե�
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
