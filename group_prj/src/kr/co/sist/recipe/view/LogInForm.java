package kr.co.sist.recipe.view;

import java.awt.Color;
import java.awt.Font;

//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.recipe.evt.LogInEvt;

@SuppressWarnings("serial")
public class LogInForm extends JFrame {
	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JButton jbtLogIn, jbtSignIn;
	private JLabel jlblLogo;
	
	public LogInForm() {
		setLayout(null);
//		String path="";
//		ImageIcon logo=new ImageIcon();
		jlblLogo=new JLabel("LOGO");
		
		jtfId=new JTextField();
		jtfId.setBorder(new TitledBorder("ID"));
		jpfPass=new JPasswordField();
		jpfPass.setBorder(new TitledBorder("PASSWORD"));
		jbtLogIn=new JButton("LOG IN");
		jbtSignIn=new JButton("SIGN IN");
		
		Font font=new Font("Helvetica", Font.PLAIN, 20);
		jtfId.setFont(font);
		jpfPass.setFont(font);
		
		//색
		jlblLogo.setOpaque(true);
		jlblLogo.setBackground(Color.LIGHT_GRAY);
		
		//위치설정
		jlblLogo.setBounds(30, 30, 140, 120);
		jtfId.setBounds(180, 35, 250, 50);
		jpfPass.setBounds(180, 90, 250, 50);
		jbtLogIn.setBounds(440, 30, 100, 85);
		jbtSignIn.setBounds(440, 120, 100, 30);
		setBounds(50, 50, 580, 220);
		
		//이벤트 추가
<<<<<<< HEAD
		LogInEvt le=new LogInEvt(this);
		jbtLogIn.addActionListener(le);
		jbtSignIn.addActionListener(le);
=======
		LogInEvt lfe=new LogInEvt(this);
		jbtLogIn.addActionListener(lfe);
		jbtSignIn.addActionListener(lfe);
>>>>>>> branch 'master' of https://github.com/jyungcel89/group3_prj_2.git
		
		//배치
		add(jlblLogo);
		add(jtfId);
		add(jpfPass);
		add(jbtLogIn);
		add(jbtSignIn);
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}//LogInForm

	public JTextField getJtfId() {
		return jtfId;
	}

	public void setJtfId(JTextField jtfId) {
		this.jtfId = jtfId;
	}

	public JPasswordField getJpfPass() {
		return jpfPass;
	}

	public void setJpfPass(JPasswordField jpfPass) {
		this.jpfPass = jpfPass;
	}

	public JButton getJbtLogIn() {
		return jbtLogIn;
	}

	public void setJbtLogIn(JButton jbtLogIn) {
		this.jbtLogIn = jbtLogIn;
	}

	public JButton getJbtSignIn() {
		return jbtSignIn;
	}

	public void setJbtSignIn(JButton jbtSignIn) {
		this.jbtSignIn = jbtSignIn;
	}
	
}//class
