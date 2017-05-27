package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.MgrPageForm;
import kr.co.sist.recipe.vo.MgrMemberVO;
import kr.co.sist.recipe.vo.MgrRcpInfoListVO;

/**
 * 진행중
 * @author JiYong
 *
 */
public class MgrPageEvt extends WindowAdapter implements ActionListener {

	private MgrPageForm mpf;
	private MemberDAO mem_dao;
	private RecipeDAO rcp_dao;
	
	public MgrPageEvt( MgrPageForm mpf ) {
		this.mpf=mpf;
		mem_dao=MemberDAO.getInstance();
		
		memberList();
	}//MgrPageEvt
	
	//--------------------------전체메뉴관리 탭
	// 전체 메뉴리스트 조회
		public void allRecipeList(){
			
//			rcp_dao=RecipeDAO.getInstance();
//			MgrRcpInfoListVO mrl_vo=new MgrRcpInfoListVO();
			
			
		}//allRecipeList
		
	// 요청관리 리스트조회
		public void requestList(){
			
		}//requestList
	
	// 기존레시피 삭제 : 상위remove버튼
		public void rmvRecipe(){
			
		}//rmvRecipe
		
	// 요청레시피 거절 : 하위remove버튼
		public void rmvReqRecipe(){
			
		}//rmvReqRecipe
		
	// 요청레시피 승인 : submit버튼
		public void confirmReqRecipe(){
			
		}//confirmReqRecipe

	//--------------------------회원관리 탭
	// 회원관리 리스트
		public void memberList(){
			
			try {
				List<MgrMemberVO> list=mem_dao.selectAllMember();
				String[] rowMem = new String[3];
				DefaultTableModel dtmMem = mpf.getDtmMember();
				
				MgrMemberVO mmv=null;
				for( int i=0; i < list.size(); i++ ){
					mmv=list.get(i);
					rowMem[0]=mmv.getId();
					rowMem[1]=mmv.getName();
					rowMem[2]=mmv.getMail();
					
					dtmMem.addRow(rowMem);
				}//end for
				
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, "죄송합니다. 메뉴를 불러올 수 없습니다.");
				se.printStackTrace();
			}//end catch
			
		}//memberList
		
	// 회원탈퇴 시키기 : remove버튼
		public void rmvMember(){
			
		}//rmvMember
		
	// 닫기버튼
		public void checkCancel(){
			
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
	}//actionPerformed

}//class
