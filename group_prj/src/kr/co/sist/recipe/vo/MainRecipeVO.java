package kr.co.sist.recipe.vo;

public class MainRecipeVO {
	String menuName, menuPrice, menuInfo, menuType, menuImg;

	public MainRecipeVO() {
	}

	public MainRecipeVO(String menuName, String menuPrice, String menuInfo, String menuType, String menuImg) {
		super();
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.menuInfo = menuInfo;
		this.menuType = menuType;
		this.menuImg = menuImg;
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

	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	@Override
	public String toString() {
		return "MainRecipeVO [menuName=" + menuName + ", menuPrice=" + menuPrice + ", menuInfo=" + menuInfo
				+ ", menuType=" + menuType + ", menuImg=" + menuImg + "]\n";
	}
	
	
}//class
