package dynamic_beat_3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;
// 음악 삽입을 위해 javazoom.net에서 JLayer 다운받아 사용 - mp3 파일 사용 가능한 애. 
// Build Path > Java Build Path > Add External JARs > jl.1.0.1 추가 

public class Music extends Thread{
	// 스레드는 하나의 별도의 작은 프로그램
	private Player player;
	private boolean isLoop; // 곡 재생 관련 : 무한 반복 vs 한번만 재생되고 꺼지게 컨트롤. 
	private File file; 
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	// 생성자 ㄴ
	public Music(String name, boolean isLoop) {
		// 예외처리 
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/"+name).toURI()); 
			// music 폴더 안에 있는 해당 이름의 파일을 실행시킴 
			// toURI : 파일의 위치를 가져옴 
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis); // 해당 파일을 버퍼에 담아서 읽어올 수 있도록 해줌 
			player = new Player(bis); // player에 해당 파일을 담음 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// getTime 메소드 - 현재 재생되고 있는 음악의 위치(시간, ms 단위 사용) 알려줌 
	// ex) 3분짜리 음악이 현재 10초까지 재생되었다 ==> getTime은 10,000을 반환함. 
	// 이 함수를 이용해서 나중에 note 떨어뜨릴 때 시간 계산 예정 
	public int getTime() {
		if (player ==null)
			return 0;
		return player.getPosition();
	}
	
	// close 메소드 - 음악 중지시킬 때 사용. ex) 음악 재생시키다가 다른 곡으로 바꿀 때. 
	public void close() {
		isLoop = false;
		player.close();
		this.interrupt(); // 해당 스레드를 중지상태로 만듦 
		// 스레드 : 하나의 작은 프로그램. 
		// 우리가 하고 있는 게임과 별도로, 우리에게 곡을 들려주는, 곡을 재생해주는 작은 프로그램이 별도로 존재한다고 생각하면 되는데, this.interrupt();는 이 프로그램을 종료해줌. 
	}
	
	// run 메소드 - 스레드 상속 받으면 거의 무조건 써야 함. 
	@Override
	public void run() {
		try {
			do {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			}while(isLoop);
			// 곡을 실행시킨 후, isLoop가 true면 무한반복 시킴 
		}catch (Exception e) {
			System.out.println(e.getMessage()); //오류발생할 경우 오류메시지 출력 
		}
	}

}
