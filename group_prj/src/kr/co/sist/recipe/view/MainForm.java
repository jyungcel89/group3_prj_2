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

@SuppressWarnings("serial")
public class MainForm extends JFrame{

	JButton jbSearch, jbFstImg, jbSecImg, jbTrdImg, jbMypage, jbClose, jbAddRecipe;
	JTable jtRecipe;
	DefaultTableModel dtmRecipe;
	JCheckBox chkOne, chkTwo, chkThree, chkFour;
	JTextField jtfSearch;	
	
	public MainForm() {
		super("홍홍홍의 편의점 레시피");
		setLayout(null);
		
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/workspace/group_prj/src/kr/co/sist/recipe/img/background_image.png"));
		setContentPane(jlImg);
		// 등록된 레시피 이미지
		JLabel jlRecent = new JLabel("최근 레시피");
		jlRecent.setFont(new Font("", Font.BOLD, 15));
		
		JPanel jpRcntRecipe = new JPanel();
		jpRcntRecipe.setBackground(new Color(255, 255, 255, 130));
		jbFstImg = new JButton("메뉴이름",new ImageIcon("C:/dev/workspace/group_prj/src/kr/co/sist/recipe/img/m1_l4.gif"));
		jbSecImg = new JButton("메뉴이름");
		jbTrdImg = new JButton("메뉴이름");
		
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
		chkOne = new JCheckBox("첫번째");
		chkTwo = new JCheckBox("두번째");
		chkThree = new JCheckBox("세번째");
		chkFour = new JCheckBox("네번째");
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
		
		// 테이블
		String[] columnName = {"이미지", "메뉴이름", "총가격", "소개"};
		String[][] rowData = {{"jpg","딘나맛있맨","4000","맛있어"}};
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
		
		// 최근 메뉴 패널 배치
		jpRcntRecipe.setBounds(20, 130, 830, 260);
		// 검색조건 패널 배치
		jpSrchOption.setBounds(20, 400, 830, 50);
		// 테이블 배치
		jspTab.setBounds(20, 450, 830, 200);
		// 하단 버튼패널 배치
		jpFootBtns.setBounds(20, 660, 830, 50);
		
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
		
		
		setBounds(10, 10, 880, 770);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//MainForm
	
	public static void main(String[] args) {
		new MainForm();
	}//main

}//class
