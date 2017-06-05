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
	private RecipeDAO rda;  
	private IngdntDAO ida;
	private AddRecipeForm arf;
	private IngrdntCategVO icv;
	private String file,path;
	public MgrPageForm mpf;
	private LogInEvt le;
	
	@SuppressWarnings("static-access")
	public AddRecipeEvt(AddRecipeForm arf){
		this.arf=arf;
		ida=IngdntDAO.getInstance();
		rda=RecipeDAO.getInstance();
		selectMgrRecipeInfo();
		String id=le.logId; 
		if(id.equals("mgr")){
			arf.getJbRequest().setVisible(false);
			arf.getJbMgr().setVisible(true);
			arf.getJtfRecipeName().setEditable(false);
		}//end if
	}//AddRecipeEvt
	////////////////////////////////////////// AddRecipeForm
	
	//������ ��忡�� ��ư�� ������ �����ư �����ٶ�
	public void showHideButton(String logId){
		logId = LogInEvt.logId;
	}//showHideButton
	
	// ����߰� ���� (add��ư)
	public void addIngdnt(){
		Object[] rowData=new Object[3];
		DefaultTableModel dtmIngrdnt=arf.getDtmAddedIngrednt();
		JTable table=arf.getJtIngrednt();
		int select=table.getSelectedRow();
		
			//��ȣ,�̹���,�޴��ڵ�","�޴�","����","����,
			rowData[0]=table.getValueAt(select,0);
			rowData[1]=table.getValueAt(select,1);
			
			// ���� ��� �߰� �Ұ� ���ǹ� 
			 boolean flag=false;
		      if(dtmIngrdnt.getRowCount()!=0){
		            
		            for(int i=0; i<dtmIngrdnt.getRowCount(); i++){
		               if(dtmIngrdnt.getValueAt(i, 0).equals(table.getValueAt(select,0))){
		                  JOptionPane.showMessageDialog(arf, "���� ��Ḧ �ΰ��� �̻� �߰��ϽǼ� �����ϴ� ");
		                  flag=false;
		                  break;
		               }else if(!(dtmIngrdnt.getValueAt(i, 0).equals(table.getValueAt(select,0)))){
		               flag=true;
		               }
		            }//end for
		         }else if(dtmIngrdnt.getRowCount()==0){
		            flag=true;
		      }//end if
		   
		      if(flag){
		         dtmIngrdnt.addRow(rowData);
		      }//end if
		      
			// ���̺� ��� ����
			JTable table2=arf.getJtaddedIngrednt();
			int[] priceArr=new int[table2.getRowCount()];
			int totalPrice=0; 
			for(int i=0; i<table2.getRowCount();i++){
					priceArr[i]=Integer.parseInt(table2.getValueAt(i,1).toString());
					totalPrice+=priceArr[i];
			}//end for
			
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
//			System.out.println(resultPrice);
			arf.getLblTotalPrice().setText(Integer.toString(resultPrice));
		}else{
			JOptionPane.showMessageDialog(null,"0�����ϴ� ���̾ȵ�...");
			return;
		}//end if
	}//rmvIngdnt
	
	// �̹����߰� ���� (add��ư)
	public void addImg(){
		FileDialog fdImg=new FileDialog(arf,
				"������ �̹�������!", FileDialog.LOAD);
		fdImg.setVisible(true);
		path=fdImg.getDirectory();
		file=fdImg.getFile();
		
		if(file != null){
			String vaildFile="jpg,gif,png,bmp,JPG,GIF,PNG,BMP";
			if(!vaildFile.contains(file.substring(file.lastIndexOf(".")+1))){
				JOptionPane.showMessageDialog(arf, 
						"�����Ͻ� ������ �̹����� �ƴմϴ�.");
				return;
			}//end if
			
			ImageIcon temp=new ImageIcon(path+file);
			int width = temp.getIconWidth();
			int height = temp.getIconHeight();
			
			if(width==260 && height==200){
				arf.getLblImg().setIcon(temp);
			}else{
				JOptionPane.showMessageDialog(null, 
						"�̹��� ������ ũ���/n���� : 260px / ���� : 200px �� ���� ������ּ���.");
			}//end if
			
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
				id=le.logId;
				int totalPrice=Integer.parseInt(arf.getLblTotalPrice().getText());
				AddRecipeVO arv= new AddRecipeVO(menuName,img,foodType,info,recipe_make,totalPrice,id);
				ida.insertRecipe(arv);
		}catch(NullPointerException npe){
				JOptionPane.showMessageDialog(null,
						"���Ի����� �ٽ� Ȯ�����ּ���");
		}catch (SQLException e) {
				e.printStackTrace();
		}//end catch
		
		
	}//reqRecipe
	/////////////////////////////////////////33333333333333333333333333333333333333
	// �����ڿ��� ��û ���� (request��ư)
	public void reqRecipeIngrdnt(){
		
		JTable table=arf.getJtaddedIngrednt();
		
		// �߰��Ϸ��� �޴��̸�
		String menuName=arf.getJtfRecipeName().getText();
		String[] ingrdntName=new String[table.getRowCount()];
		// �߰��Ϸ��� ��� �̸����� �迭�� ������ // �߰�ǥ�� �׷����� ����
		for(int i=0; i<table.getRowCount(); i++){
			ingrdntName[i]=table.getValueAt(i,0).toString();
		}//end for
		
		if(ingrdntName.length!=0 && !menuName.equals("")){
			try {
				// ��� �޴��̸��� �ִ� ����Ʈ�� �迭�� ������
//				System.out.println("������"+rda.getAllMenuName().size());
				String[] allMenuNameArr = new String[rda.getAllMenuName().size()];
				rda.getAllMenuName().toArray(allMenuNameArr);
				int cnt;
				int flag=0;
//-------------------------------------------------------------------
				// ��û ������ ����
				// �����ϴ� �޴��̸� ��� ��
				for(int i=0; i<allMenuNameArr.length; i++){
					
					// �޴��̸��� �ش��ϴ� ���� �迭
					String[] orginIngdntNameArr = new String[ida.selectIngdntOfRecp(allMenuNameArr[i]).size()];
					ida.getIngdntOfRecp(allMenuNameArr[i]).toArray(orginIngdntNameArr);
					cnt=0;
					for(int k=0; k<ingrdntName.length; k++){
						for(int j=0; j<orginIngdntNameArr.length; j++){
							
							if(ingrdntName[k].equals(orginIngdntNameArr[j])){
								cnt++;
//								System.out.println(cnt);
								// �ߺ��Ǵ� ��ᰡ 3���̻��� ��
							}//end if
							
							if(cnt>2){
								if(!(orginIngdntNameArr.length>=cnt+3)){
									flag++;
								}//end if
							}//end if
							
						}//end for
					}//end for
					
				}// end for
				
				// ������ ������ ��� ����
				if(flag==0){
					addRemoveIngrdntVO arv=new addRemoveIngrdntVO(ingrdntName, menuName);
					ida.insertIngdntOfRecp(arv);
				}else{
					JOptionPane.showMessageDialog(arf, "���߰����� !! \n"
							+ "�߰��ϴ� ��� �� ���� ��Ḧ 3���̻� ���� �ִ� �޴��� �����մϴ�.\n"
							+ "�ٸ� ��Ḧ �� �߰��ϰų� ���ٸ� �����Ǹ� �����غ��ñ� �ٶ��ϴ�.");
				}//end else
				//NULLPOINTEXCEPTION�߰�
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}else{
			JOptionPane.showMessageDialog(arf, "��Ḧ �߰����ּ���");
			return;
		}//end else
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
			}//end for
			
		}catch(SQLException se){
			se.printStackTrace();
		}//end catch
	}//searchIngdnt
	
	public void selectMgrRecipeInfo(){
		try {
			MgrRecipeInfoVO mriv=ida.selectMgrRecipe(arf.getJtfRecipeName().getText());
			ImageIcon icon=new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prjsrc/kr/co/sist/recipe/img/"+mriv.getMrv().getImg());
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
			}//end for
			
			int totalPrice=0;
			int[] priceArr=new int[dtmMenu.getRowCount()];
			for(int i=0; i<dtmMenu.getRowCount();i++){
				priceArr[i]=Integer.parseInt(dtmMenu.getValueAt(i,1).toString());
				totalPrice=priceArr[i]+totalPrice;
			}//end for
			
			arf.getLblTotalPrice().setText(Integer.toString(totalPrice));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
	}//selectMgrRecipeInfo
	/////////////////////////////////////////// MgrForm
	//�����ڰ� ������ ���� ���� (edit��ư)
	
	/**
	 * ������ method
	 */
	public void deleteIngrdnt(){
		String menuName=arf.getJtfRecipeName().getText();
		try {
			ida.deleteIngdntOfRecp(menuName);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//deleteIngrdnt
	
	/**
	 * ������ �� ��� ������Ʈ method
	 */
	public void editMgr(){
		 
	  try {
			 MgrUpdateIngrdntVO muiv=new MgrUpdateIngrdntVO();
			 String menuName=arf.getJtfRecipeName().getText();
			 muiv.setFoodType(arf.getJcbCateg().getSelectedItem().toString());
			 muiv.setImg(arf.getLblImg().getIcon().toString().substring(arf.getLblImg().getIcon().toString().indexOf("F")));
			 muiv.setInfo(arf.getJtaInfo().getText());
			 muiv.setRecipeInfo(arf.getJtaWriteRecipe().getText());
			 muiv.setTotalPrice(Integer.parseInt(arf.getLblTotalPrice().getText()));
			
			ida.updateIngdntOfRecp(muiv,menuName);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//editMgr
	/**
	 * �߰��� �̹��� ���ϸ��� ũ�⺰�� ������ ����� method
	 */
	public void imgCopy(){
		
		try {
            int width = 260;
            int height = 200;
            File file_name = new File(path+file);
           String path="C:/dev/group_prj_git/group3_prj_2/group_prjsrc/kr/co/sist/recipe/img";
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
	            }//end if
            }//end for
        } catch (Exception e) {
            e.printStackTrace();
        }//end catch
        	if(file==null||file==""){
        		JOptionPane.showMessageDialog(null, "�̹��� ������ �����ϴ�.");
        		return;
        	}//end if
        }//imgCopy

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==arf.getJbSearch()){
			searchIngdnt(icv);
		}//end if
		if(e.getSource()==arf.getJbAddIngrednt()){
			if(arf.getJtIngrednt().getRowCount()==0){
				JOptionPane.showMessageDialog(null, "��Ḧ �˻����ּ���");
				return;
			}//end if
			addIngdnt();
		}//end if
		if(e.getSource()==arf.getJbAddImg()){
			addImg();
		}//end if
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
		}//end if
		if(e.getSource()==arf.getJbRmvIngrednt()){
			rmvIngdnt();
		}//end if
		if(e.getSource()==arf.getJbMgr()){
			 int index=JOptionPane.showConfirmDialog(null, 
					 "������ �����Ͻðڽ��ϱ�?");
			 switch (index) {
			 case JOptionPane.OK_OPTION:
				editMgr();
				deleteIngrdnt();
				reqRecipeIngrdnt();
				//���� ���� �� ������
				JOptionPane.showMessageDialog(null, 
						"���������� ����Ǿ����ϴ�.");
				arf.dispose();
				break;
			case JOptionPane.NO_OPTION:
				JOptionPane.showMessageDialog(null, "�����մϴ�.");
				return;
			}//end switch
		}//end if
		if(e.getSource() == arf.getJbClose()){
	    	  int selectNum = JOptionPane.showConfirmDialog(arf, 
	    			  "[ �޴���û ] â�� �����ðڽ��ϱ�?\n�ۼ����� ������ ������� �ʽ��ϴ�.");
	    	  switch (selectNum) {
	    	  case JOptionPane.OK_OPTION:
				arf.dispose();
	    	  }//end switch
		}//end if
	}//actionPerformed
}//class
