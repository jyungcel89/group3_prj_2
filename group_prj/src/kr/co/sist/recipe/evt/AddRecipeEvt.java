package kr.co.sist.recipe.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.view.MgrPageForm;
import kr.co.sist.recipe.vo.AddRecipeVO;
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.MgrRecipeInfoVO;
import kr.co.sist.recipe.vo.MgrUpdateIngrdntVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.addRemoveIngrdntVO;

public class AddRecipeEvt extends WindowAdapter implements ActionListener {
	private IngdntDAO ida;
	private AddRecipeForm arf;
	private IngrdntCategVO icv;
	private String file;
	private MainFormEvt mfe;
	public MgrPageForm mpf;
	@SuppressWarnings("static-access")
	public AddRecipeEvt(AddRecipeForm arf){
		this.arf=arf;
		ida=IngdntDAO.getInstance();
		selectMgrRecipeInfo();
		System.out.println();
		String id=mfe.logId;
		if(id.equals("mgr")){
			arf.getJbRequest().setVisible(false);
			arf.getJbMgr().setVisible(true);
			arf.getJtfRecipeName().setEditable(false);
		}
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
			
			JTable table2=arf.getJtaddedIngrednt();
			int[] priceArr=new int[table2.getRowCount()];
			 int totalPrice=0;
				for(int i=0; i<table2.getRowCount();i++){
						priceArr[i]=Integer.parseInt(table2.getValueAt(i,1).toString());
						totalPrice=priceArr[i]+totalPrice;
				}
				arf.getLblTotalPrice().setText(Integer.toString(totalPrice));
				
				
	}//addIngdnt
	
	// 재료삭제 수행 (del버튼)
	public void rmvIngdnt(){
		DefaultTableModel dtm=(DefaultTableModel)arf.getJtaddedIngrednt().getModel();
		int minusPrice=Integer.parseInt(arf.getJtaddedIngrednt().getValueAt(arf.getJtaddedIngrednt().getSelectedRow(),1).toString());
		dtm.removeRow(arf.getJtaddedIngrednt().getSelectedRow());
		int labelPrice=Integer.parseInt(arf.getLblTotalPrice().getText());
		if(labelPrice>0){
			int totalPrice=Integer.parseInt(arf.getLblTotalPrice().getText().toString());
			int resultPrice=0;
			resultPrice=totalPrice-minusPrice;
			System.out.println(resultPrice);
			arf.getLblTotalPrice().setText(Integer.toString(resultPrice));
		}else{
			JOptionPane.showMessageDialog(null,"0원이하는 말이안되...");
			return;
		}
	}//rmvIngdnt
	
