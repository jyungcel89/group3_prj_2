 package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.view.LogInForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.InsertMemberVO;
import kr.co.sist.recipe.vo.MemberVO;

/**
 * 회원가입 창 이벤트
 * !!!!!!!!회원정보 수정 부분 미완료 추후 완료!!!!!!
 * <수정사항>
 * 1. parameter 제거
 * @author JiYong
 *
 */
public class SignEvt extends WindowAdapter implements ActionListener {
	
	private SignInForm sf;
	private MemberDAO mem_dao;
	private boolean flag;
	
	public SignEvt(SignInForm sf) {
		this.sf = sf;
	}//SignEvt

	/**
	 * 회원가입
	 * SignInForm에서 id,pw,name,mail을 받아 DB에 추가하는 method
	 * id - 중복확인
	 * pw, pwChk - 일치여부 확인
	 * id, pw, pwChk, name, mail - 입력여부 확인
	 */
	public void addMember(){
		 
		mem_dao=MemberDAO.getInstance();
		InsertMemberVO imem_vo=new InsertMemberVO();
		
		String id=sf.getJtfId().getText();
		String pw=new String(sf.getJpfPw().getPassword());
		String pwChk=new String(sf.getJpfChkPw().getPassword());
		String name=sf.getJtfName().getText();
		String mail=sf.getJtfMail().getText();
		
		if( flag ){
			try {
				// 비밀번호, 비밀번호 확인 부분이 입력하지 않았을때
				if( pw.equals("") || pwChk.equals("") ){
					JOptionPane.showMessageDialog(sf, 
							"비밀번호를 반드시 입력해주세요.");
					return;
				}//end if
				// 비밀번호가 일치하지 않을때
				if( !pw.equals( pwChk ) ){
					JOptionPane.showMessageDialog(sf, 
							"비밀번호가 일치하지 않습니다.");
					return;
				}//end if
				// 이름을 입력하지 않았을때
				if( name.equals("") ){
					JOptionPane.showMessageDialog(sf, 
							"이름을 반드시 입력해주세요.");
					return;
				}//end if
				// 메일을 입력하지 않았을때
				if( mail.equals("") ){
					JOptionPane.showMessageDialog(sf, 
							"메일을 반드시 입력해주세요.");
					return;
				}//end if
				
				//InsertMemberVO를 통해 회원정보를 DB에 넣기
				imem_vo.setId(id);
				imem_vo.setPw(pw);
				imem_vo.setName(name);
				imem_vo.setMail(mail);
				
				mem_dao.insertMember(imem_vo);
				//가입 성공시 메세지 > 로그인 화면으로 이동
				JOptionPane.showMessageDialog(sf, 
						"가입에 성공하셨습니다. 다시 로그인 해주세요.");
				sf.dispose();
				new LogInForm();
			} catch (SQLException se) {
				se.printStackTrace();
			}//end catch
		}else{
			// 아이디 중복 체크 버튼을 클릭하지 않았을때
			JOptionPane.showMessageDialog(sf, 
					"아이디 중복 체크를 먼저 해주세요.");
			return;
		}//end if
	}//addMember
	
	/**
	 * 2017-05-30
	 * 정윤호 추가  \n 마이페이지에서 정보수정으로 들어갔을시 수정버튼 이벤트 
	 */
	
	 
	// 자신의 정보 수정
	public void editMember(){ 
		mem_dao=MemberDAO.getInstance();
		MemberVO mem_vo=new MemberVO();
		

			
			
			
			
		mem_vo.setId("duck");///////////////////////////아이디 연결해야함 
		mem_vo.setPw(new String(sf.getJpfPw().getPassword()));
		mem_vo.setMail(sf.getJtfMail().getText().toString());
		
			int updateFlag=JOptionPane.showConfirmDialog(sf, 
					"수정 하시겠습니까?");
			switch (updateFlag) {
			case JOptionPane.OK_OPTION:
				try {
					if(mem_dao.updateMember(mem_vo)){
						JOptionPane.showMessageDialog(sf, "정상적으로 수정되었습니다.");
					}else{
						JOptionPane.showMessageDialog(sf, "죄송합니다. 잠시후에 다시시도해 주세요");
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(sf, "죄송합니다. 잠시후에 다시시도해 주세요");
					e.printStackTrace();
				}
				sf.dispose();
				break;
			case JOptionPane.NO_OPTION : 
				sf.dispose();
				break;
			case JOptionPane.CANCEL_OPTION : 
				sf.dispose();
				break;
			}//end switch
			
		
	}//editMember
	
	/**
	 * 아이디 중복확인
	 * 입력한 아이디가 기존 회원아이디와 중복되는지 판단하는 method
	 */
	public void checkId(){
		
		mem_dao=MemberDAO.getInstance();
		String id=sf.getJtfId().getText().trim();
		
		try {
			//아이디 중복 체크
			boolean chkId=mem_dao.checkId(id);
			if( chkId ){
				JOptionPane.showMessageDialog(sf, 
						id+"는(은) 이미 사용 중인 아이디 입니다.");
			}else{
				JOptionPane.showMessageDialog(sf, 
						id+"는(은) 사용 가능한 아이디 입니다.");
				flag=true;
			}//end if
			
			//아이디 입력 하지 않았을때
			if( id.equals("") ){
				JOptionPane.showMessageDialog(sf, 
						"아이디가 입력되지 않았습니다.");
				return;
			}//end if
		} catch (SQLException se) {
			se.printStackTrace();
		}//end catch
	}// checkId
	
	/**
	 * 취소버튼 이벤트
	 * 취소버튼 클릭시 확인Dialog를 띄운다.
	 */
	public void checkCancel(){
		int cancelFlag=JOptionPane.showConfirmDialog(sf, 
				"가입을 취소하시겠습니까?");
		switch (cancelFlag) {
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
		if(ae.getSource()==sf.getJbtUpdate()){
			editMember();
		}//end if
	}//actionPerformed

}//class
