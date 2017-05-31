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
		super("홍홍홍의 편의점 레시피");
		setLayout(null); 
		Font defaultFont =new Font("맑은 고딕", Font.BOLD, 14);
		
		JLabel jlUserName=new JLabel(" [ "+logId+" ]님 환영합니다.");
		jlUserName.setFont(defaultFont);
		jlUserName.setForeground(Color.white);
		
		JLabel jlRecent = new JLabel("◑ 최신 레시피");
		jlRecent.setFont(defaultFont);

		// 검색조건
		JLabel jlSearch = new JLabel("◑ 검색 조건");
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
		// 관리자 - 회원 구분하여 버튼 사용
		if( logId.equals("mgr") ){
			jbMypage = new JButton("관리자페이지");
			jbAddRecipe.setVisible(false);
		}else{
			jbMypage = new JButton("마이페이지");
		}
		jbClose = new JButton("닫기");
		jbLogOut = new JButton("로그아웃");
		jbLogOut.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		jbLogOut.setForeground(Color.white);
		jbLogOut.setBackground(new Color(0, 0, 0, 130));
		
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
		
		jtRecipe = new JTable(dtmRecipe){
			//컬럼에 이미지를 넣기 위한 method Override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtRecipe.getTableHeader().setReorderingAllowed(false);
		JScrollPane jspTab = new JScrollPane(jtRecipe);
		// 테이블 설정 
		// 컬럼의 높이설정
		jtRecipe.setRowHeight(120);
		// 컬럼의 넓이 설정
		jtRecipe.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtRecipe.getColumnModel().getColumn(1).setPreferredWidth(120);
		jtRecipe.getColumnModel().getColumn(2).setPreferredWidth(50);
		jtRecipe.getColumnModel().getColumn(3).setPreferredWidth(450);
		jtRecipe.getColumnModel().getColumn(4).setPreferredWidth(30);
 
		// 백그라운드 설정
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/background_image.png"));
		setContentPane(jlImg);
		
		// 이벤트 선언
		MainFormEvt mfe= new MainFormEvt(this, logId);
		 
		// 최신 이미지 등록 뷰, 이미지 가져와서 등록
		JPanel jpRcntRecipe = new JPanel();
		jpRcntRecipe.setBackground(new Color(255, 255, 255, 130));
		jbFstImg = new JButton(new ImageIcon(imgPath1));
		jbSecImg = new JButton(new ImageIcon(imgPath2));
		jbTrdImg = new JButton(new ImageIcon(imgPath3));
		
		// 이미지 이름 등록
		JLabel jlMenuName1=new JLabel(" ▷ "+imgName1);
		JLabel jlMenuName2=new JLabel(" ▷ "+imgName2);
		JLabel jlMenuName3=new JLabel(" ▷ "+imgName3);
		
		// 사용자 아이디 라벨 배치
		jlUserName.setBounds(120, 10, 170, 30);
		
		// 이미지 버튼 배치
		jpRcntRecipe.setLayout(null);
		jlRecent.setBounds(10, 10, 200, 25);
		jbFstImg.setBounds(10, 50, 260, 200);
		jbSecImg.setBounds(285, 50, 260, 200);
		jbTrdImg.setBounds(560, 50, 260, 200);
		
		// 이미지 이름 배치
		jlMenuName1.setBounds(10, 250, 200, 30);
		jlMenuName2.setBounds(285, 250, 200, 30);
		jlMenuName3.setBounds(560, 250, 200, 30);
		
		// 검색 조건(체크박스) , 검색 버튼 배치
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
		
		// 하단버튼 > 레시피 추가버튼, 마이페이지버튼, 닫기 버튼 배치
		jpFootBtns.setLayout(null);
		jpFootBtns.setOpaque(false);
		jbAddRecipe.setBounds(0, 10, 100, 30);
		jbMypage.setBounds(600, 10, 120, 30);
		jbClose.setBounds(730, 10, 100, 30);
		
		// 최근 메뉴 패널 배치
		jpRcntRecipe.setBounds(20, 130, 830, 290);
		// 검색조건 패널 배치
		jpSrchOption.setBounds(20, 430, 830, 50);
		// 테이블 배치
		jspTab.setBounds(20, 480, 830, 420);
		// 하단 버튼패널 배치
		jpFootBtns.setBounds(20, 910, 830, 50);
		
		// 사용자 아이디 라벨 붙이기
		add(jlUserName);
		
		// 최근메뉴 이미지 버튼 붙이기
		jpRcntRecipe.add(jlRecent);
		jpRcntRecipe.add(jbFstImg);
		jpRcntRecipe.add(jbSecImg);
		jpRcntRecipe.add(jbTrdImg);
		
		jpRcntRecipe.add(jlMenuName1);
		jpRcntRecipe.add(jlMenuName2);
		jpRcntRecipe.add(jlMenuName3);
		
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
		add(jbLogOut);
		
		// 이벤트 적용
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
