package kr.co.sist.recipe.dao;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.omg.Messaging.SyncScopeHelper;

import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.vo.AddRecipeVO;
import kr.co.sist.recipe.vo.IngdntVO;
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.addRemoveIngrdntVO;

public class IngdntDAO {
	
	private static IngdntDAO ing_dao;
	private AddRecipeForm arf;
	public static IngdntDAO getInstance(){
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
	public boolean insertIngdntOfRecp(addRemoveIngrdntVO addIngVo)throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag=false;
			try {
				con=getConnection();
					
					for(int i=0;i<addIngVo.getIngrdntName().length;i++){
					String selectIngrdntCode ="select distinct ingredients_code "
							+ "from RECIPE_INGREDIENTS "
							+ "where INGREDIENT_NAME='"+addIngVo.getIngrdntName()[i]+"'";
					
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
				pstmt.setString(2, addIngVo.getIngrdntName()[i]);
				pstmt.setString(3, addIngVo.getMenuName());
				pstmt.executeUpdate();
				}
				
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
//	 public boolean updateIngdntOfRecp(IngdntVO ingVo){
//		
//			Connection con=null;
//			PreparedStatement pstmt = null;
//			try{
//				con = getConnection();
//				String query="update reciperegister set img=?, food_type=?, info=?, recipe_info=? where menu_name=?";
//				pstmt = con.prepareStatement(query);
//				pstmt.setString(1, updateVo.getMenuImg());
//				pstmt.setString(2, updateVo.getMenuType());
//				pstmt.setString(3, updateVo.getMenuSimpleInfo());
//				pstmt.setString(4, updateVo.getMenuDetailInfo());
//				pstmt.setString(5, menuName);
//				
//				pstmt.executeUpdate();
//			}finally {
//				if(pstmt!= null){ pstmt.close(); }
//				if(con!= null){ con.close(); }
//			}//end finally
//		 return false;
//	 }//updateIngdntOfRecp
	 
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 /**
	 *  05-26 홍승환 코드 작성
	 * 	 수정사항: 관리자창에서 재료를 클릭 후 삭제를 하기 위하 메소드로 기존에 재료코드를 받아와 재료코드를 가지고 삭제되도록
	 *  했지만 그렇게 하지 않고 재료명,메뉴이름이 들어 있는 addRemoveIngrdntVO를 만들어 매개변수로 받게함
	 *  재료명,메뉴명이 2개가 묶이면 식별이 가능하기 때문에 잠을잤음...zzzzzzzzzz
	 */
	public boolean deleteIngdntOfRecp(addRemoveIngrdntVO removeIngVo)throws SQLException{
		      Connection con = null;
		      PreparedStatement pstmt = null;
		      boolean flag=false;
		      try {
		         con = getConnection();
		         String deleteIngrdnt="delete from RECIPE_INGREDIENTS "
		         		+ " where INGREDIENT_NAME='"+removeIngVo.getIngrdntName()+"' "
		         		+ " and MENU_NAME='"+removeIngVo.getMenuName()+"'";
		         pstmt=con.prepareStatement(deleteIngrdnt);
		         pstmt.executeUpdate(); 
		         flag=true;
		      } finally {
		         // 5.
		         if (pstmt!= null) {
		            pstmt.close();
		         }
		         if (con != null) {
		            con.close();
		         }
		      } // end finally
		 return flag;
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
	
	
	public boolean insertRecipe(AddRecipeVO arv)throws SQLException{
		boolean flag=false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con=getConnection();
			String insertRecipe="insert into RECIPEREGISTER(MENU_NAME, IMG, FOOD_TYPE, INFO, "
					+ "RECIPE_INFO, TOTALPRICE, ID, INPUTDATE, RECIPE_FLAG)"
					+ " values(?,?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),'S')";
			pstmt=con.prepareStatement(insertRecipe);
			pstmt.setString(1, arv.getMenuName());
			pstmt.setString(2, arv.getMenuImg());
			pstmt.setString(3, arv.getMenuType());
			pstmt.setString(4, arv.getMenuSimpleInfo());
			pstmt.setString(5, arv.getMenuDetailInfo());
			pstmt.setInt(6, arv.getMenuPrice());
			pstmt.setString(7, arv.getId());
			pstmt.executeUpdate();
			flag=true;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			} // end if

			if (con != null) {
				con.close();
			} // end if
		}
	return flag;
	}
	
	
	/* public static void main(String[] args){
		 //			System.out.println(IngdntDAO.getInstance().selectIngdntOfRecp("하태짝태").toString());
					 IngrdntCategVO icv=new IngrdntCategVO("GS25","과자");
					
		 //					System.out.println(IngdntDAO.getInstance().deleteIngdntOfRecp(iv));
					 try {
						 System.out.println(IngdntDAO.getInstance().selectIngdnt(icv));
//						 String[] arr={"김치"};
						 
//						 addRemoveIngrdntVO iv= new addRemoveIngrdntVO(arr,"하태짝태");
//						System.out.println(IngdntDAO.getInstance().deleteIngdntOfRecp(iv));
//						 System.out.println(IngdntDAO.getInstance().insertIngdntOfRecp(iv));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "안댐;");
						e.printStackTrace();
					
					}
					
	 }*/
}//class

