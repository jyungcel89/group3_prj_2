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
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.addIngrdntVO;

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
		//지용이인 내가 수정 하였다!!!!!!! merge 하였음
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
	 /**
	 * 05-25 홍승환 작성
	 * 수정사항:레시피 추가 폼에서 내가 선택한 재료들을 요청하여 메뉴당 재료테이블에 insert를 하는 메서드 인데
	 * 기존의 VO에선 menuName을 받아오는VO가 없어 addIngVo를 만들어 매개변수로 넣어줌
	 */
	public boolean insertIngdntOfRecp(addIngrdntVO addIngVo)throws SQLException{
		 
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag=false;
			try {
				con=getConnection();
				
			
					con = getConnection();
					String selectIngrdntCode ="select distinct ingredients_code "
							+ "from RECIPE_INGREDIENTS "
							+ "where INGREDIENT_NAME='"+addIngVo.getIngrdntName()+"'"; 
					pstmt = con.prepareStatement(selectIngrdntCode);
					rs = pstmt.executeQuery();
					String result="";
						if(rs.next()) {
						result=rs.getString("ingredients_code");
						addIngVo.setIngrdntCode(result);
						} // end while
			  System.out.println(addIngVo.getIngrdntCode());
			  if (pstmt != null) {
					pstmt.close();
				} // end if
			  
				String insertIngrdnt="insert into RECIPE_INGREDIENTS(INGREDIENTS_CODE,INGREDIENT_NAME,MENU_NAME)"
						+ " values(?,?,?)";
				pstmt=con.prepareStatement(insertIngrdnt);
			// 4.
				pstmt.setString(1, addIngVo.getIngrdntCode());
				pstmt.setString(2, addIngVo.getIngrdntName());
				pstmt.setString(3, addIngVo.getMenuName());
				
				pstmt.executeUpdate();
				flag=true;
			} finally {
			// 5.
				
				if (pstmt != null) {
					pstmt.close();
				} // end if

				if (con != null) {
					con.close();
				} // end if
				if (rs != null) {
					rs.close();
				} // end if
			}
		return flag;
	 }//insertIngdntOfRecp
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean updateIngdntOfRecp(IngdntVO ingVo){
		return false;
	 }//updateIngdntOfRecp
	 
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean deleteIngdntOfRecp(int ingdntCode)throws SQLException{
		 
		 return false;
	 }//updateIngdntOfRecp
	 
	 // 레시피 추가 창에서 카테고리별 재료 조회
	 /**
	  *	작성자:홍승환
	  *	   날짜:05-25
	  *		특이사항: 이 메서드는 원래 IngrdntSchVO를 매개변수로 받아 카테고리별 재료명,가격조회를 하기 위해 만든 메서드
	  *		인데 매개변수의 오류가 있어 편의점브랜드,재료종류를 가진 IngrdntCategVo를 만들어 매개변수로 새로넣음
	  */
	public List<ShowIngdntVO> selectIngdnt(IngrdntCategVO icv)throws SQLException{
		 List<ShowIngdntVO> list = new ArrayList<ShowIngdntVO>();

			Connection con = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = getConnection();
				String selectIngrdnt ="select price,ingredient_name "
						+ "from ingredients "
						+ "where brand='"+icv.getBrand()+"' and type='"+icv.getIngrdntSort()+"'";

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
	 }//selectIngdnt
	 public static void main(String[] args){
		 try {
			 
//			System.out.println(IngdntDAO.getInstance().selectIngdntOfRecp("하태짝태").toString());
//			 IngrdntCategVO icv=new IngrdntCategVO("GS25","과자");
//			System.out.println(IngdntDAO.getInstance().selectIngdnt(icv));
			addIngrdntVO iv= new addIngrdntVO("김치","하태짝태");
			System.out.println(IngdntDAO.getInstance().insertIngdntOfRecp(iv));
			JOptionPane.showMessageDialog(null, "굿");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	 }
}//class
