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
import kr.co.sist.recipe.vo.AddRecipeVO;
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.addRemoveIngrdntVO;
import kr.co.sist.recipe.vo.MgrRecipeVO;
import kr.co.sist.recipe.vo.MgrUpdateIngrdntVO;
import kr.co.sist.recipe.vo.MgrRecipeInfoVO;
public class IngdntDAO {
	
	private static IngdntDAO ing_dao;
	public static IngdntDAO getInstance(){
		if(ing_dao==null){
			ing_dao = new IngdntDAO();
		}
		return ing_dao;
	}//getInstance
	
	private Connection getConnection() throws SQLException {
		Connection con = null;
		Properties prop = new Properties();
		try {
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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return con;
	}// getConnection
	
	 /**
	  * 레시피 당 재료조회
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
				//StringBuilder 사용
				String selectIngrdnt ="select i.BRAND,ri.INGREDIENT_NAME,i.PRICE "
						+ "from INGREDIENTS i,RECIPE_INGREDIENTS ri "
						+ "where(ri.INGREDIENT_NAME=i.INGREDIENT_NAME) "
						+ "and ri.MENU_NAME=?"; 
				pstmt = con.prepareStatement(selectIngrdnt);
				pstmt.setString(1,recipeName);
				rs = pstmt.executeQuery();
				
				ShowIngdntVO siv= null;
				while (rs.next()) {
					siv =new ShowIngdntVO();
					siv.setBrand(rs.getString("brand"));
					siv.setIngrdntName(rs.getString("ingredient_name"));
					siv.setIngrdntPrice(rs.getString("price"));
					
					list.add(siv);
				} // end while

			} finally {
				// 5.
				if (rs != null) { rs.close(); } // end if
				if (pstmt != null) { pstmt.close(); } // end if
				if (con != null) { con.close(); } // end if
			}//end finally
			return list;
	 }//selectIngdntOfRecp
	 
	 /**
	  * 사용자, 관리자 : 레시피 추가 창에서 재료선택후 메뉴당 재료 테이블에 추가
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
						} // end if
						
					  System.out.println(addIngVo.getIngrdntCode());
					  
					  if (pstmt != null) { pstmt.close(); } // end if
					  
						String insertIngrdnt="insert into RECIPE_INGREDIENTS(INGREDIENTS_CODE,INGREDIENT_NAME,MENU_NAME)"
								+ " values(?,?,?)";
						pstmt=con.prepareStatement(insertIngrdnt);
					// 4.
						pstmt.setString(1, addIngVo.getIngrdntCode());
						pstmt.setString(2, addIngVo.getIngrdntName()[i]);
						pstmt.setString(3, addIngVo.getMenuName());
						pstmt.executeUpdate();
						
					} // end for
					flag=true;
			} finally {
					if (pstmt != null) { pstmt.close(); } // end if
					if (con != null) { con.close(); } // end if
					if (rs != null) {	rs.close(); } // end if
			}//end finally
			return flag;
	 }//insertIngdntOfRecp
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean updateIngdntOfRecp(MgrUpdateIngrdntVO muiv,String menuName)throws SQLException{
		 boolean result=false;
			Connection con=null;
			PreparedStatement pstmt=null;
			try{
				con=getConnection();
				String updateRecipe="update RECIPEREGISTER "
						+ "set IMG=?, FOOD_TYPE=?,INFO=?,RECIPE_INFO=?,TOTALPRICE=?,INPUTDATE=to_char(sysdate,'yyyy-mm-dd')"
						+ "where MENU_NAME=?";
				pstmt=con.prepareStatement(updateRecipe);
				pstmt.setString(1, muiv.getImg());
				pstmt.setString(2, muiv.getFoodType());
				pstmt.setString(3, muiv.getInfo());
				pstmt.setString(4, muiv.getRecipeInfo());
				pstmt.setInt(5, muiv.getTotalPrice());
				pstmt.setString(6,menuName);
				pstmt.executeUpdate(); 
				result=true;
			}finally{
				if( pstmt != null ){ pstmt.close(); };//end if
				if( con != null ){ con.close(); };//end if
			}//end finally
			return result;
	 }//updateIngdntOfRecp
	 
	 /**
	  * 관리자 : 레시피 추가 창에서 재료 수정
	 *  05-26 홍승환 코드 작성
	 * 	 수정사항: 관리자창에서 재료를 클릭 후 삭제를 하기 위하 메소드로 기존에 재료코드를 받아와 재료코드를 가지고 삭제되도록
	 *  했지만 그렇게 하지 않고 재료명,메뉴이름이 들어 있는 addRemoveIngrdntVO를 만들어 매개변수로 받게함
	 *  재료명,메뉴명이 2개가 묶이면 식별이 가능하기 때문에 잠을잤음...zzzzzzzzzz
	 */
	public boolean deleteIngdntOfRecp(String menuName)throws SQLException{
		      Connection con = null;
		      PreparedStatement pstmt = null;
		      boolean flag=false;
		      try {
			         con = getConnection();
			         String deleteIngrdnt=
			        		 "delete from RECIPE_INGREDIENTS where MENU_NAME=?";
			         pstmt=con.prepareStatement(deleteIngrdnt);
			         pstmt.setString(1,menuName);
			         pstmt.executeUpdate(); 
			         flag=true;
		      } finally {
		         // 5.
			         if (pstmt!= null) { pstmt.close(); }//end if
			         if (con != null) { con.close(); }//end if
		      } // end finally
		      return flag;
	 }//updateIngdntOfRecp
	
	
	/**
	 * 회원 삭제 시 해당 회원이 레시피를 추가하면서 
	 * 추가된 레시피당 재료를 삭제하는 method
	 *  - 회원id를 받아서 서브쿼리로 조건을 줌
	 * @param id
	 * @return flag
	 * @throws SQLException
	 */
	public boolean deleteIngdntOfRecpMem(String id)throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag=false;
		try {
			con = getConnection();
			String deleteIngrdnt=
					"delete from recipe_ingredients where menu_name in (select menu_name from reciperegister where id=?)";
			pstmt=con.prepareStatement(deleteIngrdnt);
			pstmt.setString(1,id);
			pstmt.executeUpdate(); 
			flag=true;
		} finally {
			// 5.
			if (pstmt!= null) { pstmt.close(); }//end if
			if (con != null) { con.close(); }//end if
		} // end finally
		return flag;
	}//updateIngdntOfRecp
	 
	
	
