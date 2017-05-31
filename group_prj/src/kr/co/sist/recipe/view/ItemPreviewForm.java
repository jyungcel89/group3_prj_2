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

import kr.co.sist.recipe.evt.ItemPreviewEvt;
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
	private ItemPreviewEvt ipe;
	
	public ItemPreviewForm(MainRecipeVO mrv){
		setLayout(null);
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/previewBack.png"));
	     setContentPane(jlImg);
	     
		//region ��ǰ��,���,����,�ϸ�ũ,����¹�,�̹��� �󺧹� �̹��� ������ ����
		Font defaultFont=new Font("���� ���",Font.BOLD,14);
		
		jlRecipeName=new JLabel("�� "+mrv.getMenuName()+" ��");
		jlRecipeName.setFont(new Font("���� ���", Font.BOLD, 17));
		jlRecipeName.setBounds(30,70,300,30);
		
		jlIngrednt=new JLabel("�� ���");
		jlIngrednt.setBounds(400,70,100,30);
		
		JLabel jlScore=new JLabel("����");
		jlScore.setBounds(570,320,100,30);
		
		JLabel jlBookmark=new JLabel("�ϸ�ũ");
		jlBookmark.setBounds(435,320,50,30);
		
		JLabel jlMakeMethod=new JLabel("�� ������");
		jlMakeMethod.setBounds(400,380,70,40);
		
		// �̹��� ���
		String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/b_";
		// �̹��� �־��ֱ�
		ImageIcon imgIcon=new ImageIcon(path+mrv.getMenuImg());
		JLabel imgLabel=new JLabel(imgIcon);
		imgLabel.setBounds(30,100,330,270);
	
		JLabel jlSimpleInfo = new JLabel("�� ���� ����");
		jlSimpleInfo.setBounds(30, 380, 100, 40);
		
		//region ������̺� ����
		String[] columnNames={"����","����"};
		String[][] data={};
		dtmIngrednt=new DefaultTableModel(data, columnNames);
		jtIngrednt=new JTable(dtmIngrednt){
			private static final long serialVersionUID = 1L;
			//�÷��� ���� ���� ���� method Override
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		//�÷��� �����Ͽ� �������� ���ϵ��� ����
		jtIngrednt.getTableHeader().setReorderingAllowed(false);
		//�÷��� ���� ����
		jtIngrednt.setRowHeight(20);
		//�÷��� ���� ���� 
		//"��ȣ","�̹���","�޴��ڵ�","����","����"
		jtIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
		jtIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
		jspIngrednt=new JScrollPane(jtIngrednt);
		jspIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspIngrednt.setBounds(400,100,350,180);
		
		//endregion ������̺��� ��
		
		//region ���ƿ� üũ�ڽ� ����
		jchBookmark=new JCheckBox();
		jchBookmark.setOpaque(false);
		jchBookmark.setBounds(480,320,20,30);
		//endregion ���ƿ� üũ�ڽ� ���� ��
	
		//region ���� �޺��ڽ� ����
		jcScore=new JComboBox<String>();
		jcScore.addItem("--");
		jcScore.addItem("1");
		jcScore.addItem("2");
		jcScore.addItem("3");
		jcScore.addItem("4");
		jcScore.addItem("5");
		jcScore.setBounds(610,325,50,20);
		//endregion ���� �޺��ڽ� ���� ��
		
		//region �ݱ�,���� ��ư ����
		jbSubmit=new JButton("����");
		jbSubmit.setBounds(670,323,70,23);
		
		jbClose=new JButton("Ȯ��");
		jbClose.setBounds(670,610,100,30);
		//endregion �ݱ�,�����ư ���� ��
		
		//region ����¹� TextArea
		
		jtaSimple=new JTextArea(mrv.getMenuSimpeInfo());
		jtaSimple.setFont(new Font("���� ���", Font.PLAIN, 12));
		jtaSimple.setLineWrap(true);
		jtaSimple.setBounds(30, 420, 330, 70);
		jtaSimple.setEditable(false);
		
		jtaDetail=new JTextArea(mrv.getMenuDetailInfo());
		jtaDetail.setLineWrap(true);
		jspTextArea=new JScrollPane(jtaDetail);
		jspTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspTextArea.setBounds(400,420,350,150);
		//endregion ����¹� TextArea��
		
		add(jlRecipeName);
		add(jtaSimple);
		
		Component[] com={imgLabel,jlIngrednt,jspIngrednt,jlScore,jlBookmark,jcScore,jchBookmark
				,jbSubmit,jlMakeMethod,jspTextArea,jbClose,jlSimpleInfo};
		
		for(int i=0; i<com.length;i++){
			com[i].setFont(defaultFont);
			add(com[i]);
		}
		
		//�̺�Ʈ �߰�
		ItemPreviewEvt ipe=new ItemPreviewEvt(this);
		jbSubmit.addActionListener(ipe);
		jbClose.addActionListener(ipe);
		jchBookmark.addActionListener(ipe);
		
		
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
