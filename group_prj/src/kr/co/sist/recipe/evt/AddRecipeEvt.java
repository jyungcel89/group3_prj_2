package kr.co.sist.recipe.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.channels.ShutdownChannelGroupException;
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
import kr.co.sist.recipe.view.MainForm;
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
	private addRemoveIngrdntVO arv;
	private String file;
	private int totalPrice;
	private MainForm mf;
	private MainFormEvt mfe;
	public MgrPageForm mpf;
	public AddRecipeEvt(AddRecipeForm arf){
		this.arf=arf;
		ida=IngdntDAO.getInstance();
		selectMgrRecipeInfo();
		System.out.println();
		String id=mfe.logId;
		if(id.equals("mgr")){
			arf.getJbRequest().setVisible(false);
			arf.getJbMgr().setVisible(true);
		}
	}
	////////////////////////////////////////// AddRecipeForm
	
	//������ ��忡�� ��ư�� ������ �����ư �����ٶ�
	
	
	// ����߰� ���� (add��ư)
	public void addIngdnt(){
		
		Object[] rowData=new Object[3];
		DefaultTableModel dtmIngrdnt=arf.getDtmAddedIngrednt();
		JTable table=arf.getJtIngrednt();
		int select=table.getSelectedRow();
		//��ȣ,�̹���,�޴��ڵ�","�޴�","����","����,
			rowData[0]=table.getValueAt(select,0);
			rowData[1]=table.getValueAt(select,1);
			dtmIngrdnt.addRow(rowData);
			
			JTable table2=arf.getJtaddedIngrednt();
			int[] priceArr=new int[table2.getRowCount()];
			 totalPrice=0;
				for(int i=0; i<table2.getRowCount();i++){
						priceArr[i]=Integer.parseInt(table2.getValueAt(i,1).toString());
						totalPrice=priceArr[i]+totalPrice;
				}
				arf.getLblTotalPrice().setText(Integer.toString(totalPrice));
	}//addIngdnt
	
	// ������ ���� (del��ư)
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
			JOptionPane.showMessageDialog(null,"0�����ϴ� ���̾ȵ�...");
			return;
		}
	}//rmvIngdnt
	
	// �̹����߰� ���� (add��ư)
	public void addImg(){
		FileDialog fdImg=new FileDialog(arf,"������ �̹�������!", FileDialog.LOAD);
		fdImg.setVisible(true);
		String path=fdImg.getDirectory();
		file=fdImg.getFile();
		if(file != null){
			String vaildFile="jpg,gif,png,bmp";
			if(!vaildFile.contains(file.substring(file.lastIndexOf(".")+1))){
				JOptionPane.showMessageDialog(arf, "�����Ͻ� ������ �̹����� �ƴմϴ�.");
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
		String id=mfe.logId;
		int totalPrice=Integer.parseInt(arf.getLblTotalPrice().getText());
		AddRecipeVO arv= new AddRecipeVO(menuName,img,foodType,info,recipe_make,totalPrice,id);
		
		if(arv!=null){
		try {
			ida.insertRecipe(arv);
			System.out.println("����");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			JOptionPane.showMessageDialog(arf,"����� �Է���...");
		}
	}//reqRecipe
	// �����ڿ��� ��û ���� (request��ư)
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
			System.out.println("����");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			JOptionPane.showMessageDialog(arf, "������ �Է��ϰų� ��Ḧ �߰����ּ���");
			return;
		}
	}//reqRecipe
 
	// ��� ��ȸ ���� < IngdntDAO���� selectIngdnt(IngdntSchVO:������, ���Ÿ��) :  List<showIngredntVO>
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
		//��ȣ,�̹���,�޴��ڵ�","�޴�","����","����,
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
			//��ȣ,�̹���,�޴��ڵ�","�޴�","����","����,
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
	//�����ڰ� ������ ���� ���� (edit��ư)
	public void editMgr(){
		
		String menuName=arf.getJtfRecipeName().getText();
		MgrUpdateIngrdntVO muiv=new MgrUpdateIngrdntVO();
		 muiv.setFoodType(arf.getJcbCateg().getSelectedItem().toString());
		 muiv.setImg(arf.getLblImg().getIcon().toString().substring(arf.getLblImg().getIcon().toString().indexOf("F")));
		 muiv.setInfo(arf.getJtaInfo().getText());
		 muiv.setRecipeInfo(arf.getJtaWriteRecipe().getText());
		 muiv.setTotalPrice(Integer.parseInt(arf.getLblTotalPrice().getText()));
		 arv=new addRemoveIngrdntVO();
		 
		 try {
			 int index=JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?");
			 switch (index) {
			case JOptionPane.OK_OPTION:
				ida.updateIngdntOfRecp(muiv,menuName);
				ida.insertIngdntOfRecp(arv);
				JOptionPane.showMessageDialog(null,"���������� ����Ǿ����ϴ�.");
				break;
			case JOptionPane.NO_OPTION:
				return;
			default:
				break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
						reqRecipeIngrdnt();
		}
		if(e.getSource()==arf.getJbRmvIngrednt()){
			rmvIngdnt();
		}
		if(e.getSource()==arf.getJbMgr()){
			editMgr();
		}
	
		 if(e.getSource() == arf.getJbClose()){
	    	  int selectNum = JOptionPane.showConfirmDialog(arf, "â�� �����ðڽ��ϱ�?");
	    	  switch (selectNum) {
			case JOptionPane.OK_OPTION:
				arf.dispose();
			}//end switch
	      }//end if //�ݱ��ư
	}//actionPerformed
}//class