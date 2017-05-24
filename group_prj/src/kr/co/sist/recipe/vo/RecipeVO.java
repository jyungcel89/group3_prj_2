package kr.co.sist.recipe.vo;

public class RecipeVO {
	String menuName, menuPrice, menuInfo, menuType, flag;
	int menuCode;
	
	public RecipeVO() {
	}

	public RecipeVO(String menuName, String menuPrice, String menuInfo, String menuType, String flag, int menuCode) {
		super();
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.menuInfo = menuInfo;
		this.menuType = menuType;
		this.flag = flag;
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}

	public String getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}
	
}//class
