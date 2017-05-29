package kr.co.sist.recipe.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.w3c.dom.ls.LSInput;

import kr.co.sist.recipe.vo.BookmarkVO;
import kr.co.sist.recipe.vo.ShowRecipeVO;
 
/**
 * 2017-05-28 추가 및 수정
 * @author JiYong
 *
 */
public class BookmarkDAO {

	private static BookmarkDAO book_dao;
	
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
			 File file=new File("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/dao/recipe_db.properties");
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

	      } catch (FileNotFoundException fe) {
	         fe.printStackTrace();
	      } catch (IOException ie) {
	         ie.printStackTrace();
	      } // end catch

	      return con;
	   }// getConnection
	
	// 마이페이지 북마크리스트 조회
	public List<BookmarkVO> searchAll(String id){
		return null;
	}//searchAll
	
	// 마이페이지 상품보기팝업에서 북마크 체크
	public boolean insertBookmark(BookmarkVO bmv) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. 드라이버로딩
			//2. 커넥션 얻기
			con = getConnection();
			//3. 쿼리문 생성객체 얻기
			String insertBookmark = "insert into bookmark(id, menu_name) values(?,?)";
			pstmt = con.prepareStatement(insertBookmark);
			//4. 쿼리수행 후 결과얻기
				// 바인드변수에 값 설정
			pstmt.setString(1, bmv.getId());
			pstmt.setString(2, bmv.getMenuName());
			
			flag=pstmt.executeUpdate();

			if(flag!=0){ // flag가 0일시 insert된 결과가 없다는 것을 뜻함으로  오류 창을 띄워줘야한다 - 경우의 수 없을듯 (중복 입력되는 경우를 막아한다)
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
	
	// 마이페이지 상품보기팝업에서 북마크 해제
	public boolean rmvBookmark(BookmarkVO bmv) throws SQLException{
		boolean result=false;
		int flag=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//1. 드라이버로딩
			//2. 커넥션 얻기
			con = getConnection();
			//3. 쿼리문 생성객체 얻기
			String deleteBookmark = "delete from bookmark where id=? and menu_name=?  ";
			pstmt = con.prepareStatement(deleteBookmark);
			//4. 쿼리수행 후 결과얻기
				// 바인드변수에 값 설정
			
			pstmt.setString(1, bmv.getId());
			pstmt.setString(2, bmv.getMenuName());
			
			flag=pstmt.executeUpdate();
			 

			if(flag!=0){ // flag가 0일시 delete된 결과가 없다는 것을 뜻함으로  오류 창을 띄워줘야한다 - 경우의 수 없을듯 (중복 입력되는 경우를 막아한다)
				result=true;
           }else{
                 result=false;
           }//end if
			
		}finally {
			//5. 연결끊기
			if(pstmt!=null){ pstmt.close(); }
			if(con!=null){ con.close(); }
		}//end finally
		return result;
	}//rmvBookmark
	
	// 마이페이지 북마크리스트의 메뉴 > 메뉴 정보창에 정보들 넣기
	public ShowRecipeVO showBookmarkMenu(String menuName){
		return null;
		 
	}//showBookmarkMenu
	
	
	
	
}//class
