package kr.co.sist.recipe.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.evt.MyPageEvt;

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
        String[] menuColumnNames={"이름","이미지","타입","간단소개","가격","등록상태"};
        String[][] menuData={};
        // 테이블 수정 막기
        dtmMyMenu=new DefaultTableModel(menuData, menuColumnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //북마크 테이블
        String[] bookmarkColumnNames={"이름","이미지","타입","간단소개","가격"};
        String[][] bkData={};
        // 테이블 수정 막기
        dtmFavorMenu=new DefaultTableModel(bkData, bookmarkColumnNames){
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
        MyPageEvt mpe = new MyPageEvt(this);
        jbRmvFavorMenu.addActionListener(mpe);
        jbEditMyInfo.addActionListener(mpe);
        jbClose.addActionListener(mpe);

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



    public JButton getJbRmvFavorMenu() {
        return jbRmvFavorMenu;
    }



    public JButton getJbEditMyInfo() {
        return jbEditMyInfo;
    }



    public JButton getJbClose() {
        return jbClose;
    }




    public DefaultTableModel getDtmMyMenu() {
        return dtmMyMenu;
    }



    public void setDtmMyMenu(DefaultTableModel dtmMyMenu) {
        this.dtmMyMenu = dtmMyMenu;
    }



    public DefaultTableModel getDtmFavorMenu() {
        return dtmFavorMenu;
    }



    public void setDtmFavorMenu(DefaultTableModel dtmFavorMenu) {
        this.dtmFavorMenu = dtmFavorMenu;
    }



    public JTable getJtMyMenu() {
        return jtMyMenu;
    }



    public void setJtMyMenu(JTable jtMyMenu) {
        this.jtMyMenu = jtMyMenu;
    }



    public JTable getJtFavorMenu() {
        return jtFavorMenu;
    }



    public void setJtFavorMenu(JTable jtFavorMenu) {
        this.jtFavorMenu = jtFavorMenu;
    }



    public void setJbRmvFavorMenu(JButton jbRmvFavorMenu) {
        this.jbRmvFavorMenu = jbRmvFavorMenu;
    }



    public void setJbEditMyInfo(JButton jbEditMyInfo) {
        this.jbEditMyInfo = jbEditMyInfo;
    }



    public void setJbClose(JButton jbClose) {
        this.jbClose = jbClose;
    }



    ///////////////////////////////////////////////차후 삭제요망////////////////////////////////////
    public static void main(String[] args) {
        new MyPageForm();
    }//main

}//class

