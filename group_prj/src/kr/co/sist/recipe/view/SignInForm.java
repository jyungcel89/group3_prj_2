package kr.co.sist.recipe.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.recipe.evt.SignEvt;

@SuppressWarnings("serial")
public class SignInForm extends JDialog {
	private JLabel jlbId, jlbPw, jlbChkPw, jlbName, jlbEmail;
	public JTextField jtfId, jtfName, jtfEmail;
	private JPasswordField jpfPw, jpfChkPw;
	private JButton jbtChkId, jbtSubmit, jbtCancel;
	
	public SignInForm() {
		setLayout(null);
		
		jlbId=new JLabel("아이디");
		jlbPw=new JLabel("비밀번호");
		jlbChkPw=new JLabel("비밀번호 확인");
		jlbName=new JLabel("성명");
		jlbEmail=new JLabel("E-MAIL");
		jtfId=new JTextField();
		jtfName=new JTextField();
		jtfEmail=new JTextField();
		jpfPw=new JPasswordField();
		jpfChkPw=new JPasswordField();
		jbtChkId=new JButton("중복확인");
		jbtSubmit=new JButton("가입");
		jbtCancel=new JButton("취소");
		
		//Font
		Font font=new Font("Helvetica",	Font.BOLD, 15);
		jlbId.setFont(font);
		jlbPw.setFont(font);
		jlbChkPw.setFont(font);
		jlbName.setFont(font);
		jlbEmail.setFont(font);
		jtfId.setFont(font);
		jtfName.setFont(font);
		jtfEmail.setFont(font);
		jpfPw.setFont(font);
		jpfChkPw.setFont(font);
		jbtChkId.setFont(font);
		jbtSubmit.setFont(font);
		jbtCancel.setFont(font);
		
		//위치설정 간격 10
		jlbId.setBounds(50, 100, 100, 30);
		jlbPw.setBounds(50, 140, 100, 30);
		jlbChkPw.setBounds(50, 180, 100, 30);
		jlbName.setBounds(50, 220, 100, 30);
		jlbEmail.setBounds(50, 260, 100, 30);
		
		jtfId.setBounds(160, 100, 130, 30);
		jpfPw.setBounds(160, 140, 200, 30);
		jpfChkPw.setBounds(160, 180, 200, 30);
		jtfName.setBounds(160, 220, 130, 30);
		jtfEmail.setBounds(160, 260, 250, 30);
		
		jbtChkId.setBounds(300, 100, 110, 30);
		jbtSubmit.setBounds(240, 320, 80, 50);
		jbtCancel.setBounds(330, 320, 80, 50);
		setBounds(50, 50, 470, 440);
		
		//이벤트 추가
		SignEvt se=new SignEvt(this);
		jbtChkId.addActionListener(se);
		jbtSubmit.addActionListener(se);
		jbtCancel.addActionListener(se);
		
		//배치
		add(jlbId);
		add(jlbPw);
		add(jlbChkPw);
		add(jlbName);
		add(jlbEmail);
		
		add(jtfId);
		add(jtfName);
		add(jtfEmail);
		add(jpfPw);
		add(jpfChkPw);
		
		add(jbtChkId);
		add(jbtSubmit);
		add(jbtCancel);
		
		//
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

	public JTextField getJtfEmail() {
		return jtfEmail;
	}

	public void setJtfEmail(JTextField jtfEmail) {
		this.jtfEmail = jtfEmail;
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

}//class