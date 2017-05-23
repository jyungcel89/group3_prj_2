package kr.co.sist.recipe.vo;

public class IngdntVO {

	private String ingrdntName,ingrdntBrand,ingrdntType;
	private int ingrdntPrice,ingrdntCode;
	
	
	public IngdntVO(){
		
	}
	public IngdntVO(String ingrdntName,String ingrdntBrand,String ingrdntType,int ingrdntPrice,int ingrdntCode){
		super();
		this.ingrdntName=ingrdntName;
		this.ingrdntBrand=ingrdntBrand;
		this.ingrdntType=ingrdntType;
		this.ingrdntPrice=ingrdntPrice;
		this.ingrdntCode=ingrdntCode;
		
	}
	public String getIngrdntName() {
		return ingrdntName;
	}
	public String getIngrdntBrand() {
		return ingrdntBrand;
	}
	public String getIngrdntType() {
		return ingrdntType;
	}
	public int getIngrdntPrice() {
		return ingrdntPrice;
	}
	public int getIngrdntCode() {
		return ingrdntCode;
	}
	public void setIngrdntName(String ingrdntName) {
		this.ingrdntName = ingrdntName;
	}
	public void setIngrdntBrand(String ingrdntBrand) {
		this.ingrdntBrand = ingrdntBrand;
	}
	public void setIngrdntType(String ingrdntType) {
		this.ingrdntType = ingrdntType;
	}
	public void setIngrdntPrice(int ingrdntPrice) {
		this.ingrdntPrice = ingrdntPrice;
	}
	public void setIngrdntCode(int ingrdntCode) {
		this.ingrdntCode = ingrdntCode;
	}
	@Override
	public String toString() {
		return "IngdntVO [ingrdntName=" + ingrdntName + ", ingrdntBrand=" + ingrdntBrand + ", ingrdntType="
				+ ingrdntType + ", ingrdntPrice=" + ingrdntPrice + ", ingrdntCode=" + ingrdntCode + "]";
	}
	
	
}
