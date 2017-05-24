package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.view.LogInForm;
import kr.co.sist.recipe.view.SignInForm;

/**
 * 임시로 넣어놨던 것
 * 추후 수정할 것!!
 * @author JiYong
 *
 */
public class LogInEvt extends WindowAdapter implements  ActionListener {

	private LogInForm lf;
	
	public LogInEvt(LogInForm lf) {
		this.lf=lf;
	}//LogInFormEvt
	// 로그인 확인
	public void loginChk(){
		String strId=lf.getJtfId().getText();
		String strPw=new String(lf.getJpfPass().getPassword());
		if(strId.equals("mgr")&&strPw.equals("1234")){
			JOptionPane.showMessageDialog(lf, "관리자 계정 로그인 성공!");
		}else if(strId.equals("user")&&strPw.equals("1234")){
			JOptionPane.showMessageDialog(lf, "사용자 계정 로그인 성공!");
		}else{
			JOptionPane.showMessageDialog(lf, 
					"아이디와 비밀번호를 바르게 입력해주세요.");
		}//end else if
	}//loginChk

	public void moveSignin(){
		new SignInForm();
	}//moveSignin
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==lf.getJbtLogIn()){
			loginChk();
		}//end if
		if(ae.getSource()==lf.getJbtSignIn()){
			moveSignin();
		}//end if
	}//actionPerformed
	

}//class
