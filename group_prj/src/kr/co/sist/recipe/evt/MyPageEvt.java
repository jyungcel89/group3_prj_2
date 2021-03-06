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
import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MyPageForm;
import kr.co.sist.recipe.view.SignInForm;
import kr.co.sist.recipe.vo.BookmarkUpdateVO;
import kr.co.sist.recipe.vo.BookmarkVO;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MyRecipeVO;

@SuppressWarnings("unused")
public class MyPageEvt extends WindowAdapter implements ActionListener, MouseListener {
       private MyPageForm mypf;
       private MainFormEvt mfe;
       private BookmarkDAO bdao;
       private RecipeDAO rdao;
       private MemberDAO mem_dao;
       private IngdntDAO idt_dao;
       private LogInEvt le;
       
		/**
	     * 마이페이지 이벤트
	     * <수정사항>
	     * 1. MyPageForm 객체명 변경 : mpf > mypf
	     * @param mypf
	     */
       public MyPageEvt(MyPageForm mypf){
              this.mypf=mypf;
              bdao=BookmarkDAO.getInstance();
              rdao=RecipeDAO.getInstance();
              mem_dao=MemberDAO.getInstance();
              idt_dao=IngdntDAO.getInstance();
              
              showMyRecipe();
              showBookmark();
       }//MyPageEvt
       
       // 내가 등록한 메뉴 리스트
       @SuppressWarnings("static-access")
       public void showMyRecipe(){
              try {
                     List<MyRecipeVO> listMyRcp = rdao.myRecipe(le.logId);
                     Object[] rowMenu = new Object[6];
                     DefaultTableModel dtmMenu = mypf.getDtmMyMenu();
                     String path = System.getProperty("user.dir")+"/src/kr/co/sist/recipe/img/s_";
                     
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
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
              
       }//showMyRecipe
       
       	/**
	     * 마이페이지 : 요청거절 삭제버튼 
	     * - 나의 등록레시피 상태창에서 "요청거절" 상태인 레시피만 삭제 처리
	     * - RecipeDAO - deleteRecipeUser(menuName) method 실행
	     */ 
	    public void rmvRecipe(){
			try {
				// 테이블에서 클릭 > menuName 가져오기
				JTable jtMyMenu=mypf.getJtMyMenu();
				int row=jtMyMenu.getSelectedRow();
				String value = (String) jtMyMenu.getValueAt(row, 0);
				String valueFlag = (String) jtMyMenu.getValueAt(row, 5);
				
				// valueFlag값이 "요청거절"이 아니면 return
				if( !valueFlag.equals("요청거절") ){
					JOptionPane.showMessageDialog(null, 
							"[ 요청거절 ] 상태인 레시피만 삭제 가능합니다.");
					return;
				}//end if
				int flag=JOptionPane.showConfirmDialog(null, 
						"[ "+value+" ] 선택하신 메뉴를 정말 삭제하시겠습니까?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// 가져온 menuName 값 > 삭제
					idt_dao.deleteIngdntOfRecp(value);
					rdao.deleteRecipeUser(value);
					JOptionPane.showMessageDialog(null,
							"성공적으로 삭제되었습니다.");
					
				}//end catch
				
				// 삭제 후 갱신
				showMyRecipe();//회원아이디 들어가야됨
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(null, 
						"레시피를 선택해주세요.");
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(null, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
       }//rmvRecipe
	    
       // 북마크한 메뉴 리스트
       @SuppressWarnings("static-access")
       public void showBookmark(){
              try {
                     List<BookmarkVO> bklist = bdao.searchAll(le.logId);
                     Object[] rowMenu = new Object[5];
                     DefaultTableModel dtmMenu = mypf.getDtmFavorMenu();
                     String path = System.getProperty("user.dir")+"/src/kr/co/sist/recipe/img/s_";
                     
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
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
              
       }//showBookmark
       
       // 북마크한 메뉴 삭제
       public void rmvBookmark() { 
              String id="duck";
              BookmarkUpdateVO bmuvo= new BookmarkUpdateVO();
              try {
                     // 테이블에서 클릭 > menuName 가져오기
                     JTable jtRcp=mypf.getJtFavorMenu();
                     int row=jtRcp.getSelectedRow();
                     String value = (String) jtRcp.getValueAt(row, 0);
                     int flag=JOptionPane.showConfirmDialog(null,
                                  "[ "+value+" ] 선택하신 메뉴를 정말 삭제하시겠습니까?");
                     switch (flag) {
                     case JOptionPane.OK_OPTION:
                           // 가져온 menuName 값 > 삭제
                           bmuvo.setId(id);
                           bmuvo.setMenuName(value);
                           bdao.rmvBookmark(bmuvo);
                     }//end catch
                     // 삭제 후 갱신
                     showBookmark();
              } catch (ArrayIndexOutOfBoundsException aioobe) {
                     JOptionPane.showMessageDialog(null,
                                  "레시피를 선택해주세요.");
                     aioobe.printStackTrace();
              } catch (SQLException se) {
                     JOptionPane.showMessageDialog(null,
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
       }//rmvBookmark
       
       // 내 정보창으로 이동 > 내정보 값 가져와서 SignInForm에 setter값을 설정
       public void goMyInfo(){
    	   new SignInForm();
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
	      			int selectNum = JOptionPane.showConfirmDialog(null, "MyPage창을 닫으시겠습니까?");
	      			switch (selectNum) {
	      			case JOptionPane.OK_OPTION:
	      				mypf.dispose();
      			}// end switch
      		}//end if
              
       }//actionPerformed
       

	@Override
       public void mouseClicked(MouseEvent me) {
     		if( me.getClickCount()==2 ){
    			// 메뉴리스트 더블클릭
    			if( me.getSource()==mypf.getJtMyMenu() ){
    				JTable jtMyRcp=mypf.getJtMyMenu();
    				int row=jtMyRcp.getSelectedRow();
    				String value=(String)jtMyRcp.getValueAt(row, 0);
//    				String valueFlag=(String)jtMyRcp.getValueAt(row, 5);
    				MainRecipeVO mrv;
    				try {
    					mrv=rdao.selectOneRecipe(value);
    					//MENU_NAME, IMG, FOOD_TYPE, INFO, RECIPE_INFO
    					new ItemPreviewForm(mrv, mfe);
    				} catch (SQLException se) {
    					JOptionPane.showMessageDialog(null, 
    							"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
    					return;
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
    							"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
    					se.printStackTrace();
    				}// end catch
    			}//end if
         	}//end if
              
       }//mouseClicked
       @Override
       public void mousePressed(MouseEvent e) {
              // TODO Auto-generated method stub
       }//mousePressed
       
       @Override
       public void mouseReleased(MouseEvent e) {
              // TODO Auto-generated method stub
       }//mouseReleased
       
       @Override
       public void mouseEntered(MouseEvent e) {
              // TODO Auto-generated method stub
       }//mouseEntered
       
       @Override
       public void mouseExited(MouseEvent e) {
              // TODO Auto-generated method stub
       }//mouseExited
}
