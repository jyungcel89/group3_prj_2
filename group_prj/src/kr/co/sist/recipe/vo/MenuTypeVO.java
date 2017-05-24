package kr.co.sist.recipe.vo;

public class MenuTypeVO {
	String anju, dessert, meal, bunsik;

	public MenuTypeVO() {
	}

	public MenuTypeVO(String anju, String dessert, String meal, String bunsik) {
		super();
		this.anju = anju;
		this.dessert = dessert;
		this.meal = meal;
		this.bunsik = bunsik;
	}

	public String getAnju() {
		return anju;
	}

	public void setAnju(String anju) {
		this.anju = anju;
	}

	public String getDessert() {
		return dessert;
	}

	public void setDessert(String dessert) {
		this.dessert = dessert;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getBunsik() {
		return bunsik;
	}

	public void setBunsik(String bunsik) {
		this.bunsik = bunsik;
	}
	
	
}//class
