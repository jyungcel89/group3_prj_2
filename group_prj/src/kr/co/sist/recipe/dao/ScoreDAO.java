package kr.co.sist.recipe.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.vo.ScoreVO;

public class ScoreDAO {

	private static ScoreDAO score_dao;
	
	static ScoreDAO getInstance(){
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
			
			pstmt.executeUpdate();
			result=true;
			
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
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. 드라이버로딩
			//2. 커넥션 얻기
			con = getConnection();
			//3. 쿼리문 생성객체 얻기
			String updatePoint = "update score set value = ? where id =? and menu_name = ?";
			pstmt = con.prepareStatement(updatePoint);
			//4. 쿼리수행 후 결과얻기
				// 바인드변수에 값 설정
			pstmt.setInt(1, sv.getValue());
			pstmt.setString(2, sv.getId());
			pstmt.setString(3, sv.getMenuName());
			
			pstmt.executeUpdate();
			result=true;
			
		}finally {
			//5.
			if(pstmt!=null){ pstmt.close(); }
			if(con!=null){ con.close(); }
		}//end finally
		System.out.println();
		
		return result;
		
	}//updateScore
//////////////////////////////////////////////////// 상품보기 팝업 - 평점 갱신///////////////////////////////////////////////////////////////
	
////////////////////단위테스트///////////////////////////////////////////	
	public static void main(String[] args){
		String id="duck";
		String menu_name="오지 치즈 후라이";
		int value=4;
		
		ScoreDAO sd = new ScoreDAO();
		ScoreVO sv = new ScoreVO();
		
		sv.setId(id);
		sv.setMenuName(menu_name);
		sv.setValue(value);
		
		try {
			System.out.println(sd.insertScore(sv));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
////////////////////단위테스트///////////////////////////////////////////	
	
	
	
	
	// 메인폼 - 전체평점계산
	// 연산된 값을 가지고 메인폼에 보이기 위해서는 RecipeDAO전체조회 메소드
	// 에서 레시피테이블과 점수테이블을 gourpby(avg)조건으로 조인하는 쿼리필요
	// 테이블에 값저장X 값을 바로 가져오는 형식
	public double getAvg(String menuName){
		return 0;
	}//getAvg
	
}//class
