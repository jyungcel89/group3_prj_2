package kr.co.sist.recipe.vo;

import java.util.List;

public class MgrRecipeInfoVO {
	private List<ShowIngdntVO> siv;
	private MgrRecipeVO mrv;
	
	public MgrRecipeInfoVO(List<ShowIngdntVO> siv,MgrRecipeVO mrv){
		super();
		this.siv=siv;
		this.mrv=mrv;
	}
	public MgrRecipeInfoVO(){
		
	}
	public List<ShowIngdntVO> getSiv() {
		return siv;
	}

	public MgrRecipeVO getMrv() {
		return mrv;
	}

	public void setSiv(List<ShowIngdntVO> siv) {
		this.siv = siv;
	}

	public void setMrv(MgrRecipeVO mrv) {
		this.mrv = mrv;
	}
	
}
