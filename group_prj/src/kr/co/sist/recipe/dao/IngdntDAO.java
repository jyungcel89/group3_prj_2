package kr.co.sist.recipe.dao;

import java.util.List;

import kr.co.sist.recipe.vo.IngdntSchVO;
import kr.co.sist.recipe.vo.IngdntVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;

public class IngdntDAO {
	
	private static IngdntDAO ing_dao;
	
	static IngdntDAO getInstance(){
		if(ing_dao==null){
			ing_dao = new IngdntDAO();
		}
		return ing_dao;
	}//getInstance
	
	// 레시피 당 재료조회
	 public List<ShowIngdntVO> selectIngdntOfRecp(IngdntVO ingVo){
		return null;
	 }//selectIngdntOfRecp
	 
	 // 사용자, 관리자 : 레시피 추가 창에서 재료선택후 메뉴당 재료 테이블에 추가
	 public boolean insertIngdntOfRecp(IngdntVO ingVo){
		return false;
	 }//insertIngdntOfRecp
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean updateIngdntOfRecp(IngdntVO ingVo){
		return false;
	 }//updateIngdntOfRecp
	 
	 // 관리자 : 레시피 추가 창에서 재료 수정
	 public boolean deleteIngdntOfRecp(int ingdntCode){
		 return false;
	 }//updateIngdntOfRecp
	 
	 // 레시피 추가 창에서 카테고리별 재료 조회
	 public List<ShowIngdntVO> selectIngdnt(IngdntSchVO ingSchVo){
		return null;
	 }//selectIngdnt
	  
}//class
