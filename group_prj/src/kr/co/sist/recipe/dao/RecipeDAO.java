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
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MenuTypeVO;
import kr.co.sist.recipe.vo.MyRecipeVO;
import kr.co.sist.recipe.vo.RecipeInfoUpdateVO;
/**
 * <��������>
 * 1. flag ���� : recipe_flag='Y' : ���� / recipe_flag='N' : ��û���� / recipe_flag='S' : ���δ��
 * 2. updateFlag method�� ���� : updateFlagY()
 * 3. updateFlagN method �߰� 
 * @author user
 *
 */
public class RecipeDAO {
       private static RecipeDAO rcp_dao;
       
       private RecipeDAO(){
              
       }//RecipeDAO
       
       public static RecipeDAO getInstance(){
              if(rcp_dao==null){
                     rcp_dao = new RecipeDAO();
              }//end if
              return rcp_dao;
       }//getInstance
       
       private Connection getConnection() throws SQLException{
              Connection con=null;
              
              Properties prop = new Properties();
              try {
            	  File file=new File(System.getProperty("user.dir")+"/src/kr/co/sist/recipe/dao/recipe_db.properties");
                     
                     if(file.exists()){
                           prop.load(new FileInputStream(file));
                           String driver= prop.getProperty("driver");
                           String url= prop.getProperty("url");
                           String id= prop.getProperty("dboid");
                           String pass= prop.getProperty("dbopwd");
                           
                           
                           // ����̹� ����
                           try {
                                  Class.forName(driver);
                                  con= DriverManager.getConnection(url,id,pass);
                           } catch (ClassNotFoundException e) {
                                  e.printStackTrace();
                           }//end catch
                           
                     }else{
                           JOptionPane.showMessageDialog(null, "���������� ��ΰ� �߸��Ǿ����ϴ�.");
                     }//end else
              } catch (FileNotFoundException e) {
                     e.printStackTrace();
              } catch (IOException e) {
                     e.printStackTrace();
              }//end catch
              
              return con;
       }//getConnection
       
       /**
        * ������, ��������, ���������� �� -  �ϳ��� ������ ���� ��ȸ
        * @param menuCode
        * @return
        */
       public MainRecipeVO selectOneRecipe(String menuName) throws SQLException{
              MainRecipeVO mrv = new MainRecipeVO();
              Connection con = null;
              PreparedStatement pstmt= null;
              ResultSet rs= null;
              
              try {
                     con= getConnection();
                     
                     String selectQuery=
                                  "select menu_name, img, totalprice, food_type, info, recipe_info from reciperegister where menu_name=?";
                     
                     pstmt = con.prepareStatement(selectQuery);
                     pstmt.setString(1, menuName);
                     rs = pstmt.executeQuery();
                     
                     // ���õ� �Ѱ��� �޴������� ������ RecipeVO�� ����
                     while(rs.next()){
                           mrv.setMenuName(menuName);
                           mrv.setMenuImg(rs.getString("img"));
                           mrv.setMenuPrice(rs.getString("totalprice"));
                           mrv.setMenuType(rs.getString("food_type"));
                           mrv.setMenuSimpeInfo(rs.getString("info"));
                            mrv.setMenuDetailInfo(rs.getString("recipe_info"));
                     }//end while
                     
              }finally {
                     if(rs!= null){ rs.close(); }
                     if(pstmt!= null){ pstmt.close(); }
                     if(con!= null){ con.close(); }
              }//end finally
              
              return mrv;
              
       }//selectOneRecipe
       
