package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MainForm;

public class ItemPreviewEvt extends WindowAdapter implements ActionListener, ItemListener {

	private ItemPreviewForm previewFrm;

	public ItemPreviewEvt(ItemPreviewForm previewFrm) {
		this.previewFrm = previewFrm;
	}// ItemPreviewEvt

	// 메뉴정보 가져와서 보여줌
	public void showRcpInfo() {

	}// showRcpInfo

	// 평점 ( 조건문 : 없을때 있을때 )
	public void chkScore() {

	}// chkScore

	// 북마크 ( 조건문 : 체크 없을때 있을때 )
	public void chkBookmark() {

	}// chkBookmark

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == previewFrm.getJbClose()) {
			int selectNum = JOptionPane.showConfirmDialog(previewFrm, "창을 닫으시겠습니까?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION:
				previewFrm.dispose();
			}// end switch
		}//end if

	}//actionPerformed

}// class
