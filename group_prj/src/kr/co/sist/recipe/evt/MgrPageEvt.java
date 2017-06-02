package kr.co.sist.recipe.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.recipe.dao.IngdntDAO;
import kr.co.sist.recipe.dao.MemberDAO;
import kr.co.sist.recipe.dao.RecipeDAO;
import kr.co.sist.recipe.view.AddRecipeForm;
import kr.co.sist.recipe.view.ItemPreviewForm;
import kr.co.sist.recipe.view.MgrPageForm;
import kr.co.sist.recipe.vo.MainRecipeVO;
import kr.co.sist.recipe.vo.MgrMemberVO;

/**
 * ������������ �̺�Ʈ Ŭ����
 * @author JiYong
 *
 */ 
public class MgrPageEvt extends WindowAdapter implements ActionListener, MouseListener {

	private MgrPageForm mpf;
	private MemberDAO mem_dao;
	private RecipeDAO rcp_dao;
	private IngdntDAO ida_dao;
	MainFormEvt mfe;
	
	public MgrPageEvt( MgrPageForm mpf, MainFormEvt mfe ) {
		this.mpf=mpf;
		this.mfe=mfe;
		mem_dao=MemberDAO.getInstance();
		rcp_dao=RecipeDAO.getInstance();
		ida_dao=IngdntDAO.getInstance();
		allRecipeList();
		requestList();
		memberList();
	}//MgrPageEvt
	
