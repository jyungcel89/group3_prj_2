 package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.MemberVO;

public class SignEvt extends WindowAdapter implements ActionListener {
	
	private SignInForm sif;
	// 회원가입한 멤버 넣기
	public void addMember(/*MemberVO*/){
	}//addMember
	
	// 자신의 정보 수정
	public void editMember(/*MemberVO*/){
	}//editMember
	
	// 아이디 중복확인
	public void checkId(String id){
	}// checkId
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}//

}//class
