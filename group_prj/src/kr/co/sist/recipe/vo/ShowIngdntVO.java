package kr.co.sist.recipe.vo;

public class ShowIngdntVO {

	private String ingrdntName,ingrdntPrice;
	
	public ShowIngdntVO(){
		
	}
	public ShowIngdntVO(String ingrdntName,String ingrdntPrice){
		super();
		this.ingrdntName=ingrdntName;
		this.ingrdntPrice=ingrdntPrice;
	}
	
	@Override
	public String toString() {
		return "ShowIngdntVO [ingrdntName=" + ingrdntName + ", ingrdntPrice=" + ingrdntPrice + "]";
	}
	public String getIngrdntName() {
		return ingrdntName;
	}
	public String getIngrdntPrice() {
		return ingrdntPrice;
	}
	public void setIngrdntName(String ingrdntName) {
		this.ingrdntName = ingrdntName;
	}
	public void setIngrdntPrice(String ingrdntPrice) {
		this.ingrdntPrice = ingrdntPrice;
	}
	
}