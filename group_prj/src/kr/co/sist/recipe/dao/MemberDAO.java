package kr.co.sist.recipe.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.vo.InsertMemberVO;
import kr.co.sist.recipe.vo.LoginVO;
import kr.co.sist.recipe.vo.MemberVO;
import kr.co.sist.recipe.vo.MgrMemberVO;

public class MemberDAO {

	private static MemberDAO mem_dao;
	
	static MemberDAO getInstance(){
		if(mem_dao==null){
			mem_dao = new MemberDAO();
		}
		return mem_dao;
	}//getInstance
	
	//////////////////////////05-22-2017 작성////////////////////////
	private Connection getConnection()throws SQLException{
		Connection con=null;
		
		Properties prop=new Properties();
		try{
			//파일 경로 확인하고 수정할 것!
			File file=new File("C:/dev/workspace/group_prj/src/kr/co/sist/recipe/dao/member_db.properties");
			
			if( file.exists() ){
				prop.load(new FileInputStream(file));
				String driver=prop.getProperty("driver");
				String url=prop.getProperty("url");
				String id=prop.getProperty("dboid");
				String pass=prop.getProperty("dbopwd");
				
				//드라이버 연동
				try{
					//연동이 잘 되었다면 연결
					Class.forName(driver);
					con=DriverManager.getConnection(url, id, pass);
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}//end catch
			}else{
				JOptionPane.showMessageDialog(null, "설정파일의 경로를 확인하세요.");
			}//end else if
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}//end catch
		return con;
	}//getConnection
	
	// 마이페이지 정보
	public MemberVO selectOneMember(String id){
		return null;
	}//selectOneMember
	
	/**
	 * 관리자 - 전체 회원 목록 조회<br>
	 * id,name,email을 조회하여
	 * MgrMemberVO에 저장하고 List에 추가하여 반환하는 일
	 * @return
	 * @throws SQLException
	 */
	public List<MgrMemberVO> selectAllMember() throws SQLException{
		List<MgrMemberVO>mgrMemberList=new ArrayList<MgrMemberVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		//1.드라이버 로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리문 생성객체 얻기
			String selectAllMember=
					"select id,name,email from members";
			pstmt=con.prepareStatement(selectAllMember);
		//4.쿼리수행 후 결과 얻기
			rs=pstmt.executeQuery();
			MgrMemberVO mmv=null;
			while( rs.next() ){
				mmv=new MgrMemberVO();
				mmv.setId(rs.getString("id"));
				mmv.setName(rs.getString("name"));
				mmv.setEmail(rs.getString("email"));
				
				mgrMemberList.add(mmv);
			}//end while
		} finally {
		//5.연결끊기
			if( rs != null ){ rs.close(); };//end if
			if( pstmt !=null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		
		return mgrMemberList;
	}//selectAllMember
	
	/**
	 * 회원가입<br>
	 * 입력된 회원정보(id,pw,name,email)를 받아서 
	 * db의 members 테이블에 추가하는 일<br>
	 * <수정사항><br>
	 * 1.boolean > void<br>
	 * 2.InsertMemberVO 추가 > id,pw,name,email 있는 VO<br>
	 * @param imemVo
	 * @throws SQLException
	 */
	public void insertMember(InsertMemberVO imemVo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
		//1.드라이버 로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리문 생성객체 얻기
			//회원가입시 정보를 members 테이블에 추가하는 쿼리문  
			String insertMember=
					"insert into members (id,pw,name,email) values (?,?,?,?)";
			pstmt=con.prepareStatement(insertMember);
		//4.쿼리문 수행 후 결과 얻기
			//바인딩.set자료형(컬럼, 들어갈 데이터)
			pstmt.setString(1, imemVo.getId());
			pstmt.setString(2, imemVo.getPw());
			pstmt.setString(3, imemVo.getName());
			pstmt.setString(4, imemVo.getEmail());
			
			pstmt.executeUpdate();
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
	}//insertMember
	
	// 관리자 회원탈퇴
	public boolean deleteMember(String id){
		return false;
	}//deleteMember
	
	// 회원정보 수정
	/**
	 * 회원정보 수정<br>
	 * 해당 회원정보(pw, email)의 값을 받아 
	 * db의 members 테이블의 값을 수정<br>
	 * <수정사항><br>
	 * 1.boolean > void<br>
	 * 2.MemberVO 항목 수정 > pw, email 있는 VO<br>
	 * @param memVo
	 * @throws SQLException
	 */
	public void updateMember(MemberVO memVo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
		//1.드라이버 로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리문 생성객체 얻기
			//로그인된 회원정보를 수정하는 쿼리문
			//해당 아이디를 가져오는 것 질문하기
			String updateMember="update members set pw=?, mail=? where id=?";
			pstmt=con.prepareStatement(updateMember);
		//4.쿼리문 수행 후 결과 얻기
			pstmt.setString(1, memVo.getPw());
			pstmt.setString(2, memVo.getMail());
//			pstmt.setString(3, );
			
			pstmt.executeUpdate(); 
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
	}//updateMember
	
	// 아이디 중복확인
	public boolean checkId(String id){
		return false;
	}//checkId
	
	// 로그인
	public boolean loginCheck(LoginVO logVo){
		return false;
	}//loginCheck
	
	
}//class
