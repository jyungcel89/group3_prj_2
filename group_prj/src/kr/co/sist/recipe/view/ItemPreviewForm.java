package kr.co.sist.recipe.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.vo.MainRecipeVO;

@SuppressWarnings("serial")
public class ItemPreviewForm extends JDialog {
	
	private JTable jtIngrednt;
	private JTextArea jtaDetail, jtaSimple;
	private JButton jbClose,jbSubmit;
	private JLabel jlRecipeName, jlIngrednt;
	private DefaultTableModel dtmIngrednt;
	private JScrollPane jspIngrednt;
	private JComboBox<String> jcScore;
	private JCheckBox jchBookmark;
	private JScrollPane jspTextArea;
	
	public ItemPreviewForm(MainForm mf, MainRecipeVO mrv){
		setLayout(null);
		 
		//region 상품명,재료,별점,북마크,만드는법,이미지 라벨및 이미지 아이콘 구역
		Font defaultFont=new Font("맑은고딕",Font.BOLD,13);
		
		jlRecipeName=new JLabel("▧ "+mrv.getMenuName()+" ▧");
		jlRecipeName.setFont(new Font("맑은고딕", Font.BOLD, 17));
		jlRecipeName.setBounds(30,70,300,30);
		
		jlIngrednt=new JLabel("◑ 재료");
		jlIngrednt.setBounds(400,70,100,30);
		
		JLabel jlScore=new JLabel("별점");
		jlScore.setBounds(570,320,100,30);
		
		JLabel jlBookmark=new JLabel("북마크");
		jlBookmark.setBounds(435,320,50,30);
		
		JLabel jlMakeMethod=new JLabel("◑ 레시피");
		jlMakeMethod.setBounds(400,380,70,30);
		
		// 이미지 경로
		String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/";
		// 이미지 넣어주기
		ImageIcon imgIcon=new ImageIcon(path+mrv.getMenuImg());
		JLabel imgLabel=new JLabel(imgIcon);
		imgLabel.setBounds(30,100,330,270);
	
		JLabel jlSimpleInfo = new JLabel("◑ 간단 설명");
		jlSimpleInfo.setBounds(30, 380, 100, 30);
		
		//region 재료테이블 영역
		String[] columnNames={"재료명","가격"};
		String[][] data={};
		dtmIngrednt=new DefaultTableModel(data, columnNames);
		jtIngrednt=new JTable(dtmIngrednt){
			private static final long serialVersionUID = 1L;
			//컬럼의 수정 막기 위한 method Override
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		//컬럼을 선택하여 움직이지 못하도록 설정
		jtIngrednt.getTableHeader().setReorderingAllowed(false);
		//컬럼의 높이 설정
		jtIngrednt.setRowHeight(100);
		//컬럼의 넒이 설정 
		//"번호","이미지","메뉴코드","설명","가격"
		jtIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
		jtIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
		jspIngrednt=new JScrollPane(jtIngrednt);
		jspIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspIngrednt.setBounds(400,100,350,180);
		
		//endregion 재료테이블영역 끝
		
		//region 좋아요 체크박스 영역
		jchBookmark=new JCheckBox();
		jchBookmark.setBounds(480,320,20,30);
		//endregion 좋아요 체크박스 영역 끝
	
		//region 별점 콤보박스 영역
		jcScore=new JComboBox<String>();
		jcScore.addItem("--");
		jcScore.addItem("1");
		jcScore.addItem("2");
		jcScore.addItem("3");
		jcScore.addItem("4");
		jcScore.addItem("5");
		jcScore.setBounds(610,325,50,20);
		//endregion 별점 콤보박스 영역 끝
		
		//region 닫기,제출 버튼 영역
		jbSubmit=new JButton("제출!");
		jbSubmit.setBounds(670,325,70,20);
		
		jbClose=new JButton("확인");
		jbClose.setBounds(650,610,100,25);
		//endregion 닫기,제출버튼 영역 끝
		
		//region 만드는법 TextArea
		
		jtaSimple=new JTextArea(mrv.getMenuSimpeInfo());
		jtaSimple.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		jtaSimple.setLineWrap(true);
		jtaSimple.setBounds(30, 420, 330, 70);
		jtaSimple.setEditable(false);
		
		jtaDetail=new JTextArea(mrv.getMenuDetailInfo());
		jtaDetail.setLineWrap(true);
		jspTextArea=new JScrollPane(jtaDetail);
		jspTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspTextArea.setBounds(400,420,350,150);
		//endregion 만드는법 TextArea끝
		
		add(jlRecipeName);
		add(jtaSimple);
		
		Component[] com={imgLabel,jlIngrednt,jspIngrednt,jlScore,jlBookmark,jcScore,jchBookmark
				,jbSubmit,jlMakeMethod,jspTextArea,jbClose,jlSimpleInfo};
		
		for(int i=0; i<com.length;i++){
			com[i].setFont(defaultFont);
			add(com[i]);
		}
		setVisible(true);
		setBounds(0,0,800,700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}//ItemPreviewForm

	public JTable getJtIngrednt() {
		return jtIngrednt;
	}

	public void setJtIngrednt(JTable jtIngrednt) {
		this.jtIngrednt = jtIngrednt;
	}

	public JTextArea getJtaInfo() {
		return jtaDetail;
	}

	public void setJtaInfo(JTextArea jtaInfo) {
		this.jtaDetail = jtaInfo;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public void setJbClose(JButton jbClose) {
		this.jbClose = jbClose;
	}

	public JButton getJbSubmit() {
		return jbSubmit;
	}

	public void setJbSubmit(JButton jbSubmit) {
		this.jbSubmit = jbSubmit;
	}

	public JLabel getJlRecipeName() {
		return jlRecipeName;
	}

	public void setJlRecipeName(JLabel jlRecipeName) {
		this.jlRecipeName = jlRecipeName;
	}

	public JLabel getJlIngrednt() {
		return jlIngrednt;
	}

	public void setJlIngrednt(JLabel jlIngrednt) {
		this.jlIngrednt = jlIngrednt;
	}
	
	public DefaultTableModel getDtmIngrednt() {
		return dtmIngrednt;
	}

	public void setDtmIngrednt(DefaultTableModel dtmIngrednt) {
		this.dtmIngrednt = dtmIngrednt;
	}

	public JScrollPane getJspIngrednt() {
		return jspIngrednt;
	}

	public void setJspIngrednt(JScrollPane jspIngrednt) {
		this.jspIngrednt = jspIngrednt;
	}

	public JComboBox<String> getJcScore() {
		return jcScore;
	}

	public void setJcScore(JComboBox<String> jcScore) {
		this.jcScore = jcScore;
	}

	public JCheckBox getJchBookmark() {
		return jchBookmark;
	}

	public void setJchBookmark(JCheckBox jchBookmark) {
		this.jchBookmark = jchBookmark;
	}

	public JScrollPane getJspTextArea() {
		return jspTextArea;
	}

	public void setJspTextArea(JScrollPane jspTextArea) {
		this.jspTextArea = jspTextArea;
	}
	
	
	

}//class
