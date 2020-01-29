package dynamic_beat_15;

public class Main {
	public static final int SCREEN_WIDTH=1280;
	public static final int SCREEN_HEIGHT=720;
	public static final int NOTE_SPEED = 3; // note가 떨어지는 속도 
	public static final int SLEEP_TIME = 10; // note가 떨어지는 주기 
	public static final int REACH_TIME = 2; // 노트가 생성된 이후 판정바에 도달하기 까지의 시간 
	
	public static void main(String[] args) {

		new DynamicBeat();
	}

}
