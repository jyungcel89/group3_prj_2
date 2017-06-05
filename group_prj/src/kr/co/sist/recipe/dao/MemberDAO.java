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
	
	//������
	private MemberDAO() {
	}//MemberDAO
	
	//Instance ���
	public static MemberDAO getInstance(){
		if(mem_dao==null){
			mem_dao = new MemberDAO();
		}
		return mem_dao;
	}//getInstance
	
	//Connection ���
	private Connection getConnection()throws SQLException{
		Connection con=null;
		
		Properties prop=new Properties();
		try{
			//���� ��� Ȯ���ϰ� ������ ��!
			File file=new File(System.getProperty("user.dir")+"/src/kr/co/sist/recipe/dao/recipe_db.properties");
			//�������ǰ� �ִ� JVM�� ����ڰ��:getProperty
			if( file.exists() ){
				prop.load(new FileInputStream(file));
				String driver=prop.getProperty("driver");
				String url=prop.getProperty("url");
				String id=prop.getProperty("dboid");
				String pass=prop.getProperty("dbopwd");
				
				//����̹� ����
				try{
					//������ �� �Ǿ��ٸ� ����
					Class.forName(driver);
					con=DriverManager.getConnection(url, id, pass);
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}//end catch
			}else{
				JOptionPane.showMessageDialog(null, "���������� ��θ� Ȯ���ϼ���.");
			}//end else if
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}//end catch
		return con;
	}//getConnection
	
	/**
	 * ȸ�� ������ ��ȸ - ȸ������/����â
	 * ��������
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
		//1.����̹��ε�
		//2.Connection ���
			con=getConnection();
		//3.���� ������ü ���
			String selectOneMember=
					"select id, name, mail from members where id=?";
			pstmt=con.prepareStatement(selectOneMember);
			pstmt.setString(1, id);
		//4.�������� �� ��� ���
			rs=pstmt.executeQuery();
			while( rs.next() ){
				mmv.setId(rs.getString("id"));
				mmv.setName(rs.getString("name"));
				mmv.setMail(rs.getString("mail"));
			}//end while
		}finally{
			//5.�������
			if( rs != null ){ rs.close(); };//end if
			if( pstmt !=null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		return mmv;
	}//selectOneMember
	
	/**
	 * ȸ�� - �������������� ������ ���� �� ������ �Է� ��
	 * @param 05-30 ����ȣ
	 * @throws SQLException 
	 */
	public String selectMyInfo(String id) throws SQLException{
		String result=""; 
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
		//1.����̹��ε�
		//2.Connection ���
			con=getConnection();
		//3.���� ������ü ���
			String selectMy=
					"select name from members where id=?";
			pstmt=con.prepareStatement(selectMy);
			pstmt.setString(1, id);
		//4.�������� �� ��� ���
			rs=pstmt.executeQuery();
			while( rs.next() ){
				result=rs.getString("name").toString();
			}//end while
		}finally{
			//5.�������
			if( rs != null ){ rs.close(); };//end if
			if( pstmt !=null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		
		return result;
	}//selectMyInfo
	
	
	/**
	 * ������ - ��ü ȸ�� ��� ��ȸ<br>
	 * id,name,email�� ��ȸ�Ͽ�
	 * MgrMemberVO�� �����ϰ� List�� �߰��Ͽ� ��ȯ�ϴ� ��
	 * @return
	 * @throws SQLException
	 */
	public List<MgrMemberVO> selectAllMember() throws SQLException{
		List<MgrMemberVO>mgrMemberList=new ArrayList<MgrMemberVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		//1.����̹� �ε�
		//2.Connection ���
			con=getConnection();
		//3.������ ������ü ���
			String selectAllMember=
					"select id,name,mail from members";//���ĵ� �����ͷ� ��ȸ �ϰ� �ϱ� orderBy
			pstmt=con.prepareStatement(selectAllMember);
		//4.�������� �� ��� ���
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
		//5.�������
			if( rs != null ){ rs.close(); };//end if
			if( pstmt !=null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		
		return mgrMemberList;
	}//selectAllMember
	
	/**
	 * ȸ������<br>
	 * �Էµ� ȸ������(id,pw,name,mail)�� �޾Ƽ� 
	 * db�� members ���̺� �߰��ϴ� ��<br>
	 * <��������><br>
	 * 1.boolean > void<br>
	 * 2.InsertMemberVO �߰� > id,pw,name,mail �ִ� VO<br>
	 * @param imemVo
	 * @throws SQLException
	 */
	public void insertMember(InsertMemberVO imemVo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
		//1.����̹� �ε�
		//2.Connection ���
			con=getConnection();
		//3.������ ������ü ���
			//ȸ�����Խ� ������ members ���̺� �߰��ϴ� ������  
			String insertMember=
					"insert into members(id,pw,name,mail,member_flag) values (?,?,?,?,'U')";//DB defaultó�� 
			pstmt=con.prepareStatement(insertMember);
		//4.������ ���� �� ��� ���
			//���ε�.set�ڷ���(�÷�, �� ������)
			pstmt.setString(1, imemVo.getId());
			pstmt.setString(2, imemVo.getPw());
			pstmt.setString(3, imemVo.getName());
			pstmt.setString(4, imemVo.getMail());
			
			pstmt.executeUpdate();
		}finally{
		//5.�������
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
	}//insertMember
	
	/**
	 * ������ ȸ��Ż��
	 * ȸ�� ���̵� �޾� �ش��ϴ� ȸ���� �����ϴ� ���� �ϴ� method
	 * ���� ����  - true, ���� - false
	 * @param id
	 * @return flag
	 * @throws SQLException
	 */
	public boolean deleteMember(String id) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		boolean flag=false;
		
		try{
		//1.����̹��ε�
		//2.Connection ���
			con=getConnection();
		//3.������ ������ü ���
			String deleteMember=
					"delete from members where id=?";
			pstmt=con.prepareStatement(deleteMember);
			
			pstmt.setString(1, id);
		//4.������ ���� �� ��� ���
			pstmt.executeUpdate();
			//�ش� ���̵� ��ġ�ϴ� ���� �ִٸ� flag ���� true�� ��´�.
				flag=true;
		}finally{
		//5.�������
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		return flag;
	}//deleteMember
	  
	/**
	 * ȸ������ ����<br>
	 * �ش� ȸ������(pw, email)�� ���� �޾� >(id, pw, email)�� ���� �޾� 
	 * db�� members ���̺��� ���� ����<br>
	 * <��������><br>
	 * 1.boolean > void > boolean ������ ���� <br>
	 * 2.MemberVO �׸� ���� > pw, email �ִ� VO// > ���� id ���� VO<br>
	 * @param memVo
	 * @throws SQLException
	 */
	public boolean updateMember(MemberVO memVo) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
		//1.����̹� �ε�
		//2.Connection ���
			con=getConnection();
		//3.������ ������ü ���
			//�α��ε� ȸ�������� �����ϴ� ������
			//�ش� ���̵� �������� �� �����ϱ�
			String updateMember="update members set pw=?, mail=? where id=?";
			pstmt=con.prepareStatement(updateMember);
		//4.������ ���� �� ��� ���
			pstmt.setString(1, memVo.getPw());
			pstmt.setString(2, memVo.getMail());
			pstmt.setString(3, memVo.getId());
			
			flag=pstmt.executeUpdate(); 
			   if(flag!=0){ // flag�� 0�Ͻ� insert�� ����� ���ٴ� ���� ��������  ���� â�� �������Ѵ� - ����� �� ������ (�ߺ� �ԷµǴ� ��츦 �����Ѵ�)
                   result=true;
			   }else{
				   result=false;
			   }//end if
		}finally{
		//5.�������
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
		}//end finally
		return result;
	}//updateMember
	
	/**
	 * ���̵� �ߺ�Ȯ��
	 * �Էµ� ���̵� �޾Ƽ� members ���̺� �ִ� ���̵����� �Ǵ�
	 * �̹� �ִٸ� true, ��� ��� �����ϴٸ� false
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
		//1.����̹��ε�
		//2.Connection ���
			con=getConnection();
		//3.������ ������ü ���
			String checkId="select id from members where id=?";
			pstmt=con.prepareStatement(checkId);
			
			pstmt.setString(1, id);
		//4.������ ���� �� ��� ���.
			rs=pstmt.executeQuery();
			//�ߺ� ���̵� �ִٸ� flag���� true�� ��´�.
			if(rs.next()){
				flag=true;
			}//end if
		}finally{
		//5.�������
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
			if( rs != null ){ rs.close(); }//end if
		}//end finally
		return flag;
	}//checkId
	
	/**
	 * �α���
	 * LoginVO( id, pw )
	 * �α��� â���� �Էµ� ���̵�� ��й�ȣ�� �޾Ƽ�
	 * members ���̺� �ִ� ȸ�������� ��ġ�ϴ��� �Ǵ�
	 * ��ġ�ϸ� true, ���ٸ� false
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
		//1.����̹��ε�
		//2.Connection ���
			con=getConnection();
		//3.������ ������ü ���
			String loginCheck="select id from members where id=? and pw=?";
			pstmt=con.prepareStatement(loginCheck);
			
			pstmt.setString(1, logVo.getId());
			pstmt.setString(2, logVo.getPw());
			
			pstmt.executeQuery();
		//4.������ ���� �� ��� ���.
			rs=pstmt.executeQuery();
			//���̵�� ��й�ȣ�� �´ٸ� flag���� true�� ��´�.
			if(rs.next()){
				flag=true;
			}//end if
		}finally{
		//5.�������
			if( pstmt != null ){ pstmt.close(); };//end if
			if( con != null ){ con.close(); };//end if
			if( rs != null ){ rs.close(); }//end if
		}//end finally
		return flag;
	}//loginCheck
	
}//class
