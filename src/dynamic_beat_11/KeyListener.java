// KeyListener 클래스 : 사용자가 입력한 키를 감지하여 이벤트를 발생시키는 클래스. 
package dynamic_beat_11;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
	@Override 
	public void keyPressed(KeyEvent e) { // key를 눌렀을 때의 이벤트 처리 
		// 게임 진행 X일 때의 키보드 처리 
		if(DynamicBeat.game == null) { 
			return;
			// 현재 게임이 진행되고 있지 않다면 return을 넣어줘서 아래의 if문들을 수행하지 않도록 함 
			//==> 키보드 입력하더라도 어떠한 변화도 일어나지 않도록 키보드 입력을 무력화시킴.  
		}		
		// 키보드 입력 처리 (게임진행 O일 때) 
		if(e.getKeyCode() == KeyEvent.VK_S) {
			DynamicBeat.game.pressS();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D) {
			DynamicBeat.game.pressD();
		}
		else if(e.getKeyCode() == KeyEvent.VK_F) {
			DynamicBeat.game.pressF();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			DynamicBeat.game.pressSpace();
		}
		else if(e.getKeyCode() == KeyEvent.VK_J) {
			DynamicBeat.game.pressJ();
		}
		else if(e.getKeyCode() == KeyEvent.VK_K) {
			DynamicBeat.game.pressK();
		}
		else if(e.getKeyCode() == KeyEvent.VK_L) {
			DynamicBeat.game.pressL();
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) { // key를 눌렀다가 뗄 때의 이벤트 처리 
		if(DynamicBeat.game == null) {
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			DynamicBeat.game.releaseS();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D) {
			DynamicBeat.game.releaseD();
		}
		else if(e.getKeyCode() == KeyEvent.VK_F) {
			DynamicBeat.game.releaseF();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			DynamicBeat.game.releaseSpace();
		}
		else if(e.getKeyCode() == KeyEvent.VK_J) {
			DynamicBeat.game.releaseJ();
		}
		else if(e.getKeyCode() == KeyEvent.VK_K) {
			DynamicBeat.game.releaseK();
		}
		else if(e.getKeyCode() == KeyEvent.VK_L) {
			DynamicBeat.game.releaseL();
		}
		
		
	}

}
