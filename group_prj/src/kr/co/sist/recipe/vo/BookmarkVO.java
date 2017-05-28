package kr.co.sist.recipe.vo;

public class BookmarkVO {

	private String id, menuName;

	public BookmarkVO() {
	}

	public BookmarkVO(String id, String menuName) {
		super();
		this.id = id;
		this.menuName = menuName;
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
	
	
	
}
