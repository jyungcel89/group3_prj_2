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
	
	//*********************getConnection()�߰�(���� ������)*********************************
	private Connection getConnection() throws SQLException {
		Connection con = null;
		//�������� ���� ���� �Ͽ���!!!!!!! merge �Ͽ���
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
	 
	 
	 // ������ : ������ �߰� â���� ��� ����
	 /**
	 *  05-26 ȫ��ȯ �ڵ� �ۼ�
	 * 	 ��������: ������â���� ��Ḧ Ŭ�� �� ������ �ϱ� ���� �޼ҵ�� ������ ����ڵ带 �޾ƿ� ����ڵ带 ������ �����ǵ���
	 *  ������ �׷��� ���� �ʰ� ����,�޴��̸��� ��� �ִ� addRemoveIngrdntVO�� ����� �Ű������� �ް���
	 *  ����,�޴����� 2���� ���̸� �ĺ��� �����ϱ� ������ ��������...zzzzzzzzzz
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
	
	
	/* public static void main(String[] args){
		 //			System.out.println(IngdntDAO.getInstance().selectIngdntOfRecp("����¦��").toString());
					 IngrdntCategVO icv=new IngrdntCategVO("GS25","����");
					
		 //					System.out.println(IngdntDAO.getInstance().deleteIngdntOfRecp(iv));
					 try {
						 System.out.println(IngdntDAO.getInstance().selectIngdnt(icv));
//						 String[] arr={"��ġ"};
						 
//						 addRemoveIngrdntVO iv= new addRemoveIngrdntVO(arr,"����¦��");
//						System.out.println(IngdntDAO.getInstance().deleteIngdntOfRecp(iv));
//						 System.out.println(IngdntDAO.getInstance().insertIngdntOfRecp(iv));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "�ȴ�;");
						e.printStackTrace();
					
					}
					
	 }*/
}//class

