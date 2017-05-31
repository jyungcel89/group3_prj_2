package kr.co.sist.recipe.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.evt.MgrPageEvt;

@SuppressWarnings("serial")
public class MgrPageForm extends JDialog {

	private JLabel jlbMenuList, jlbMenuRequest, jlbMember;
	private JTable jtMenuList, jtMenuRequest, jtMember;
	private DefaultTableModel dtmMenuList, dtmMenuRequest, dtmMember;
	private JTabbedPane jtpTab;
	private JButton jbRmvMenu, jbRmvRqust, jbSmitRqust, jbRmvMember, jbClose;
	
	public MgrPageForm(String logId/*MainForm mf*/) {
//		super(mf,"Manager Page",true);
//		this.mf=mf;
		setLayout(null);
		JLabel jlBackImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/mgrpageBack.png"));
	    setContentPane(jlBackImg);
		Font defaultFont=new Font("���� ���",Font.BOLD,14);
		
		jlbMenuList=new JLabel("�� �޴� ����Ʈ");
		jlbMenuRequest=new JLabel("�� �޴� ��û��� ����Ʈ");
		jlbMember=new JLabel("�� ��ü ȸ�� ����Ʈ");
		jlbMenuList.setFont(defaultFont);
		jlbMenuRequest.setFont(defaultFont);
		jlbMember.setFont(defaultFont);
		System.out.println("MgrPage : "+logId);
		jlbMenuList=new JLabel("�޴� ����Ʈ");
		jlbMenuRequest=new JLabel("�޴� ��û��� ����Ʈ");
		jlbMember=new JLabel("��ü ȸ�� ����Ʈ");
		
		JLabel jlUserName=new JLabel(" [ "+logId+" ] �� ȯ���մϴ�.");
		jlUserName.setFont(defaultFont);
		jlUserName.setForeground(Color.white);
		
		jbRmvMenu=new JButton("����");
		jbSmitRqust=new JButton("��û ����");
		jbRmvRqust=new JButton("��û ����");
		jbRmvMember=new JButton("����");
		jbClose=new JButton("�ݱ�");
		
		//���̺�
		String[] menuColumnNames={"�޴��̸�","�޴��̹���","�޴�Ÿ��","���ܼ���","����"};
		String[][] menuData={{"","","",""}};
		String[] memberColumnNames={"���̵�","�̸�","�̸���"};
		String[][] memberData={{"","",""}};
		// ���̺� ���� ����
		dtmMenuList=new DefaultTableModel(menuData, menuColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// ���̺� ���� ����
		dtmMenuRequest=new DefaultTableModel(menuData, menuColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// ���̺� ���� ����
		dtmMember=new DefaultTableModel(memberData, memberColumnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtMenuList=new JTable(dtmMenuList); 
		jtMenuRequest=new JTable(dtmMenuRequest);
		jtMember=new JTable(dtmMember);
		//�÷� ���� 
		jtMenuList.getTableHeader().setReorderingAllowed(false);
		jtMenuRequest.getTableHeader().setReorderingAllowed(false);
		jtMember.getTableHeader().setReorderingAllowed(false);
		//�÷� ���� ����
		jtMenuList.setRowHeight(100);
		jtMenuRequest.setRowHeight(100);
		//�÷� �ʺ� ����
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
		
		//�г�
		//�޴����� �г�
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
		
		//ȸ������ �г�
		JPanel jpMgrMember=new JPanel();
		jpMgrMember.setLayout(null);
		jpMgrMember.add(jlbMember);
		jpMgrMember.add(jspMember);
		jpMgrMember.add(jbRmvMember);
		jpMgrMember.setOpaque(false);

		// �� �����ϰ� �ٲٱ�
		Color bgColor = new Color(255, 255, 255, 130);
		UIManager.put("TabbedPane.contentAreaColor", bgColor);

		//�� ����
		jtpTab=new JTabbedPane();
		jtpTab.add("�޴� ����",jpMgrMenu);
		jtpTab.add("ȸ�� ����", jpMgrMember);
//		jtpTab.setForeground(Color.BLACK);
		jtpTab.setBackgroundAt(0,new Color(255, 255, 255, 130));
		jtpTab.setBackgroundAt(1,new Color(255, 255, 255, 130));
		
		jtpTab.setOpaque(false);
		jtpTab.putClientProperty("TabbedPane.contentOpaque", Boolean.FALSE);
		jtpTab.putClientProperty("TabbedPane.tabsOpaque", Boolean.FALSE);
		

		
		//�̺�Ʈ
		MgrPageEvt mpe=new MgrPageEvt(this);
		jbRmvMenu.addActionListener(mpe);
		jbRmvRqust.addActionListener(mpe);
		jbSmitRqust.addActionListener(mpe);
		jbRmvMember.addActionListener(mpe);
		jbClose.addActionListener(mpe);
		jtMenuList.addMouseListener(mpe);
		jtMenuRequest.addMouseListener(mpe);
		
		//�� ��ġ
		jtpTab.setBounds(10, 100, 900, 640);
		//�޴������� ��ġ
		jlbMenuList.setBounds(10, 10, 130, 40);
		jlbMenuRequest.setBounds(10, 310, 170, 40);
		jbRmvMenu.setBounds(790, 10, 100, 30);
		jbSmitRqust.setBounds(790, 310, 100, 30);
		jbRmvRqust.setBounds(680, 310, 100, 30);
		jspMenuList.setBounds(10, 50, 880, 250);
		jspRequest.setBounds(10, 350, 880, 250);
		
		//ȸ�������� ��ġ
		jlbMember.setBounds(10, 10, 130, 40);
		jbRmvMember.setBounds(790, 10, 100, 30);
		jspMember.setBounds(10, 50, 880, 550);
		
		// ����� ���̵� �� ��ġ
		jlUserName.setBounds(10, 10, 170, 30);
		
		jbClose.setBounds(810, 750, 100, 30);
		setBounds(50, 50, 940, 840);
		//��ġ
		add(jtpTab);
		add(jbClose);
		// ����� ���̵� �� ���̱�
		add(jlUserName);
		
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}//MgrPageForm
	
//	public static void main(String[] args) {
//		new MgrPageForm();
//	}//main

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

