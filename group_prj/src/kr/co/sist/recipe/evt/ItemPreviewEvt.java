package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.dao.BookmarkDAO;
import kr.co.sist.recipe.dao.ScoreDAO;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MainForm;
import kr.co.sist.recipe.vo.ShowIngdntVO;
import kr.co.sist.recipe.vo.BookmarkUpdateVO;
import kr.co.sist.recipe.vo.ScoreVO;

public class ItemPreviewEvt extends WindowAdapter implements ActionListener, ItemListener {
	private IngdntDAO ida;
	private ItemPreviewForm ipf;
	private BookmarkDAO bmdao;
	private ScoreDAO sdao;
	private int scoreFlag;  
	private MainForm mf;
	private MainFormEvt mfe;
	private LogInEvt le;
	
	
	@SuppressWarnings("static-access")
	public ItemPreviewEvt(MainForm mf, ItemPreviewForm ipf, MainFormEvt mfe) {
		this.mf = mf;
		this.ipf=ipf;
		this.mfe=mfe;
		
		ida=IngdntDAO.getInstance();
		showRcpInfo();
		//////////////////복사 ///////////////////////
		bmdao=BookmarkDAO.getInstance();
		sdao=ScoreDAO.getInstance();
		chkScore();
		chkBookmark();
		
		// 관리자 북마크, 수정 X
		if(le.logId.equals("mgr")){
			ipf.getJchBookmark().setEnabled(false);
			ipf.getJbSubmit().setVisible(false);
		}//end if
		
		//////////////////////////////////////////////
	}//ItemPreviewEvt
	
	// 메뉴정보 가져와서 보여줌
	public void showRcpInfo() {
		String recipeName=ipf.getJlRecipeName().getText().replace("▧","");
		DefaultTableModel dtm=(DefaultTableModel)ipf.getJtIngrednt().getModel();
		dtm.setNumRows(0);
		try{
			List<ShowIngdntVO> lstMenu=ida.selectIngdntOfRecp(recipeName);
			Object[] rowMenu=new Object[3];
			DefaultTableModel dtmMenu=ipf.getDtmIngrednt();
			ShowIngdntVO si=null;
			for( int i=0; i<lstMenu.size(); i++ ){
				si=lstMenu.get(i);
//				System.out.println(si.getBrand());
				rowMenu[0]=si.getBrand();
				rowMenu[1]=si.getIngrdntName();
				rowMenu[2]=si.getIngrdntPrice();
				dtmMenu.addRow(rowMenu);
			}//end for
		}catch(SQLException se){
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
			se.printStackTrace();
		}//end catch
			
	}// showRcpInfo

	// 평점 ( 조건문 : 없을때 있을때 )
	public void chkScore() {
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
//		String id="duck";/////////////////////////////////////////////////////////////////////////////////////아이디 연결해야됨 
		String menuName=ipf.getJlRecipeName().getText().replaceAll("▧","");
		int score =0;
		
		bmuvo.setId(le.logId);
		bmuvo.setMenuName(menuName);
		
		try {
			score=sdao.popUpChkScore(bmuvo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
			e.printStackTrace();
		}//end catch
		
		scoreFlag=score;
		ipf.getJcScore().setSelectedIndex(score);
	}// chkScore

	// 북마크 ( 조건문 : 체크 없을때 있을때 )
	public void chkBookmark() {
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
//		String id="duck";/////////////////////////////////////////////////////////////////////////////////////아이디 연결해야됨 
		String menuName=ipf.getJlRecipeName().getText().replaceAll("▧","");
		bmuvo.setId(le.logId);
		bmuvo.setMenuName(menuName);
		
		try {
			if(bmdao.popUpChkBookmark(bmuvo)){
				ipf.getJchBookmark().setSelected(true);
			}//end if
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
		}//end catch
		
	}// chkBookmark

	
	/////////////////////////////////////////////북마크 체크시
	public void insertBookmark(){
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
		
		String menuName=ipf.getJlRecipeName().getText().replaceAll("▧","");
		
		bmuvo.setId(le.logId);
		bmuvo.setMenuName(menuName);
		try {
			bmdao.insertBookmark(bmuvo);
			JOptionPane.showMessageDialog(ipf, "북마크가 추가되었습니다.");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
			e.printStackTrace();
		}//end catch
	}//insertBookmark
	/////////////////////////////////////////////북마크 해제시
	public void rmvBookmark(){
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
		
//		String id="duck";/////////////////////////////////////////////////////////////////////////////////////아이디 연결해야됨 
		String menuName=ipf.getJlRecipeName().getText().replaceAll("▧","");
		
		bmuvo.setId(le.logId);
		bmuvo.setMenuName(menuName);
		try {
			bmdao.rmvBookmark(bmuvo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////스코어 점수 주기
	public void insertScore(){
		ScoreVO svo = new ScoreVO();
		
//		String id="duck";/////////////////////////////////////////////////////////////////////////////////////아이디 연결해야됨 
		String menuName=ipf.getJlRecipeName().getText().replaceAll("▧","");
		int value=ipf.getJcScore().getSelectedIndex();
		if(value==0){
			JOptionPane.showMessageDialog(ipf, "점수는 0점을 주실수 없습니다.");
			return;
		}//end if
		
		svo.setId(le.logId);
		svo.setMenuName(menuName);
		svo.setValue(value);
		
		try {
			sdao.insertScore(svo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
			e.printStackTrace();
		}//end catch
	}//insertScore
	
	
	/////////////////////////////////////////////스코어 점수변경
	public void updateScroe(){
		
		try {
			ScoreVO svo = new ScoreVO();
			String id= le.logId; 
			String menuName=ipf.getJlRecipeName().getText().replaceAll("▧","");
			int value=ipf.getJcScore().getSelectedIndex();
			if(value==0){
				JOptionPane.showMessageDialog(ipf, "점수를 선택하여 주세요.");
				return;
			}//end if
			svo.setId(id);
			svo.setMenuName(menuName);
			svo.setValue(value);
			sdao.updateScore(svo);
//			mfe.newRecipe();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "잠시후에 시도해 주세요 ");
			e.printStackTrace();
		}//end catch
	}//updateScore
	
	
	@Override
	public void itemStateChanged(ItemEvent ie) {
		
	}//itemStateChanged

	@Override 
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == ipf.getJbClose()) {
			int selectNum = JOptionPane.showConfirmDialog(ipf, "[ 상품 미리보기 ] 창을 닫으시겠습니까?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION:
				ipf.dispose();
			}// end switch
		}//end if
		 
		if(ae.getSource()==ipf.getJchBookmark()){
			if(ipf.getJchBookmark().isSelected()){
				insertBookmark();
			}else{
				rmvBookmark();
			}//end else
		}//end if
		
		if(ae.getSource()==ipf.getJbSubmit()){
			if(scoreFlag==0){
//				System.out.println("insert");
				insertScore();
				mfe.searchList();
				scoreFlag=1;
				JOptionPane.showMessageDialog(ipf,"점수가 반영되었습니다.");
			}else{
//				System.out.println("update");
				updateScroe();
				mfe.searchList();
				JOptionPane.showMessageDialog(ipf,"점수가 반영되었습니다.");
			}//end else
			
		}//end if
		
	}//actionPerformed

}// class
