package kr.co.sist.recipe.dao;

import java.util.List;

import kr.co.sist.recipe.vo.AddRecipeVO;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.RecipeVO;
import kr.co.sist.recipe.vo.ShowRecipeVO;

public class RecipeDAO {

	private static MemberDAO mem_dao;
	
	static MemberDAO getInstance(){
		if(mem_dao==null){
			mem_dao = new MemberDAO();
		}//end if
		return mem_dao;
	}//getInstance
	
	// 레시피 전체 조회 + 선택조회
	public RecipeVO selectOneRecipe(String menuCode){
		return null;
	}//selectOneRecipe
	
	// 메인폼 - 전체레시피 조회
	public List<MainRecipeVO> ShowAllRecipe(){
		return null;
	}//ShowAllRecipe
	
	// 메인폼 - 최신메뉴
	public List<ShowRecipeVO> showNewRecipe(){
		return null;
	}//showNewRecipe

	// 관리자폼 - 모든레시피, 요청레시피 조회
	public List<MainRecipeVO> recipeList(String flag){
		return null;
	}//recipeList
	
	// 관리자 - 기존메뉴 삭제
	public boolean deleteRecipe(String menuName){
		return false;
	}//deleteRecipe
	
	// 회원 - 레시피 승인요청
	public boolean insertRecipe(AddRecipeVO addVo){
		return false;
	}//insertRecipe
	
	// 관리자 - 기존메뉴수정
	public boolean updateRecipe(AddRecipeVO addVo){
		return false;
	}//updateRecipe
	
	// 관리자 - 레시피요청 승인
	public boolean confirmRecipe(String menuCode){
		return false;
	}//confirmRecipe
	
	
}//class
