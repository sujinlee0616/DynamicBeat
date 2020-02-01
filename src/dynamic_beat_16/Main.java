package dynamic_beat_16;

public class Main {
	public static final int SCREEN_WIDTH=1280;
	public static final int SCREEN_HEIGHT=720;
	public static final int NOTE_SPEED = 3; // note가 떨어지는 속도 
	// NOTE_SPEED : 쓰레드가 멈췄다가 다음에 실행될때까지 내려오는 y값 (픽셀) 
	public static final int SLEEP_TIME = 10; // note가 떨어지는 주기 
	// SLEEP_TIME : 쓰레드를 이 시간동안 실행 중지시킴 
    // => Note 쓰레드는 일시중지했다가 SLEEP_TIME 만큼의 시간이 지나면 다시 실행됨.
	// => SLEEP_TIME은 곧 note가 떨어지는 주기가 됨. 
	public static final int REACH_TIME = 2; // 노트가 생성된 이후 판정바에 도달하기 까지의 시간 
	
	public static void main(String[] args) {

		new DynamicBeat();
	}

}
