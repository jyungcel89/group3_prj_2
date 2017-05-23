package kr.co.sist.recipe.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

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
	public AddRecipeForm(){
		setLayout(null);
		
//		region ������ �̹��� �߰��ϴ� �� �� �̹��� ������
		imgIcon=new ImageIcon("C:/dev/workspace/group_prj/src/kr/co/sist/recipe/view/FI_0031.JPG");
		lblImg = new JLabel(imgIcon);
		lblImg.setBounds(50,30,300,220);
		
//		endregion ������ �̹��� �߰��ϴ� �� �� �̹��� �����ܳ�
		
//		region �������̹��� �߰���ư
		jbAddImg=new JButton("���� �ø���");
		jbAddImg.setBounds(220,260,130,30);
//		endregion �������̹��� �߰���ư ��
		
//		region ��ǰ��,�丮�з�,���ܼҰ� �� �߰� ����
		lblRecipeName = new JLabel("��ǰ��");
		lblRecipeName.setBounds(432,30,40,30);
		lblRecipeSort=new JLabel("�丮�з�");
		lblRecipeSort.setBounds(420,70,60,30);
		lblRecipeInfo=new JLabel("���ܼҰ�");
		lblRecipeInfo.setBounds(420,110,60,30);
//		endregion ��ǰ��,�丮�з�,���ܼҰ� �� �߰� ������
		
//		region �����Ǹ� �Է��ϴ� JTextField
		jtfRecipeName=new JTextField();
		jtfRecipeName.setBounds(492,35,100,20);
//		endregion �����Ǹ� �Է��ϴ� JTextField��
		
//		region ������ �丮�з� �޺��ڽ� ����
		jcbCateg=new JComboBox<String>();
		jcbCateg.setBounds(492,75,100,20);
//		endregion ������ �丮�з� �޺��ڽ� ������
		
//		region ������ ���ܼ��� �ؽ�Ʈ�����
		jtaInfo = new JTextArea();
		jtaInfo.setBounds(492,115,200,130);
		jtaInfo.setLineWrap(true);
//		endregion ������ ���ܼ��� �ؽ�Ʈ����
		
//		region ��ἱ��,������,���з� �� �߰� ����
		Font ingredntChoice=new Font("����",Font.BOLD,20);
		lblIngredntChoice=new JLabel("��ἱ��");
		lblIngredntChoice.setFont(ingredntChoice);
		lblIngredntChoice.setBounds(50,290,100,30);
		lblConvenienceStore=new JLabel("������");
		lblConvenienceStore.setBounds(55,330,120,40);
		lblIngredntSort=new JLabel("���з�");
		lblIngredntSort.setBounds(402, 330,120,40);
//		endregion ��ἱ��,������,���з� �� �߰� ���� ��
		
//		region ������,���з� �޺��ڽ� ����
		jcbStore=new JComboBox<String>();
		jcbStore.setBounds(110,330,120,30);
		jcbIngrdntSort=new JComboBox<String>();
		jcbIngrdntSort.setBounds(470,330,120,30);
//		endregion ������,���з� �޺��ڽ� ���� ��
		
//		region �˻���ư
		jbSearch=new JButton("�˻�");
		jbSearch.setBounds(620,330,100,30);
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
		jtIngrednt.setRowHeight(100);
		//�÷��� ���� ����
		//"��ȣ","�̹���","�޴��ڵ�","����","����"
		jtIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
		jtIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
		jspIngrednt=new JScrollPane(jtIngrednt);
		jspIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspIngrednt.setBounds(20,380,580,80);
		//����߰� ���̺��κ� ��
		
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
				jtaddedIngrednt.setRowHeight(100);
				//�÷��� ���� ����
				//"��ȣ","�̹���","�޴��ڵ�","����","����"
				jtaddedIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
				jtaddedIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
				jspAddedIngrednt=new JScrollPane(jtaddedIngrednt);
				jspAddedIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				jspAddedIngrednt.setBounds(20,480,580,80);
		
				
		//�߰��� ������̺� ��
		
//		endregion ����߰�,�߰��� ��� ���̺� ���� ��
		
//		region ����� ���� �� �߰� ��ư
		jbAddIngrednt=new JButton("Add");
		jbAddIngrednt.setBounds(620, 390,100,55);
		jbRmvIngrednt=new JButton("Delete");
		jbRmvIngrednt.setBounds(620,490,100,55);
//		endregion ����� ���� �� �߰� ��ư ��
		
//		region ����� �Ѱ��� �󺧰� ������ ���� ������ ��
		Font priceResult=new Font("����",Font.BOLD,20);
		
		lblTotalPrice=new JLabel("7000��");
		lblTotalPrice.setFont(priceResult);
		lblTotalPrice.setForeground(Color.red);
		lblTotalPrice.setBounds(540,555,100,50);
		lblTotalPriceView=new JLabel("�Ѱ���:");
		lblTotalPriceView.setFont(priceResult);
		lblTotalPriceView.setBounds(460,555,100,50);
//		endregion ����� �Ѱ��� �󺧰� ������ ���� ������ �� ��
		
//		region ������ �ۼ� ��
		Font design=new Font("����",Font.BOLD,22);
		lblWriteRecipe=new JLabel("������ �ۼ�");
		lblWriteRecipe.setBounds(310, 585,150,40);
		lblWriteRecipe.setFont(design);
//		endregion ������ �ۼ� �� ��
		
//		region ���ǽ� �ۼ� TextArea
		
		jtaWriteRecipe=new JTextArea(5,10);
		jtaWriteRecipe.setLineWrap(true);
		jtaWriteRecipe.setBackground(Color.WHITE);
		jspTextArea=new JScrollPane(jtaWriteRecipe);
		jspTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspTextArea.setBounds(15, 630,700,100);
//		endregion ���ǽ� �ۼ� TextArea ��
		
//		region ��û,�ݱ�,������ ��ư ����
		jbClose=new JButton("Close");
		jbClose.setBounds(615, 750,100,40);
		jbRequest=new JButton("Request");
		jbRequest.setBounds(495,750,100,40);
		jbMgr=new JButton("MGR Modify");
		jbMgr.setBounds(15,750,100,40);
//		endregion ��û,�ݱ�,������ ��ư ���� ��
		
		Component[] com={jtaInfo,jcbCateg,jtfRecipeName,lblRecipeInfo,lblRecipeSort,lblRecipeName
				,lblImg,jbAddImg,lblTotalPriceView,lblTotalPrice,jbRmvIngrednt,jbAddIngrednt
				,jspAddedIngrednt,jspIngrednt,jbSearch,jcbStore,lblConvenienceStore,lblIngredntChoice
				,lblIngredntSort,jcbIngrdntSort,jbRequest,jbClose,jspTextArea,lblWriteRecipe,jbMgr};
		
		for(int i=0; i<com.length;i++){
			add(com[i]);
		}
		setBounds(0,0,750,850);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args){
		new AddRecipeForm();
	}
}