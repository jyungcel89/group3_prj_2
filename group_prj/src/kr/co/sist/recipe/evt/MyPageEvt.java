package kr.co.sist.recipe.evt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kr.co.sist.recipe.dao.BookmarkDAO;
import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MainForm;
import kr.co.sist.recipe.view.MyPageForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.BookmarkUpdateVO;
import kr.co.sist.recipe.vo.BookmarkVO;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MyRecipeVO;
@SuppressWarnings("serial")
public class MyPageEvt extends WindowAdapter implements ActionListener, MouseListener {
		private MainForm mf;
       private MyPageForm mypf;
       private BookmarkDAO bdao;
       private RecipeDAO rdao;
       private BookmarkVO bv;
       private MemberDAO mdao;
       public static String logId;
       
	    /**
	     * ���������� �̺�Ʈ
	     * <��������>
	     * 1. MyPageForm ��ü�� ���� : mpf > mypf
	     * @param mypf
	     */
       public MyPageEvt(MyPageForm mypf, String logId){
              this.mypf=mypf;
              this.logId=logId;
              bdao=BookmarkDAO.getInstance();
              rdao=RecipeDAO.getInstance();
              mdao=MemberDAO.getInstance();
              showMyRecipe();////////////////////////////////ȸ�� ���̵� ������
              showBookmark();//////////////////////////////ȸ�� ���̵� ������
       }//MyPageEvt
       
       // ���� ����� �޴� ����Ʈ
       public void showMyRecipe(){
              try {
                     List<MyRecipeVO> listMyRcp = rdao.myRecipe(logId);
                     Object[] rowMenu = new Object[6];
                     DefaultTableModel dtmMenu = mypf.getDtmMyMenu();
                     
                     MyRecipeVO myrv=null;
                     dtmMenu.setRowCount(0);
                     // name,img,type,info,price
                     for( int i=0; i < listMyRcp.size(); i++ ){
                           myrv=listMyRcp.get(i);
                           rowMenu[0]=myrv.getMenuName();
                           rowMenu[1]=myrv.getMenuImg();
                           rowMenu[2]=myrv.getMenuType();
                           rowMenu[3]=myrv.getMenuInfo();
                           rowMenu[4]=myrv.getMenuPrice();
                           rowMenu[5]=myrv.getFlag();
                           
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
                     List<BookmarkVO> bklist = bdao.searchAll(logId);
                     Object[] rowMenu = new Object[5];
                     DefaultTableModel dtmMenu = mypf.getDtmFavorMenu();
                     
                     BookmarkVO bmvo=null;
                     dtmMenu.setRowCount(0);
                     // name,img,type,info,price
                     for( int i=0; i < bklist.size(); i++ ){
                           bmvo=bklist.get(i);
                           rowMenu[0]=bmvo.getMenuName();
                           rowMenu[1]=bmvo.getImg();
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
    	   	  SignInForm sif=new SignInForm();
    	   	  String mail="";
    	   	  String id="duck"; /////////////////////////////���̵� ���� �ؾߵ� 

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
              sif.setBackgroundPath("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/edit_signinBack.png");
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
       
   	// �ݱ��ư
		public void checkCancel(){
			mypf.dispose();
		}//checkCancel
       
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
              if(ae.getSource()==mypf.getJbClose()){
                     checkCancel();
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
    					new ItemPreviewForm(mrv);
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
    					new ItemPreviewForm(mrv);
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
