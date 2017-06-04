package kr.co.sist.recipe.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.evt.AddRecipeEvt;

@SuppressWarnings("serial")
public class AddRecipeForm extends JDialog {

	private JComboBox<String> jcbStore,jcbCateg,jcbIngrdntSort;
	private JTable jtIngrednt,jtaddedIngrednt;
	private JTextArea jtaInfo,jtaWriteRecipe;
	private JButton jbAddImg,jbAddIngrednt,jbRmvIngrednt,jbRequest,jbClose,jbSearch,jbMgr;
	private JTextField jtfRecipeName;
	private JLabel lblImg,lblRecipeName,lblRecipeSort,lblRecipeInfo,
	lblIngredntChoice,lblConvenienceStore,lblIngredntSort,lblTotalPrice,lblTotalPriceView
	,lblWriteRecipe; 
	private JScrollPane jspIngrednt,jspAddedIngrednt,jspTextArea;
	private DefaultTableModel dtmIngrednt,dtmAddedIngrednt;
	private ImageIcon imgIcon;
	
	public AddRecipeForm(String menuName){
		this.setTitle("ȫȫȫ ������ �޴� �߰�");
		this.setModal(true);
		setLayout(null);
		// ��׶���
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/addrcpBack.png"));
		setContentPane(jlImg);
		Font defaultFont =new Font("���� ���", Font.BOLD, 14);
	
//		region ������ �̹��� �߰��ϴ� �� �� �̹��� ������
		imgIcon=new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/default.jpg");
		lblImg = new JLabel(imgIcon);
		lblImg.setBounds(80,50,260,200);
		add(lblImg);
		
		//�⺻�̹���
		JLabel img = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/default.jpg"));
		img.setBounds(80,50,260,200);
		add(img); 
		
//		region �������̹��� �߰���ư
		jbAddImg=new JButton("���� �ø���");
		jbAddImg.setBounds(210,270,130,30);
//		endregion �������̹��� �߰���ư ��
		
//		region ��ǰ��,�丮�з�,���ܼҰ� �� �߰� ����
		lblRecipeName = new JLabel("��ǰ��");
		lblRecipeName.setBounds(370,40,40,30);
		lblRecipeSort=new JLabel("�丮�з�");
		lblRecipeSort.setBounds(370,80,60,30);
		lblRecipeInfo=new JLabel("���ܼҰ�");
		lblRecipeInfo.setBounds(370,120,60,30);
//		endregion ��ǰ��,�丮�з�,���ܼҰ� �� �߰� ������
		
//		region �����Ǹ� �Է��ϴ� JTextField
		jtfRecipeName=new JTextField(menuName);
		jtfRecipeName.setBounds(442,45,250,25);
//		endregion �����Ǹ� �Է��ϴ� JTextField��
		
//		region ������ �丮�з� �޺��ڽ� ����
		jcbCateg=new JComboBox<String>();
		jcbCateg.setBounds(442,85,250,25);
//		endregion ������ �丮�з� �޺��ڽ� ������
		
//		region ������ ���ܼ��� �ؽ�Ʈ�����
		jtaInfo = new JTextArea();
		jtaInfo.setBounds(442,130,250,130);
		jtaInfo.setLineWrap(true);
//		endregion ������ ���ܼ��� �ؽ�Ʈ����
		
//		region ��ἱ��,������,���з� �� �߰� ����
		lblIngredntChoice=new JLabel("�� ��ἱ��");
		lblIngredntChoice.setFont(defaultFont);
		lblIngredntChoice.setBounds(40,315,90,25);
		lblConvenienceStore=new JLabel("������");
		lblConvenienceStore.setBounds(210,350,50,25);
		lblIngredntSort=new JLabel("���з�");
		lblIngredntSort.setBounds(405, 350,120,25);
//		endregion ��ἱ��,������,���з� �� �߰� ���� ��
		
//		region ������,���з� �޺��ڽ� ����
		jcbStore=new JComboBox<String>();
		jcbStore.setBounds(260,350,120,25);
		jcbIngrdntSort=new JComboBox<String>();
		jcbIngrdntSort.setBounds(470,350,120,25);
//		endregion ������,���з� �޺��ڽ� ���� ��
		
//		region �˻���ư
		jbSearch=new JButton("�˻�");
		jbSearch.setBounds(610,350,85,26);
//		endregion �˻���ư ��
		
//		region ����߰�,�߰��� ��� ���̺� ����
	
		//����߰� ���̺� �κ�
		String[] columnNames1={"����","����"};
		String[][] data1={};
		dtmIngrednt=new DefaultTableModel(data1, columnNames1);
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
		jspIngrednt.setBounds(40,390,550,80);
		//����߰� ���̺�κ� ��
		
		//�߰��� ������̺�
		
				String[] columnNames2={"����","����"};
				String[][] data2={};
				dtmAddedIngrednt=new DefaultTableModel(data2, columnNames2);
				jtaddedIngrednt=new JTable(dtmAddedIngrednt){
					private static final long serialVersionUID = 1L;
					//�÷��� ���� ���� ���� method Override
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}//isCellEditable
				};
				//�÷��� �����Ͽ� �������� ���ϵ��� ����
				jtaddedIngrednt.getTableHeader().setReorderingAllowed(false);
				//�÷��� ���� ����
				jtaddedIngrednt.setRowHeight(20);
				//�÷��� ���� ����
				//"��ȣ","�̹���","�޴��ڵ�","����","����"
				jtaddedIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
				jtaddedIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
				jspAddedIngrednt=new JScrollPane(jtaddedIngrednt);
				jspAddedIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				jspAddedIngrednt.setBounds(40,490,550,80);
		
				
		//�߰��� ������̺� ��
		
//		endregion ����߰�,�߰��� ��� ���̺� ���� ��
		
//		region ����� ���� �� �߰� ��ư
		jbAddIngrednt=new JButton("�߰�");
		jbAddIngrednt.setBounds(610, 390,85,78);
		jbRmvIngrednt=new JButton("����");
		jbRmvIngrednt.setBounds(610,490,85,78);
//		endregion ����� ���� �� �߰� ��ư ��
		
//		region ����� �Ѱ��� �󺧰� ������ ���� ������ ��
		lblTotalPrice=new JLabel("");
		lblTotalPrice.setFont(defaultFont);
		Font priceResult=new Font("���� ���",Font.BOLD,15);
		
