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
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.vo.AddRecipeVO;
import kr.co.sist.recipe.vo.IngdntVO;
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.addRemoveIngrdntVO;
import kr.co.sist.recipe.vo.MgrRecipeVO;
import kr.co.sist.recipe.vo.MgrUpdateIngrdntVO;
import kr.co.sist.recipe.vo.LoginVO;
import kr.co.sist.recipe.vo.MgrRecipeInfoVO;
public class IngdntDAO {
	
	private static IngdntDAO ing_dao;
	public static IngdntDAO getInstance(){
		if(ing_dao==null){
			ing_dao = new IngdntDAO();
		}
		return ing_dao;
	}//getInstance
	//*********************getConnection()�߰�(���� ������)*********************************
	private Connection getConnection() throws SQLException {
		Connection con = null;
		//�������� ���� ���� �Ͽ���!!!!!!! merge �Ͽ���
		Properties prop = new Properties();
		try {
//			File file = new File("C:/dev/group3_prj_2/group3_prj_2/group_prj/src/kr/co/sist/recipe/dao/recipe_db.properties");
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
				JOptionPane.showMessageDialog(null, "���������� ��θ� Ȯ�����ּ���");
			} // end else

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return con;
	}// getConnection
	//
	
	// ������ �� �����ȸ
	 /**
	*��������*
	-���� ��ȹ ����:selectIngdntOfRecp()�� �Ű������� ingredntOfRecipeVO���� ��ü�� �Ű������� �޾���.
	-���� ����:selectIngdntOfRecp(String recipeName)�� ���� String���� �����Ǹ��� �޵��� �ص�				
	�� �κ��� Ư�� �����ǿ� ���� ���� ������ ��ȸ�ϱ� ������ VO�� ���� �ʰ� ������ Name�� �޵��� �� ���� �ٽ�
	
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
						+ "and ri.MENU_NAME=?";
				pstmt = con.prepareStatement(selectIngrdnt);
				pstmt.setString(1,recipeName);
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
	 
	 // �����, ������ : ������ �߰� â���� ��ἱ���� �޴��� ��� ���̺� �߰�
	 /**
	 * 05-25 ȫ��ȯ �ۼ�
	 * ��������:������ �߰� ������ ���� ������ ������ ��û�Ͽ� �޴��� ������̺� insert�� �ϴ� �޼��� �ε�
	 * ������ VO���� menuName�� �޾ƿ���VO�� ���� addIngVo�� ����� �Ű������� �־���
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
	 
	 // ������ : ������ �߰� â���� ��� ����
	 public boolean updateIngdntOfRecp(MgrUpdateIngrdntVO muiv,String menuName)throws SQLException{
		 boolean result=false;
			Connection con=null;
			PreparedStatement pstmt=null;
			try{
				con=getConnection();
				String updateRecipe="update RECIPEREGISTER "
						+ "set IMG=?, FOOD_TYPE=?,INFO=?,RECIPE_INFO=?,TOTALPRICE=?,INPUTDATE=to_char(sysdate,'yyyy-mm-dd')"
						+ " where MENU_NAME='"+menuName+"'";
				pstmt=con.prepareStatement(updateRecipe);
				pstmt.setString(1, muiv.getImg());
				pstmt.setString(2, muiv.getFoodType());
				pstmt.setString(3, muiv.getInfo());
				pstmt.setString(4, muiv.getRecipeInfo());
				pstmt.setInt(5, muiv.getTotalPrice());
				pstmt.executeUpdate(); 
				result=true;
			}finally{
				if( pstmt != null ){ pstmt.close(); };//end if
				if( con != null ){ con.close(); };//end if
			}//end finally
			return result;
	 }//updateIngdntOfRecp
	 
	 
	 // ������ : ������ �߰� â���� ��� ����
	 /**
	 *  05-26 ȫ��ȯ �ڵ� �ۼ�
	 * 	 ��������: ������â���� ��Ḧ Ŭ�� �� ������ �ϱ� ���� �޼ҵ�� ������ ����ڵ带 �޾ƿ� ����ڵ带 ������ �����ǵ���
	 *  ������ �׷��� ���� �ʰ� ����,�޴��̸��� ��� �ִ� addRemoveIngrdntVO�� ����� �Ű������� �ް���
	 *  ����,�޴����� 2���� ���̸� �ĺ��� �����ϱ� ������ ��������...zzzzzzzzzz
	 */
	public boolean deleteIngdntOfRecp(String menuName)throws SQLException{
		      Connection con = null;
		      PreparedStatement pstmt = null;
		      boolean flag=false;
		      try {
		         con = getConnection();
		         String deleteIngrdnt="delete from RECIPE_INGREDIENTS where MENU_NAME='"+menuName+"'";
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
						+ "where menu_name='"+menuName+"'";
				String selectMgrRecipeInfo2 ="select ri.INGREDIENT_NAME,i.PRICE "
						+ "from INGREDIENTS i,RECIPE_INGREDIENTS ri "
						+ "where(ri.INGREDIENT_NAME=i.INGREDIENT_NAME) "
						+ "and ri.MENU_NAME='"+menuName+"'";
				pstmt = con.prepareStatement(selectMgrRecipeInfo);
				pstmt2 = con.prepareStatement(selectMgrRecipeInfo2);
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
				}
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
			}
			return mriv;
	 }//selectIngdnt
	
	
	 // ������ �߰� â���� ī�װ��� ��� ��ȸ
	 /**
	  *	�ۼ���:ȫ��ȯ
	  *	   ��¥:05-25
	  *		Ư�̻���: �� �޼���� ���� IngrdntSchVO�� �Ű������� �޾� ī�װ��� ����,������ȸ�� �ϱ� ���� ���� �޼���
	  *		�ε� �Ű������� ������ �־� �������귣��,��������� ���� IngrdntCategVo�� ����� �Ű������� ���γ���
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
}//class

