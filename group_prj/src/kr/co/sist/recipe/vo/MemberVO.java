package kr.co.sist.recipe.vo;

/**
 * MemberDAO - updateMember method에서 사용<br/>
 * 회원정보 수정 시 필요한 VO<br/>
 * 수정 정보 ( 비밀번호pw, 이메일mail )<br/>
 * @author JiYong
 *
 */
public class MemberVO {

	private String pw, mail;
	
	public MemberVO() {
	}//MemberVO

	public MemberVO(String pw, String mail) {
		super();
		this.pw = pw;
		this.mail = mail;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	
}
