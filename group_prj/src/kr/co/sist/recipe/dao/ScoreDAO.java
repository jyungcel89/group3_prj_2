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
	            System.out.println(driver);

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
	
	
	///////////////////// 상품보기 팝업 - 평점주기/////////////////////
	public boolean insertScore(String id, String menu_name, int value) throws SQLException{
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
			
			pstmt.setString(1, id);
			pstmt.setString(2, menu_name);
			pstmt.setInt(3, value);
			
			pstmt.executeUpdate();
			result=true;
			
		}finally {
			//5.
			if(pstmt!=null){ pstmt.close(); }
			if(con!=null){ con.close(); }
		}//end finally
		
		
		if(result){
			System.out.println("추가되었음");
		}else{
			System.out.println("추가 안되었음");
			
		}
		return result;
		
	}//insertScore
	
////////////////////단위테스트///////////////////////////////////////////	
	public static void main(String[] args){
		String id="duck";
		String menu_name="오지 치즈 후라이";
		int value=3;
		
		ScoreDAO sd = new ScoreDAO();
		
		try {
			sd.insertScore(id, menu_name, value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
////////////////////단위테스트///////////////////////////////////////////	
	// 상품보기 팝업 - 평점 갱신
	public boolean updateScore(ScoreVO scoreVo){
		return false;
	}//updateScore
	
	// 메인폼 - 전체평점계산
	// 연산된 값을 가지고 메인폼에 보이기 위해서는 RecipeDAO전체조회 메소드
	// 에서 레시피테이블과 점수테이블을 gourpby(avg)조건으로 조인하는 쿼리필요
	// 테이블에 값저장X 값을 바로 가져오는 형식
	public double getAvg(String menuName){
		return 0;
	}//getAvg
	
}//class
