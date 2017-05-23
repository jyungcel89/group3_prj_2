package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyPageEvt extends WindowAdapter implements ActionListener {

	
	// 내가 등록한 메뉴 리스트
	public void showMyRecipe(){
		
	}//showMyRecipe
	
	// 북마크한 메뉴 리스트
	public void showBookmark(){
		
	}//showBookmark
	
	// 북마크한 메뉴 삭제
	public void rmvBookmark(){
		
	}//rmvBookmark
	
	// 내 정보창으로 이동 > 내정보 값 가져와서 SignInForm에 setter값을 설정
	public void goMyInfo(){
		
	}//goMyInfo
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
