package kr.co.sist.recipe.dao;

import kr.co.sist.recipe.vo.ScoreVO;

public class ScoreDAO {

	private static ScoreDAO score_dao;
	
	static ScoreDAO getInstance(){
		if(score_dao==null){
			score_dao = new ScoreDAO();
		}
		return score_dao;
	}//getInstance
	
	// 상품보기 팝업 - 평점주기
	public boolean insertScore(ScoreVO scoreVo){
		return false;
	}//insertScore
	
	// 상품보기 팝업 - 평점 갱신
	public boolean updateScore(ScoreVO scoreVo){
		return false;
	}//updateScore
	
	// 메인폼 - 전체평점계산
	// 연산된 값을 가지고 메인폼에 보이기 위해서는 RecipeDAO전체조회 메소드
	// 에서 레시피테이블과 점수테이블을 gourpby(avg)조건으로 조인하는 쿼리필요
	// 테이블에 값저장X 값을 바로 가져오는 형식
	public double getAvg(String menuName){
		return 0;
	}//getAvg
	
}//class
