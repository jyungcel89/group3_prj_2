package kr.co.sist.recipe.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MyPageForm extends JDialog {

	private JLabel jlbMyMenu, jlbFavorMenu;
	private JTable jtMyMenu, jtFavorMenu;
	private DefaultTableModel dtmMyMenu, dtmFavorMenu;
	private JButton jbRmvFavorMenu, jbEditMyInfo, jbClose;
	
	public MyPageForm() {
		setLayout(null);
		
		jlbMyMenu=new JLabel("내가 등록한 메뉴");
		jlbFavorMenu=new JLabel("북마크 리스트");
		
		jbEditMyInfo=new JButton("내 정보 수정");
		jbRmvFavorMenu=new JButton("북마크 삭제");
		jbClose=new JButton("닫기");
		
		//테이블
		String[] menuColumnNames={"메뉴명","타입","가격","간단설명"};
		String[][] menuData={{"간장라면","면류","9000","맛없고 비싸다"}};
		// 테이블 수정 막기
		dtmMyMenu=new DefaultTableModel(menuData, menuColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// 테이블 수정 막기
		dtmFavorMenu=new DefaultTableModel(menuData, menuColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtMyMenu=new JTable(dtmMyMenu); 
		jtFavorMenu=new JTable(dtmFavorMenu);
		
		jtMyMenu.getTableHeader().setReorderingAllowed(false);
		jtFavorMenu.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane jspMenuList=new JScrollPane(jtMyMenu);
		JScrollPane jspRequest=new JScrollPane(jtFavorMenu);
		
		//패널
		//마이페이지 패널
		JPanel jpMyMenu=new JPanel();
		jpMyMenu.setBorder(new TitledBorder("MyPage"));
		jpMyMenu.setLayout(null);
		jpMyMenu.add(jlbMyMenu);
		jpMyMenu.add(jlbFavorMenu);
		jpMyMenu.add(jspMenuList);
		jpMyMenu.add(jspRequest);
		jpMyMenu.add(jbRmvFavorMenu);
		
		//이벤트
		
		
		//패널 위치
		jpMyMenu.setBounds(10, 100, 900, 640);
		//패널 위치
		jlbMyMenu.setBounds(10, 30, 100, 30);
		jlbFavorMenu.setBounds(10, 330, 100, 30);
		jbRmvFavorMenu.setBounds(780, 330, 110, 30);
		jspMenuList.setBounds(10, 70, 880, 250);
		jspRequest.setBounds(10, 370, 880, 250);
		
		jbEditMyInfo.setBounds(10, 750, 110, 30);
		jbClose.setBounds(810, 750, 100, 30);
		setBounds(50, 50, 940, 840);
		//배치
		add(jpMyMenu);
		add(jbEditMyInfo);
		add(jbClose);
		
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}//MgrPageForm
	public static void main(String[] args) {
		new MyPageForm();
	}//main

}//class

