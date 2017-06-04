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
		this.setTitle("홍홍홍 레시피 메뉴 추가");
		this.setModal(true);
		setLayout(null);
		// 백그라운드
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/addrcpBack.png"));
		setContentPane(jlImg);
		Font defaultFont =new Font("맑은 고딕", Font.BOLD, 14);
	
//		region 레시피 이미지 추가하는 라벨 및 이미지 아이콘
		imgIcon=new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/default.jpg");
		lblImg = new JLabel(imgIcon);
		lblImg.setBounds(80,50,260,200);
		add(lblImg);
		
		//기본이미지
		JLabel img = new JLabel(new ImageIcon("C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/default.jpg"));
		img.setBounds(80,50,260,200);
		add(img); 
		
//		region 레시피이미지 추가버튼
		jbAddImg=new JButton("사진 올리기");
		jbAddImg.setBounds(210,270,130,30);
//		endregion 레시피이미지 추가버튼 끝
		
//		region 상품명,요리분류,간단소개 라벨 추가 영역
		lblRecipeName = new JLabel("상품명");
		lblRecipeName.setBounds(370,40,40,30);
		lblRecipeSort=new JLabel("요리분류");
		lblRecipeSort.setBounds(370,80,60,30);
		lblRecipeInfo=new JLabel("간단소개");
		lblRecipeInfo.setBounds(370,120,60,30);
//		endregion 상품명,요리분류,간단소개 라벨 추가 영역끝
		
//		region 레시피명 입력하는 JTextField
		jtfRecipeName=new JTextField(menuName);
		jtfRecipeName.setBounds(442,45,250,25);
//		endregion 레시피명 입력하는 JTextField끝
		
//		region 레시피 요리분류 콤보박스 영역
		jcbCateg=new JComboBox<String>();
		jcbCateg.setBounds(442,85,250,25);
//		endregion 레시피 요리분류 콤보박스 영역끝
		
//		region 레시피 간단설명 텍스트에어리어
		jtaInfo = new JTextArea();
		jtaInfo.setBounds(442,130,250,130);
		jtaInfo.setLineWrap(true);
//		endregion 레시피 간단설명 텍스트에어리어끝
		
//		region 재료선택,편의점,재료분류 라벨 추가 영역
		lblIngredntChoice=new JLabel("◑ 재료선택");
		lblIngredntChoice.setFont(defaultFont);
		lblIngredntChoice.setBounds(40,315,90,25);
		lblConvenienceStore=new JLabel("편의점");
		lblConvenienceStore.setBounds(210,350,50,25);
		lblIngredntSort=new JLabel("재료분류");
		lblIngredntSort.setBounds(405, 350,120,25);
//		endregion 재료선택,편의점,재료분류 라벨 추가 영역 끝
		
//		region 편의점,재료분류 콤보박스 영역
		jcbStore=new JComboBox<String>();
		jcbStore.setBounds(260,350,120,25);
		jcbIngrdntSort=new JComboBox<String>();
		jcbIngrdntSort.setBounds(470,350,120,25);
//		endregion 편의점,재료분류 콤보박스 영역 끝
		
//		region 검색버튼
		jbSearch=new JButton("검색");
		jbSearch.setBounds(610,350,85,26);
//		endregion 검색버튼 끝
		
