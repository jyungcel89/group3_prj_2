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

import kr.co.sist.recipe.vo.IngdntSchVO;
import kr.co.sist.recipe.vo.IngdntVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;

public class IngdntDAO {
	
	private static IngdntDAO ing_dao;
	
	static IngdntDAO getInstance(){
		if(ing_dao==null){
			ing_dao = new IngdntDAO();
		}
		return ing_dao;
	}//getInstance
	
	//*********************getConnection()추가(원래 없었음)*********************************
	private Connection getConnection() throws SQLException {
		Connection con = null;

		Properties prop = new Properties();
		try {
			File file = new File("C:/dev/group3_prj_2/group3_prj_2/group_prj/src/kr/co/sist/recipe/dao/recipe_db.properties");
			
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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return con;
	}// getConnection
	//
	
	// 레시피 당 재료조회
	 /**
	*수정사항*
	-기존 계획 사항:selectIngdntOfRecp()는 매개변수로 ingredntOfRecipeVO형의 객체를 매개변수로 받았음.
	-변경 사항:selectIngdntOfRecp(String recipeName)과 같이 String형의 레시피명만을 받도록 해둠				
	이 부분은 특정 레시피에 대한 재료와 가격을 조회하기 때문에 VO를 받지 않고 레시피 Name만 받도록 한 것이 핵심
	
	 */
	public List<ShowIngdntVO> selectIngdntOfRecp(String recipeName)throws SQLException{
		 
		 List<ShowIngdntVO> list = new ArrayList<ShowIngdntVO>();

			Connection con = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = getConnection();
				String selectIngrdnt ="select ri.INGREDIENT_NAME,i.PRICE "
						+ "from INGREDIENTS i,RECIPE_INGREDIENTS ri "
						+ "where(ri.INGREDIENT_NAME=i.INGREDIENT_NAME) "
						+ "and ri.MENU_NAME='"+recipeName+"'"; 
				pstmt = con.prepareStatement(selectIngrdnt);
				rs = pstmt.executeQuery();
				
				ShowIngdntVO siv= null;
				while (rs.next()) {
					siv =new ShowIngdntVO();
					siv.setIngrdntName(rs.getString("ingredient_name"));
					siv.setIngrdntPrice(rs.getString("price"));
					
					list.add(siv);
				} // end while

			} finally {
				// 5.
				if (rs != null) {
					rs.close();
				} // end if

				if (pstmt != null) {
					pstmt.close();
				} // end if

				if (con != null) {
					con.close();
				} // end if
			}

			return list;
		 
	 }//selectIngdntOfRecp
	 
	 // 사용자, 관리자 : 레시피 추가 창에서 재료선택후 메뉴당 재료 테이블에 추가
	 public boolean insertIngdntOfRecp(IngdntVO ingVo){
		return false;
	 }//insertIngdntOfRecp
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean updateIngdntOfRecp(IngdntVO ingVo){
		return false;
	 }//updateIngdntOfRecp
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean deleteIngdntOfRecp(int ingdntCode){
		 return false;
	 }//updateIngdntOfRecp
	 
	 // 레시피 추가 창에서 카테고리별 재료 조회
	 public List<ShowIngdntVO> selectIngdnt(IngdntSchVO ingSchVo){
		return null;
	 }//selectIngdnt
	 public static void main(String[] args){
		 try {
			 IngdntVO iv= new IngdntVO();
			 
			System.out.println(IngdntDAO.getInstance().selectIngdntOfRecp("하태짝태").toString());
			JOptionPane.showMessageDialog(null, "굿");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	 }
}//class
