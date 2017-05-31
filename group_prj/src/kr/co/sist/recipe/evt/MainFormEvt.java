package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MainForm;
import kr.co.sist.recipe.view.MgrPageForm;
import kr.co.sist.recipe.view.MyPageForm;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MenuTypeVO;

public class MainFormEvt implements MouseListener, ItemListener, ActionListener {
   private MainForm mainFrm;
   private RecipeDAO rcp_dao;
   private MenuTypeVO mtv;
   public static String logId;
   
   
   public MainFormEvt() {
   }//MainFormEvt

   public MainFormEvt(MainForm mainFrm, String logId) {
      this.mainFrm = mainFrm;
      this.logId = logId;
      rcp_dao = RecipeDAO.getInstance();
      
      newRecipe();
      
      mtv = new MenuTypeVO();
      
      mtv.setAnju("");
      mtv.setMeal("");
      mtv.setDessert("");
      mtv.setBunsik("");
      mainFrm.getJtfSearch().setText("");
      searchList();
   }// MainFormEvt

   // �ֱ� ��ϵ� ������ �̹��� ����
   public void newRecipe() {
      try {
         List<MainRecipeVO> dataList = rcp_dao.showNewRecipe();
         String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/";
         
         // ����� ��¥���� 3������ �̹��� ���
         mainFrm.setImgPath1(path + dataList.get(0).getMenuImg());
         mainFrm.setImgPath2(path + dataList.get(1).getMenuImg());
         mainFrm.setImgPath3(path + dataList.get(2).getMenuImg());
         System.out.println(dataList.get(2).getMenuImg());
         // ����� ��¥���� 3������ �̸� ���
         mainFrm.setImgName1(dataList.get(0).getMenuName());
         mainFrm.setImgName2(dataList.get(1).getMenuName());
         mainFrm.setImgName3(dataList.get(2).getMenuName());

      } catch (SQLException e) {
         e.printStackTrace();
      } // end catch

   }// newRecipe

   // ���õ� ������ �˻����� ��������
   public void searchCondition() {
      mtv = new MenuTypeVO();
       
      mtv.setAnju("");
      mtv.setMeal("");
      mtv.setDessert("");
      mtv.setBunsik("");

      // üũ�ڽ� �ɼ��� ���õɶ� vo�� �ش簪����
      if (mainFrm.getChkOne().isSelected()) {
         mtv.setAnju("���ַ�");
      } // end if
      if (mainFrm.getChkTwo().isSelected()) {
         mtv.setMeal("�Ļ��");
      } // end if
      if (mainFrm.getChkThree().isSelected()) {
         mtv.setDessert("����Ʈ");
      } // end if
      if (mainFrm.getChkFour().isSelected()) {
         mtv.setBunsik("�нķ�");
      } // end if

   }// searchCondition

   // �˻��������� ����Ʈ ��ȸ
   public void searchList() {

      try {
         // �˻����� �޼ҵ� �����Ͽ� ������ �ɷ���
    	 String searchText = mainFrm.getJtfSearch().getText();
         searchCondition();
         List<MainRecipeVO> list = rcp_dao.selectAllRecipe(mtv, searchText);
         Object[] rowMenu = new Object[5];
         DefaultTableModel dtmMenu = mainFrm.getDtmRecipe();
         String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/s_";

         // �޴������� ����� VO��
         MainRecipeVO mrv = null;
         dtmMenu.setRowCount(0);

         for (int i = 0; i < list.size(); i++) {
            mrv = list.get(i);
            rowMenu[0] = mrv.getMenuName();
            rowMenu[1] = new ImageIcon(path+mrv.getMenuImg());
            rowMenu[2] = mrv.getMenuType();
            rowMenu[3] = mrv.getMenuSimpeInfo();
            rowMenu[4] = mrv.getMenuPrice();

            dtmMenu.addRow(rowMenu);
         } // end for

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(mainFrm, "�˼��մϴ�. �޴��� �ҷ��� �� �����ϴ�.");
         e.printStackTrace();
      } // end catch

   }// searchList

   // ����������(������ ������)�� �̵� ��ư
   public void showAddRecipe() {
	   new AddRecipeForm();
   }// addRecipe
   
   public void addRecipe() {
	   
   }// addRecipe

   //05-29-2017 �߰�
   // ����������(������ ������)�� �̵� ��ư
   // member_flag �߰��Ǹ� �� �������� �߰�
   public void movePage() {
	   System.out.println("���� > ������"+logId);
	   if( logId.equals("mgr") ){
		   new MgrPageForm(logId);
	   }else{
		   new MyPageForm(logId);
	   }//end if
   }//movePage
   
   // �ݱ�
   public void close() {

   }// close

   @Override 
   public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == mainFrm.getJbSearch()) {
         searchList();
      }//end if //�˻���ư
      
      if(ae.getSource()== mainFrm.getJbFstImg()){
    	  MainRecipeVO mrv;
		try {
			mrv = rcp_dao.selectOneRecipe(mainFrm.getImgName1());
			new ItemPreviewForm(mrv);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
      }//end if
      if(ae.getSource()== mainFrm.getJbSecImg()){
    	  MainRecipeVO mrv;
    	  try {
    		  mrv = rcp_dao.selectOneRecipe(mainFrm.getImgName2());
    		  new ItemPreviewForm(mrv);
    	  } catch (SQLException e) {
    		  e.printStackTrace();
    	  }//end catch
      }//end if
      if(ae.getSource()== mainFrm.getJbTrdImg()){
    	  MainRecipeVO mrv;
    	  try {
    		  mrv = rcp_dao.selectOneRecipe(mainFrm.getImgName3());
    		  new ItemPreviewForm(mrv);
    	  } catch (SQLException e) {
    		  e.printStackTrace();
    	  }//end catch
      }//end if
      
      //05-29-2017 �߰�
      if(ae.getSource()==mainFrm.getJbMypage()){
    	  movePage();
      }//end if
      
      if(ae.getSource() == mainFrm.getJbClose()){
    	  int selectNum = JOptionPane.showConfirmDialog(mainFrm, "â�� �����ðڽ��ϱ�?");
    	  switch (selectNum) {
		case JOptionPane.OK_OPTION:
			mainFrm.dispose();
		}//end switch
      }//end if //�ݱ��ư
      
      if(ae.getSource() == mainFrm.getJbMypage()){
    	  
      }//end if
      
      if(ae.getSource() == mainFrm.getJbAddRecipe()){
    	  showAddRecipe();
      }//end if
       
   }// actionPerformed

   @Override
   public void mouseClicked(MouseEvent me) {
      if (me.getClickCount() == 2) {
    	  JTable jtTmp = mainFrm.getJtRecipe();
    	  int selecedRow = jtTmp.getSelectedRow();
    	  MainRecipeVO mrv;
		try {
			mrv = rcp_dao.selectOneRecipe((String)jtTmp.getValueAt(selecedRow, 0));
			/// �ڼ��� ������ ���� �����;���
			new ItemPreviewForm(mrv);
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(mainFrm, 
					"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
			se.printStackTrace();
		}//end catch
      }//end if
   }//mouseClicked

   @Override
   public void itemStateChanged(ItemEvent ie) {
      if (ie.getStateChange() == ItemEvent.SELECTED) {
         searchCondition();
      } // end if
   }// itemStateChanged

   @Override
   public void mousePressed(MouseEvent e) {
      // TODO Autogenerated method stub

   }

   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO Autogenerated method stub

   }

   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Autogenerated method stub

   }

   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Autogenerated method stub

   }

}// class