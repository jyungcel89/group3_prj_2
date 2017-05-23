package kr.co.sist.recipe.dao;

import java.util.List;

import org.w3c.dom.ls.LSInput;

import kr.co.sist.recipe.vo.BookmarkVO;
import kr.co.sist.recipe.vo.ShowRecipeVO;

public class BookmarkDAO {

	private static BookmarkDAO book_dao;
	
	static BookmarkDAO getInstance(){
		if(book_dao==null){
			book_dao = new BookmarkDAO();
		}
		return book_dao;
	}//getInstance
	
	
	// 마이페이지 북마크리스트 조회
	public List<BookmarkVO> searchAll(String id){
		return null;
	}//searchAll
	
	// 마이페이지 상품보기팝업에서 북마크 체크
	public boolean insertBookmark(BookmarkVO bookVo){
		return false;
		
	}//insertBookmark
	
	// 마이페이지 상품보기팝업에서 북마크 해제
	public boolean rmvBookmark(BookmarkVO bookVo){
		return false;
		
	}//rmvBookmark
	
	// 마이페이지 북마크리스트의 메뉴 > 메뉴 정보창에 정보들 넣기
	public ShowRecipeVO showBookmarkMenu(String menuName){
		return null;
		
	}//showBookmarkMenu
	
	
	
	
}//class
