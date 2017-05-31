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

   // 최근 등록된 레시피 이미지 띄우기
   public void newRecipe() {
      try {
         List<MainRecipeVO> dataList = rcp_dao.showNewRecipe();
         String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/";
         
         // 등록한 날짜기준 3위까지 이미지 등록
         mainFrm.setImgPath1(path + dataList.get(0).getMenuImg());
         mainFrm.setImgPath2(path + dataList.get(1).getMenuImg());
         mainFrm.setImgPath3(path + dataList.get(2).getMenuImg());
         System.out.println(dataList.get(2).getMenuImg());
         // 등록한 날짜기준 3위까지 이름 등록
         mainFrm.setImgName1(dataList.get(0).getMenuName());
         mainFrm.setImgName2(dataList.get(1).getMenuName());
         mainFrm.setImgName3(dataList.get(2).getMenuName());

      } catch (SQLException e) {
         e.printStackTrace();
      } // end catch

   }// newRecipe

   // 선택된 레시피 검색조건 가져오기
   public void searchCondition() {
      mtv = new MenuTypeVO();
       
      mtv.setAnju("");
      mtv.setMeal("");
      mtv.setDessert("");
      mtv.setBunsik("");

      // 체크박스 옵션이 선택될때 vo에 해당값저장
      if (mainFrm.getChkOne().isSelected()) {
         mtv.setAnju("안주류");
      } // end if
      if (mainFrm.getChkTwo().isSelected()) {
         mtv.setMeal("식사류");
      } // end if
      if (mainFrm.getChkThree().isSelected()) {
         mtv.setDessert("디저트");
      } // end if
      if (mainFrm.getChkFour().isSelected()) {
         mtv.setBunsik("분식류");
      } // end if

   }// searchCondition

   // 검색조건으로 리스트 조회
   public void searchList() {

      try {
         // 검색조건 메소드 실행하여 조건을 걸러줌
    	 String searchText = mainFrm.getJtfSearch().getText();
         searchCondition();
         List<MainRecipeVO> list = rcp_dao.selectAllRecipe(mtv, searchText);
         Object[] rowMenu = new Object[5];
         DefaultTableModel dtmMenu = mainFrm.getDtmRecipe();
         String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/s_";

         // 메뉴종류가 저장된 VO에
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
         JOptionPane.showMessageDialog(mainFrm, "죄송합니다. 메뉴를 불러올 수 없습니다.");
         e.printStackTrace();
      } // end catch

   }// searchList

   // 마이페이지(관리자 페이지)로 이동 버튼
   public void showAddRecipe() {
	   new AddRecipeForm();
   }// addRecipe
   
   public void addRecipe() {
	   
   }// addRecipe

   //05-29-2017 추가
   // 마이페이지(관리자 페이지)로 이동 버튼
   // member_flag 추가되면 그 조건으로 추가
   public void movePage() {
	   System.out.println("메인 > 관리자"+logId);
	   if( logId.equals("mgr") ){
		   new MgrPageForm(logId);
	   }else{
		   new MyPageForm(logId);
	   }//end if
   }//movePage
   
   // 닫기
   public void close() {

   }// close

   @Override 
   public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == mainFrm.getJbSearch()) {
         searchList();
      }//end if //검색버튼
      
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
      
      //05-29-2017 추가
      if(ae.getSource()==mainFrm.getJbMypage()){
    	  movePage();
      }//end if
      
      if(ae.getSource() == mainFrm.getJbClose()){
    	  int selectNum = JOptionPane.showConfirmDialog(mainFrm, "창을 닫으시겠습니까?");
    	  switch (selectNum) {
		case JOptionPane.OK_OPTION:
			mainFrm.dispose();
		}//end switch
      }//end if //닫기버튼
      
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
			/// 자세한 정보도 같이 가져와야함
			new ItemPreviewForm(mrv);
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(mainFrm, 
					"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
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