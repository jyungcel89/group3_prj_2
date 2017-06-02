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
import kr.co.sist.recipe.view.MyPageForm;
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
	private MyPageForm mypf;
	private MyPageEvt mype;
	private MainForm mf;
	private MainFormEvt mfe;
	
	
	
	public ItemPreviewEvt(MainForm mf, ItemPreviewForm ipf, MainFormEvt mfe) {
		this.mf = mf;
		this.ipf=ipf;
		this.mfe=mfe;
		ida=IngdntDAO.getInstance();
		showRcpInfo();
		//////////////////���� ///////////////////////
		bmdao=BookmarkDAO.getInstance();
		sdao=ScoreDAO.getInstance();
		chkScore();
		chkBookmark();
		
		
		//////////////////////////////////////////////
	}//ItemPreviewEvt
	
	// �޴����� �����ͼ� ������
	public void showRcpInfo() {
		String recipeName=ipf.getJlRecipeName().getText().replace("�� ","").replace(" ��","");
		DefaultTableModel dtm=(DefaultTableModel)ipf.getJtIngrednt().getModel();
		dtm.setNumRows(0);
		try{
		List<ShowIngdntVO> lstMenu=ida.selectIngdntOfRecp(recipeName);
		Object[] rowMenu=new Object[3];
		DefaultTableModel dtmMenu=ipf.getDtmIngrednt();
		ShowIngdntVO si=null;
		for( int i=0; i<lstMenu.size(); i++ ){
			si=lstMenu.get(i);
			System.out.println(si.getBrand());
			rowMenu[0]=si.getBrand();
			rowMenu[1]=si.getIngrdntName();
			rowMenu[2]=si.getIngrdntPrice();
			dtmMenu.addRow(rowMenu);
		}
		}catch(SQLException se){
			se.printStackTrace();
		}
			
	}// showRcpInfo

	// ���� ( ���ǹ� : ������ ������ )
	public void chkScore() {
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
		String id="duck";/////////////////////////////////////////////////////////////////////////////////////���̵� �����ؾߵ� 
		String menuName=ipf.getJlRecipeName().getText().replaceAll(" ", "").replaceAll("��","");
		int score =0;
		
		bmuvo.setId(id);
		bmuvo.setMenuName(menuName);
		
		try {
			score=sdao.popUpChkScore(bmuvo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "����Ŀ� �õ��� �ּ��� ");
			e.printStackTrace();
		}
		
		scoreFlag=score;
		ipf.getJcScore().setSelectedIndex(score);
	}// chkScore

	// �ϸ�ũ ( ���ǹ� : üũ ������ ������ )
	public void chkBookmark() {
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
		String id="duck";/////////////////////////////////////////////////////////////////////////////////////���̵� �����ؾߵ� 
		String menuName=ipf.getJlRecipeName().getText().replaceAll(" ", "").replaceAll("��","");
		bmuvo.setId(id);
		bmuvo.setMenuName(menuName);
		
		try {
			if(bmdao.popUpChkBookmark(bmuvo)){
				ipf.getJchBookmark().setSelected(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ipf, "����Ŀ� �õ��� �ּ��� ");
		}
		
		
		
	}// chkBookmark

	
	/////////////////////////////////////////////�ϸ�ũ üũ��
	public void insertBookmark(){
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
		
		String id="duck";/////////////////////////////////////////////////////////////////////////////////////���̵� �����ؾߵ� 
		String menuName=ipf.getJlRecipeName().getText().replaceAll(" ", "").replaceAll("��","");
		
		bmuvo.setId(id);
		bmuvo.setMenuName(menuName);
		try {
			bmdao.insertBookmark(bmuvo);
			JOptionPane.showMessageDialog(ipf, "�ϸ�ũ�� �߰��Ǿ����ϴ�.");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "����Ŀ� �õ��� �ּ��� ");
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////�ϸ�ũ ������
	public void rmvBookmark(){
		BookmarkUpdateVO bmuvo = new BookmarkUpdateVO();
		
		String id="duck";/////////////////////////////////////////////////////////////////////////////////////���̵� �����ؾߵ� 
		String menuName=ipf.getJlRecipeName().getText().replaceAll(" ", "").replaceAll("��","");
		
		bmuvo.setId(id);
		bmuvo.setMenuName(menuName);
		try {
			bmdao.rmvBookmark(bmuvo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "����Ŀ� �õ��� �ּ��� ");
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////���ھ� ���� �ֱ�
	public void insertScore(){
		ScoreVO svo = new ScoreVO();
		
		String id="duck";/////////////////////////////////////////////////////////////////////////////////////���̵� �����ؾߵ� 
		String menuName=ipf.getJlRecipeName().getText().replaceAll(" ", "").replaceAll("��","");
		int value=ipf.getJcScore().getSelectedIndex();
		if(value==0){
			JOptionPane.showMessageDialog(ipf, "������ 0���� �ֽǼ� �����ϴ�.");
			return;
		}
		
		svo.setId(id);
		svo.setMenuName(menuName);
		svo.setValue(value);
		try {
			sdao.insertScore(svo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "����Ŀ� �õ��� �ּ��� ");
			e.printStackTrace();
		}
	}
	
	
	/////////////////////////////////////////////���ھ� ��������
	public void updateScroe(){
		
		try {
			ScoreVO svo = new ScoreVO();
			String id= mfe.logId; 
			String menuName=ipf.getJlRecipeName().getText().replaceAll(" ", "").replaceAll("��","");
			int value=ipf.getJcScore().getSelectedIndex();
			if(value==0){
				JOptionPane.showMessageDialog(ipf, "������ �����Ͽ� �ּ���.");
				return;
			}//end if
			svo.setId(id);
			svo.setMenuName(menuName);
			svo.setValue(value);
			sdao.updateScore(svo);
//			mfe.newRecipe();
			mfe.searchList();
			JOptionPane.showMessageDialog(ipf, value+"���� �ݿ��Ǿ����ϴ�.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ipf, "����Ŀ� �õ��� �ּ��� ");
			e.printStackTrace();
		}//end catch
	}//updateScore
	
	@Override
	public void itemStateChanged(ItemEvent ie) {
		
	}//itemStateChanged

	@Override 
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == ipf.getJbClose()) {
			int selectNum = JOptionPane.showConfirmDialog(ipf, "â�� �����ðڽ��ϱ�?");
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
				insertScore();
			}else{
				updateScroe();
			}//end else
			
		}//end if
		
	}//actionPerformed

}// class
