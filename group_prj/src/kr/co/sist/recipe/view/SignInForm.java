package kr.co.sist.recipe.view;

import java.awt.Font;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.evt.LogInEvt;
import kr.co.sist.recipe.evt.SignEvt;

@SuppressWarnings("serial")
public class SignInForm extends JDialog {
	private JLabel jlbId, jlbPw, jlbChkPw, jlbName, jlbMail;
	public JTextField jtfId, jtfName, jtfMail;
	private JPasswordField jpfPw, jpfChkPw;
	private JButton jbtChkId, jbtSubmit, jbtCancel, jbtUpdate;
	private MemberDAO mdao;
	private LogInEvt le;
	
	@SuppressWarnings("static-access")
	public SignInForm() {
		mdao=MemberDAO.getInstance();
		setTitle("ȫȫȫ ������ ȸ������");
		setLayout(null);
		String backgroundPath="";
	    
		jlbId=new JLabel("���̵�");
		jlbPw=new JLabel("��й�ȣ");
		jlbChkPw=new JLabel("��й�ȣ Ȯ��");
		jlbName=new JLabel("����");
		jlbMail=new JLabel("E-MAIL");
		jtfId=new JTextField();
		jtfName=new JTextField();
		jtfMail=new JTextField();
		jpfPw=new JPasswordField();
		jpfChkPw=new JPasswordField();
		jbtChkId=new JButton("�ߺ�Ȯ��");
		jbtSubmit=new JButton("����");
		jbtCancel=new JButton("���");
		//////////////////////���� ��ư �߰�///////////////////
		jbtUpdate=new JButton("����");
		////////////////////////////////////////////////////////
		
		//Font
		Font font=new Font("Helvetica",	Font.BOLD, 13);
		jlbId.setFont(font);
		jlbPw.setFont(font);
		jlbChkPw.setFont(font);
		jlbName.setFont(font);
		jlbMail.setFont(font);
		jtfId.setFont(font);
		jtfName.setFont(font);
		jtfMail.setFont(font);
		jpfPw.setFont(font);
		jpfChkPw.setFont(font);
		jbtChkId.setFont(font);
		jbtSubmit.setFont(font);
		jbtCancel.setFont(font);
		//////////////////////���� ��ư �߰�///////////////////
		jbtUpdate.setFont(font);
		////////////////////////////////////////////////////////
		
		//��ġ���� ���� 10
		jlbId.setBounds(50, 100, 100, 30);
		jlbPw.setBounds(50, 140, 100, 30);
		jlbChkPw.setBounds(50, 180, 100, 30);
		jlbName.setBounds(50, 220, 100, 30);
		jlbMail.setBounds(50, 260, 100, 30);
		
		jtfId.setBounds(160, 100, 130, 30);
		jpfPw.setBounds(160, 140, 200, 30);
		jpfChkPw.setBounds(160, 180, 200, 30);
		jtfName.setBounds(160, 220, 130, 30);
		jtfMail.setBounds(160, 260, 250, 30);
		
		jbtChkId.setBounds(300, 100, 110, 30);
		jbtSubmit.setBounds(270, 320, 80, 40);
		jbtCancel.setBounds(360, 320, 80, 40);
		setBounds(50, 50, 470, 420);
		jbtSubmit.setBounds(240, 320, 80, 50);
		jbtCancel.setBounds(330, 320, 80, 50);
		//////////////////////���� ��ư �߰�///////////////////
		jbtUpdate.setBounds(330,320,80,50);
		////////////////////////////////////////////////////////
		setBounds(50, 50, 470, 420);
		
		//�̺�Ʈ �߰�
		SignEvt se=new SignEvt(this);
		jbtChkId.addActionListener(se);
		jbtSubmit.addActionListener(se);
		jbtCancel.addActionListener(se);
		jbtUpdate.addActionListener(se);
		jtfId.addActionListener(se);
		jtfMail.addActionListener(se);
		jtfName.addActionListener(se);
		jpfPw.addActionListener(se);
		jpfChkPw.addActionListener(se);
		
		// ��� ����
		if(!(le.logId==null)){
			backgroundPath = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/edit_signinBack.png";
		}else{
			backgroundPath = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/signinBack.png";
		}//end else
		ImageIcon background = new ImageIcon(backgroundPath);
		JLabel jlBackImg = new JLabel(background);
	    setContentPane(jlBackImg);
		
		//��ġ
		add(jlbId);
		add(jlbPw);
		add(jlbChkPw);
		add(jlbName);
		add(jlbMail);
		
		add(jtfId);
		add(jtfName);
		add(jtfMail);
		add(jpfPw);
		add(jpfChkPw);
		
		add(jbtChkId);
		add(jbtSubmit);
		add(jbtCancel);
		//////////////////////���� ��ư �߰�///////////////////
		add(jbtUpdate);
		jbtUpdate.setVisible(false);
		////////////////////////////////////////////////////////
		//
			System.out.println("signinForm"+le.logId);
		//����ڰ� �α��� �� ������������ �� �������� ������ �Ұ�� ���ǿ� ���� ������Ʈ ���� �Ӽ��� �ٲپ� �غκ�
		if(le.logId!=null){
		   	  String mail="";
	          try {
	        	  	mail=mdao.selectMyInfo(le.logId);
	          } catch (SQLException e) {
					JOptionPane.showMessageDialog(null, 
							"������ �Ͻ����� ��ְ� ������ϴ�. \n����Ŀ� �ٽ� �õ��� �ּ���.");
					e.printStackTrace();
	          }//end catch
	          jtfId.setText(le.logId);
	          jtfId.setEditable(false);
	          jtfName.setText(mail);
	          jbtChkId.setVisible(false);
	          jbtSubmit.setVisible(false);
	          jbtCancel.setVisible(false);
	          jbtUpdate.setVisible(true);
	          jtfId.setEditable(false);
	          jtfName.setEditable(false);
		}//end if
		
		setModalityType(DEFAULT_MODALITY_TYPE);
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}//SignInForm

	public JTextField getJtfId() {
		return jtfId;
	}

	public void setJtfId(JTextField jtfId) {
		this.jtfId = jtfId;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public void setJtfName(JTextField jtfName) {
		this.jtfName = jtfName;
	}

	public JTextField getJtfMail() {
		return jtfMail;
	}

	public void setJtfMail(JTextField jtfMail) {
		this.jtfMail = jtfMail;
	}

	public JPasswordField getJpfPw() {
		return jpfPw;
	}

	public void setJpfPw(JPasswordField jpfPw) {
		this.jpfPw = jpfPw;
	}

	public JPasswordField getJpfChkPw() {
		return jpfChkPw;
	}

	public void setJpfChkPw(JPasswordField jpfChkPw) {
		this.jpfChkPw = jpfChkPw;
	}

	public JButton getJbtChkId() {
		return jbtChkId;
	}

	public void setJbtChkId(JButton jbtChkId) {
		this.jbtChkId = jbtChkId;
	}

	public JButton getJbtSubmit() {
		return jbtSubmit;
	}

	public void setJbtSubmit(JButton jbtSubmit) {
		this.jbtSubmit = jbtSubmit;
	}

	public JButton getJbtCancel() {
		return jbtCancel;
	}

	public void setJbtCancel(JButton jbtCancel) {
		this.jbtCancel = jbtCancel;
	}
	public JButton getJbtUpdate() {
		return jbtUpdate;
	}

	public void setJbtUpdate(JButton jbtUpdate) {
		this.jbtUpdate = jbtUpdate;
	}

	
}//class