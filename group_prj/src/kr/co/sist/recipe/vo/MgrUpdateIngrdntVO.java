package kr.co.sist.recipe.vo;

public class MgrUpdateIngrdntVO {
	private String img,foodType,info,recipeInfo;
	private int totalPrice;
	
	public MgrUpdateIngrdntVO(){
		
	}
	public MgrUpdateIngrdntVO(String img,String foodType,String info,String recipeInfo,int totalPrice){
		this.img=img;
		this.foodType=foodType;
		this.info=recipeInfo;
		this.totalPrice=totalPrice;
	}
	public String getImg() {
		return img;
	}
	public String getFoodType() {
		return foodType;
	}
	public String getInfo() {
		return info;
	}
	public String getRecipeInfo() {
		return recipeInfo;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public void setRecipeInfo(String recipeInfo) {
		this.recipeInfo = recipeInfo;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
