package kr.co.sist.recipe.vo;


public class IngredientOfRecipeVO {
	
	
	private String ingrdntName,ingrdntPrice,ingrdntBrand;
	
	public IngredientOfRecipeVO(){
		
	}
	public IngredientOfRecipeVO(String ingrdntName,String ingrdntPrice,String ingrdntBrand){
		this.ingrdntName=ingrdntName;
		this.ingrdntPrice=ingrdntPrice;
		this.ingrdntBrand=ingrdntBrand;
	}
	public String getIngrdntName() {
		return ingrdntName;
	}
	public String getIngrdntPrice() {
		return ingrdntPrice;
	}
	public String getIngrdntBrand() {
		return ingrdntBrand;
	}
	public void setIngrdntName(String ingrdntName) {
		this.ingrdntName = ingrdntName;
	}
	public void setIngrdntPrice(String ingrdntPrice) {
		this.ingrdntPrice = ingrdntPrice;
	}
	public void setIngrdntBrand(String ingrdntBrand) {
		this.ingrdntBrand = ingrdntBrand;
	}
	@Override
	public String toString() {
		return "ingredientOfRecipeVO [ingrdntName=" + ingrdntName + ", ingrdntPrice=" + ingrdntPrice + ", ingrdntBrand="
				+ ingrdntBrand + "]";
	}
	
	
}
