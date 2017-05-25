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
	
}
