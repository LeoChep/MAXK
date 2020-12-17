package com.wesure.maxk.bean;

public class AntiSolution {
     SameEffectStruct[] byEffect;
     SameEffectStruct unkonw;
     private int maxEffect=Integer.MIN_VALUE;
	public SameEffectStruct[] getByEffect() {
		return byEffect;
	}
	public void setByEffect(SameEffectStruct[] byEffect) {
		this.byEffect = byEffect;
	}
	public SameEffectStruct getUnkonw() {
		return unkonw;
	}
	public void setUnkonw(SameEffectStruct unkonw) {
		this.unkonw = unkonw;
	}
	public int getMaxEffect() {
		return maxEffect;
	}
	public int getMaxUsebleEffect(int turn) {
		int max=this.maxEffect;
		while (max>0) {
			
			for (int i=0;i<this.getByEffect()[max].getColumns().size();i++) {
				    if (Math.abs(this.getByEffect()[max].getColumns().get(i).moveTurn-turn)>6)
				    	
				    	return max;
				    	}
			
			max--;
			
		}
		return -1;
	}
	public void setMaxEffect(int maxEffect) {
		this.maxEffect = maxEffect;
	}
	
}
