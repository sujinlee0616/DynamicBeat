package dynamic_beat_9;

public class Track {

	private String titleImage; // 제목 부분 이미지 
	private String startImage; // 게임 선택 창 이미지
	private String gameImage; // 해당 곡을 실행했을 때 이미지
	private String startMusic; // 게임 선택 창 음악
	private String GameMusic; // 해당 곡 실행 시의 음악 
	
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public String getStartImage() {
		return startImage;
	}
	public void setStartImage(String startImage) {
		this.startImage = startImage;
	}
	public String getGameImage() {
		return gameImage;
	}
	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}
	public String getStartMusic() {
		return startMusic;
	}
	public void setStartMusic(String startMusic) {
		this.startMusic = startMusic;
	}
	public String getGameMusic() {
		return GameMusic;
	}
	public void setGameMusic(String gameMusic) {
		GameMusic = gameMusic;
	}
	
	public Track(String titleImage, String startImage, String gameImage, String startMusic, String gameMusic) {
		super();
		this.titleImage = titleImage;
		this.startImage = startImage;
		this.gameImage = gameImage;
		this.startMusic = startMusic;
		GameMusic = gameMusic;
	}
	
	
	
}
