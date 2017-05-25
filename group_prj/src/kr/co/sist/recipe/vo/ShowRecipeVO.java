package kr.co.sist.recipe.vo;

public class ShowRecipeVO {
	String menuName, menuImg;

	public ShowRecipeVO() {
	}

	public ShowRecipeVO(String menuName, String menuImg) {
		super();
		this.menuName = menuName;
		this.menuImg = menuImg;
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

	@Override
	public String toString() {
		return "ShowRecipeVO [menuName=" + menuName + ", menuImg=" + menuImg + "]\n";
	}
	
	
	
}//class
