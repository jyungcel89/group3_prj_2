package kr.co.sist.recipe.vo;

public class MainRecipeVO {
	String menuName, menuPrice, menuSimpeInfo, menuDetailInfo, menuType, menuImg;

	public MainRecipeVO() {
	}

	public MainRecipeVO(String menuName, String menuPrice, String menuSimpeInfo, String menuDetailInfo, String menuType,
			String menuImg) {
		super();
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.menuSimpeInfo = menuSimpeInfo;
		this.menuDetailInfo = menuDetailInfo;
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

	public String getMenuSimpeInfo() {
		return menuSimpeInfo;
	}

	public void setMenuSimpeInfo(String menuSimpeInfo) {
		this.menuSimpeInfo = menuSimpeInfo;
	}

	public String getMenuDetailInfo() {
		return menuDetailInfo;
	}

	public void setMenuDetailInfo(String menuDetailInfo) {
		this.menuDetailInfo = menuDetailInfo;
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

	
	
}//class
