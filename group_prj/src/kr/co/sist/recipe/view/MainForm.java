package kr.co.sist.recipe.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.evt.MainFormEvt;

@SuppressWarnings("serial")
public class MainForm extends JFrame{

	private JButton jbSearch, jbFstImg, jbSecImg, jbTrdImg, jbMypage, jbClose, jbAddRecipe, jbLogOut;
	private JTable jtRecipe;
	private DefaultTableModel dtmRecipe;
	private JCheckBox chkOne, chkTwo, chkThree, chkFour;
	private JTextField jtfSearch;	
	private String imgPath1, imgPath2, imgPath3;
	private String imgName1, imgName2, imgName3;

	public MainForm(String logId) { 
		super("ȫȫȫ�� ������ ������");
		setLayout(null); 
		Font defaultFont =new Font("���� ���", Font.BOLD, 14);
		
		JLabel jlUserName=new JLabel(" [ "+logId+" ]�� ȯ���մϴ�.");
		jlUserName.setFont(defaultFont);
		jlUserName.setForeground(Color.white);
		
		JLabel jlRecent = new JLabel("�� �ֽ� ������");
		jlRecent.setFont(defaultFont);

		// �˻�����
		JLabel jlSearch = new JLabel("�� �˻� ����");
		jlSearch.setFont(defaultFont);
		
		JPanel jpSrchOption = new JPanel(){
		    protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		jpSrchOption.setOpaque(false);
		jpSrchOption.setBackground(new Color(255, 255, 255, 130));
		chkOne = new JCheckBox("���ַ�");
		chkTwo = new JCheckBox("�Ļ��");
		chkThree = new JCheckBox("����Ʈ");
		chkFour = new JCheckBox("�нķ�");
		jtfSearch = new JTextField();
		jbSearch = new JButton("�� ��");
		chkOne.setOpaque(false);
		chkTwo.setOpaque(false);
		chkThree.setOpaque(false);
		chkFour.setOpaque(false);
		

		// �Ʒ� ������ �߰���ư, ������������ư, �ݱ� ��ư
		JPanel jpFootBtns = new JPanel();
		jbAddRecipe = new JButton("�޴� ��û");
		// ������ - ȸ�� �����Ͽ� ��ư ���
		if( logId.equals("mgr") ){
			jbMypage = new JButton("������������");
			jbAddRecipe.setVisible(false);
		}else{
			jbMypage = new JButton("����������");
		}
		jbClose = new JButton("�ݱ�");
		jbLogOut = new JButton("�α׾ƿ�");
		jbLogOut.setFont(new Font("���� ���", Font.BOLD, 12));
		jbLogOut.setForeground(Color.white);
		jbLogOut.setBackground(new Color(0, 0, 0, 130));
		
		// ���̺�
		String[] columnName = {"�޴��̸�", "�̹���", "�޴�Ÿ��", "���ܼҰ�","����"};
		String[][] rowData = {{"�޴��� �˻����ּ���","","", "", ""}};
		// ���̺� ���� ����
		dtmRecipe = new DefaultTableModel(rowData, columnName){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtRecipe = new JTable(dtmRecipe){
			//�÷��� �̹����� �ֱ� ���� method Override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtRecipe.getTableHeader().setReorderingAllowed(false);
		JScrollPane jspTab = new JScrollPane(jtRecipe);
		// ���̺� ���� 
		// �÷��� ���̼���
		jtRecipe.setRowHeight(120);
		// �÷��� ���� ����
		jtRecipe.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtRecipe.getColumnModel().getColumn(1).setPreferredWidth(120);
		jtRecipe.getColumnModel().getColumn(2).setPreferredWidth(50);
		jtRecipe.getColumnModel().getColumn(3).setPreferredWidth(450);
		jtRecipe.getColumnModel().getColumn(4).setPreferredWidth(30);
 
		// ��׶��� ����
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/background_image.png"));
		setContentPane(jlImg);
		
		// �̺�Ʈ ����
		MainFormEvt mfe= new MainFormEvt(this, logId);
		 
		// �ֽ� �̹��� ��� ��, �̹��� �����ͼ� ���
		JPanel jpRcntRecipe = new JPanel();
		jpRcntRecipe.setBackground(new Color(255, 255, 255, 130));
		jbFstImg = new JButton(new ImageIcon(imgPath1));
		jbSecImg = new JButton(new ImageIcon(imgPath2));
		jbTrdImg = new JButton(new ImageIcon(imgPath3));
		
		// �̹��� �̸� ���
		JLabel jlMenuName1=new JLabel(" �� "+imgName1);
		JLabel jlMenuName2=new JLabel(" �� "+imgName2);
		JLabel jlMenuName3=new JLabel(" �� "+imgName3);
		
		// ����� ���̵� �� ��ġ
		jlUserName.setBounds(120, 10, 170, 30);
		
		// �̹��� ��ư ��ġ
		jpRcntRecipe.setLayout(null);
		jlRecent.setBounds(10, 10, 200, 25);
		jbFstImg.setBounds(10, 50, 260, 200);
		jbSecImg.setBounds(285, 50, 260, 200);
		jbTrdImg.setBounds(560, 50, 260, 200);
		
		// �̹��� �̸� ��ġ
		jlMenuName1.setBounds(10, 250, 200, 30);
		jlMenuName2.setBounds(285, 250, 200, 30);
		jlMenuName3.setBounds(560, 250, 200, 30);
		
		// �˻� ����(üũ�ڽ�) , �˻� ��ư ��ġ
		jpSrchOption.setLayout(null);
//		jpSrchOption.setOpaque(false);
		jlSearch.setBounds(10, 10, 100, 25);
		chkOne.setBounds(110, 10, 70, 30);
		chkTwo.setBounds(180, 10, 70, 30);
		chkThree.setBounds(260, 10, 70, 30);
		chkFour.setBounds(340, 10, 70, 30);
		jtfSearch.setBounds(420, 13, 280, 25);
		jbSearch.setBounds(720, 11, 100, 28);
		jbLogOut.setBounds(20, 17, 95, 20);
		
		// �ϴܹ�ư > ������ �߰���ư, ������������ư, �ݱ� ��ư ��ġ
		jpFootBtns.setLayout(null);
		jpFootBtns.setOpaque(false);
		jbAddRecipe.setBounds(0, 10, 100, 30);
		jbMypage.setBounds(600, 10, 120, 30);
		jbClose.setBounds(730, 10, 100, 30);
		
		// �ֱ� �޴� �г� ��ġ
		jpRcntRecipe.setBounds(20, 130, 830, 290);
		// �˻����� �г� ��ġ
		jpSrchOption.setBounds(20, 430, 830, 50);
		// ���̺� ��ġ
		jspTab.setBounds(20, 480, 830, 420);
		// �ϴ� ��ư�г� ��ġ
		jpFootBtns.setBounds(20, 910, 830, 50);
		
		// ����� ���̵� �� ���̱�
		add(jlUserName);
		
		// �ֱٸ޴� �̹��� ��ư ���̱�
		jpRcntRecipe.add(jlRecent);
		jpRcntRecipe.add(jbFstImg);
		jpRcntRecipe.add(jbSecImg);
		jpRcntRecipe.add(jbTrdImg);
		
		jpRcntRecipe.add(jlMenuName1);
		jpRcntRecipe.add(jlMenuName2);
		jpRcntRecipe.add(jlMenuName3);
		
		// �˻�����, �˻���ư ���̱�
		jpSrchOption.add(jlSearch);
		jpSrchOption.add(chkOne);
		jpSrchOption.add(chkTwo);
		jpSrchOption.add(chkThree);
		jpSrchOption.add(chkFour);
		jpSrchOption.add(jtfSearch);
		jpSrchOption.add(jbSearch);
		
		// ��ư ���̱�
		jpFootBtns.add(jbAddRecipe);
		jpFootBtns.add(jbMypage);
		jpFootBtns.add(jbClose);
		
		// �г� ���̱� 
		add(jpSrchOption);
		add(jpRcntRecipe);
		add(jspTab);
		add(jpFootBtns);
		add(jbLogOut);
		
		// �̺�Ʈ ����
		jbSearch.addActionListener(mfe);
		jtRecipe.addMouseListener(mfe);
		jbFstImg.addActionListener(mfe);
		jbSecImg.addActionListener(mfe);
		jbTrdImg.addActionListener(mfe);
		jbMypage.addActionListener(mfe);
		jbAddRecipe.addActionListener(mfe);
		jbClose.addActionListener(mfe);
		
		jtfSearch.addActionListener(mfe);
		chkOne.addActionListener(mfe);
		chkTwo.addActionListener(mfe);
		chkThree.addActionListener(mfe);
		chkFour.addActionListener(mfe);
		
		jbLogOut.addActionListener(mfe);
		// �θ�â
		setBounds(10, 10, 880, 1010);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//MainForm
	
	
	
	//////////////////////////////////////////////////////////////////////
	public JButton getJbSearch() {
		return jbSearch;
	}




	public void setJbSearch(JButton jbSearch) {
		this.jbSearch = jbSearch;
	}




	public JButton getJbFstImg() {
		return jbFstImg;
	}




	public void setJbFstImg(JButton jbFstImg) {
		this.jbFstImg = jbFstImg;
	}




	public JButton getJbSecImg() {
		return jbSecImg;
	}




	public void setJbSecImg(JButton jbSecImg) {
		this.jbSecImg = jbSecImg;
	}




	public JButton getJbTrdImg() {
		return jbTrdImg;
	}




	public void setJbTrdImg(JButton jbTrdImg) {
		this.jbTrdImg = jbTrdImg;
	}




	public JButton getJbMypage() {
		return jbMypage;
	}




	public void setJbMypage(JButton jbMypage) {
		this.jbMypage = jbMypage;
	}




	public JButton getJbClose() {
		return jbClose;
	}




	public void setJbClose(JButton jbClose) {
		this.jbClose = jbClose;
	}




	public JButton getJbAddRecipe() {
		return jbAddRecipe;
	}




	public void setJbAddRecipe(JButton jbAddRecipe) {
		this.jbAddRecipe = jbAddRecipe;
	}




	public JTable getJtRecipe() {
		return jtRecipe;
	}




	public void setJtRecipe(JTable jtRecipe) {
		this.jtRecipe = jtRecipe;
	}




	public DefaultTableModel getDtmRecipe() {
		return dtmRecipe;
	}




	public void setDtmRecipe(DefaultTableModel dtmRecipe) {
		this.dtmRecipe = dtmRecipe;
	}




	public JCheckBox getChkOne() {
		return chkOne;
	}




	public void setChkOne(JCheckBox chkOne) {
		this.chkOne = chkOne;
	}




	public JCheckBox getChkTwo() {
		return chkTwo;
	}




	public void setChkTwo(JCheckBox chkTwo) {
		this.chkTwo = chkTwo;
	}




	public JCheckBox getChkThree() {
		return chkThree;
	}




	public void setChkThree(JCheckBox chkThree) {
		this.chkThree = chkThree;
	}




	public JCheckBox getChkFour() {
		return chkFour;
	}




	public void setChkFour(JCheckBox chkFour) {
		this.chkFour = chkFour;
	}




	public JTextField getJtfSearch() {
		return jtfSearch;
	}




	public void setJtfSearch(JTextField jtfSearch) {
		this.jtfSearch = jtfSearch;
	}

	



	public String getImgPath1() {
		return imgPath1;
	}



	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}



	public String getImgPath2() {
		return imgPath2;
	}



	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
	}



	public String getImgPath3() {
		return imgPath3;
	}



	public void setImgPath3(String imgPath3) {
		this.imgPath3 = imgPath3;
	}
	
	


	public String getImgName1() {
		return imgName1;
	}



	public void setImgName1(String imgName1) {
		this.imgName1 = imgName1;
	}



	public String getImgName2() {
		return imgName2;
	}



	public void setImgName2(String imgName2) {
		this.imgName2 = imgName2;
	}



	public String getImgName3() {
		return imgName3;
	}



	public void setImgName3(String imgName3) {
		this.imgName3 = imgName3;
	}



	public JButton getJbLogOut() {
		return jbLogOut;
	}



	public void setJbLogOut(JButton jbLogOut) {
		this.jbLogOut = jbLogOut;
	}



//	public static void main(String[] args) {
//		new MainForm(String logId);
//	}//main

}//class
