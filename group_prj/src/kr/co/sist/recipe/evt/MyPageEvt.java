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
	     * 마이페이지 이벤트
	     * <수정사항>
	     * 1. MyPageForm 객체명 변경 : mpf > mypf
	     * @param mypf
	     */
       public MyPageEvt(MyPageForm mypf, String logId){
              this.mypf=mypf;
              this.logId=logId;
              bdao=BookmarkDAO.getInstance();
              rdao=RecipeDAO.getInstance();
              mdao=MemberDAO.getInstance();
              showMyRecipe();////////////////////////////////회원 아이디 들어가야함
              showBookmark();//////////////////////////////회원 아이디 들어가야함
       }//MyPageEvt
       
       // 내가 등록한 메뉴 리스트
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
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
              
              
       }//showMyRecipe
       
       //05-29-2017
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
//				System.out.println(row);
//				System.out.println("row : "+row+", 선택 값 : "+value+
//						"\n row : "+row+", 선택 값 : "+valueFlag);
//				System.out.println("delFlag : "+rdao.deleteRecipeUser(value));
				
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
					rdao.deleteRecipeUser(value);
				}//end catch
				
				// 삭제 후 갱신
				showMyRecipe();//회원아이디 들어가야됨
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(null, "레시피를 선택해주세요.");
//				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(null, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
       }//rmvRecipe
       
       // 북마크한 메뉴 리스트
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
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
              
       }//showBookmark
       
       // 북마크한 메뉴 삭제
       public void rmvBookmark(){
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
    	   	  SignInForm sif=new SignInForm();
    	   	  String mail="";
    	   	  String id="duck"; /////////////////////////////아이디 연결 해야됨 

              try {
            	  mail=mdao.selectMyInfo(id).toString();
			} catch (SQLException e) {
				e.printStackTrace();
			}
              sif.getJtfId().setText(id);
              sif.getJtfName().setText(mail);
              
              //////// 필요 없는 버튼들 안보이게 //////
              sif.getJbtChkId().setVisible(false);
              sif.getJbtSubmit().setVisible(false);
              sif.setBackgroundPath("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/edit_signinBack.png");
              sif.getJbtCancel().setVisible(false);
              ////////////////////////////////////////////
              ///////////////////취소 버튼 수정 버튼으로 변경////////////////
              sif.getJbtUpdate().setVisible(true);
              ////////////////////////////////////////////////////////////////////
              ////////////////아이디 이름 부분 정보 입력//////////////////////////////////아이디 연결 부분 !!!!!!!!!!!!!!!!!!!!!!!!!
              
              ////////////////////////////////////////////////////
              
              //////////////수정 불가 부분 설정///////////////
              sif.getJtfId().setEditable(false);
              sif.getJtfName().setEditable(false);
              ///////////////////////////////////////////////////
              
              /////////////////버튼 이름 변경/////////////////
              ///////////////////////////////////////////////////
              /////////버튼 이름 변경함과 동시에 sigevt부분에 메소드 추가되어야함 (이벤트)//
       }//goMyInfo
       
   	// 닫기버튼
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
      			int selectNum = JOptionPane.showConfirmDialog(null, "창을 닫으시겠습니까?");
      			switch (selectNum) {
      			case JOptionPane.OK_OPTION:
      				mypf.dispose();
      			}// end switch
      		}//end if
              
       }//actionPerformed
       
       @Override
       public void mouseClicked(MouseEvent me) {
    	   //05-29-2017
    	   //나중에 북마크테이블로 변경할것!
     		if( me.getClickCount()==2 ){
    			// 메뉴리스트 더블클릭
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
    							"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
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
    							"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
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