       /**
        * ������������ - ���� �ø� ������ ��ȸ
        * �޼ҵ�� myRecipe
        * @return
        * @throws SQLException
        */
       public List<MyRecipeVO> myRecipe(String id) throws SQLException{
              List<MyRecipeVO> myRecipelist = new ArrayList<MyRecipeVO>();
              Connection con=null;
              PreparedStatement pstmt= null;
              ResultSet rs = null;
              try {
                     con= getConnection();
                     
                     String selectQuery=
                                  "select menu_name, img, food_type, info, totalprice, recipe_flag from reciperegister where id=?";
                     
                     pstmt = con.prepareStatement(selectQuery);
                     pstmt.setString(1, id);
                     rs = pstmt.executeQuery();
                     
                     //�����id �ΰ˻��� myRecipe�� VO�� ����
                     MyRecipeVO myrv=null;
                     while(rs.next()){
                           myrv=new MyRecipeVO();
                            myrv.setMenuName(rs.getString("menu_name"));
                           myrv.setMenuImg(rs.getString("img"));
                            myrv.setMenuType(rs.getString("food_type"));
                           myrv.setMenuInfo(rs.getString("info"));
                            myrv.setMenuPrice(rs.getString("totalprice"));
                           if(rs.getString("recipe_flag").equals("Y")){
                                  myrv.setFlag("����");
                           }else if(rs.getString("recipe_flag").equals("S")){
                                  myrv.setFlag("���δ��");
                           }else{
                                  myrv.setFlag("��û����");
                           }//end if
                           
                           myRecipelist.add(myrv);
                     }//end while
                     
              }finally {
                     if(rs!= null){ rs.close(); }
                     if(pstmt!= null){ pstmt.close(); }
                     if(con!= null){ con.close(); }
              }//end finally
              
              return myRecipelist;
       }//myRecipe
       
       
       /**
        * ������ - ��ü������ ���� ��ȸ
        * �޼ҵ�� showAllRecipe > selectAllRecipe�� ����
        * @return
        * @throws SQLException
        */
       public List<MainRecipeVO> selectAllRecipe(MenuTypeVO mtv, String srchText) throws SQLException{
              List<MainRecipeVO> recpList = new ArrayList<MainRecipeVO>();
              Connection con=null;
              PreparedStatement pstmt= null;
              ResultSet rs = null;
              
              try{
                     con= getConnection();
                     
                     // �˻�����(�Ļ��, ���ַ�, ����Ʈ, �н�)�� üũ�Ǿ� �ְų� �˻�� ������ ������ �ɷ� �˻�
                     // �˻������� ������ ��ü �˻�
                     StringBuilder sbSelectRecipe = new StringBuilder();
                     sbSelectRecipe.append("select menu_name, img, food_type, info, recipe_info, totalprice from reciperegister");
                     
                     if(mtv.getAnju().equals("") && mtv.getBunsik().equals("") && mtv.getDessert().equals("") && mtv.getMeal().equals("")){
                           // �޴�Ÿ���� üũ�Ǿ��������� ���
                           if(!srchText.equals("")){
                                  // �޴�Ÿ���� üũ�Ǿ� ���� �ʰ� �˻�� ���� ���
                                  sbSelectRecipe.append(" where menu_name like '%'||?||'%' and recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  pstmt.setString(1, srchText);
                                  rs=pstmt.executeQuery();
                           }else{
                                  // �޴�Ÿ���� üũ�Ǿ� ���� �ʰ� �˻�� ���� �ʴ� ���
                        	   	  sbSelectRecipe.append(" where recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  rs=pstmt.executeQuery();
                           }//end else
                     }else{
                           // �޴�Ÿ���� üũ�Ǿ� ���� ��
                           if(!srchText.equals("")){
                                  // Ÿ���� üũ�Ǿ��ְ� �˻�� ���� ���
                                  sbSelectRecipe.append(" where menu_name like '%'||?||'%' and food_type in(?,?,?,?)  and recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  pstmt.setString(1, srchText);
                                  
                                  pstmt.setString(2, mtv.getAnju());
                                  pstmt.setString(3, mtv.getMeal());
                                  pstmt.setString(4, mtv.getDessert());
                                  pstmt.setString(5, mtv.getBunsik());
                                  
                                  rs=pstmt.executeQuery();
                           }else{
                                  // �޴�Ÿ���� üũ�Ǿ� �ְ� �˻�� ���� �ʴ� ���
                                  sbSelectRecipe.append(" where food_type in(?,?,?,?) and recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  
                                  // ������ ���� ��
                                  pstmt.setString(1, mtv.getAnju());
                                  pstmt.setString(2, mtv.getMeal());
                                  pstmt.setString(3, mtv.getDessert());
                                  pstmt.setString(4, mtv.getBunsik());
                                  
                                  rs=pstmt.executeQuery();
                           }//end else
                     }//end else
                     
                     MainRecipeVO mrv = null;
                     while(rs.next()){
                           mrv = new MainRecipeVO();
                            mrv.setMenuName(rs.getString("menu_name"));
                           mrv.setMenuImg(rs.getString("img"));
                           mrv.setMenuType(rs.getString("food_type"));
                           mrv.setMenuSimpeInfo(rs.getString("info"));
                           mrv.setMenuPrice(rs.getString("totalprice"));
                            mrv.setMenuDetailInfo(rs.getString("recipe_info"));
                           
                           recpList.add(mrv);
                     }//end while
                     
              }finally {
                     if(rs!= null){ rs.close(); }//end if
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              
              return recpList;
       }//ShowAllRecipe
       
       /**
        * ������ - �ֽŸ޴� ����Ʈ �̸�, �̹��� ����
        * @return List<ShowRecipeVO>
        * @throws SQLException
        */
       public List<MainRecipeVO> showNewRecipe() throws SQLException{
              List<MainRecipeVO> recntImgList = new ArrayList<MainRecipeVO>();
              Connection con= null;
              PreparedStatement pstmt = null;
              ResultSet rs = null;
              try{
                     con = getConnection();
                     
                     // ��ϳ�¥ �������� ����
                     String query="select menu_name, img, totalprice, food_type, info, recipe_info from reciperegister where recipe_flag='Y' order by inputdate";
                     pstmt = con.prepareStatement(query);
                     
                     rs = pstmt.executeQuery();
                     
                     MainRecipeVO mrv = null;
                     while(rs.next()){
                           mrv = new MainRecipeVO();
                           mrv.setMenuName(rs.getString("menu_name"));
                           mrv.setMenuImg(rs.getString("img"));
                           mrv.setMenuPrice(rs.getString("totalprice"));
                           mrv.setMenuType(rs.getString("food_type"));
                           mrv.setMenuSimpeInfo(rs.getString("info"));
                           mrv.setMenuDetailInfo(rs.getString("recipe_info"));
                           
                           recntImgList.add(mrv);
                     }
              }finally {
                     if(rs!= null){ rs.close(); }//end if
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//finally
              
              return recntImgList;
       }//showNewRecipe
       
       // �����ϴ� ��� �޴�
       public List<String> getAllMenuName() throws SQLException{
    	   Connection con= null;
           PreparedStatement pstmt = null;
           ResultSet rs = null;
           List<String> nameList = new ArrayList<String>();
           try{
        	    con = getConnection();
        	    String query="select menu_name from reciperegister where recipe_flag='Y'";
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();
        	    
                while(rs.next()){
                	nameList.add(rs.getString("menu_name"));
                }//end while
                
           }finally{
        	   if(rs!= null){ rs.close(); }//end if
               if(pstmt!= null){ pstmt.close(); }//end if
               if(con!= null){ con.close(); }//end if
           }//end finally
           return nameList;
       }//getAllMenuName
       
       /**
        * ��������
        *  - jtMenuList, jtMenuRequest DB��ȸ
        * recipe_flag='Y' : ���� / recipe_flag='N' : ��û���� / recipe_flag='S' : ���δ��
        *  - flag="Y" �� �� ��緹����, flag="S" �� �� ��û������ ���̺��� ����Ʈ����
        * @param flag
        * @return List<MainRecipeVO>
        * @throws SQLException
        */
       public List<MainRecipeVO> recipeList(String flag) throws SQLException{
              List<MainRecipeVO> list = new ArrayList<MainRecipeVO>();
              Connection con=null;
              PreparedStatement pstmt = null;
              ResultSet rs = null;
              
              try{
                     con = getConnection();
                     
                     String selectQuery=
                                  "select menu_name, img, food_type, info, recipe_info, totalprice from reciperegister where recipe_flag=?";
                     pstmt = con.prepareStatement(selectQuery);
                     
                     // ���ε� ���� flag���ǿ� ���� �̺�Ʈ ó��
                     pstmt.setString(1, flag);
                     rs = pstmt.executeQuery();
                     
                     MainRecipeVO mrv = null;
                     while(rs.next()){
                           mrv = new MainRecipeVO();
                           
                            mrv.setMenuName(rs.getString("menu_name"));
                           mrv.setMenuImg(rs.getString("img"));
                           mrv.setMenuType(rs.getString("food_type"));
                           mrv.setMenuSimpeInfo(rs.getString("info"));
                           mrv.setMenuPrice(rs.getString("totalprice"));
                            mrv.setMenuDetailInfo(rs.getString("recipe_info"));
                           
                           list.add(mrv);
                     }//end while
                     
              }finally{
                     if(rs!= null){ rs.close(); }//end if
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              return list;
       }//recipeList
       
       /**
        * ȸ��
        *  - ��û���� �޴� ����
        * @param menuName
        * @return boolean
        */
       public boolean deleteRecipeUser(String menuName) throws SQLException{
              Connection con=null;
              PreparedStatement pstmt = null;
              
              try{
                     con = getConnection();
                     
                     String query="delete from reciperegister where menu_name=? and recipe_flag='N'";
                     pstmt = con.prepareStatement(query);
                     
                     pstmt.setString(1, menuName);
                     
                     pstmt.executeUpdate();
              }finally {
                     if(pstmt!= null){ pstmt.close(); }
                     if(con!= null){ con.close(); }
              }//end finally
              
              return true;
       }//deleteRecipe
       /**
        * ������
        *  - �����޴� ����
        * @param menuName
        * @return boolean
        */
       public boolean deleteRecipe(String menuName) throws SQLException{
          Connection con=null;
          PreparedStatement pstmt = null;
          
          try{
	             con = getConnection();
	             
	             String query="delete from reciperegister where menu_name=? and recipe_flag='Y'";
	             pstmt = con.prepareStatement(query);
	             
	             pstmt.setString(1, menuName);
	             
	             pstmt.executeUpdate();
          }finally {
	             if(pstmt!= null){ pstmt.close(); }
	             if(con!= null){ con.close(); }
          }//end finally
          
          return true;
       }//deleteRecipe
       
       
       /**
        * ȸ�� ���� �� �ش� ȸ���� �߰��� ������ �����ϴ� method
        *  - ȸ�� id �޾Ƽ� ����
	    * @param id
	    * @return
	    * @throws SQLException
	    */
	    public boolean deleteRecipeMem(String id) throws SQLException{
	    	   Connection con=null;
	    	   PreparedStatement pstmt = null;
	    	   
	    	   try{
	    		   con = getConnection();
	    		   
	    		   String query=
	    				   "delete from reciperegister where id=?";
	    		   pstmt = con.prepareStatement(query);
	    		   
	    		   pstmt.setString(1, id);
	    		   
	    		   pstmt.executeUpdate();
	    	   }finally {
	    		   if(pstmt!= null){ pstmt.close(); }
	    		   if(con!= null){ con.close(); }
	    	   }//end finally
	    	   
	    	   return true;
	    }//deleteRecipe
       
       /**
        * �������� > �߰�/�����޴��� : ������ư
        * ������������ �����޴��� �����ϸ� ������ ������Ʈ
        * �����ִ� �޴��̸��� ������ �ٲ۴�.
        * �����ڴ� �޴��̸��� ����Ұ�.
        *
        * ---------------------------------�������----------------------------------------
        * �Ű������� AddRecipeVO addVo > String menuName���� ����
        * @param addVo
        * @return
        */
       public boolean updateRecipe(RecipeInfoUpdateVO updateVo, String menuName) throws SQLException{
              Connection con=null;
              PreparedStatement pstmt = null;
              
              try{
                     con = getConnection();
                     
                     String query="update reciperegister set img=?, food_type=?, info=?, recipe_info=? where menu_name=?";
                     pstmt = con.prepareStatement(query);
                     
                     pstmt.setString(1, updateVo.getMenuImg());
                     pstmt.setString(2, updateVo.getMenuType());
                     pstmt.setString(3, updateVo.getMenuSimpleInfo());
                     pstmt.setString(4, updateVo.getMenuDetailInfo());
                     pstmt.setString(5, menuName);
                     
                     pstmt.executeUpdate();
              }finally {
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              
              return true;
       }//updateRecipe
       
       /**
        * ��������
        *  - ��û���� ��ư
        *  - ����ó��> reciperegister���̺��� flag�÷��� "S"���� "Y"�� �������ش�.
        * ---------------------------------�������----------------------------------------
        * �޼ҵ�� insertRecipe > updateFlag �� ����
        * @param menuCode
        * @return
        */
       public boolean updateFlagY(String menuName) throws SQLException{
              Connection con=null;
              PreparedStatement pstmt = null;
              
              try{
                     con = getConnection();
                     
                     String query="update reciperegister set recipe_flag='Y' where menu_name=? and recipe_flag='S'";
                     pstmt = con.prepareStatement(query);
                     
                     pstmt.setString(1, menuName);
                     
                     pstmt.executeUpdate();
              }finally {
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              
              return true;
       }//updateFlagY
       
       /**
        * ��������
        *  - ��û���� ��ư
        *  - ����ó��> reciperegister���̺��� flag�÷��� "S"���� "N"�� �������ش�.
        * ---------------------------------�������----------------------------------------
        * �޼ҵ�� insertRecipe > updateFlag �� ����
        * @param menuCode
        * @return
        */
       public boolean updateFlagN(String menuName) throws SQLException{
              Connection con=null;
              PreparedStatement pstmt = null;
              
              try{
                     con = getConnection();
                     
                     String query="update reciperegister set recipe_flag='N' where menu_name=? and recipe_flag='S'";
                     pstmt = con.prepareStatement(query);
                     
                     pstmt.setString(1, menuName);
                     
                     pstmt.executeUpdate();
              }finally {
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              
              return true;
       }//updateFlagY
       
       
       /**
        * ȸ�� - ������ ���ο�û��ư�� ������ ���̺� flag�� S�� ���·� �߰�
        *
        * ---------------------------------�������----------------------------------------
        * �޼ҵ�� requestRecipe > insertRecipe���� ����
        * �Ű����� id �߰�
        * @param addVo(AddRecipeVO) : String menuName, menuImg, menuInfo, menuType, id
        *                                                                          Date inputDate
        * @return boolean
        */
       public boolean insertRecipe(MainRecipeVO mrv, String id) throws SQLException{
              Connection con=null;
              PreparedStatement pstmt = null;
              
              try{
                     con = getConnection();
                     
                     String query="insert into reciperegister values(?, ?, ?, ?, ?, ?, ?, sysdate, 'S')";
                     pstmt = con.prepareStatement(query);
                     
                     pstmt.setString(1, mrv.getMenuName());
                     pstmt.setString(2, mrv.getMenuImg());
                     pstmt.setString(3, mrv.getMenuType());
                     pstmt.setString(4, mrv.getMenuSimpeInfo());
                     pstmt.setString(5, mrv.getMenuDetailInfo());
                     pstmt.setString(6, mrv.getMenuPrice());
                     pstmt.setString(7, id); //id�� mainForm���� �α����� id�� ������
                     
                     pstmt.executeUpdate();
              }finally {
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              
              return true;
       }//insertRecipe
       
}//class