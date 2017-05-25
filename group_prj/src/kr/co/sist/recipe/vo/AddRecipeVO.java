package kr.co.sist.recipe.vo;
 
public class AddRecipeVO {
	String menuName, menuImg, menuType, menuSimpleInfo, menuDetailInfo, menuPrice;

	public AddRecipeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddRecipeVO(String menuName, String menuImg, String menuType, String menuSimpleInfo, String menuDetailInfo,
			String menuPrice) {
		super();
		this.menuName = menuName;
		this.menuImg = menuImg;
		this.menuType = menuType;
		this.menuSimpleInfo = menuSimpleInfo;
		this.menuDetailInfo = menuDetailInfo;
		this.menuPrice = menuPrice;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuSimpleInfo() {
		return menuSimpleInfo;
	}

	public void setMenuSimpleInfo(String menuSimpleInfo) {
		this.menuSimpleInfo = menuSimpleInfo;
	}

	public String getMenuDetailInfo() {
		return menuDetailInfo;
	}

	public void setMenuDetailInfo(String menuDetailInfo) {
		this.menuDetailInfo = menuDetailInfo;
	}

	public String getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}
	
	
}
