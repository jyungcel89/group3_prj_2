package kr.co.sist.recipe.evt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kr.co.sist.recipe.dao.BookmarkDAO;
import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MainForm;
import kr.co.sist.recipe.view.MyPageForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.BookmarkUpdateVO;
import kr.co.sist.recipe.vo.BookmarkVO;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MyRecipeVO;
public class MyPageEvt extends WindowAdapter implements ActionListener, MouseListener {
       private MyPageForm mypf;
       private MainFormEvt mfe;
       private BookmarkDAO bdao;
       private RecipeDAO rdao;
       private MemberDAO mdao;
          
	    /**
	     * ���������� �̺�Ʈ
	     * <��������>
	     * 1. MyPageForm ��ü�� ���� : mpf > mypf
	     * @param mypf
	     */
       public MyPageEvt(MyPageForm mypf){
              this.mypf=mypf;
        
              bdao=BookmarkDAO.getInstance();
              rdao=RecipeDAO.getInstance();
              mdao=MemberDAO.getInstance();
              showMyRecipe();////////////////////////////////ȸ�� ���̵� ������
              showBookmark();//////////////////////////////ȸ�� ���̵� ������
       }//MyPageEvt
       
       // ���� ����� �޴� ����Ʈ
       public void showMyRecipe(){
              try {
                     List<MyRecipeVO> listMyRcp = rdao.myRecipe(mfe.logId);
                     Object[] rowMenu = new Object[6];
                     DefaultTableModel dtmMenu = mypf.getDtmMyMenu();
                     String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/s_";
                     
                     MyRecipeVO mrv=null;
                     dtmMenu.setRowCount(0);
                     // name,img,type,info,price
                     for( int i=0; i < listMyRcp.size(); i++ ){
                    	 mrv=listMyRcp.get(i);
                           rowMenu[0]=mrv.getMenuName();
                           rowMenu[1]=new ImageIcon(path+mrv.getMenuImg());
                           rowMenu[2]=mrv.getMenuType();
                           rowMenu[3]=mrv.getMenuInfo();
                           rowMenu[4]=mrv.getMenuPrice();
                           rowMenu[5]=mrv.getFlag();
                           
                           dtmMenu.addRow(rowMenu);
                     }//end for
                     
              } catch (SQLException se) {
                     JOptionPane.showMessageDialog(null,
                                  "�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
                     se.printStackTrace();
              }//end catch
              
              
       }//showMyRecipe
       
       //05-29-2017
       	/**
	     * ���������� : ��û���� ������ư 
	     * - ���� ��Ϸ����� ����â���� "��û����" ������ �����Ǹ� ���� ó��
	     * - RecipeDAO - deleteRecipeUser(menuName) method ����
	     */ 
	    public void rmvRecipe(){
			try {
				// ���̺��� Ŭ�� > menuName ��������
				JTable jtMyMenu=mypf.getJtMyMenu();
				int row=jtMyMenu.getSelectedRow();
				String value = (String) jtMyMenu.getValueAt(row, 0);
				String valueFlag = (String) jtMyMenu.getValueAt(row, 5);
//				System.out.println(row);
//				System.out.println("row : "+row+", ���� �� : "+value+
//						"\n row : "+row+", ���� �� : "+valueFlag);
//				System.out.println("delFlag : "+rdao.deleteRecipeUser(value));
				
				// valueFlag���� "��û����"�� �ƴϸ� return
				if( !valueFlag.equals("��û����") ){
					JOptionPane.showMessageDialog(null, 
							"[ ��û���� ] ������ �����Ǹ� ���� �����մϴ�.");
					return;
				}//end if
				int flag=JOptionPane.showConfirmDialog(null, 
						"[ "+value+" ] �����Ͻ� �޴��� ���� �����Ͻðڽ��ϱ�?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// ������ menuName �� > ����
					rdao.deleteRecipeUser(value);
				}//end catch
				
				// ���� �� ����
				showMyRecipe();//ȸ�����̵� ���ߵ�
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(null, "�����Ǹ� �������ּ���.");
//				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(null, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch
       }//rmvRecipe
       
       // �ϸ�ũ�� �޴� ����Ʈ
       public void showBookmark(){
              try {
                     List<BookmarkVO> bklist = bdao.searchAll(mfe.logId);
                     Object[] rowMenu = new Object[5];
                     DefaultTableModel dtmMenu = mypf.getDtmFavorMenu();
                     String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/s_";
                     
                     BookmarkVO bmvo=null;
                     dtmMenu.setRowCount(0);
                     // name,img,type,info,price
                     for( int i=0; i < bklist.size(); i++ ){
                           bmvo=bklist.get(i);
                           rowMenu[0]=bmvo.getMenuName();
                           rowMenu[1]=new ImageIcon(path+bmvo.getImg());
                           rowMenu[2]=bmvo.getMenuType();
                           rowMenu[3]=bmvo.getMenuInfo();
                           rowMenu[4]=bmvo.getMenuPrice();
                           
                           dtmMenu.addRow(rowMenu);
                     }//end for
                     
              } catch (SQLException se) {
                     JOptionPane.showMessageDialog(null,
                                  "�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
                     se.printStackTrace();
              }//end catch
              
       }//showBookmark
       
       // �ϸ�ũ�� �޴� ����
       public void rmvBookmark() { 
              String id="duck";
              BookmarkUpdateVO bmuvo= new BookmarkUpdateVO();
              try {
                     // ���̺��� Ŭ�� > menuName ��������
                     JTable jtRcp=mypf.getJtFavorMenu();
                     int row=jtRcp.getSelectedRow();
                     String value = (String) jtRcp.getValueAt(row, 0);
                     int flag=JOptionPane.showConfirmDialog(null,
                                  "[ "+value+" ] �����Ͻ� �޴��� ���� �����Ͻðڽ��ϱ�?");
                     switch (flag) {
                     case JOptionPane.OK_OPTION:
                           // ������ menuName �� > ����
                           bmuvo.setId(id);
                           bmuvo.setMenuName(value);
                           bdao.rmvBookmark(bmuvo);
                     }//end catch
                     // ���� �� ����
                     showBookmark();
              } catch (ArrayIndexOutOfBoundsException aioobe) {
                     JOptionPane.showMessageDialog(null,
                                  "�����Ǹ� �������ּ���.");
                     aioobe.printStackTrace();
              } catch (SQLException se) {
                     JOptionPane.showMessageDialog(null,
                                  "�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
                     se.printStackTrace();
              }//end catch
       }//rmvBookmark
       
       // �� ����â���� �̵� > ������ �� �����ͼ� SignInForm�� setter���� ����
       public void goMyInfo(){
    	   	  String id=mfe.logId; 
    	   	  SignInForm sif=new SignInForm(id);
    	   	  String mail="";

              try {
            	  mail=mdao.selectMyInfo(id).toString();
			} catch (SQLException e) {
				e.printStackTrace();
			}
              sif.getJtfId().setText(id);
              sif.getJtfName().setText(mail);
              
              //////// �ʿ� ���� ��ư�� �Ⱥ��̰� //////
              sif.getJbtChkId().setVisible(false);
              sif.getJbtSubmit().setVisible(false);
              sif.getJbtCancel().setVisible(false);
              ////////////////////////////////////////////
              ///////////////////��� ��ư ���� ��ư���� ����////////////////
              sif.getJbtUpdate().setVisible(true);
              ////////////////////////////////////////////////////////////////////
              ////////////////���̵� �̸� �κ� ���� �Է�//////////////////////////////////���̵� ���� �κ� !!!!!!!!!!!!!!!!!!!!!!!!!
              
              ////////////////////////////////////////////////////
              
              //////////////���� �Ұ� �κ� ����///////////////
              sif.getJtfId().setEditable(false);
              sif.getJtfName().setEditable(false);
              ///////////////////////////////////////////////////
              
              /////////////////��ư �̸� ����/////////////////
              ///////////////////////////////////////////////////
              /////////��ư �̸� �����԰� ���ÿ� sigevt�κп� �޼ҵ� �߰��Ǿ���� (�̺�Ʈ)//
       }//goMyInfo
       
       @Override 
       public void actionPerformed(ActionEvent ae) {
              if(ae.getSource()==mypf.getJbEditMyInfo()){
                     goMyInfo();
              }//end if
              
              if(ae.getSource()==mypf.getJbRmvFavorMenu()){
                   rmvBookmark();
              }//end if
              
              if(ae.getSource()==mypf.getJbRmvMyMenu()){
            	  rmvRecipe();
              }//end if
              
              if (ae.getSource() == mypf.getJbClose()) {
      			int selectNum = JOptionPane.showConfirmDialog(null, "â�� �����ðڽ��ϱ�?");
      			switch (selectNum) {
      			case JOptionPane.OK_OPTION:
      				mypf.dispose();
      			}// end switch
      		}//end if
              
       }//actionPerformed
       
       @Override
       public void mouseClicked(MouseEvent me) {
    	   //05-29-2017
    	   //���߿� �ϸ�ũ���̺�� �����Ұ�!
     		if( me.getClickCount()==2 ){
    			// �޴�����Ʈ ����Ŭ��
    			if( me.getSource()==mypf.getJtMyMenu() ){
    				JTable jtMyRcp=mypf.getJtMyMenu();
    				int row=jtMyRcp.getSelectedRow();
    				String value=(String)jtMyRcp.getValueAt(row, 0);
    				MainRecipeVO mrv;
    				try {
    					mrv=rdao.selectOneRecipe(value);
    					//MENU_NAME, IMG, FOOD_TYPE, INFO, RECIPE_INFO
    			        ItemPreviewForm ipf = new ItemPreviewForm(mrv,mfe);
    	                   ipf.getJchBookmark().setVisible(false);
    	                   ipf.getJcScore().setVisible(false);
    	                   ipf.getJbSubmit().setVisible(false);
    	                   ipf.getJlBookmark().setVisible(false);
    	                   ipf.getJlScore().setVisible(false);
    				} catch (SQLException se) {
    					JOptionPane.showMessageDialog(null, 
    							"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
    					se.printStackTrace();
    				}// end catch
    			}//end if
    			
    			if( me.getSource()==mypf.getJtFavorMenu() ){
    				JTable jtBookRcp=mypf.getJtFavorMenu();
    				int row=jtBookRcp.getSelectedRow();
    				String value=(String)jtBookRcp.getValueAt(row, 0);
    				MainRecipeVO mrv;
    				try {
    					mrv=rdao.selectOneRecipe(value);
    					//MENU_NAME, IMG, FOOD_TYPE, INFO, RECIPE_INFO
    					new ItemPreviewForm(mrv,mfe);
    				} catch (SQLException se) {
    					JOptionPane.showMessageDialog(null, 
    							"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
    					se.printStackTrace();
    				}// end catch
    			}//end if
    			
         	}//end if
              
       }//mouseClicked
       @Override
       public void mousePressed(MouseEvent e) {
              // TODO Auto-generated method stub
              
       }
       @Override
       public void mouseReleased(MouseEvent e) {
              // TODO Auto-generated method stub
              
       }
       @Override
       public void mouseEntered(MouseEvent e) {
              // TODO Auto-generated method stub
              
       }
       @Override
       public void mouseExited(MouseEvent e) {
              // TODO Auto-generated method stub
              
       }
}
