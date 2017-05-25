package kr.co.sist.recipe.vo;




/**
 * @author 05-25:홍승환
 *	IngrdntSchVO삭제 후 IngrdntCategVO를 만듬
 * 레시피 추가창에서 재료를 검색할때 편의점 종류와 재료의 유형을 가지고 재료가 조회되기 때문임
 *
 */
public class IngrdntCategVO {
	private String brand,ingrdntSort;
	
	public IngrdntCategVO(){
		
	}
	public IngrdntCategVO(String brand,String ingrdntSort){
		super();
		this.brand=brand;
		this.ingrdntSort=ingrdntSort;
	}
	public String getBrand() {
		return brand;
	}
	public String getIngrdntSort() {
		return ingrdntSort;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setIngrdntSort(String ingrdntSort) {
		this.ingrdntSort = ingrdntSort;
	}
	@Override
	public String toString() {
		return "IngrdntCategVO [brand=" + brand + ", ingrdntSort=" + ingrdntSort + "]";
	}
	
}
