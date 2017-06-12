package kr.co.sist.recipe.view;


import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.evt.MainFormEvt;
import kr.co.sist.recipe.evt.MgrPageEvt;

@SuppressWarnings("serial")
public class MgrPageForm extends JDialog {

	private JLabel jlbMenuList, jlbMenuRequest, jlbMember;
	private JTable jtMenuList, jtMenuRequest, jtMember;
	private DefaultTableModel dtmMenuList, dtmMenuRequest, dtmMember;
	private JTabbedPane jtpTab;
	private JButton jbRmvMenu, jbRmvRqust, jbSmitRqust, jbRmvMember, jbClose;
	private MainFormEvt mfe;
	
	public MgrPageForm(String logId, MainFormEvt mfe) {
		setTitle("홍홍홍 레시피 관리자");
		setLayout(null);
		JLabel jlBackImg = new JLabel(new ImageIcon(System.getProperty("user.dir")+"/src/kr/co/sist/recipe/img/mgrpageBack.png"));
	    setContentPane(jlBackImg);
		Font defaultFont=new Font("맑은 고딕",Font.BOLD,14);
		
		jlbMenuList=new JLabel("◑ 메뉴 리스트");
		jlbMenuRequest=new JLabel("◑ 메뉴 요청대기 리스트");
		jlbMember=new JLabel("◑ 전체 회원 리스트");
		jlbMenuList.setFont(defaultFont);
		jlbMenuRequest.setFont(defaultFont);
		jlbMember.setFont(defaultFont);
//		System.out.println("MgrPage : "+logId);
		jlbMenuList=new JLabel("메뉴 리스트");
		jlbMenuRequest=new JLabel("메뉴 요청대기 리스트");
		jlbMember=new JLabel("전체 회원 리스트");
		
		JLabel jlUserName=new JLabel(" [ "+logId+" ] 님 환영합니다.");
		jlUserName.setFont(defaultFont);
		jlUserName.setForeground(Color.white);
		
		jbRmvMenu=new JButton("삭제");
		jbSmitRqust=new JButton("요청 승인");
		jbRmvRqust=new JButton("요청 거절");
		jbRmvMember=new JButton("삭제");
		jbClose=new JButton("닫기");
		
		//테이블
		String[] menuColumnNames={"메뉴이름","메뉴이미지","메뉴타입","간단설명","가격"};
		String[][] menuData={{"","","",""}};
		String[] memberColumnNames={"아이디","이름","이메일"};
		String[][] memberData={{"","",""}};
		// 테이블 수정 막기
		dtmMenuList=new DefaultTableModel(menuData, menuColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// 테이블 수정 막기
		dtmMenuRequest=new DefaultTableModel(menuData, menuColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// 테이블 수정 막기
		dtmMember=new DefaultTableModel(memberData, memberColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtMenuList=new JTable(dtmMenuList){
			//컬럼에 이미지를 넣기 위한 method Override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}//getColumnClass
		}; 
		jtMenuRequest=new JTable(dtmMenuRequest){
			//컬럼에 이미지를 넣기 위한 method Override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtMember=new JTable(dtmMember);
		//컬럼 고정 
		jtMenuList.getTableHeader().setReorderingAllowed(false);
		jtMenuRequest.getTableHeader().setReorderingAllowed(false);
		jtMember.getTableHeader().setReorderingAllowed(false);
		//컬럼 높이 설정
		jtMenuList.setRowHeight(120);
		jtMenuRequest.setRowHeight(120);
		//컬럼 너비 설정
		jtMenuList.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtMenuList.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtMenuList.getColumnModel().getColumn(2).setPreferredWidth(10);
		jtMenuList.getColumnModel().getColumn(3).setPreferredWidth(450);
		jtMenuList.getColumnModel().getColumn(4).setPreferredWidth(5);
		
		jtMenuRequest.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtMenuRequest.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtMenuRequest.getColumnModel().getColumn(2).setPreferredWidth(10);
		jtMenuRequest.getColumnModel().getColumn(3).setPreferredWidth(450);
		jtMenuRequest.getColumnModel().getColumn(4).setPreferredWidth(5);
		
		JScrollPane jspMenuList=new JScrollPane(jtMenuList);
		JScrollPane jspRequest=new JScrollPane(jtMenuRequest);
		JScrollPane jspMember=new JScrollPane(jtMember);
		
		//패널
		//메뉴관리 패널
		JPanel jpMgrMenu=new JPanel();
		jpMgrMenu.setLayout(null);
		jpMgrMenu.add(jlbMenuList);
		jpMgrMenu.add(jlbMenuRequest);
		jpMgrMenu.add(jspMenuList);
		jpMgrMenu.add(jspRequest);
		jpMgrMenu.add(jbRmvMenu);
		jpMgrMenu.add(jbRmvRqust);
		jpMgrMenu.add(jbSmitRqust);
		jpMgrMenu.setOpaque(false);
		
		//회원관리 패널
		JPanel jpMgrMember=new JPanel();
		jpMgrMember.setLayout(null);
		jpMgrMember.add(jlbMember);
		jpMgrMember.add(jspMember);
		jpMgrMember.add(jbRmvMember);
		jpMgrMember.setOpaque(false);

		// 탭 투명하게 바꾸기
		Color bgColor = new Color(255, 255, 255, 130);
		UIManager.put("TabbedPane.contentAreaColor", bgColor);

		//탭 생성
		jtpTab=new JTabbedPane();
		jtpTab.add("메뉴 관리",jpMgrMenu);
		jtpTab.add("회원 관리", jpMgrMember);
//		jtpTab.setForeground(Color.BLACK);
		jtpTab.setBackgroundAt(0,new Color(255, 255, 255, 130));
		jtpTab.setBackgroundAt(1,new Color(255, 255, 255, 130));
		
		jtpTab.setOpaque(false);
		jtpTab.putClientProperty("TabbedPane.contentOpaque", Boolean.FALSE);
		jtpTab.putClientProperty("TabbedPane.tabsOpaque", Boolean.FALSE);
		

		
		//이벤트
		MgrPageEvt mpe=new MgrPageEvt(this,mfe);
		jbRmvMenu.addActionListener(mpe);
		jbRmvRqust.addActionListener(mpe);
		jbSmitRqust.addActionListener(mpe);
		jbRmvMember.addActionListener(mpe);
		jbClose.addActionListener(mpe);
		jtMenuList.addMouseListener(mpe);
		jtMenuRequest.addMouseListener(mpe);
		
		//탭 위치
		jtpTab.setBounds(10, 100, 900, 640);
		//메뉴관리탭 위치
		jlbMenuList.setBounds(10, 10, 130, 40);
		jlbMenuRequest.setBounds(10, 310, 170, 40);
		jbRmvMenu.setBounds(790, 10, 100, 30);
		jbSmitRqust.setBounds(790, 310, 100, 30);
		jbRmvRqust.setBounds(680, 310, 100, 30);
		jspMenuList.setBounds(10, 50, 880, 250);
		jspRequest.setBounds(10, 350, 880, 250);
		
		//회원관리탭 위치
		jlbMember.setBounds(10, 10, 130, 40);
		jbRmvMember.setBounds(790, 10, 100, 30);
		jspMember.setBounds(10, 50, 880, 550);
		
		// 사용자 아이디 라벨 배치
		jlUserName.setBounds(10, 10, 170, 30);
		
		jbClose.setBounds(810, 750, 100, 30);
		//배치
		add(jtpTab);
		add(jbClose);
		// 사용자 아이디 라벨 붙이기
		add(jlUserName);
		
		setBounds(50, 50, 940, 840);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}//MgrPageForm

	public JLabel getJlbMenuList() {
		return jlbMenuList;
	}

	public void setJlbMenuList(JLabel jlbMenuList) {
		this.jlbMenuList = jlbMenuList;
	}

	public JLabel getJlbMenuRequest() {
		return jlbMenuRequest;
	}

	public void setJlbMenuRequest(JLabel jlbMenuRequest) {
		this.jlbMenuRequest = jlbMenuRequest;
	}

	public JLabel getJlbMember() {
		return jlbMember;
	}

	public void setJlbMember(JLabel jlbMember) {
		this.jlbMember = jlbMember;
	}

	public JTable getJtMenuList() {
		return jtMenuList;
	}

	public void setJtMenuList(JTable jtMenuList) {
		this.jtMenuList = jtMenuList;
	}

	public JTable getJtMenuRequest() {
		return jtMenuRequest;
	}

	public void setJtMenuRequest(JTable jtMenuRequest) {
		this.jtMenuRequest = jtMenuRequest;
	}

	public JTable getJtMember() {
		return jtMember;
	}

	public void setJtMember(JTable jtMember) {
		this.jtMember = jtMember;
	}

	public DefaultTableModel getDtmMenuList() {
		return dtmMenuList;
	}

	public void setDtmMenuList(DefaultTableModel dtmMenuList) {
		this.dtmMenuList = dtmMenuList;
	}

	public DefaultTableModel getDtmMenuRequest() {
		return dtmMenuRequest;
	}

	public void setDtmMenuRequest(DefaultTableModel dtmMenuRequest) {
		this.dtmMenuRequest = dtmMenuRequest;
	}

	public DefaultTableModel getDtmMember() {
		return dtmMember;
	}

	public void setDtmMember(DefaultTableModel dtmMember) {
		this.dtmMember = dtmMember;
	}

	public JTabbedPane getJtpTab() {
		return jtpTab;
	}

	public void setJtpTab(JTabbedPane jtpTab) {
		this.jtpTab = jtpTab;
	}

	public JButton getJbRmvMenu() {
		return jbRmvMenu;
	}

	public void setJbRmvMenu(JButton jbRmvMenu) {
		this.jbRmvMenu = jbRmvMenu;
	}

	public JButton getJbRmvRqust() {
		return jbRmvRqust;
	}

	public void setJbRmvRqust(JButton jbRmvRqust) {
		this.jbRmvRqust = jbRmvRqust;
	}

	public JButton getJbSmitRqust() {
		return jbSmitRqust;
	}

	public void setJbSmitRqust(JButton jbSmitRqust) {
		this.jbSmitRqust = jbSmitRqust;
	}

	public JButton getJbRmvMember() {
		return jbRmvMember;
	}

	public void setJbRmvMember(JButton jbRmvMember) {
		this.jbRmvMember = jbRmvMember;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public void setJbClose(JButton jbClose) {
		this.jbClose = jbClose;
	}

}//class