	//--------------------------��ü�޴����� ��------------------------
		/**
		 * ��ü �޴�����Ʈ ��ȸ
		 *  recipe_flag='Y'
		 * ���ε� ������ ��ü ����Ʈ�� ��ȸ
		 * RecipeDAO - recipeList("Y") method �� ����
		 * menu_flag='Y' 
		 */
		public void allRecipeList(){
			
			try {
				String flag="Y";
				List<MainRecipeVO> listAllRcp = rcp_dao.recipeList(flag);
				Object[] rowMenu = new Object[5];
				DefaultTableModel dtmMenu = mpf.getDtmMenuList();
				String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/s_";
				
				MainRecipeVO mrv=null; 
				dtmMenu.setRowCount(0);
				// name,img,type,info,price 
				for( int i=0; i < listAllRcp.size(); i++ ){
					mrv=listAllRcp.get(i);
					rowMenu[0]=mrv.getMenuName();
					rowMenu[1]=new ImageIcon(path+mrv.getMenuImg());
					rowMenu[2]=mrv.getMenuType();
					rowMenu[3]=mrv.getMenuSimpeInfo();
					rowMenu[4]=mrv.getMenuPrice();
					
					dtmMenu.addRow(rowMenu);
				}//end for
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch
		}//allRecipeList
		
		
		/**
		 * ������ ��û ����Ʈ ��ȸ
		 * - recipe_flag='S'
		 * - ����ó������ ���� ���δ�� ������ ��ü ����Ʈ�� ��ȸ
		 * - RecipeDAO - recipeList("S") method �� ����
		 */
		public void requestList(){
			
			try {
				String flag="S";
				List<MainRecipeVO> listReqRcp = rcp_dao.recipeList(flag);
				Object[] rowMenu = new Object[5];
				DefaultTableModel dtmMenu = mpf.getDtmMenuRequest();
				String path = "C:/dev/group_prj_git/group3_prj_2/group_prj/src/kr/co/sist/recipe/img/s_";
				
				MainRecipeVO mrv=null;
				dtmMenu.setRowCount(0);
				// name,img,type,info,price 
				for( int i=0; i < listReqRcp.size(); i++ ){
					mrv=listReqRcp.get(i);
					rowMenu[0]=mrv.getMenuName();
					rowMenu[1]= new ImageIcon(path+mrv.getMenuImg());
					rowMenu[2]=mrv.getMenuType();
					rowMenu[3]=mrv.getMenuSimpeInfo();
					rowMenu[4]=mrv.getMenuPrice();
					
					dtmMenu.addRow(rowMenu);
				}//end for
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch
		}//requestList
	
		
		/**
		 * ���������� ���� : ����remove��ư
		 * ���������Ǹ� ���� ó�����ִ� method
		 * ���õ� �������� menuName���� �����ͼ�
		 * RecipeDAO - deleteRecipe(menuName) method �� ����
		 */
		public void rmvRecipe(){
			
			try {
				// ���̺��� Ŭ�� > menuName ��������
				JTable jtRcp=mpf.getJtMenuList();
				int row=jtRcp.getSelectedRow();
				String value = (String) jtRcp.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", ���� �� : "+value);
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
				
				int flag=JOptionPane.showConfirmDialog(mpf, 
						"[ "+value+" ] �����Ͻ� �޴��� ���� �����Ͻðڽ��ϱ�?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// ������ menuName �� > ����
					rcp_dao.deleteRecipe(value);
					ida_dao.deleteIngdntOfRecp(value);
				}//end catch
				// ���� �� ����
				allRecipeList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch

		}//rmvRecipe
		
		
		/**
		 * ��û������ ���� : ����remove��ư
		 * recipe_flag S > N
		 * - ���δ�� ������ �����Ǹ� ��û���� ���·� ó�����ִ� method
		 * - ���õ� �������� menuName���� �����ͼ� 
		 * - RecipeDAO - updateFlagY(menuName) method �� ����
		 */
		public void rmvReqRecipe(){
			
			try {
				// ���̺��� Ŭ�� > menuName ��������
				JTable jtReqRcp=mpf.getJtMenuRequest();
				int row=jtReqRcp.getSelectedRow();
				String value = (String) jtReqRcp.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", ���� �� : "+value);
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
				
				int flag=JOptionPane.showConfirmDialog(mpf, 
						"[ "+value+" ] �����Ͻ� �޴��� ��û���� �Ͻðڽ��ϱ�?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// ������ menuName �� > ����
					rcp_dao.updateFlagN(value);
				}//end catch
				// ���� �� ����
				requestList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch
			
		}//rmvReqRecipe
		
		
		/**
		 * ��û������ ���� : submit��ư
		 * - recipeFlag 'S' > 'Y'
		 * - ���δ�� ������ �����Ǹ� ���� ó�����ִ� method
		 * - ���õ� �������� menuName���� �����ͼ� 
		 * - RecipeDAO - updateFlagY(menuName) method �� ����
		 */
		public void confirmReqRecipe(){
			
			try {
				// ���̺��� Ŭ�� > menuName ��������
				JTable jtSmtRcp=mpf.getJtMenuRequest();
				int row=jtSmtRcp.getSelectedRow();
				String value = (String) jtSmtRcp.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", ���� �� : "+value);
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
				
				int flag=JOptionPane.showConfirmDialog(mpf, 
						"[ "+value+" ] �����Ͻ� ��û �޴��� ���� �Ͻðڽ��ϱ�?");
				switch (flag) {
				case JOptionPane.OK_OPTION:
					// ������ menuName �� > ����
					rcp_dao.updateFlagY(value);
				}//end catch
				// ���� �� ����
				requestList();
				allRecipeList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch
		}//confirmReqRecipe

		
	//--------------------------ȸ������ ��----------------------------
		/**
		 * ȸ������ ����Ʈ : �ڵ�����
		 * ��üȸ���� id, name, mail�� ���̺� �����ش�.
		 * MemberDAO - selectAllMember() method�� ����
		 */
		public void memberList(){
			
			try {
				List<MgrMemberVO> listMem=mem_dao.selectAllMember();
				String[] rowMem = new String[3];
				DefaultTableModel dtmMem = mpf.getDtmMember();
				
				MgrMemberVO mmv=null;
				dtmMem.setRowCount(0);
				//id,name,mail
				for( int i=0; i < listMem.size(); i++ ){
					mmv=listMem.get(i);
					rowMem[0]=mmv.getId();
					rowMem[1]=mmv.getName();
					rowMem[2]=mmv.getMail();
					
					dtmMem.addRow(rowMem);
				}//end for
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. ȸ�������� �ҷ��� �� �����ϴ�.");
				se.printStackTrace();
			}//end catch
		}//memberList
		
		
		/**
		 * ȸ��Ż�� ��Ű�� : remove ��ư
		 * ���̺��� ���õ� ȸ�� id�� �����ͼ� 
		 * MemberDAO - deleteMember(id) method�� ����
		 */
		public void rmvMember(){ 
			
			try {
				// ���̺��� Ŭ�� > id ��������
				JTable jtMem=mpf.getJtMember();
				int row=jtMem.getSelectedRow();
				String value = (String) jtMem.getValueAt(row, 0);
//				System.out.println(row);
//				System.out.println("row : "+row+", ���� �� : "+value);
			int flag=JOptionPane.showConfirmDialog(mpf, 
					"[ "+value+" ] �����Ͻ� ȸ���� ���������� �����Ͻðڽ��ϱ�?");
			switch (flag) {
			case JOptionPane.OK_OPTION:
				// ������ id �� > ����
				mem_dao.deleteMember(value);
			}//end switch
//				System.out.println("delFlag : "+mem_dao.deleteMember(value));
			
				// ���� �� ����
				memberList();
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(mpf, 
						"�����Ǹ� �������ּ���.");
				aioobe.printStackTrace();
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(mpf, 
						"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
				se.printStackTrace();
			}//end catch
		}//rmvMember

	@Override
	public void actionPerformed(ActionEvent ae) {
		// ������ - �޴������� - �޴�����Ʈ ������ư
		if( ae.getSource() == mpf.getJbRmvMenu() ){
				rmvRecipe();
		}//end if
		// ������ - �޴������� - �޴���û����Ʈ ��û������ư
		if( ae.getSource() == mpf.getJbRmvRqust() ){
			rmvReqRecipe();
		}//end if
		// ������ - �޴������� - �޴���û����Ʈ ��û���ι�ư
		if( ae.getSource() == mpf.getJbSmitRqust() ){
			confirmReqRecipe();
		}//end if
		// ������ - ȸ�������� - ��ü ȸ������Ʈ ������ư
		if( ae.getSource() == mpf.getJbRmvMember() ){
			rmvMember();
		}//end if
		
		if (ae.getSource() == mpf.getJbClose()) {
			int selectNum = JOptionPane.showConfirmDialog(mpf, "â�� �����ðڽ��ϱ�?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION:
				mpf.dispose();
			}// end switch
		}//end if
		
	}//actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {
		if( me.getClickCount()==2 ){
			// �޴�����Ʈ ����Ŭ��
			if( me.getSource()==mpf.getJtMenuList() ){
				JTable jtSmtRcp=mpf.getJtMenuList();
				int row=jtSmtRcp.getSelectedRow();
				String value=(String)jtSmtRcp.getValueAt(row, 0);
				//MENU_NAME, IMG, FOOD_TYPE, INFO, RECIPE_INFO
				new AddRecipeForm(value);
			}//end if
			
			// �޴���û����Ʈ ����Ŭ��
			if( me.getSource()==mpf.getJtMenuRequest() ){
				try {
					JTable jtReqRcp=mpf.getJtMenuRequest();
					int row=jtReqRcp.getSelectedRow();
					String value=(String)jtReqRcp.getValueAt(row, 0);
					MainRecipeVO mrv;
					mrv=rcp_dao.selectOneRecipe(value);
					//MENU_NAME, IMG, FOOD_TYPE, INFO, RECIPE_INFO
					new ItemPreviewForm(mrv,mfe);
				} catch (SQLException se) {
					JOptionPane.showMessageDialog(mpf, 
							"�˼��մϴ�. �Ͻ����� ������ְ� �߻��Ͽ����ϴ�.\n����Ŀ� �ٽ� �õ����ּ���.");
					se.printStackTrace();
				}//end catch
			}//end if
		}//end if
	}//mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}//class
