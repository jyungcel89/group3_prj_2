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
import java.text.DecimalFormat;
import java.util.Properties;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.vo.BookmarkUpdateVO;
import kr.co.sist.recipe.vo.ScoreVO;
 
public class ScoreDAO {

	private static ScoreDAO score_dao;
	
	public static ScoreDAO getInstance(){
		if(score_dao==null){
			score_dao = new ScoreDAO();
		}
		return score_dao;
	}//getInstance 
	
	private Connection getConnection() throws SQLException {
	      Connection con = null;
	      //�������� ���� ���� �Ͽ���!!!!!!! merge �Ͽ���
	      Properties prop = new Properties();
	      try {
	         File file = new File("C:/dev/workspace/group_prj2/src/kr/co/sist/recipe/dao/recipe_db.properties");
	         
	         if (file.exists()) {
	            prop.load(new FileInputStream(file));
	            String driver = prop.getProperty("driver");
	            String url = prop.getProperty("url");
	            String id = prop.getProperty("dboid");
	            String pass = prop.getProperty("dbopwd");

	            try {
	               Class.forName(driver);
	               con = DriverManager.getConnection(url, id, pass);
	            } catch (ClassNotFoundException e) {
	               e.printStackTrace();
	            } // end catch

	         } else {
	            JOptionPane.showMessageDialog(null, "���������� ��θ� Ȯ�����ּ���");
	         } // end else

	      } catch (FileNotFoundException fe) {
	         fe.printStackTrace();
	      } catch (IOException ie) {
	         ie.printStackTrace();
	      } // end catch
	      return con;
	   }// getConnection
	
	
//////////////////////////////////////////////////// ��ǰ���� �˾� - �����ֱ�///////////////////////////////////////////////////////////////
	public boolean insertScore(ScoreVO sv) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. ����̹��ε�
			//2. Ŀ�ؼ� ���
			con = getConnection();
			//3. ������ ������ü ���
			String insertPoint = "insert into score(id,menu_name,value) values(?,?,?)";
			pstmt = con.prepareStatement(insertPoint);
			//4. �������� �� ������
				// ���ε庯���� �� ����
			pstmt.setString(1, sv.getId());
			pstmt.setString(2, sv.getMenuName());
			pstmt.setInt(3, sv.getValue());
			
			flag=pstmt.executeUpdate();

			if(flag!=0){
                 result=true;
           }else{
                 result=false;
           }//end if
		}finally {
			//5.
			if(pstmt!=null){ pstmt.close(); }
			if(con!=null){ con.close(); }
		}//end finally
		return result;
	}//insertScore
//////////////////////////////////////////////////// ��ǰ���� �˾� - �����ֱ�///////////////////////////////////////////////////////////////
	
	
//////////////////////////////////////////////////// ��ǰ���� �˾� - ���� ����///////////////////////////////////////////////////////////////
	public boolean updateScore(ScoreVO sv) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. ����̹��ε�
			//2. Ŀ�ؼ� ���
			con = getConnection();
			//3. ������ ������ü ���
			String updatePoint = "update score set value = ? where id = ? and menu_name = ?";
			pstmt = con.prepareStatement(updatePoint.toString());
			//4. �������� �� ������
				// ���ε庯���� �� ����
			pstmt.setInt(1, sv.getValue());
			pstmt.setString(2, sv.getId());
			pstmt.setString(3, sv.getMenuName());
			
			flag=pstmt.executeUpdate();
			
			if(flag!=0){
				result=true;
			}else{
				result=false;
			}//end if
			
		}finally {
			//5.
			if(pstmt!=null){ pstmt.close(); }
			if(con!=null){ con.close(); }
		}//end finally
		return result;
	}//updateScore
//////////////////////////////////////////////////// ��ǰ���� �˾� - ���� ����///////////////////////////////////////////////////////////////
	
	
//////////////////////////////////////////////////// ������ - ��ü�������///////////////////////////////////////////////////////////////
	// ����� ���� ������ �������� ���̱� ���ؼ��� RecipeDAO��ü��ȸ �޼ҵ�
	// ���� ���������̺�� �������̺��� gourpby(avg)�������� �����ϴ� �����ʿ�
	// ���̺� ������X ���� �ٷ� �������� ����
	public String getAvg(String menuName) throws SQLException{
		String result="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DecimalFormat form = new DecimalFormat("#.##");// �Ҽ��� 1�ڸ� ������ ǥ���ϱ����� ����

		try{
		// 1.
		// 2.
			con = getConnection();
		// 3.
			String scoreAvg = "select value from score where menu_name=?";
		// 4.
			pstmt=con.prepareStatement(scoreAvg.toString());
			pstmt.setString(1, menuName);
			rs=pstmt.executeQuery();

			int sum=0;
			int cnt=0;
				while (rs.next()) {
					sum+=rs.getInt("value");
					cnt++;
				} // end while
				if(sum!=0){
					double flag=0.0;
					flag=(double)sum/cnt;
					result=form.format(flag); // ��� ���� �Ҽ��� 2°�ڸ� ���� ǥ��
				}else{
					result="-"; // �ʱ� ���� "-" �ѹ��� �˻��� ���� �ʴ��� ���� - �� ǥ��
				} //end if
		}finally{
		// 5.
			if (rs != null) { rs.close();	} // end if
			if (pstmt != null) { pstmt.close(); } // end if
			if (con != null) { con.close(); } // end if
		}//end finally
		return result;
	}//getAvg
	
	public int popUpChkScore(BookmarkUpdateVO buvo) throws SQLException{
		int result=0;
 	   Connection con=null;
        PreparedStatement pstmt= null;
        ResultSet rs = null;
        try {
               con= getConnection();
               
               String chkBmQuery=
                            "select value from score where id=? and menu_name=?";
               
               pstmt = con.prepareStatement(chkBmQuery);
               pstmt.setString(1, buvo.getId());
               pstmt.setString(2, buvo.getMenuName());
               rs = pstmt.executeQuery();
               
               if(rs.next()){
             	  result=rs.getInt("VALUE");
               }
               
        }finally {
               if(rs!= null){ rs.close(); }
               if(pstmt!= null){ pstmt.close(); }
               if(con!= null){ con.close(); }
        }//end finally
 	   
 	   
 	   return result;
		
	}//popUpChkScore
	
	
	
	
	
	
	
	
	
	
//////////////////////////////////////////////////// ������ - ��ü�������///////////////////////////////////////////////////////////////
////////////////////�����׽�Ʈ///////////////////////////////////////////	
/*	
 	public static void main(String[] args){
		String id="duck";
		String menu_name="";
		int value=5;
		
		ScoreDAO sd = new ScoreDAO();
		ScoreVO sv = new ScoreVO();
		
		sv.setId(id);
		sv.setMenuName(menu_name);
		sv.setValue(value);
		
		try {
			//System.out.println(sd.insertScore(sv));
			System.out.println(sd.getAvg(menu_name));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
*/
////////////////////�����׽�Ʈ///////////////////////////////////////////	
}//class
