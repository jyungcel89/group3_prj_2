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
import org.w3c.dom.ls.LSInput;
import kr.co.sist.recipe.vo.BookmarkUpdateVO;
import kr.co.sist.recipe.vo.BookmarkVO;
import kr.co.sist.recipe.vo.MyRecipeVO;
import kr.co.sist.recipe.vo.MainRecipeVO;
 
/**
 * 2017-05-28 �߰� �� ����
 * @author JiYong
 *
 */ 
public class BookmarkDAO {
       private static BookmarkDAO book_dao;
       
       private BookmarkDAO(){
              
       }
       
       public static BookmarkDAO getInstance(){
              if(book_dao==null){
                     book_dao = new BookmarkDAO();
              }
              return book_dao;
       }//getInstance
       
       private Connection getConnection() throws SQLException {
             Connection con = null;
             Properties prop = new Properties();
             try {
//                      File file=new File("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/dao/recipe_db.properties");
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
             } catch (FileNotFoundException fe) {
                fe.printStackTrace();
             } catch (IOException ie) {
                ie.printStackTrace();
             } // end catch
             return con;
          }// getConnection
       
       // ���������� �ϸ�ũ����Ʈ ��ȸ
       public List<BookmarkVO> searchAll(String id) throws SQLException{
              List<BookmarkVO> mbmlist = new ArrayList<BookmarkVO>();
              Connection con=null;
              PreparedStatement pstmt= null;
              ResultSet rs = null;
              try {
                     con= getConnection();
                     
                     String selectQuery=
                                  "select  rr.menu_name, img, food_type, info, totalprice from reciperegister rr, bookmark bm where bm.id=? and rr.menu_name=bm.menu_name";
                     
                     pstmt = con.prepareStatement(selectQuery);
                     pstmt.setString(1, id);
                     rs = pstmt.executeQuery();
                     
                     //�����id �ΰ˻��� bookmark ������ VO�� ����
                     BookmarkVO bmvo=null;
                     while(rs.next()){
                           bmvo=new BookmarkVO();
                            bmvo.setMenuName(rs.getString("menu_name"));
                           bmvo.setImg(rs.getString("img"));
                            bmvo.setMenuType(rs.getString("food_type"));
                           bmvo.setMenuInfo(rs.getString("info"));
                            bmvo.setMenuPrice(rs.getString("totalprice"));
                           
                           mbmlist.add(bmvo);
                     }//end while
                     
              }finally {
                     if(rs!= null){ rs.close(); }
                     if(pstmt!= null){ pstmt.close(); }
                     if(con!= null){ con.close(); }
              }//end finally
              
              return mbmlist;
              
       }//searchAll
       
       ///////////////////////////////////////////// ���������� ��ǰ�����˾����� �ϸ�ũ üũ
       public boolean insertBookmark(BookmarkUpdateVO bmuvo) throws SQLException{
              boolean result=false;
              int flag=0;
              Connection con = null;
              PreparedStatement pstmt = null;
              
              try{
                     //1. ����̹��ε�
                     //2. Ŀ�ؼ� ���
                     con = getConnection();
                     //3. ������ ������ü ���
                     String insertBookmark = "insert into bookmark(id, menu_name) values(?,?)";
                     pstmt = con.prepareStatement(insertBookmark);
                     //4. �������� �� ������
                           // ���ε庯���� �� ����
                     pstmt.setString(1, bmuvo.getId());
                     pstmt.setString(2, bmuvo.getMenuName());
                     
                     flag=pstmt.executeUpdate();
                     if(flag!=0){ // flag�� 0�Ͻ� insert�� ����� ���ٴ� ���� ��������  ���� â�� �������Ѵ� - ����� �� ������ (�ߺ� �ԷµǴ� ��츦 �����Ѵ�)
                           result=true;
           }else{
                 result=false;
           }//end if
                     
              }finally {
                     //5.
                     if(pstmt!=null){ pstmt.close(); }
                     if(con!=null){ con.close(); }
              }//end finally
              
              return result;
       }//insertBookmark
       
       // ���������� ��ǰ�����˾����� �ϸ�ũ ����
       public boolean rmvBookmark(BookmarkUpdateVO bmuv) throws SQLException{
              boolean result=false;
              int flag=0;
              Connection con = null;
              PreparedStatement pstmt = null;
              
              try{
                     //1. ����̹��ε�
                     //2. Ŀ�ؼ� ���
                     con = getConnection();
                     //3. ������ ������ü ���
                     String deleteBookmark = "delete from bookmark where id=? and menu_name=?  ";
                     pstmt = con.prepareStatement(deleteBookmark);
                     //4. �������� �� ������
                           // ���ε庯���� �� ����
                     
                     pstmt.setString(1, bmuv.getId());
                     pstmt.setString(2, bmuv.getMenuName());
                     
                     flag=pstmt.executeUpdate();
                     
                     if(flag!=0){ // flag�� 0�Ͻ� delete�� ����� ���ٴ� ���� ��������  ���� â�� �������Ѵ� - ����� �� ������ (�ߺ� �ԷµǴ� ��츦 �����Ѵ�)
                           result=true;
           }else{
                 result=false;
           }//end if
                     
              }finally {
                     //5. �������
                     if(pstmt!=null){ pstmt.close(); }
                     if(con!=null){ con.close(); }
              }//end finally
              return result;
       }//rmvBookmark
//     
//     // ���������� �ϸ�ũ����Ʈ�� �޴� > �޴� ����â�� ������ �ֱ�
//     public ShowRecipeVO showBookmarkMenu(String menuName){
//           return null;
//           
//     }//showBookmarkMenu
       
       public boolean popUpChkBookmark( BookmarkUpdateVO  bmuvo) throws SQLException{
    	   boolean result=false;
    	   Connection con=null;
           PreparedStatement pstmt= null;
           ResultSet rs = null;
           try {
                  con= getConnection();
                  
                  String chkBmQuery=
                               "select * from bookmark where id=? and menu_name=?";
                  
                  pstmt = con.prepareStatement(chkBmQuery);
                  pstmt.setString(1, bmuvo.getId());
                  pstmt.setString(2, bmuvo.getMenuName());
                  rs = pstmt.executeQuery();
                  
                  if(rs.next()){
                	  result=true;
                  }
                  
           }finally {
                  if(rs!= null){ rs.close(); }
                  if(pstmt!= null){ pstmt.close(); }
                  if(con!= null){ con.close(); }
           }//end finally
    	   
    	   
    	   return result;
       }
       
       
       
       
       
       
       ///////////////////////////////////////////���� ���� ���
       /**
     * @param args
     */
    public static void main(String[] args){
              BookmarkDAO bd= BookmarkDAO.getInstance();
//           List<BookmarkVO> list = null;
//           try {
//                  list=bd.searchAll("duck");
//           } catch (SQLException e) {
//                  e.printStackTrace();
//           }
//                  for(BookmarkVO tmp : list){
//                  System.out.println(tmp.toString());
//}
       }
       
       
}//class
