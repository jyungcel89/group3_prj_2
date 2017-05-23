package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MgrPageEvt extends WindowAdapter implements ActionListener {

	//--------------------------전체메뉴관리 탭
	// 전체 메뉴리스트 조회
		public void allRecipeList(){
			
		}//allRecipeList
		
	// 요청관리 리스트조회
		public void requestList(){
			
		}//requestList
	
	// 기존레시피 삭제 : 상위remove버튼
		public void rmvRecipe(){
			
		}//rmvRecipe
		
	// 요청레시피 거절 : 하위remove버튼
		public void rmvReqRecipe(){
			
		}//rmvReqRecipe
		
	// 요청레시피 승인 : submit버튼
		public void confirmReqRecipe(){
			
		}//confirmReqRecipe

	//--------------------------회원관리 탭
	// 회원관리 리스트
		public void memberList(){
			
		}//memberList
		
	// 회원탈퇴 시키기 : remove버튼
		public void rmvMember(){
			
		}//rmvMember
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}//class
