package kr.co.sist.recipe.vo;

/**
 * 메인폼에서 하나의 메뉴정보를 가져오는 VO
 * @author user
 *
 */
public class SelectRecipeInfoVO {

	String menuName, menuImg, menuPrice, menuSimpleInfo, recipeInfo, menuType;

	public SelectRecipeInfoVO() {
	}

	public SelectRecipeInfoVO(String menuName, String menuImg, String menuPrice, String menuSimpleInfo,
			String recipeInfo, String menuType) {
		super();
		this.menuName = menuName;
		this.menuImg = menuImg;
		this.menuPrice = menuPrice;
		this.menuSimpleInfo = menuSimpleInfo;
		this.recipeInfo = recipeInfo;
		this.menuType = menuType;
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

	public String getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}

	public String getMenuSimpleInfo() {
		return menuSimpleInfo;
	}

	public void setMenuSimpleInfo(String menuSimpleInfo) {
		this.menuSimpleInfo = menuSimpleInfo;
	}

	public String getRecipeInfo() {
		return recipeInfo;
	}

	public void setRecipeInfo(String recipeInfo) {
		this.recipeInfo = recipeInfo;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	
	
	
}
