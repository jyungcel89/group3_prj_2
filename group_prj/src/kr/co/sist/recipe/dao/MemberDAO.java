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
	private RecipeDAO rcp_dao;
	
	//생성자
	private MemberDAO() {
	}//MemberDAO
	
	//Instance 얻기
	public static MemberDAO getInstance(){
		if(mem_dao==null){
			mem_dao = new MemberDAO();
		}
		return mem_dao;
	}//getInstance
	
	//Connection 얻기
	private Connection getConnection()throws SQLException{
		Connection con=null;
		
		Properties prop=new Properties();
		try{
			//파일 경로 확인하고 수정할 것!
			File file=new File(System.getProperty("user.dir")+"/src/kr/co/sist/recipe/dao/recipe_db.properties");
			//현재실행되고 있는 JVM의 사용자경로:getProperty
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
	
	/**
	 * 회원 상세정보 조회 - 회원가입/수정창
	 * 수정사항
	 * 1.MemberVO > MgrMemeberVO (id,name,mail)
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public MgrMemberVO selectOneMember(String id) throws SQLException{
		MgrMemberVO mmv=new MgrMemberVO();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
		//1.드라이버로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리 생성객체 얻기
			String selectOneMember=
					"select id, name, mail from members where id=?";
			pstmt=con.prepareStatement(selectOneMember);
			pstmt.setString(1, id);
		//4.쿼리수행 후 결과 얻기
			rs=pstmt.executeQuery();
			while( rs.next() ){
				mmv.setId(rs.getString("id"));
				mmv.setName(rs.getString("name"));
				mmv.setMail(rs.getString("mail"));
			}//end while
		}finally{
			//5.연결끊기
			if( rs != null ){ rs.close(); };//end if
			if( pstmt !=null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		return mmv;
	}//selectOneMember
	
	/**
	 * 회원 - 마이페이지에서 내정보 수정 시 데이터 입력 값
	 * @param 05-30 정윤호
	 * @throws SQLException 
	 */
	public String selectMyInfo(String id) throws SQLException{
		String result=""; 
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
		//1.드라이버로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리 생성객체 얻기
			String selectMy=
					"select name from members where id=?";
			pstmt=con.prepareStatement(selectMy);
			pstmt.setString(1, id);
		//4.쿼리수행 후 결과 얻기
			rs=pstmt.executeQuery();
			while( rs.next() ){
				result=rs.getString("name").toString();
			}//end while
		}finally{
			//5.연결끊기
			if( rs != null ){ rs.close(); };//end if
			if( pstmt !=null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		
		return result;
	}//selectMyInfo
	
	
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
					"select id,name,mail from members";//정렬된 데이터로 조회 하게 하기 orderBy
			pstmt=con.prepareStatement(selectAllMember);
		//4.쿼리수행 후 결과 얻기
			rs=pstmt.executeQuery();
			MgrMemberVO mmv=null;
			while( rs.next() ){
				mmv=new MgrMemberVO();
				mmv.setId(rs.getString("id"));
				mmv.setName(rs.getString("name"));
				mmv.setMail(rs.getString("mail"));
				
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
	 * 입력된 회원정보(id,pw,name,mail)를 받아서 
	 * db의 members 테이블에 추가하는 일<br>
	 * <수정사항><br>
	 * 1.boolean > void<br>
	 * 2.InsertMemberVO 추가 > id,pw,name,mail 있는 VO<br>
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
					"insert into members(id,pw,name,mail,member_flag) values (?,?,?,?,'U')";//DB default처리 
			pstmt=con.prepareStatement(insertMember);
		//4.쿼리문 수행 후 결과 얻기
			//바인딩.set자료형(컬럼, 들어갈 데이터)
			pstmt.setString(1, imemVo.getId());
			pstmt.setString(2, imemVo.getPw());
			pstmt.setString(3, imemVo.getName());
			pstmt.setString(4, imemVo.getMail());
			
			pstmt.executeUpdate();
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
	}//insertMember
	
	/**
	 * 관리자 회원탈퇴
	 * 회원 아이디를 받아 해당하는 회원을 삭제하는 일을 하는 method
	 * 삭제 성공  - true, 실패 - false
	 * @param id
	 * @return flag
	 * @throws SQLException
	 */
	public boolean deleteMember(String id) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		boolean flag=false;
		
		try{
		//1.드라이버로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리문 생성객체 얻기
			String deleteMember=
					"delete from members where id=?";
			pstmt=con.prepareStatement(deleteMember);
			
			pstmt.setString(1, id);
		//4.쿼리문 수행 후 결과 얻기
			pstmt.executeUpdate();
			//해당 아이디가 일치하는 것이 있다면 flag 값에 true를 담는다.
				flag=true;
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		return flag;
	}//deleteMember
	  
	/**
	 * 회원정보 수정<br>
	 * 해당 회원정보(pw, email)의 값을 받아 >(id, pw, email)의 값을 받아 
	 * db의 members 테이블의 값을 수정<br>
	 * <수정사항><br>
	 * 1.boolean > void > boolean 값으로 수정 <br>
	 * 2.MemberVO 항목 수정 > pw, email 있는 VO// > 수정 id 포함 VO<br>
	 * @param memVo
	 * @throws SQLException
	 */
	public boolean updateMember(MemberVO memVo) throws SQLException{
		boolean result=false;
		int flag=0;
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
			pstmt.setString(3, memVo.getId());
			
			flag=pstmt.executeUpdate(); 
			   if(flag!=0){ // flag가 0일시 insert된 결과가 없다는 것을 뜻함으로  오류 창을 띄워줘야한다 - 경우의 수 없을듯 (중복 입력되는 경우를 막아한다)
                   result=true;
			   }else{
				   result=false;
			   }//end if
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		return result;
	}//updateMember
	
	/**
	 * 아이디 중복확인
	 * 입력된 아이디를 받아서 members 테이블에 있는 아이디인지 판단
	 * 이미 있다면 true, 없어서 사용 가능하다면 false
	 * @param id
	 * @return flag
	 * @throws SQLException
	 */
	public boolean checkId(String id) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean flag=false;
		try{
		//1.드라이버로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리문 생성객체 얻기
			String checkId="select id from members where id=?";
			pstmt=con.prepareStatement(checkId);
			
			pstmt.setString(1, id);
		//4.쿼리문 수행 후 결과 얻기.
			rs=pstmt.executeQuery();
			//중복 아이디가 있다면 flag값에 true를 담는다.
			if(rs.next()){
				flag=true;
			}//end if
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
			if( rs != null ){ rs.close(); }//end if
		}//end finally
		return flag;
	}//checkId
	
	/**
	 * 로그인
	 * LoginVO( id, pw )
	 * 로그인 창에서 입력된 아이디와 비밀번호를 받아서
	 * members 테이블에 있는 회원정보와 일치하는지 판단
	 * 일치하면 true, 없다면 false
	 * @param logVo
	 * @return flag
	 * @throws SQLException
	 */
	public boolean loginCheck(LoginVO logVo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean flag=false;
		try{
		//1.드라이버로딩
		//2.Connection 얻기
			con=getConnection();
		//3.쿼리문 생성객체 얻기
			String loginCheck="select id from members where id=? and pw=?";
			pstmt=con.prepareStatement(loginCheck);
			
			pstmt.setString(1, logVo.getId());
			pstmt.setString(2, logVo.getPw());
			
			pstmt.executeQuery();
		//4.쿼리문 수행 후 결과 얻기.
			rs=pstmt.executeQuery();
			//아이디와 비밀번호가 맞다면 flag값에 true를 담는다.
			if(rs.next()){
				flag=true;
			}//end if
		}finally{
		//5.연결끊기
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
			if( rs != null ){ rs.close(); }//end if
		}//end finally
		return flag;
	}//loginCheck
	
}//class
