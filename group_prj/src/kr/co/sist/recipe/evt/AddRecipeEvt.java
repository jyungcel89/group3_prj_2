package kr.co.sist.recipe.evt;

import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
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
	private String file,path;
	private MainFormEvt mfe;
	public MgrPageForm mpf;
	@SuppressWarnings("static-access")
	public AddRecipeEvt(AddRecipeForm arf){
		this.arf=arf;
		ida=IngdntDAO.getInstance();
		selectMgrRecipeInfo();
		String id=mfe.logId;
		if(id.equals("mgr")){
			arf.getJbRequest().setVisible(false);
			arf.getJbMgr().setVisible(true);
			arf.getJtfRecipeName().setEditable(false);
		}
	}
	////////////////////////////////////////// AddRecipeForm
	
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
			 int totalPrice=0;
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
		path=fdImg.getDirectory();
		file=fdImg.getFile();
		
		if(file != null){
			String vaildFile="jpg,gif,png,bmp,JPG,GIF,PNG,BMP";
			if(!vaildFile.contains(file.substring(file.lastIndexOf(".")+1))){
				JOptionPane.showMessageDialog(arf, "�����Ͻ� ������ �̹����� �ƴմϴ�.");
				return;
			}//end if
			
			ImageIcon temp=new ImageIcon(path+file);
			int width = temp.getIconWidth();
			int height = temp.getIconHeight();
			if(width==260 && height==200){
				arf.getLblImg().setIcon(temp);
			}else{
				JOptionPane.showMessageDialog(null, "�̹��� ������ ũ���/n���� : 260px / ���� : 200px �� ���� ������ּ���.");
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
				} catch (SQLException e) {
				e.printStackTrace();
				}catch(NullPointerException npe){
					JOptionPane.showMessageDialog(null,"���Ի����� �ٽ� Ȯ�����ּ���");
					return;
				}
		
		
	}//reqRecipe
	// �����ڿ��� ��û ���� (request��ư)
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
			JOptionPane.showMessageDialog(arf, "��Ḧ �߰����ּ���");
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
	
	public void deleteIngrdnt(){
		String menuName=arf.getJtfRecipeName().getText();
		try {
			ida.deleteIngdntOfRecp(menuName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//deleteIngrdnt
	
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
					e.printStackTrace();
				}
	}//editMgr
	public void imgCopy(){
		
		
		try {
            int width = 260;
            int height = 200;
            File file_name = new File(path+file);
           String path="C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img";
            File file_name_b = new File(path+"/b_FI_"+file);
            File file_name_s = new File(path+"/s_FI_"+file);
            
            BufferedImage original_image = ImageIO.read(file_name);
            BufferedImage copy_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = copy_image.createGraphics();
            graphic.drawImage(original_image, 0, 0, width, height, null);
            
            String[] format={"jpg","gif","png","bmp","JPG","GIF","PNG","BMP"};
            for(int i=0; i<format.length;i++){
            if(file.substring(file.indexOf(".")+1).equals(format[i])){
            	ImageIO.write(copy_image,format[i], file_name_b);
            	ImageIO.write(copy_image,format[i], file_name_s);
            }
            }
        } catch (Exception e) {
        	if(file==null||file==""){
        		JOptionPane.showMessageDialog(null, "�̹��� ������ �����ϴ�.");
        		return;
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
				JOptionPane.showMessageDialog(null, "��Ḧ �˻����ּ���");
				return;
			}
				addIngdnt();
		}
		if(e.getSource()==arf.getJbAddImg()){
				addImg();
		}
		if(e.getSource()==arf.getJbRequest()){
				 int index=JOptionPane.showConfirmDialog(null, "���� ������ ��û�Ͻðڽ��ϱ�?");
				 switch (index) {
				case JOptionPane.OK_OPTION:
					imgCopy();
					reqRecipe();
					reqRecipeIngrdnt();
					JOptionPane.showMessageDialog(null,"���������� ��û�Ǿ����ϴ�!");
					break;
				case JOptionPane.NO_OPTION:
					return;
				default:
					break;
				}//end switch
		}
		if(e.getSource()==arf.getJbRmvIngrednt()){
			rmvIngdnt();
		}
		if(e.getSource()==arf.getJbMgr()){
			 int index=JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?");
			 switch (index) {
		case JOptionPane.OK_OPTION:
				editMgr();
				deleteIngrdnt();
				reqRecipeIngrdnt();
				JOptionPane.showMessageDialog(null, "���������� ����Ǿ����ϴ�.");
				arf.dispose();
				break;
			case JOptionPane.NO_OPTION:
				JOptionPane.showMessageDialog(null, "�����մϴ�.");
				return;
			}
		}
			if(e.getSource() == arf.getJbClose()){
	    	  int selectNum = JOptionPane.showConfirmDialog(arf, "â�� �����ðڽ��ϱ�?");
	    	  switch (selectNum) {
			case JOptionPane.OK_OPTION:
				arf.dispose();
			}//end switch
		}
	}
}