	/**
	 * -김수연-
	 * 메뉴 당 재료들 이름만 조회
	 * 메뉴추가에서 재료추가 조건에 사용
	 * @param recipeName
	 * @return List<String>
	 * @throws SQLException
	 */
	public List<String> getIngdntOfRecp(String recipeName) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<String> ingList = new ArrayList<String>();
		
		try{
			con = getConnection();
			String selectIngrdnt = "select INGREDIENT_NAME from RECIPE_INGREDIENTS where MENU_NAME=?";
			pstmt = con.prepareStatement(selectIngrdnt);
			pstmt.setString(1, recipeName);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ingList.add(rs.getString("INGREDIENT_NAME"));
			}//end while
		}finally{
			if(rs!= null){ rs.close(); }
            if(pstmt!= null){ pstmt.close(); }
            if(con!= null){ con.close(); }
		}//end finally
		
		return ingList;
		
	}//getIngdntOfRecp
	
	
	public MgrRecipeInfoVO selectMgrRecipe(String menuName)throws SQLException{
			Connection con = null; 
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			ResultSet rs = null;
			ResultSet rs2= null;
			MgrRecipeVO mrv=null;
			MgrRecipeInfoVO mriv=null;
			ShowIngdntVO si=null;
			try {
					con = getConnection();
					String selectMgrRecipeInfo ="select MENU_NAME,IMG, FOOD_TYPE, INFO, RECIPE_INFO, TOTALPRICE,ID "
							+ "from RECIPEREGISTER "
							+ "where menu_name=?";
					String selectMgrRecipeInfo2 ="select ri.INGREDIENT_NAME,i.PRICE "
							+ "from INGREDIENTS i,RECIPE_INGREDIENTS ri "
							+ "where(ri.INGREDIENT_NAME=i.INGREDIENT_NAME) "
							+ "and ri.MENU_NAME=?";
					pstmt = con.prepareStatement(selectMgrRecipeInfo);
					pstmt.setString(1,menuName);
					pstmt2 = con.prepareStatement(selectMgrRecipeInfo2);
					pstmt2.setString(1,menuName);
					rs = pstmt.executeQuery();
					rs2=pstmt2.executeQuery();
					
					List<ShowIngdntVO>list=new ArrayList<ShowIngdntVO>();
					mrv= new MgrRecipeVO();
					mriv=new MgrRecipeInfoVO(list,mrv);
					while (rs.next()) {
						mriv.getMrv().setMenu_name(rs.getString("menu_name"));
						mriv.getMrv().setImg(rs.getString("img"));
						mriv.getMrv().setFoodType(rs.getString("food_type"));
						mriv.getMrv().setInfo(rs.getString("info"));
						mriv.getMrv().setRecipe_info(rs.getString("recipe_info"));
						mriv.getMrv().setTotalPrice(rs.getString("totalprice"));
						mriv.getMrv().setId(rs.getString("id"));
					} // end while
					
					while(rs2.next()){
						si=new ShowIngdntVO();
						si.setIngrdntName(rs2.getString("ingredient_name"));
						si.setIngrdntPrice(rs2.getString("price"));
						list.add(si);
					} //end while
					mriv.setSiv(list);
					
			} finally {
				// 5.
					if (rs != null&&rs2!=null) {
						rs.close();
						rs2.close();
					} // end if
	
					if (pstmt != null&&pstmt2!=null) {
						pstmt.close();
						pstmt2.close();
					} // end if
	
					if (con != null) {
						con.close();
					} // end if
			}//end finally
			return mriv;
	 }//selectIngdnt
	
	
	 /**
	  * 레시피 추가 창에서 카테고리별 재료 조회
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
							+ "where brand=? and type=?";
	
					pstmt = con.prepareStatement(selectIngrdnt);
					pstmt.setString(1,icv.getBrand());
					pstmt.setString(2,icv.getIngrdntSort());
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
					if (rs != null) {	rs.close(); } // end if
					if (pstmt != null) { pstmt.close(); } // end if
					if (con != null) { con.close(); 	} // end if
			}//end finally
			return list;
	 }//selectIngdnt
	
	/**
	 * 레시피 테이블에 레시피 추가
	 * @param arv
	 * @return
	 * @throws SQLException
	 */
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
				if (pstmt != null) { pstmt.close(); } // end if
				if (con != null) { con.close(); } // end if
		}//end finally
			return flag;
	}//insertRecipe
}//class
