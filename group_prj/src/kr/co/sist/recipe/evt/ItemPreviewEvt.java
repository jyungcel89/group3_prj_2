package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MainForm;
import kr.co.sist.recipe.vo.IngrdntCategVO;
import kr.co.sist.recipe.vo.ShowIngdntVO;

public class ItemPreviewEvt extends WindowAdapter implements ActionListener, ItemListener {
	private IngdntDAO ida;
	private ItemPreviewForm ipf;
	private MainForm mf;
	public ItemPreviewEvt(ItemPreviewForm ipf) {
		this.ipf=ipf;
		ida=IngdntDAO.getInstance();
		showRcpInfo();
	}//ItemPreviewEvt
	
	// 메뉴정보 가져와서 보여줌
	public void showRcpInfo() {
	String recipeName=ipf.getJlRecipeName().getText().replace("▧ ","").replace(" ▧","");
	System.out.println(recipeName);
	DefaultTableModel dtm=(DefaultTableModel)ipf.getJtIngrednt().getModel();
	dtm.setNumRows(0);
	try{
	List<ShowIngdntVO> lstMenu=ida.selectIngdntOfRecp(recipeName);
	Object[] rowMenu=new Object[3];
	DefaultTableModel dtmMenu=ipf.getDtmIngrednt();
	ShowIngdntVO si=null;
	for( int i=0; i<lstMenu.size(); i++ ){
		si=lstMenu.get(i);
		rowMenu[0]=si.getIngrdntName();
		rowMenu[1]=si.getIngrdntPrice();
		dtmMenu.addRow(rowMenu);
	}
	}catch(SQLException se){
		se.printStackTrace();
	}
		
		
		
		
	}// showRcpInfo

	// 평점 ( 조건문 : 없을때 있을때 )
	public void chkScore() {

	}// chkScore

	// 북마크 ( 조건문 : 체크 없을때 있을때 )
	public void chkBookmark() {

	}// chkBookmark

	// 닫기
	public void checkCancel(){
		ipf.dispose();
	}//checkCancel
	
	@Override
	public void itemStateChanged(ItemEvent ie) {
		
	}//itemStateChanged

	@Override 
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == ipf.getJbClose()) {
			int selectNum = JOptionPane.showConfirmDialog(ipf, "창을 닫으시겠습니까?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION:
				ipf.dispose();
			}// end switch
		}//end if
		if(ae.getSource()==ipf.getJbSubmit()){
			
		}//end if
		if(ae.getSource()==ipf.getJbClose()){
			checkCancel();
		}//end if
		
	}//actionPerformed

}// class
