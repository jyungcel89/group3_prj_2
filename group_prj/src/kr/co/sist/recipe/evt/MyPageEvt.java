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
public class MyPageEvt extends WindowAdapter implements ActionListener, MouseListener {
       private MyPageForm mpf;
       private BookmarkDAO bdao;
       private RecipeDAO rdao;
       private BookmarkVO bv;
       
       public MyPageEvt(MyPageForm mpf){
              this.mpf=mpf;
              bdao=BookmarkDAO.getInstance();
              rdao=RecipeDAO.getInstance();
              showMyRecipe("mgr");////////////////////////////////회원 아이디 들어가야함
              showBookmark();//////////////////////////////회원 아이디 들어가야함
       }
       
       // 내가 등록한 메뉴 리스트
       public void showMyRecipe(String id){
              try {
                     List<MyRecipeVO> listMyRcp = rdao.myRecipe(id);
                     Object[] rowMenu = new Object[6];
                     DefaultTableModel dtmMenu = mpf.getDtmMyMenu();
                     
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
                     JOptionPane.showMessageDialog(mpf,
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
              
              
       }//showMyRecipe
       
       // 북마크한 메뉴 리스트
       public void showBookmark(){
              String id="duck";
              try {
                     List<BookmarkVO> bklist = bdao.searchAll(id);
                     Object[] rowMenu = new Object[5];
                     DefaultTableModel dtmMenu = mpf.getDtmFavorMenu();
                     
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
                     JOptionPane.showMessageDialog(mpf,
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
              
       }//showBookmark
       
       // 북마크한 메뉴 삭제
       public void rmvBookmark() throws SQLException{
              String id="duck";
              BookmarkUpdateVO bmuvo= new BookmarkUpdateVO();
              try {
                     // 테이블에서 클릭 > menuName 가져오기
                     JTable jtRcp=mpf.getJtFavorMenu();
                     int row=jtRcp.getSelectedRow();
                     String value = (String) jtRcp.getValueAt(row, 0);
                     int flag=JOptionPane.showConfirmDialog(mpf,
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
                     JOptionPane.showMessageDialog(mpf,
                                  "레시피를 선택해주세요.");
                     aioobe.printStackTrace();
              } catch (SQLException se) {
                     JOptionPane.showMessageDialog(mpf,
                                  "죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
                     se.printStackTrace();
              }//end catch
       }//rmvBookmark
       
       // 내 정보창으로 이동 > 내정보 값 가져와서 SignInForm에 setter값을 설정
       public void goMyInfo(){
              SignInForm sif=new SignInForm();
              //////// 필요 없는 버튼들 안보이게 //////
              sif.getJbtChkId().setVisible(false);
              sif.getJbtSubmit().setVisible(false);
              sif.setBackgroundPath("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/edit_signinBack.png");
              ////////////////////////////////////////////
              ///////////////////취소 버튼 수정 버튼으로 변경////////////////
              //변경할래 아니면 버튼하나 더만들어두고 켰다 껐다 할래 //
              ////////////////////////////////////////////////////////////////////
              ////////////////아이디 이름 부분 정보 입력/////
              ////////////////////////////////////////////////////
              
              //////////////수정 불가 부분 설정///////////////
              sif.getJtfId().setEditable(false);
              sif.getJtfName().setEditable(false);
              ///////////////////////////////////////////////////
              
              /////////////////버튼 이름 변경/////////////////
              ///////////////////////////////////////////////////
              /////////버튼 이름 변경함과 동시에 sigevt부분에 메소드 추가되어야함 (이벤트)//
       }//goMyInfo
       
       
       
       
       @Override
       public void actionPerformed(ActionEvent ae) {
              if(ae.getSource()==mpf.getJbEditMyInfo()){
                     goMyInfo();
              }//end if
              if(ae.getSource()==mpf.getJbRmvFavorMenu()){
                     try {
                           rmvBookmark();
                     } catch (SQLException e) {
                           e.printStackTrace();
                     }
              }//end if
              
              if (ae.getSource() == mpf.getJbClose()) {
      			int selectNum = JOptionPane.showConfirmDialog(mpf, "창을 닫으시겠습니까?");
      			switch (selectNum) {
      			case JOptionPane.OK_OPTION:
      				mpf.dispose();
      			}// end switch
      		}//end if
              
       }//actionPerformed
       
       @Override
       public void mouseClicked(MouseEvent me) {
              if( me.getSource()==mpf.getJtFavorMenu() ){
                     if( me.getClickCount()==2 ){
//                         new ItemPreviewForm(MainForm mf, MainRecipeVO mrv);
                     }
              }
              
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
