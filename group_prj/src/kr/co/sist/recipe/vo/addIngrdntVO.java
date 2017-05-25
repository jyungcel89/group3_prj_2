package kr.co.sist.recipe.vo;

public class addIngrdntVO {
	private String ingrdntName,menuName,ingrdntCode;
	
	public addIngrdntVO(){
		
	}
	public addIngrdntVO(String ingrdntName,String menuName){
		super();
		this.ingrdntName=ingrdntName;
		this.menuName=menuName;
	}
	public String getIngrdntName() {
		return ingrdntName;
	}
	public String getMenuName() {
		return menuName;
	}
	public String getIngrdntCode() {
		return ingrdntCode;
	}
	public void setIngrdntName(String ingrdntName) {
		this.ingrdntName = ingrdntName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public void setIngrdntCode(String ingrdntCode) {
		this.ingrdntCode = ingrdntCode;
	}
	
}
