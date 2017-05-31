package kr.co.sist.recipe.vo;

public class MgrRecipeInfoVO {
	private ShowIngdntVO siv;
	private MgrRecipeVO mrv;
	
	public MgrRecipeInfoVO(ShowIngdntVO siv,MgrRecipeVO mrv){
		super();
		this.siv=siv;
		this.mrv=mrv;
	}

	public ShowIngdntVO getSiv() {
		return siv;
	}

	public MgrRecipeVO getMrv() {
		return mrv;
	}

	public void setSiv(ShowIngdntVO siv) {
		this.siv = siv;
	}

	public void setMrv(MgrRecipeVO mrv) {
		this.mrv = mrv;
	}
	
}