		lblTotalPrice=new JLabel();
		lblTotalPrice.setFont(priceResult);
		lblTotalPrice.setForeground(Color.red);
		lblTotalPrice.setBounds(540,560,100,50);
		lblTotalPriceView=new JLabel("�Ѱ���:");
		lblTotalPriceView.setFont(defaultFont);
		lblTotalPriceView.setBounds(460,560,100,50);
//		endregion ����� �Ѱ��� �󺧰� ������ ���� ������ �� ��
		
//		region ������ �ۼ� ��
		lblWriteRecipe=new JLabel("�� ������ �ۼ�");
		lblWriteRecipe.setBounds(40, 585,150,40);
		lblWriteRecipe.setFont(defaultFont);
//		endregion ������ �ۼ� �� ��
		
//		region ���ǽ� �ۼ� TextArea
		
		jtaWriteRecipe=new JTextArea(5,10);
		jtaWriteRecipe.setLineWrap(true);
		jtaWriteRecipe.setBackground(Color.WHITE);
		jspTextArea=new JScrollPane(jtaWriteRecipe);
		jspTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspTextArea.setBounds(40, 630,660,100);
//		endregion ���ǽ� �ۼ� TextArea ��
		
//		region ��û,�ݱ�,������ ��ư ����
//		endregion ��û,�ݱ�,������ ��ư ���� ��
		jbClose=new JButton("�ݱ�");
		jbClose.setBounds(617, 750,100,40);
		jbRequest=new JButton("�޴� ��û");
		jbRequest.setBounds(495,750,100,40);
		jbMgr=new JButton("�޴� ����");
		jbMgr.setVisible(false);
		jbMgr.setBounds(20,750,100,40);
//		endregion ��û,�ݱ�,������ ��ư ���� ��s
		jcbCateg.addItem("���ַ�");
		jcbCateg.addItem("����Ʈ");
		jcbCateg.addItem("�нķ�");
		jcbCateg.addItem("�Ļ��");
		jcbStore.addItem("GS25");
		jcbStore.addItem("7-eleven");
		jcbStore.addItem("CU");
		jcbIngrdntSort.addItem("����");
		jcbIngrdntSort.addItem("��Ÿ");
		jcbIngrdntSort.addItem("���̽�ũ��");
		jcbIngrdntSort.addItem("���");
		jcbIngrdntSort.addItem("�Ｎ�丮");
		
		//�̺�Ʈ �߰�
		AddRecipeEvt are = new AddRecipeEvt(this);
		jbSearch.addActionListener(are);
		jcbCateg.addActionListener(are);
		jcbStore.addActionListener(are);
		jbAddImg.addActionListener(are);
		jbAddIngrednt.addActionListener(are);
		jbRequest.addActionListener(are);
		jbClose.addActionListener(are);
		jbRmvIngrednt.addActionListener(are);
		jbMgr.addActionListener(are);
		
		//��ġ
		Component[] com={jtaInfo,jcbCateg,jtfRecipeName,lblRecipeInfo,lblRecipeSort,lblRecipeName
				,jbAddImg,lblTotalPriceView,lblTotalPrice,jbRmvIngrednt,jbAddIngrednt
				,jspAddedIngrednt,jspIngrednt,jbSearch,jcbStore,lblConvenienceStore,lblIngredntChoice
				,lblIngredntSort,jcbIngrdntSort,jbRequest,jbClose,jspTextArea,lblWriteRecipe,jbMgr};
		
		for(int i=0; i<com.length;i++){
			add(com[i]);
		}//end for
		setResizable(false);
		setBounds(50,50,750,850);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}//AddRecipeForm

	
	public JButton getJbSearch() {
		return jbSearch;
	}

	public JComboBox<String> getJcbStore() {
		return jcbStore;
	}

	public JComboBox<String> getJcbCateg() {
		return jcbCateg;
	}

	public DefaultTableModel getDtmIngrednt() {
		return dtmIngrednt;
	}

	public DefaultTableModel getDtmAddedIngrednt() {
		return dtmAddedIngrednt;
	}

	public JTextField getJtfRecipeName() {
		return jtfRecipeName;
	}

	public JTable getJtIngrednt() {
		return jtIngrednt;
	}

	public JButton getJbRequest() {
		return jbRequest;
	}

	public JTable getJtaddedIngrednt() {
		return jtaddedIngrednt;
	}

	public JLabel getLblImg() {
		return lblImg;
	}

	public JButton getJbAddImg() {
		return jbAddImg;
	}

	public JComboBox<String> getJcbIngrdntSort() {
		return jcbIngrdntSort;
	}
	public JButton getJbAddIngrednt() {
		return jbAddIngrednt;
	}
	public JButton getJbClose() {
		return jbClose;
	}
	public JButton getJbRmvIngrednt() {
		return jbRmvIngrednt;
	}
	public JTextArea getJtaInfo() {
		return jtaInfo;
	}

	public JTextArea getJtaWriteRecipe() {
		return jtaWriteRecipe;
	}

	public JLabel getLblTotalPrice() {
		return lblTotalPrice;
	}

	public JButton getJbMgr() {
		return jbMgr;
	}
	
}//class

