package kr.co.sist.recipe.view;
 
import java.awt.Component;
import java.awt.Font;

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
import kr.co.sist.recipe.vo.SelectRecipeInfoVO;

@SuppressWarnings("serial")
public class ItemPreviewForm extends JDialog {
	
	private JTable jtIngrednt;
	private JTextArea jtaInfo;
	private JButton jbClose,jbSubmit;
	private JLabel jlRecipeName,jlIngrednt,jlScore,jlBookmark,jlMakeMethod,imgLabel;
	private ImageIcon imgIcon;
	private DefaultTableModel dtmIngrednt;
	private JScrollPane jspIngrednt;
	private JComboBox<Integer> jcScore;
	private JCheckBox jchBookmark;
	private JScrollPane jspTextArea;
	
	public ItemPreviewForm(MainForm mf, SelectRecipeInfoVO sriv){
		setLayout(null);
		
		//region 상품명,재료,별점,북마크,만드는법,이미지 라벨및 이미지 아이콘 구역
		Font defaultFont=new Font("돋음",Font.BOLD,15);
		jlRecipeName=new JLabel("상품명");
		jlRecipeName.setBounds(170,50,100,40);
		
		jlIngrednt=new JLabel("재료");
		jlIngrednt.setBounds(400,50,100,40);
		
		jlScore=new JLabel("별점");
		jlScore.setBounds(400,300,100,40);
		
		jlBookmark=new JLabel("북마크♡");
		jlBookmark.setBounds(640,300,100,40);
		
		jlMakeMethod=new JLabel("만드는법");
		jlMakeMethod.setBounds(370,400,100,40);
		
		imgIcon=new ImageIcon("C:/dev/workspace/group_prj/src/kr/co/sist/recipe/view/FI_0031.JPG");
		imgLabel=new JLabel(imgIcon);
		imgLabel.setBounds(30,100,330,270);
		//endregion //상품명,재료,별점,북마크,만드는법,이미지 라벨 구역끝
		
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
		
		//region 별점 콤보박스 영역
		jcScore=new JComboBox<Integer>();
		jcScore.setBounds(440,310,100,30);
		//endregion 별점 콤보박스 영역 끝

		//region 좋아요 체크박스 영역
		jchBookmark=new JCheckBox();
		jchBookmark.setBounds(705,300,300,40);
		//endregion 좋아요 체크박스 영역 끝
		
		//region 닫기,제출 버튼 영역
		jbSubmit=new JButton("제출!");
		jbSubmit.setBounds(550,310,70,30);
		
		jbClose=new JButton("확인");
		jbClose.setBounds(650,610,100,40);
		//endregion 닫기,제출버튼 영역 끝
		
		//region 만드는법 TextArea
		
		jtaInfo=new JTextArea();
		jtaInfo.setLineWrap(true);
		jspTextArea=new JScrollPane(jtaInfo);
		jspTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspTextArea.setBounds(95,450,600,150);
		//endregion 만드는법 TextArea끝
		
		Component[] com={jlRecipeName,imgLabel,jlIngrednt,jspIngrednt,jlScore,jlBookmark,jcScore,jchBookmark
				,jbSubmit,jlMakeMethod,jspTextArea,jbClose};
		
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
		return jtaInfo;
	}

	public void setJtaInfo(JTextArea jtaInfo) {
		this.jtaInfo = jtaInfo;
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

	public JLabel getJlScore() {
		return jlScore;
	}

	public void setJlScore(JLabel jlScore) {
		this.jlScore = jlScore;
	}

	public JLabel getJlBookmark() {
		return jlBookmark;
	}

	public void setJlBookmark(JLabel jlBookmark) {
		this.jlBookmark = jlBookmark;
	}

	public JLabel getJlMakeMethod() {
		return jlMakeMethod;
	}

	public void setJlMakeMethod(JLabel jlMakeMethod) {
		this.jlMakeMethod = jlMakeMethod;
	}

	public JLabel getImgLabel() {
		return imgLabel;
	}

	public void setImgLabel(JLabel imgLabel) {
		this.imgLabel = imgLabel;
	}

	public ImageIcon getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(ImageIcon imgIcon) {
		this.imgIcon = imgIcon;
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

	public JComboBox<Integer> getJcScore() {
		return jcScore;
	}

	public void setJcScore(JComboBox<Integer> jcScore) {
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
