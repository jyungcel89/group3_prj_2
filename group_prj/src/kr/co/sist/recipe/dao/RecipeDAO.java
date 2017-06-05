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
 * <수정사항>
 * 1. flag 수정 : recipe_flag='Y' : 승인 / recipe_flag='N' : 요청거절 / recipe_flag='S' : 승인대기
 * 2. updateFlag method명 변경 : updateFlagY()
 * 3. updateFlagN method 추가 
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
                           
                           
                           // 드라이버 연동
                           try {
                                  Class.forName(driver);
                                  con= DriverManager.getConnection(url,id,pass);
                           } catch (ClassNotFoundException e) {
                                  e.printStackTrace();
                           }//end catch
                           
                     }else{
                           JOptionPane.showMessageDialog(null, "설정파일의 경로가 잘못되었습니다.");
                     }//end else
              } catch (FileNotFoundException e) {
                     e.printStackTrace();
              } catch (IOException e) {
                     e.printStackTrace();
              }//end catch
              
              return con;
       }//getConnection
       
       /**
        * 메인폼, 관리자폼, 마이페이지 폼 -  하나의 레시피 정보 조회
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
                     
                     // 선택된 한가지 메뉴에대한 정보를 RecipeVO에 저장
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
        * 마이페이지폼 - 내가 올린 레시피 조회
        * 메소드명 myRecipe
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
                     
                     //사용자id 로검색한 myRecipe를 VO에 저장
                     MyRecipeVO myrv=null;
                     while(rs.next()){
                           myrv=new MyRecipeVO();
                            myrv.setMenuName(rs.getString("menu_name"));
                           myrv.setMenuImg(rs.getString("img"));
                            myrv.setMenuType(rs.getString("food_type"));
                           myrv.setMenuInfo(rs.getString("info"));
                            myrv.setMenuPrice(rs.getString("totalprice"));
                           if(rs.getString("recipe_flag").equals("Y")){
                                  myrv.setFlag("승인");
                           }else if(rs.getString("recipe_flag").equals("S")){
                                  myrv.setFlag("승인대기");
                           }else{
                                  myrv.setFlag("요청거절");
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
        * 메인폼 - 전체레시피 정보 조회
        * 메소드명 showAllRecipe > selectAllRecipe로 변경
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
                     
                     // 검색조건(식사류, 안주류, 디저트, 분식)이 체크되어 있거나 검색어가 있으면 조건을 걸러 검색
                     // 검색조건이 없으면 전체 검색
                     StringBuilder sbSelectRecipe = new StringBuilder();
                     sbSelectRecipe.append("select menu_name, img, food_type, info, recipe_info, totalprice from reciperegister");
                     
                     if(mtv.getAnju().equals("") && mtv.getBunsik().equals("") && mtv.getDessert().equals("") && mtv.getMeal().equals("")){
                           // 메뉴타입이 체크되어있지않은 경우
                           if(!srchText.equals("")){
                                  // 메뉴타입이 체크되어 있지 않고 검색어를 갖는 경우
                                  sbSelectRecipe.append(" where menu_name like '%'||?||'%' and recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  pstmt.setString(1, srchText);
                                  rs=pstmt.executeQuery();
                           }else{
                                  // 메뉴타입이 체크되어 있지 않고 검색어를 갖지 않는 경우
                        	   	  sbSelectRecipe.append(" where recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  rs=pstmt.executeQuery();
                           }//end else
                     }else{
                           // 메뉴타입이 체크되어 있을 때
                           if(!srchText.equals("")){
                                  // 타입이 체크되어있고 검색어를 갖는 경우
                                  sbSelectRecipe.append(" where menu_name like '%'||?||'%' and food_type in(?,?,?,?)  and recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  pstmt.setString(1, srchText);
                                  
                                  pstmt.setString(2, mtv.getAnju());
                                  pstmt.setString(3, mtv.getMeal());
                                  pstmt.setString(4, mtv.getDessert());
                                  pstmt.setString(5, mtv.getBunsik());
                                  
                                  rs=pstmt.executeQuery();
                           }else{
                                  // 메뉴타입이 체크되어 있고 검색어를 갖지 않는 경우
                                  sbSelectRecipe.append(" where food_type in(?,?,?,?) and recipe_flag='Y'");
                                  pstmt= con.prepareStatement(sbSelectRecipe.toString());
                                  
                                  // 조건이 있을 때
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
        * 메인폼 - 최신메뉴 리스트 이름, 이미지 정보
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
                     
                     // 등록날짜 기준으로 정렬
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
       
       // 존재하는 모든 메뉴
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
        * 관리자폼
        *  - jtMenuList, jtMenuRequest DB조회
        * recipe_flag='Y' : 승인 / recipe_flag='N' : 요청거절 / recipe_flag='S' : 승인대기
        *  - flag="Y" 일 때 모든레시피, flag="S" 일 때 요청레시피 테이블의 리스트정보
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
                     
                     // 바인드 변수 flag조건에 따라서 이벤트 처리
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
        * 회원
        *  - 요청거절 메뉴 삭제
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
        * 관리자
        *  - 기존메뉴 삭제
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
        * 회원 삭제 시 해당 회원이 추가한 레시피 삭제하는 method
        *  - 회원 id 받아서 삭제
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
        * 관리자폼 > 추가/수정메뉴폼 : 수정버튼
        * 관리자폼에서 기존메뉴를 수정하면 정보가 업데이트
        * 원래있던 메뉴이름을 가져와 바꾼다.
        * 관리자는 메뉴이름은 변경불가.
        *
        * ---------------------------------변경사항----------------------------------------
        * 매개변수를 AddRecipeVO addVo > String menuName으로 변경
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
        * 관리자폼
        *  - 요청승인 버튼
        *  - 승인처리> reciperegister테이블의 flag컬럼을 "S"에서 "Y"로 변경해준다.
        * ---------------------------------변경사항----------------------------------------
        * 메소드명 insertRecipe > updateFlag 로 변경
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
        * 관리자폼
        *  - 요청거절 버튼
        *  - 승인처리> reciperegister테이블의 flag컬럼을 "S"에서 "N"로 변경해준다.
        * ---------------------------------변경사항----------------------------------------
        * 메소드명 insertRecipe > updateFlag 로 변경
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
        * 회원 - 레시피 승인요청버튼을 누르면 테이블에 flag가 S인 상태로 추가
        *
        * ---------------------------------변경사항----------------------------------------
        * 메소드명 requestRecipe > insertRecipe으로 변경
        * 매개변수 id 추가
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
                     pstmt.setString(7, id); //id는 mainForm에서 로그인한 id를 가져옴
                     
                     pstmt.executeUpdate();
              }finally {
                     if(pstmt!= null){ pstmt.close(); }//end if
                     if(con!= null){ con.close(); }//end if
              }//end finally
              
              return true;
       }//insertRecipe
       
}//class