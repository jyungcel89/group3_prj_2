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

	private JButton jbSearch, jbFstImg, jbSecImg, jbTrdImg, jbMypage, jbClose, jbAddRecipe;
	private JTable jtRecipe;
	private DefaultTableModel dtmRecipe;
	private JCheckBox chkOne, chkTwo, chkThree, chkFour;
	private JTextField jtfSearch;	
	private String imgPath1, imgPath2, imgPath3;
	private JLabel menuName1, menuName2, menuName3;
	
	public MainForm() {
		super("홍홍홍의 편의점 레시피"); 
		setLayout(null);
		

		// 검색조건
		JLabel jlSearch = new JLabel("검색 조건");
		jlSearch.setFont(new Font("맑은고딕", Font.BOLD, 15));
		
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
		chkOne = new JCheckBox("안주류");
		chkTwo = new JCheckBox("식사류");
		chkThree = new JCheckBox("디저트");
		chkFour = new JCheckBox("분식류");
		jtfSearch = new JTextField();
		jbSearch = new JButton("검 색");
		chkOne.setOpaque(false);
		chkTwo.setOpaque(false);
		chkThree.setOpaque(false);
		chkFour.setOpaque(false);
		

		// 아래 레시피 추가버튼, 마이페이지버튼, 닫기 버튼
		JPanel jpFootBtns = new JPanel();
		jbAddRecipe = new JButton("메뉴 요청");
		jbMypage = new JButton("마이페이지");
		jbClose = new JButton("닫기");
		
		// 테이블
		String[] columnName = {"메뉴이름", "이미지", "메뉴타입", "간단소개","가격"};
		String[][] rowData = {{"메뉴를 검색해주세요","","", "", ""}};
		// 테이블 수정 막기
		dtmRecipe = new DefaultTableModel(rowData, columnName){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
		jtRecipe = new JTable(dtmRecipe);
		jtRecipe.getTableHeader().setReorderingAllowed(false);
		JScrollPane jspTab = new JScrollPane(jtRecipe);
		// 테이블 설정 
		// 컬럼의 높이설정
		jtRecipe.setRowHeight(100);
		// 컬럼의 넓이 설정
		jtRecipe.getColumnModel().getColumn(0).setPreferredWidth(40);
		
		// 이벤트 선언
		MainFormEvt mfe= new MainFormEvt(this);
		// 최신 이미지 등록 뷰
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/background_image.png"));
		setContentPane(jlImg);
		// 등록된 레시피 이미지
		JLabel jlRecent = new JLabel("최근 레시피");
		jlRecent.setFont(new Font("", Font.BOLD, 15));
		
		JPanel jpRcntRecipe = new JPanel();
		jpRcntRecipe.setBackground(new Color(255, 255, 255, 130));
		jbFstImg = new JButton(new ImageIcon(imgPath1));
		jbSecImg = new JButton(new ImageIcon(imgPath2));
		jbTrdImg = new JButton(new ImageIcon(imgPath3));
		
		// 이미지 버튼 배치
		jpRcntRecipe.setLayout(null);
		jlRecent.setBounds(10, 10, 200, 30);
		jbFstImg.setBounds(10, 50, 260, 200);
		jbSecImg.setBounds(285, 50, 260, 200);
		jbTrdImg.setBounds(560, 50, 260, 200);
		
		
		// 검색 조건(체크박스) , 검색 버튼 배치
		jpSrchOption.setLayout(null);
//		jpSrchOption.setOpaque(false);
		jlSearch.setBounds(10, 10, 100, 30);
		chkOne.setBounds(110, 10, 70, 30);
		chkOne.setBackground(new Color(255, 0, 0, 0));
		chkTwo.setBounds(180, 10, 70, 30);
		chkThree.setBounds(260, 10, 70, 30);
		chkFour.setBounds(340, 10, 70, 30);
		jtfSearch.setBounds(420, 13, 280, 25);
		jbSearch.setBounds(720, 11, 100, 28);
		
		// 하단버튼 > 레시피 추가버튼, 마이페이지버튼, 닫기 버튼 배치
		jpFootBtns.setLayout(null);
		jpFootBtns.setOpaque(false);
		jbAddRecipe.setBounds(0, 10, 100, 30);
		jbMypage.setBounds(620, 10, 100, 30);
		jbClose.setBounds(730, 10, 100, 30);
		
		// 최근 메뉴 패널 배치
		jpRcntRecipe.setBounds(20, 130, 830, 260);
		// 검색조건 패널 배치
		jpSrchOption.setBounds(20, 400, 830, 50);
		// 테이블 배치
		jspTab.setBounds(20, 450, 830, 450);
		// 하단 버튼패널 배치
		jpFootBtns.setBounds(20, 910, 830, 50);
		
		// 최근메뉴 이미지 버튼 붙이기
		jpRcntRecipe.add(jlRecent);
		jpRcntRecipe.add(jbFstImg);
		jpRcntRecipe.add(jbSecImg);
		jpRcntRecipe.add(jbTrdImg);
		
		// 검색조건, 검색버튼 붙이기
		jpSrchOption.add(jlSearch);
		jpSrchOption.add(chkOne);
		jpSrchOption.add(chkTwo);
		jpSrchOption.add(chkThree);
		jpSrchOption.add(chkFour);
		jpSrchOption.add(jtfSearch);
		jpSrchOption.add(jbSearch);
		
		// 버튼 붙이기
		jpFootBtns.add(jbAddRecipe);
		jpFootBtns.add(jbMypage);
		jpFootBtns.add(jbClose);
		
		// 패널 붙이기
		add(jpSrchOption);
		add(jpRcntRecipe);
		add(jspTab);
		add(jpFootBtns);
		
		// 이벤트 적용
		jbSearch.addActionListener(mfe);
		jtRecipe.addMouseListener(mfe);
		jbFstImg.addActionListener(mfe);
		jbSecImg.addActionListener(mfe);
		jbTrdImg.addActionListener(mfe);
		jbMypage.addActionListener(mfe);
		jbClose.addActionListener(mfe);
		// 부모창
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



	public static void main(String[] args) {
		new MainForm();
	}//main

}//class
