 package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.view.LogInForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.InsertMemberVO;
import kr.co.sist.recipe.vo.MemberVO;

/**
 * ȸ������ â �̺�Ʈ
 * @author JiYong
 */
public class SignEvt extends WindowAdapter implements ActionListener {
	
	private SignInForm sf;
	private MemberDAO mem_dao;
	private boolean flag;
	
	public SignEvt(SignInForm sf) {
		this.sf = sf;
	}//SignEvt

	/**
	 * ȸ������
	 * SignInForm���� id,pw,name,mail�� �޾� DB�� �߰��ϴ� method
	 * id - �ߺ�Ȯ��
	 * pw, pwChk - ��ġ���� Ȯ��
	 * id, pw, pwChk, name, mail - �Է¿��� Ȯ��
	 */
	public void addMember(){
		 
		mem_dao=MemberDAO.getInstance();
		InsertMemberVO imem_vo=new InsertMemberVO();
		
		String id=sf.getJtfId().getText().trim();
		String pw=new String(sf.getJpfPw().getPassword()).trim();
		String pwChk=new String(sf.getJpfChkPw().getPassword()).trim();
		String name=sf.getJtfName().getText().trim();
		String mail=sf.getJtfMail().getText().trim();
		
		if( flag ){
			try {
				// ��й�ȣ, ��й�ȣ Ȯ�� �κ��� �Է����� �ʾ�����
				if( pw.equals("") || pwChk.equals("") ){
					JOptionPane.showMessageDialog(sf, 
							"��й�ȣ�� �ݵ�� �Է����ּ���.");
					return;
				}//end if
				// ��й�ȣ�� ��ġ���� ������
				if( !pw.equals( pwChk ) ){
					JOptionPane.showMessageDialog(sf, 
							"��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					return;
				}//end if
				// �̸��� �Է����� �ʾ�����
				if( name.equals("") ){
					JOptionPane.showMessageDialog(sf, 
							"�̸��� �ݵ�� �Է����ּ���.");
					return;
				}//end if
				// ������ �Է����� �ʾ�����
				if( mail.equals("") ){
					JOptionPane.showMessageDialog(sf, 
							"������ �ݵ�� �Է����ּ���.");
					return;
				}//end if
				
				//InsertMemberVO�� ���� ȸ�������� DB�� �ֱ�
				imem_vo.setId(id);
				imem_vo.setPw(pw);
				imem_vo.setName(name);
				imem_vo.setMail(mail);
				
				mem_dao.insertMember(imem_vo);
				//���� ������ �޼��� > �α��� ȭ������ �̵�
				JOptionPane.showMessageDialog(sf, 
						"���Կ� �����ϼ̽��ϴ�. �ٽ� �α��� ���ּ���.");
				sf.dispose();
				new LogInForm();
			} catch (SQLException se) {
				se.printStackTrace();
			}//end catch
		}else{
			// ���̵� �ߺ� üũ ��ư�� Ŭ������ �ʾ�����
			JOptionPane.showMessageDialog(sf, 
					"���̵� �ߺ� üũ�� ���� ���ּ���.");
			return;
		}//end if
	}//addMember
	
	 
	// �ڽ��� ���� ����
	public void editMember(){ 
		mem_dao=MemberDAO.getInstance();
		MemberVO mem_vo=new MemberVO();
		String pw=new String(sf.getJpfPw().getPassword()).trim();
		String pwChk=new String(sf.getJpfChkPw().getPassword()).trim();
		
		if( !pw.equals( pwChk ) ){
			JOptionPane.showMessageDialog(sf, 
					"��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			return;
		}//end if
			
		mem_vo.setId(LogInEvt.logId);
		mem_vo.setPw(new String(sf.getJpfPw().getPassword()));
		mem_vo.setMail(sf.getJtfMail().getText().toString());
		
		int updateFlag=JOptionPane.showConfirmDialog(sf, 
				"���� �Ͻðڽ��ϱ�?");
		switch (updateFlag) {
		case JOptionPane.OK_OPTION:
			try {
				if(mem_dao.updateMember(mem_vo)){
					JOptionPane.showMessageDialog(sf, 
							"���������� �����Ǿ����ϴ�.");
				}else{
					JOptionPane.showMessageDialog(sf, 
							"�˼��մϴ�. ����Ŀ� �ٽýõ��� �ּ���");
				}//end if
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(sf, 
						"��� ������ �������ּ���");
				return;
			}//end catch
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
	 * ���̵� �ߺ�Ȯ��<br>
	 * �Է��� ���̵� ���� ȸ�����̵�� �ߺ��Ǵ��� �Ǵ��ϴ� method<br>
	 */
	public void checkId(){
		
		mem_dao=MemberDAO.getInstance();
		String id=sf.getJtfId().getText().trim();
		
		try {
			//���̵� �ߺ� üũ
			boolean chkId=mem_dao.checkId(id);
			if( chkId ){
				JOptionPane.showMessageDialog(sf, 
						id+"��(��) �̹� ��� ���� ���̵� �Դϴ�.");
			}else{
				JOptionPane.showMessageDialog(sf, 
						id+"��(��) ��� ������ ���̵� �Դϴ�.");
				flag=true;
			}//end if
			
			//���̵� �Է� ���� �ʾ�����
			if( id.equals("") ){
				JOptionPane.showMessageDialog(sf, 
						"���̵� �Էµ��� �ʾҽ��ϴ�.");
				return;
			}//end if
		} catch (SQLException se) {
			se.printStackTrace();
		}//end catch
	}// checkId
	
	/**
	 * ��ҹ�ư �̺�Ʈ
	 * ��ҹ�ư Ŭ���� Ȯ��Dialog�� ����.
	 */
	public void checkCancel(){
		int cancelFlag=JOptionPane.showConfirmDialog(sf, 
				"������ ����Ͻðڽ��ϱ�?");
		switch (cancelFlag) {
		case JOptionPane.OK_OPTION:
			sf.dispose();
		}//end switch
	}//checkCancel
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		//���̵� �ߺ�Ȯ��
		if(ae.getSource()==sf.getJbtChkId() || ae.getSource() == sf.getJtfId()){
			checkId();
		}//end if
		//ȸ������
		if(ae.getSource()==sf.getJbtSubmit() || ae.getSource() == sf.getJtfMail() || 
				ae.getSource() == sf.getJtfName() || ae.getSource() == sf.getJpfPw() ||
				ae.getSource() == sf.getJpfChkPw() ){
			addMember();
		}//end if
		//�ݱ�
		if(ae.getSource()==sf.getJbtCancel()){
			checkCancel();
		}//end if
		//ȸ������
		if(ae.getSource()==sf.getJbtUpdate()){
			editMember();
		}//end if
	}//actionPerformed

}//class
