package kr.co.sist.recipe.vo;

/**
 * MemberDAO - loginCheck method에서 사용<br/>
 * 로그인시 회원아이디,비밀번호 일치 여부 확인<br/>
 * 조회정보( id, pw )<br/>
 * @author JiYong
 *
 */
public class LoginVO {

	private String id, pw;
	
	public LoginVO() {
	}//LoginVO

	public LoginVO(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
	
}
