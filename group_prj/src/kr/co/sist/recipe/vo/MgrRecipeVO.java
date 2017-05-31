package kr.co.sist.recipe.vo;

public class MgrRecipeVO {
	private String menu_name,img,foodType,info,recipe_info,totalPrice,id,ingrdntName,ingrdntPrice;
	
	public MgrRecipeVO(String menu_name,String img,String foodType,String info,String recipe_info,String totalPrice,String id
			,String ingrdntName,String ingrdntPrice){
		super();
		this.menu_name=menu_name;
		this.img=img;
		this.foodType=foodType;
		this.info=info;
		this.recipe_info=recipe_info;
		this.totalPrice=totalPrice;
		this.id=id;
		this.ingrdntName=ingrdntName;
		this.ingrdntPrice=ingrdntPrice;
	}
	public MgrRecipeVO(){
		
	}
	public String getMenu_name() {
		return menu_name;
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

	public String getRecipe_info() {
		return recipe_info;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public String getId() {
		return id;
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
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
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

	public void setRecipe_info(String recipe_info) {
		this.recipe_info = recipe_info;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setId(String id) {
		this.id = id;
	}


	
	
	
	
	
	
	
	
}
