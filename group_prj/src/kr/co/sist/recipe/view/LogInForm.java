package kr.co.sist.recipe.view;

import java.awt.Color;
import java.awt.Font;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
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
	private boolean flag;
	private int cnt;
	 
	public LogInForm() {
		setLayout(null);
		JLabel jlBackImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/loginBack.png"));
	    setContentPane(jlBackImg);
		jlblLogo=new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/logo.png"));

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
		jlblLogo.setOpaque(false);
		
		//위치설정
		jlblLogo.setBounds(30, 20, 140, 140);
		jtfId.setBounds(180, 35, 250, 50);
		jpfPass.setBounds(180, 90, 250, 50);
		jbtLogIn.setBounds(440, 30, 100, 85);
		jbtSignIn.setBounds(440, 120, 100, 30);
		setBounds(50, 50, 580, 210);
		
		//이벤트 추가
		LogInEvt le=new LogInEvt(this);
		jtfId.addActionListener(le);
		jpfPass.addActionListener(le);
		jbtLogIn.addActionListener(le);
		jbtSignIn.addActionListener(le);
		jtfId.addKeyListener(le);
		jtfId.setFocusTraversalKeysEnabled(false);
		jpfPass.addKeyListener(le);
		
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
	
	//LogInEvt에서 로그인 결과 설정
	public void setFlag(boolean flag, int cnt) {
		this.flag = flag;
		this.cnt = cnt;
	}//setFlag
	
	public boolean getFlag() throws LoginException {
		//로그인을 수행하였으나 아이디나 비밀번호가 
		//틀려서 LoginException 강제 발생
		if(cnt==1 && !flag){
			throw new LoginException();
		}//end if
		//로그인을 수행하지 않고 닫기를 클릭하면
		//무조건 false가 반환
		return flag;
	}//end getFlag
}//class
