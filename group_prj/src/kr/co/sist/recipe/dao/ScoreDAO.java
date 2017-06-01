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
	      //지용이인 내가 수정 하였다!!!!!!! merge 하였음
	      Properties prop = new Properties();
	      try {
//	         File file = new File("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/dao/recipe_db.properties");
	         File file=new File(System.getProperty("user.dir")+"/src/kr/co/sist/recipe/dao/recipe_db.properties");
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
	            JOptionPane.showMessageDialog(null, "설정파일의 경로를 확인해주세요");
	         } // end else

	      } catch (FileNotFoundException fe) {
	         fe.printStackTrace();
	      } catch (IOException ie) {
	         ie.printStackTrace();
	      } // end catch
	      return con;
	   }// getConnection
	
	
//////////////////////////////////////////////////// 상품보기 팝업 - 평점주기///////////////////////////////////////////////////////////////
	public boolean insertScore(ScoreVO sv) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. 드라이버로딩
			//2. 커넥션 얻기
			con = getConnection();
			//3. 쿼리문 생성객체 얻기
			String insertPoint = "insert into score(id,menu_name,value) values(?,?,?)";
			pstmt = con.prepareStatement(insertPoint);
			//4. 쿼리수행 후 결과얻기
				// 바인드변수에 값 설정
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
//////////////////////////////////////////////////// 상품보기 팝업 - 평점주기///////////////////////////////////////////////////////////////
	
	
//////////////////////////////////////////////////// 상품보기 팝업 - 평점 갱신///////////////////////////////////////////////////////////////
	public boolean updateScore(ScoreVO sv) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. 드라이버로딩
			//2. 커넥션 얻기
			con = getConnection();
			//3. 쿼리문 생성객체 얻기
			String updatePoint = "update score set value = ? where id = ? and menu_name = ?";
			pstmt = con.prepareStatement(updatePoint.toString());
			//4. 쿼리수행 후 결과얻기
				// 바인드변수에 값 설정
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
//////////////////////////////////////////////////// 상품보기 팝업 - 평점 갱신///////////////////////////////////////////////////////////////
	
	
//////////////////////////////////////////////////// 메인폼 - 전체평점계산///////////////////////////////////////////////////////////////
	// 연산된 값을 가지고 메인폼에 보이기 위해서는 RecipeDAO전체조회 메소드
	// 에서 레시피테이블과 점수테이블을 gourpby(avg)조건으로 조인하는 쿼리필요
	// 테이블에 값저장X 값을 바로 가져오는 형식
	public String getAvg(String menuName) throws SQLException{
		String result="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DecimalFormat form = new DecimalFormat("#.##");// 소수점 1자리 까지만 표현하기위해 선언

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
					result=form.format(flag); // 결과 값을 소수점 2째자리 까지 표현
				}else{
					result="-"; // 초기 값은 "-" 한번도 검색이 되지 않더라도 값은 - 로 표현
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
	
	
	
	
	
	
	
	
	
	
//////////////////////////////////////////////////// 메인폼 - 전체평점계산///////////////////////////////////////////////////////////////
////////////////////단위테스트///////////////////////////////////////////	
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
////////////////////단위테스트///////////////////////////////////////////	
}//class
