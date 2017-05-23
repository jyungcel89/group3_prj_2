package kr.co.sist.recipe.vo;

public class MainRecipeVO {
	private String menuName,menuPrice,menuInfo,menuType,menuImg;
	
	public MainRecipeVO(){
		
	}
	public MainRecipeVO(String menuName,String menuPrice,String menuInfo,String menuType,String menuImg){
			super();
			this.menuName=menuName;
			this.menuPrice=menuPrice;
			this.menuInfo=menuInfo;
			this.menuType=menuType;
			this.menuImg=menuImg;
	}
	@Override
	public String toString() {
		return "MainRecipeVO [menuName=" + menuName + ", menuPrice=" + menuPrice + ", menuInfo=" + menuInfo
				+ ", menuType=" + menuType + ", menuImg=" + menuImg + "]";
	}
	public String getMenuName() {
		return menuName;
	}
	public String getMenuPrice() {
		return menuPrice;
	}
	public String getMenuInfo() {
		return menuInfo;
	}
	public String getMenuType() {
		return menuType;
	}
	public String getMenuImg() {
		return menuImg;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}
	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	
}
