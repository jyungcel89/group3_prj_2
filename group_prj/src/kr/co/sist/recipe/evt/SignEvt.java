 package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import kr.co.sist.recipe.view.LogInForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.MemberVO;

/**
 * 임시로 넣어놓음
 * 추후 수정할 것 ㅎㅅㅎ
 * @author JiYong
 *
 */
public class SignEvt extends WindowAdapter implements ActionListener {
	
	private SignInForm sf;
	private int flag;
	
<<<<<<< HEAD
	public SignEvt(SignInForm sf){
		this.sf=sf;
	}
	
=======
	
	public SignEvt(SignInForm sf) {
		this.sf = sf;
	}

>>>>>>> branch 'master' of https://github.com/jyungcel89/group3_prj_2.git
	// 회원가입한 멤버 넣기
	public void addMember(/*MemberVO*/){
		System.out.println("중복확인"+flag);
		if(flag==0){
			JOptionPane.showMessageDialog(sf, "아이디 중복확인을 해주세요.");
		}else if(sf.getJpfPw()!=sf.getJpfChkPw()){
			JOptionPane.showMessageDialog(sf, "비밀번호가 일치하지 않습니다.");
			sf.getJpfPw().setText("");
			sf.getJpfChkPw().setText("");
		}else{
			JOptionPane.showMessageDialog(new LogInForm(), "가입에 성공하셨습니다. 로그인 해주세요.");
		}//end if
	}//addMember
	
	// 자신의 정보 수정
	public void editMember(/*MemberVO*/){
	}//editMember
	
	// 아이디 중복확인
	public void checkId(/*String id*/){
		String id="admin";
		if(sf.getJtfId().equals(id)){
			JOptionPane.showMessageDialog(sf, 
					"사용중인 아이디입니다. 다른 아이디를 입력해주세요.");
			sf.getJtfId().setText("");
		}else{
			flag=JOptionPane.showConfirmDialog(sf, 
					"사용가능한 아이디입니다. 이 아이디를 사용하시겠습니까?");
			switch ( flag ) {
			case JOptionPane.OK_OPTION:
				sf.getJtfId().setEditable(false);
			case JOptionPane.CANCEL_OPTION:
				sf.getJtfId().setText("");
			}//end switch
			System.err.println("중복확인1"+flag);
		}//end if
	}// checkId
	
	//클래스 다이어그램에 없는 method - 취소버튼 이벤트
	public void checkCancel(){
		int flag=JOptionPane.showConfirmDialog(sf, "가입을 취소하시겠습니까?");
		switch (flag) {
		case JOptionPane.OK_OPTION:
			sf.dispose();
		}//end switch
	}//checkCancel
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==sf.getJbtChkId()){
			checkId();
		}//end if
		if(ae.getSource()==sf.getJbtSubmit()){
			addMember();
		}//end if
		if(ae.getSource()==sf.getJbtCancel()){
			checkCancel();
		}//end if
	}//actionPerformed

}//class