//		region 재료추가,추가된 재료 테이블 영역
	
		//재료추가 테이블 부분
		String[] columnNames1={"재료명","가격"};
		String[][] data1={};
		dtmIngrednt=new DefaultTableModel(data1, columnNames1);
		jtIngrednt=new JTable(dtmIngrednt){
			private static final long serialVersionUID = 1L;
			//컬럼의 수정 막기 위한 method Override
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		//컬럼을 선택하여 움직이지 못하도록 설정
		jtIngrednt.getTableHeader().setReorderingAllowed(false);
		//컬럼의 높이 설정
		jtIngrednt.setRowHeight(20);
		//컬럼의 넒이 설정
		//"번호","이미지","메뉴코드","설명","가격"
		jtIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
		jtIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
		jspIngrednt=new JScrollPane(jtIngrednt);
		jspIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspIngrednt.setBounds(40,390,550,80);
		//재료추가 테이블부분 끝
		
		//추가된 재료테이블
		
				String[] columnNames2={"재료명","가격"};
				String[][] data2={};
				dtmAddedIngrednt=new DefaultTableModel(data2, columnNames2);
				jtaddedIngrednt=new JTable(dtmAddedIngrednt){
					private static final long serialVersionUID = 1L;
					//컬럼의 수정 막기 위한 method Override
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}//isCellEditable
				};
				//컬럼을 선택하여 움직이지 못하도록 설정
				jtaddedIngrednt.getTableHeader().setReorderingAllowed(false);
				//컬럼의 높이 설정
				jtaddedIngrednt.setRowHeight(20);
				//컬럼의 넒이 설정
				//"번호","이미지","메뉴코드","설명","가격"
				jtaddedIngrednt.getColumnModel().getColumn(0).setPreferredWidth(140);
				jtaddedIngrednt.getColumnModel().getColumn(1).setPreferredWidth(50);
				jspAddedIngrednt=new JScrollPane(jtaddedIngrednt);
				jspAddedIngrednt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				jspAddedIngrednt.setBounds(40,490,550,80);
		
				
		//추가된 재료테이블 끝
		
//		endregion 재료추가,추가된 재료 테이블 영역 끝
		
//		region 재료의 삭제 및 추가 버튼
		jbAddIngrednt=new JButton("추가");
		jbAddIngrednt.setBounds(610, 390,85,78);
		jbRmvIngrednt=new JButton("삭제");
		jbRmvIngrednt.setBounds(610,490,85,78);
//		endregion 재료의 삭제 및 추가 버튼 끝
		
//		region 재료의 총가격 라벨과 실제로 값을 보여줄 라벨
		lblTotalPrice=new JLabel("");
		lblTotalPrice.setFont(defaultFont);
		Font priceResult=new Font("맑은 고딕",Font.BOLD,15);
		
		lblTotalPrice=new JLabel();
		lblTotalPrice.setFont(priceResult);
		lblTotalPrice.setForeground(Color.red);
		lblTotalPrice.setBounds(540,560,100,50);
		lblTotalPriceView=new JLabel("총가격:");
		lblTotalPriceView.setFont(defaultFont);
		lblTotalPriceView.setBounds(460,560,100,50);
//		endregion 재료의 총가격 라벨과 실제로 값을 보여줄 라벨 끝
		
//		region 레시피 작성 라벨
		lblWriteRecipe=new JLabel("◑ 레시피 작성");
		lblWriteRecipe.setBounds(40, 585,150,40);
		lblWriteRecipe.setFont(defaultFont);
//		endregion 레시피 작성 라벨 끝
		
//		region 레피시 작성 TextArea
		
		jtaWriteRecipe=new JTextArea(5,10);
		jtaWriteRecipe.setLineWrap(true);
		jtaWriteRecipe.setBackground(Color.WHITE);
		jspTextArea=new JScrollPane(jtaWriteRecipe);
		jspTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspTextArea.setBounds(40, 630,660,100);
//		endregion 레피시 작성 TextArea 끝
		
//		region 요청,닫기,관리자 버튼 구역
//		endregion 요청,닫기,관리자 버튼 구역 끝
		jbClose=new JButton("닫기");
		jbClose.setBounds(617, 750,100,40);
		jbRequest=new JButton("메뉴 요청");
		jbRequest.setBounds(495,750,100,40);
		jbMgr=new JButton("메뉴 수정");
		jbMgr.setVisible(false);
		jbMgr.setBounds(20,750,100,40);
//		endregion 요청,닫기,관리자 버튼 구역 끝s
		jcbCateg.addItem("안주류");
		jcbCateg.addItem("디저트");
		jcbCateg.addItem("분식류");
		jcbCateg.addItem("식사류");
		jcbStore.addItem("GS25");
		jcbStore.addItem("7-eleven");
		jcbStore.addItem("CU");
		jcbIngrdntSort.addItem("과자");
		jcbIngrdntSort.addItem("기타");
		jcbIngrdntSort.addItem("아이스크림");
		jcbIngrdntSort.addItem("라면");
		jcbIngrdntSort.addItem("즉석요리");
		
		//이벤트 추가
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
		
		//배치
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

