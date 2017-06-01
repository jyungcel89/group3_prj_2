package kr.co.sist.recipe.vo;

public class ShowIngdntVO {

	private String brand,ingrdntName,ingrdntPrice;
	
	public ShowIngdntVO(){
		
	}

	public ShowIngdntVO(String brand, String ingrdntName, String ingrdntPrice) {
		super();
		this.brand = brand;
		this.ingrdntName = ingrdntName;
		this.ingrdntPrice = ingrdntPrice;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getIngrdntName() {
		return ingrdntName;
	}

	public void setIngrdntName(String ingrdntName) {
		this.ingrdntName = ingrdntName;
	}

	public String getIngrdntPrice() {
		return ingrdntPrice;
	}

	public void setIngrdntPrice(String ingrdntPrice) {
		this.ingrdntPrice = ingrdntPrice;
	}

	@Override
	public String toString() {
		return "ShowIngdntVO [brand=" + brand + ", ingrdntName=" + ingrdntName + ", ingrdntPrice=" + ingrdntPrice + "]";
	}
	
}