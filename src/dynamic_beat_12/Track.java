package dynamic_beat_12;

public class Track {

	private String titleImage; // 제목부분 이미지 
	private String startImage; // 게임 선택 창 표지 이미지 
	private String gameImage; // 해당 곡 실행 시 표지 이미지 
	private String startMusic; // 게임선택 창에서 나오는 30초짜리 음악 
	private String gameMusic; // 해당 곡 선택했을 때 (게임시작 후) 나오는 노래 
	private String titleName; // �� ���� 
	
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
		return gameMusic;
	}
	public void setGameMusic(String gameMusic) {
		this.gameMusic = gameMusic;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String gameMusic) {
		this.titleName = titleName;
	}
	
	public Track(String titleImage, String startImage, String gameImage, String startMusic, String gameMusic, String titleName) {
		super();
		this.titleImage = titleImage;
		this.startImage = startImage;
		this.gameImage = gameImage;
		this.startMusic = startMusic;
		this.gameMusic = gameMusic;
		this.titleName = titleName;
	}
	
	
	
}
