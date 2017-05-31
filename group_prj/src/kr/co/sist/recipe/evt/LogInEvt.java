package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.view.LogInForm;
import kr.co.sist.recipe.view.MainForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.LoginVO;
import kr.co.sist.recipe.vo.MemberVO;



/**
 * �α��� â �̺�Ʈ
 * <��������>
 * 1. parameter ����
 * @author JiYong
 *
 */
public class LogInEvt extends WindowAdapter implements  ActionListener {

	private LogInForm lf;
	private LoginVO log_vo;
	private MemberDAO mem_dao;
	
	public LogInEvt(LogInForm lf) {
		this.lf=lf;
	}//LogInFormEvt
	
	/**
	 * �α��� Ȯ��
	 * �Է��� ���̵�� ��й�ȣ�� ��ȿ���� �Ǵ��ϴ� method
	 */
	public void loginChk(){
		String logId=lf.getJtfId().getText();
		String logPw=new String(lf.getJpfPass().getPassword());
		//LogInForm���� id, pw�� �������
		if( logId.equals("") || logPw.equals("") ){
			JOptionPane.showMessageDialog(lf, 
					"���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			return;
		}//end if
		
		boolean flag=false;
		log_vo=new LoginVO();
		mem_dao=MemberDAO.getInstance();
		
		log_vo.setId(logId);
		log_vo.setPw(logPw);
		
		try {
			boolean logChk=mem_dao.loginCheck(log_vo);
			if( logChk ){
				// �α��� ���� > MainForm���� �̵�
				JOptionPane.showMessageDialog(lf, 
						logId+"�� ȯ���մϴ�.");
				flag=true;
				// ����â ���� �α��� â ����
				new MainForm(logId);
				lf.dispose();
			} else {
				// ��ġ�ϴ� ȸ�������� ������
				JOptionPane.showMessageDialog(lf, 
						"�ش� ȸ�������� �����ϴ�.");
			}//end if
		} catch (SQLException se) {
			se.printStackTrace();
		}//end catch
	}//loginChk

	/**
	 * ȸ������ ��ư
	 * ȸ������â���� �̵�
	 */
	public void moveSignin(){
		new SignInForm();
		lf.dispose();
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
