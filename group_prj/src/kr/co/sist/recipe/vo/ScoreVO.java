package kr.co.sist.recipe.vo;

public class ScoreVO {
	private String id, menuName;
	private int value;
	 
	public ScoreVO() {
	}
	
	
	public ScoreVO(String id, String menuName, int value) {
		
		this.id = id;
		this.menuName = menuName;
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	

}