	// 이미지추가 수행 (add버튼)
	public void addImg(){
		FileDialog fdImg=new FileDialog(arf,"레시피 이미지선택!", FileDialog.LOAD);
		fdImg.setVisible(true);
		String path=fdImg.getDirectory();
		file=fdImg.getFile();
		
		if(file != null){
			String vaildFile="jpg,gif,png,bmp,JPG,GIF,PNG,BMP";
			if(!vaildFile.contains(file.substring(file.lastIndexOf(".")+1))){
				JOptionPane.showMessageDialog(arf, "선택하신 파일은 이미지가 아닙니다.");
				return;
			}//end if
			
			ImageIcon temp=new ImageIcon(path+file);
			int width = temp.getIconWidth();
			int height = temp.getIconHeight();
			if(width==260 && height==20){
				arf.getLblImg().setIcon(temp);
			}else{
				JOptionPane.showMessageDialog(null, "이미지 파일의 크기는\n가로 : 260px / 세로 : 200px 로 맞춰 등록해주세요.");
			}
			
			
		}//end if
	}//addImg
	@SuppressWarnings("static-access")
	public void reqRecipe(){
		 String menuName=null;
		 String img=null;
		 String foodType=null;
		 String info=null;
		 String recipe_make=null;
		 String id=null;
	
			try {
				 menuName=arf.getJtfRecipeName().getText();
				  img=file;
				  foodType=arf.getJcbCateg().getSelectedItem().toString();
				  info=arf.getJtaInfo().getText();
				  recipe_make=arf.getJtaWriteRecipe().getText();
				  id=mfe.logId;
				  int totalPrice=Integer.parseInt(arf.getLblTotalPrice().getText());
				  AddRecipeVO arv= new AddRecipeVO(menuName,img,foodType,info,recipe_make,totalPrice,id);
				ida.insertRecipe(arv);
				JOptionPane.showMessageDialog(null, "성공적으로 레시피가 추가되었습니다.");	
				} catch (SQLException e) {
				e.printStackTrace();
				}catch(NullPointerException npe){
					JOptionPane.showMessageDialog(null,"기입사항을 다시 확인해주세요");
				}

		
		
	}//reqRecipe
	// 관리자에게 요청 수행 (request버튼)
	public void reqRecipeIngrdnt(){
		
		JTable table=arf.getJtaddedIngrednt();
		
		String[] ingrdntName=new String[table.getRowCount()];
		String menuName=arf.getJtfRecipeName().getText();
		
		if(ingrdntName.length!=0&&!menuName.equals("")){
			try {
				for(int i=0; i<table.getRowCount();i++){
					ingrdntName[i]=table.getValueAt(i,0).toString();
				}
				addRemoveIngrdntVO arv=new addRemoveIngrdntVO(ingrdntName, menuName);
				ida.insertIngdntOfRecp(arv);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(arf, "재료를 추가해주세요");
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
	
	public void selectMgrRecipeInfo(){
		try {
			MgrRecipeInfoVO mriv=ida.selectMgrRecipe(arf.getJtfRecipeName().getText());
			ImageIcon icon=new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/"+mriv.getMrv().getImg());
			arf.getJtfRecipeName().setText(mriv.getMrv().getMenu_name());
			arf.getJcbCateg().setSelectedItem(mriv.getMrv().getFoodType());
			arf.getJtaInfo().setText(mriv.getMrv().getInfo());
			arf.getLblImg().setIcon(icon);
			arf.getJtaWriteRecipe().setText(mriv.getMrv().getRecipe_info());
			
			DefaultTableModel dtmMenu=arf.getDtmAddedIngrednt();
			Object[] rowMenu=new Object[2];
			List<ShowIngdntVO> list=mriv.getSiv();
			ShowIngdntVO siv=null;
			//번호,이미지,메뉴코드","메뉴","설명","가격,
			for( int i=0; i<list.size(); i++){
				siv=list.get(i);
				rowMenu[0]=siv.getIngrdntName();
				rowMenu[1]=siv.getIngrdntPrice();
				dtmMenu.addRow(rowMenu);
			}
			int totalPrice=0;
			int[] priceArr=new int[dtmMenu.getRowCount()];
			for(int i=0; i<dtmMenu.getRowCount();i++){
				priceArr[i]=Integer.parseInt(dtmMenu.getValueAt(i,1).toString());
				totalPrice=priceArr[i]+totalPrice;
		}
		arf.getLblTotalPrice().setText(Integer.toString(totalPrice));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////// MgrForm
	//관리자가 레시피 수정 수행 (edit버튼)
	
	public void deleteIngrdnt(){
		String menuName=arf.getJtfRecipeName().getText();
		try {
			ida.deleteIngdntOfRecp(menuName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editMgr(){
		MgrUpdateIngrdntVO muiv=new MgrUpdateIngrdntVO();
		String menuName=arf.getJtfRecipeName().getText();
		 muiv.setFoodType(arf.getJcbCateg().getSelectedItem().toString());
		 muiv.setImg(arf.getLblImg().getIcon().toString().substring(arf.getLblImg().getIcon().toString().indexOf("F")));
		 muiv.setInfo(arf.getJtaInfo().getText());
		 muiv.setRecipeInfo(arf.getJtaWriteRecipe().getText());
		 muiv.setTotalPrice(Integer.parseInt(arf.getLblTotalPrice().getText()));
		try {
			ida.updateIngdntOfRecp(muiv,menuName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//
	public void copy(){
		  //이미지는 C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img에 복사
		   //이미지의 경로
		   //메뉴이름,가격,설명
		   ImageIcon icon=(ImageIcon)maf.getJlPreview().getIcon();
		  
		   
		   File file=new File(icon.toString());
		   String tempFile=file.getName();
		   if(tempFile.equals("default.jpg")){
			   JOptionPane.showMessageDialog(maf, "기본 이미지는 사용할 수 없습니다.");
			   return;
		   }
		   File sFile=new File(file.getParent()+"/s_"+tempFile);
		   //파일명 앞에 s_가 붙는 파일이 없다면
		   if(!sFile.exists()){
			   JOptionPane.showMessageDialog(maf, "메뉴 선택시 추가되는 파일인 s_"+tempFile+"\"이 필요 합니다.");
			   return;
		   }
		   //파일을 사용위치에 복붙.
		   //선택한 위치가 파일을 보여주는 위치가 아니라면
		   //복,붙을 시도한다.
		   if(!file.getParent().equals("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img")){
			   try {
				//원본 파일 복.붙
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos=new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/"+file.getName());
				
				byte[] temp=new byte[512];
				
				int readData=0;
				while((readData=fis.read(temp))!=-1){
					fos.write(temp,0,readData);
				}
				//메뉴선택 파일 복.붙
					fos.flush();
					if(fis!=null){fis.close();}
					if(fos!=null){fos.close();}
					
					 fis = new FileInputStream(file.getParent()+"/s_"+file.getName());
					 fos=new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_"+file.getName());
					 while((readData=fis.read())!=-1){
					
					 }
					 fos.write(readData);
			   } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			   
		   }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==arf.getJbSearch()){
			searchIngdnt(icv);
		}
		if(e.getSource()==arf.getJbAddIngrednt()){
			if(arf.getJtIngrednt().getRowCount()==0){
				JOptionPane.showMessageDialog(null, "재료를 검색해주세요");
				return;
			}
				addIngdnt();
		}
		if(e.getSource()==arf.getJbAddImg()){
				
				addImg();
		}
		if(e.getSource()==arf.getJbRequest()){
						reqRecipe();
						reqRecipeIngrdnt();
		}
		if(e.getSource()==arf.getJbRmvIngrednt()){
			rmvIngdnt();
		}
		if(e.getSource()==arf.getJbMgr()){
			 int index=JOptionPane.showConfirmDialog(null, "정말로 수정하시겠습니까?");
			 switch (index) {
		case JOptionPane.OK_OPTION:
				editMgr();
				deleteIngrdnt();
				reqRecipeIngrdnt();
				JOptionPane.showMessageDialog(null, "성공적으로 수행되었습니다.");
				break;
			case JOptionPane.NO_OPTION:
				JOptionPane.showMessageDialog(null, "감사합니다.");
				return;
			}
		}
			if(e.getSource() == arf.getJbClose()){
	    	  int selectNum = JOptionPane.showConfirmDialog(arf, "창을 닫으시겠습니까?");
	    	  switch (selectNum) {
			case JOptionPane.OK_OPTION:
				arf.dispose();
			}//end switch
		}
	}
}
