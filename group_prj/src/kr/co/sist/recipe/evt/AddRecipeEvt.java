package kr.co.sist.recipe.evt;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.vo.AddRecipeVO;
import kr.co.sist.recipe.vo.IngdntVO;
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.addRemoveIngrdntVO;

public class AddRecipeEvt extends WindowAdapter implements ActionListener {
	private IngdntDAO ida;
	private AddRecipeForm arf;
	private IngrdntCategVO icv;
	private addRemoveIngrdntVO arv;
	private String file;
	public AddRecipeEvt(AddRecipeForm arf){
		this.arf=arf;
		ida=IngdntDAO.getInstance();
	}
	////////////////////////////////////////// AddRecipeForm
	// 재료추가 수행 (add버튼)
	public void addIngdnt(){
		
		Object[] rowData=new Object[3];
		DefaultTableModel dtmIngrdnt=arf.getDtmAddedIngrednt();
		JTable table=arf.getJtIngrednt();
		int select=table.getSelectedRow();
		//번호,이미지,메뉴코드","메뉴","설명","가격,
			rowData[0]=table.getValueAt(select,0);
			rowData[1]=table.getValueAt(select,1);
			dtmIngrdnt.addRow(rowData);
			
	}//addIngdnt
	
	// 재료삭제 수행 (del버튼)
	public void rmvIngdnt(){
		DefaultTableModel dtm=(DefaultTableModel)arf.getJtaddedIngrednt().getModel();
		dtm.removeRow(arf.getJtaddedIngrednt().getSelectedRow());
	}//rmvIngdnt
	
	// 이미지추가 수행 (add버튼)
	public void addImg(){
		FileDialog fdImg=new FileDialog(arf,"레시피 이미지선택!", FileDialog.LOAD);
		System.out.println("ㅎㅎ");
		
		fdImg.setVisible(true);
		String path=fdImg.getDirectory();
		file=fdImg.getFile();
		if(file != null){
			String vaildFile="jpg,gif,png,bmp";
			if(!vaildFile.contains(file.substring(file.lastIndexOf(".")+1))){
				JOptionPane.showMessageDialog(arf, "선택하신 파일은 이미지가 아닙니다.");
				return;
			}//end if
			ImageIcon temp=new ImageIcon(path+file);
			arf.getLblImg().setIcon(temp);
		}//end if
	}//addImg
	public void reqRecipe(){
		
		String menuName=arf.getJtfRecipeName().getText();
		String img=file;
		String foodType=arf.getJcbCateg().getSelectedItem().toString();
		String info=arf.getJtaInfo().getText();
		String recipe_make=arf.getJtaWriteRecipe().getText();
		String id="mgr";
		JTable table=arf.getJtaddedIngrednt();
		int[] priceArr=new int[table.getRowCount()];
		int totalPrice=0;
			for(int i=0; i<table.getRowCount();i++){
					priceArr[i]=Integer.parseInt(table.getValueAt(i,1).toString());
					totalPrice=priceArr[i]+totalPrice;
			}
			arf.getLblTotalPrice().setText(Integer.toString(totalPrice));
		AddRecipeVO arv= new AddRecipeVO(menuName,img,foodType,info,recipe_make,totalPrice,id);
		if(arv!=null){
		try {
			ida.insertRecipe(arv);
			System.out.println("성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			JOptionPane.showMessageDialog(arf,"제대로 입력해...");
		}
	}
	// 관리자에게 요청 수행 (request버튼)
	public void reqRecipeIngrdnt(){
		
		JTable table=arf.getJtaddedIngrednt();
		
		String[] ingrdntName=new String[table.getRowCount()];
		String menuName=arf.getJtfRecipeName().getText();
		
		if(ingrdntName.length!=0&&!menuName.equals("")){
		for(int i=0; i<table.getRowCount();i++){
		ingrdntName[i]=table.getValueAt(i,0).toString();
		}
		arv=new addRemoveIngrdntVO(ingrdntName, menuName);
		try {
			ida.insertIngdntOfRecp(arv);
			System.out.println("성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			JOptionPane.showMessageDialog(arf, "재료명을 입력하거나 재료를 추가해주세요");
			return;
		}
	}//reqRecipe
 
	// 재료 조회 수행 < IngdntDAO에서 selectIngdnt(IngdntSchVO:편의점, 재료타입) :  List<showIngredntVO>
	public void searchIngdnt(IngrdntCategVO icv){
		DefaultTableModel dtm=(DefaultTableModel)arf.getJtIngrednt().getModel();
		dtm.setNumRows(0);
		String ingrdnt=arf.getJcbIngrdntSort().getItemAt(arf.getJcbIngrdntSort().getSelectedIndex());
		String brand=arf.getJcbStore().getItemAt(arf.getJcbStore().getSelectedIndex());
		icv=new IngrdntCategVO(brand, ingrdnt);
		try{
		List<ShowIngdntVO> lstMenu=ida.selectIngdnt(icv);
		Object[] rowMenu=new Object[3];
		DefaultTableModel dtmMenu=arf.getDtmIngrednt();
		System.out.println(lstMenu.size());
		ShowIngdntVO si=null;
		//번호,이미지,메뉴코드","메뉴","설명","가격,
		for( int i=0; i<lstMenu.size(); i++ ){
			si=lstMenu.get(i);
			rowMenu[0]=si.getIngrdntName();
			rowMenu[1]=si.getIngrdntPrice();
			
			dtmMenu.addRow(rowMenu);
		}
		}catch(SQLException se){
			se.printStackTrace();
		}
	}//searchIngdnt
	
	/////////////////////////////////////////// MgrForm
	//관리자가 레시피 수정 수행 (edit버튼)
	public void editMgr(){
		 
	}//
	// 닫기
	public void close(){
		arf.dispose();
	}//

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==arf.getJbSearch()){
			
			searchIngdnt(icv);
		}
		if(e.getSource()==arf.getJbAddIngrednt()){
				addIngdnt();
		}
		if(e.getSource()==arf.getJbAddImg()){
				addImg();
		}
		if(e.getSource()==arf.getJbRequest()){
						reqRecipe();
			//			reqRecipeIngrdnt();
		}
		if(e.getSource()==arf.getJbClose()){
			close();
		}
		if(e.getSource()==arf.getJbRmvIngrednt()){
			rmvIngdnt();
		}
	}
	

}//class