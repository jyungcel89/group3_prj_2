package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AddRecipeEvt extends WindowAdapter implements ActionListener {
	
	////////////////////////////////////////// AddRecipeForm
	// 재료추가 수행 (add버튼)
	public void addIngdnt(){
		
	}//addIngdnt
	
	// 재료삭제 수행 (del버튼)
	public void rmvIngdnt(){
		
	}//rmvIngdnt
	
	// 이미지추가 수행 (add버튼)
	public void addImg(){
		
	}//addImg

	// 관리자에게 요청 수행 (request버튼)
	public void reqRecipe(){
		
	}//reqRecipe
 
	// 재료 조회 수행 < IngdntDAO에서 selectIngdnt(IngdntSchVO:편의점, 재료타입) :  List<showIngredntVO>
	public void searchIngdnt(){
		
	}//searchIngdnt
	
	/////////////////////////////////////////// MgrForm
	//관리자가 레시피 수정 수행 (edit버튼)
	public void editMgr(){
		 
	}//
	
	// 닫기
	public void close(){
		
	}//

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	

}//class
