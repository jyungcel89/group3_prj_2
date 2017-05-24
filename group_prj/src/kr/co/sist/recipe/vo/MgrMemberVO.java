package kr.co.sist.recipe.vo;

/**
 * MemberDAO - selectAllMember method에서 사용<br/>
 * 관리자가 전체 회원 목록을 조회에 필요<br/>
 * 조회정보( id, name, mail )<br/>
 * @author JiYong
 *
 */
public class MgrMemberVO {

	private String id,name,email;
	
	public MgrMemberVO(){
	}//MgrMemberVO

	public MgrMemberVO(String id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
