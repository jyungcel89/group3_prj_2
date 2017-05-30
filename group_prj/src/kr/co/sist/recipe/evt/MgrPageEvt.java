package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MgrPageForm;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MgrMemberVO;

/**
 * 관리자페이지 이벤트 클래스
 * flag에 대한 재분류 고려중
 * @author JiYong
 *
 */ 
public class MgrPageEvt extends WindowAdapter implements ActionListener, MouseListener {

	private MgrPageForm mpf;
	private MemberDAO mem_dao;
	private RecipeDAO rcp_dao;
	
	public MgrPageEvt( MgrPageForm mpf ) {
		this.mpf=mpf;
		mem_dao=MemberDAO.getInstance();
		rcp_dao=RecipeDAO.getInstance();
		allRecipeList();
		requestList();
		memberList();
	}//MgrPageEvt
	
	//--------------------------전체메뉴관리 탭------------------------
		/**
		 * 전체 메뉴리스트 조회
		 *  recipe_flag='Y'
		 * 승인된 레시피 전체 리스트를 조회
		 * RecipeDAO - recipeList("Y") method 를 실행
		 * menu_flag='Y' 
		 */
		public void allRecipeList(){
			
			try {
				String flag="Y";
				List<MainRecipeVO> listAllRcp = rcp_dao.recipeList(flag);
				Object[] rowMenu = new Object[5];
				DefaultTableModel dtmMenu = mpf.getDtmMenuList();
				
				MainRecipeVO mrv=null; 
				dtmMenu.setRowCount(0);
				// name,img,type,info,price 
				for( int i=0; i < listAllRcp.size(); i++ ){
					mrv=listAllRcp.get(i);
					rowMenu[0]=mrv.getMenuName();
					rowMenu[1]=mrv.getMenuImg();
					rowMenu[2]=mrv.getMenuType();
					rowMenu[3]=mrv.getMenuSimpeInfo();
					rowMenu[4]=mrv.getMenuPrice();
					
					dtmMenu.addRow(rowMenu);
				}//end for
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
		}//allRecipeList
		
		
		/**
		 * 레시피 요청 리스트 조회
		 * - recipe_flag='S'
		 * - 승인처리되지 않은 승인대기 레시피 전체 리스트를 조회
		 * - RecipeDAO - recipeList("S") method 를 실행
		 */
		public void requestList(){
			
			try {
				String flag="S";
				List<MainRecipeVO> listReqRcp = rcp_dao.recipeList(flag);
				Object[] rowMenu = new Object[5];
				DefaultTableModel dtmMenu = mpf.getDtmMenuRequest();
				
				MainRecipeVO mrv=null;
				dtmMenu.setRowCount(0);
				// name,img,type,info,price 
				for( int i=0; i < listReqRcp.size(); i++ ){
					mrv=listReqRcp.get(i);
					rowMenu[0]=mrv.getMenuName();
					rowMenu[1]=mrv.getMenuImg();
					rowMenu[2]=mrv.getMenuType();
					rowMenu[3]=mrv.getMenuSimpeInfo();
					rowMenu[4]=mrv.getMenuPrice();
					
					dtmMenu.addRow(rowMenu);
				}//end for
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
		}//requestList
	
		
		/**
		 * 기존레시피 삭제 : 상위remove버튼
		 * 기존레시피를 삭제 처리해주는 method
		 * 선택된 레시피의 menuName값을 가져와서
		 * RecipeDAO - deleteRecipe(menuName) method 를 실행
		 */
		public void rmvRecipe(){
			
			try {
				// 테이블에서 클릭 > menuName 가져오기
				JTable jtRcp=mpf.getJtMenuList();
				int row=jtRcp.getSelectedRow();
				String value = (String) jtRcp.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", 선택 값 : "+value);
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
				
				int flag=JOptionPane.showConfirmDialog(mpf, 
						"[ "+value+" ] 선택하신 메뉴를 정말 삭제하시겠습니까?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// 가져온 menuName 값 > 삭제
					rcp_dao.deleteRecipe(value);
				}//end catch
				// 삭제 후 갱신
				allRecipeList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch

		}//rmvRecipe
		
		
		/**
		 * 요청레시피 거절 : 하위remove버튼
		 * recipe_flag S > N
		 * - 승인대기 상태인 레시피를 요청거절 상태로 처리해주는 method
		 * - 선택된 레시피의 menuName값을 가져와서 
		 * - RecipeDAO - updateFlagY(menuName) method 를 실행
		 */
		public void rmvReqRecipe(){
			
			try {
				// 테이블에서 클릭 > menuName 가져오기
				JTable jtReqRcp=mpf.getJtMenuRequest();
				int row=jtReqRcp.getSelectedRow();
				String value = (String) jtReqRcp.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", 선택 값 : "+value);
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
				
				int flag=JOptionPane.showConfirmDialog(mpf, 
						"[ "+value+" ] 선택하신 메뉴를 요청거절 하시겠습니까?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// 가져온 menuName 값 > 삭제
					rcp_dao.updateFlagN(value);
				}//end catch
				// 삭제 후 갱신
				requestList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
			
		}//rmvReqRecipe
		
		
		/**
		 * 요청레시피 승인 : submit버튼
		 * - recipeFlag 'S' > 'Y'
		 * - 승인대기 상태인 레시피를 승인 처리해주는 method
		 * - 선택된 레시피의 menuName값을 가져와서 
		 * - RecipeDAO - updateFlagY(menuName) method 를 실행
		 */
		public void confirmReqRecipe(){
			
			try {
				// 테이블에서 클릭 > menuName 가져오기
				JTable jtSmtRcp=mpf.getJtMenuRequest();
				int row=jtSmtRcp.getSelectedRow();
				String value = (String) jtSmtRcp.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", 선택 값 : "+value);
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
				
				int flag=JOptionPane.showConfirmDialog(mpf, 
						"[ "+value+" ] 선택하신 요청 메뉴를 승인 하시겠습니까?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// 가져온 menuName 값 > 삭제
					rcp_dao.updateFlagY(value);
				}//end catch
				// 삭제 후 갱신
				requestList();
				allRecipeList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
		}//confirmReqRecipe

		
	//--------------------------회원관리 탭----------------------------
		/**
		 * 회원관리 리스트 : 자동갱신
		 * 전체회원의 id, name, mail을 테이블에 보여준다.
		 * MemberDAO - selectAllMember() method를 실행
		 */
		public void memberList(){
			
			try {
				List<MgrMemberVO> listMem=mem_dao.selectAllMember();
				String[] rowMem = new String[3];
				DefaultTableModel dtmMem = mpf.getDtmMember();
				
				MgrMemberVO mmv=null;
				dtmMem.setRowCount(0);
				//id,name,mail
				for( int i=0; i < listMem.size(); i++ ){
					mmv=listMem.get(i);
					rowMem[0]=mmv.getId();
					rowMem[1]=mmv.getName();
					rowMem[2]=mmv.getMail();
					
					dtmMem.addRow(rowMem);
				}//end for
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 회원정보를 불러올 수 없습니다.");
				se.printStackTrace();
			}//end catch
		}//memberList
		
		
		/**
		 * 회원탈퇴 시키기 : remove 버튼
		 * 테이블에서 선택된 회원 id를 가져와서 
		 * MemberDAO - deleteMember(id) method를 실행
		 */
		public void rmvMember(){ 
			
			try {
				// 테이블에서 클릭 > id 가져오기
				JTable jtMem=mpf.getJtMember();
				int row=jtMem.getSelectedRow();
				String value = (String) jtMem.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", 선택 값 : "+value);
			int flag=JOptionPane.showConfirmDialog(mpf, 
					"[ "+value+" ] 선택하신 회원을 영구적으로 삭제하시겠습니까?");
			switch (flag) {
			case JOptionPane.OK_OPTION:
				// 가져온 id 값 > 삭제
				mem_dao.deleteMember(value);
			}//end switch
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
			
				// 삭제 후 갱신
				memberList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"레시피를 선택해주세요.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"죄송합니다. 일시적인 서버장애가 발생하였습니다.\n잠시후에 다시 시도해주세요.");
				se.printStackTrace();
			}//end catch
		}//rmvMember
		
	// 닫기버튼
		public void checkCancel(){
			mpf.dispose();
		}//checkCancel

	@Override
	public void actionPerformed(ActionEvent ae) {
		// 관리자 - 메뉴관리탭 - 메뉴리스트 삭제버튼
		if( ae.getSource() == mpf.getJbRmvMenu() ){
				rmvRecipe();
		}//end if
		// 관리자 - 메뉴관리탭 - 메뉴요청리스트 요청거절버튼
		if( ae.getSource() == mpf.getJbRmvRqust() ){
			rmvReqRecipe();
		}//end if
		// 관리자 - 메뉴관리탭 - 메뉴요청리스트 요청승인버튼
		if( ae.getSource() == mpf.getJbSmitRqust() ){
			confirmReqRecipe();
		}//end if
		// 관리자 - 회원관리탭 - 전체 회원리스트 삭제버튼
		if( ae.getSource() == mpf.getJbRmvMember() ){
			rmvMember();
		}//end if
		// 관리자 메뉴 취소버튼
		if( ae.getSource() == mpf.getJbClose() ){
			checkCancel();
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
		if( me.getSource()==mpf.getJtMenuRequest() ){
			if( me.getClickCount()==2 ){
//				new ItemPreviewEvt();
			}
		}
			if( me.getSource()==mpf.getJtMenuList() ){
				if( me.getClickCount()==2 ){
					new AddRecipeForm();
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

}//class
