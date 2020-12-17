package com.wesure.maxk.bean;

public class Solution {
     private SameEffectStruct[] byEffect;
     private SameEffectStruct unkonw;
     private int[] deta;
     private Row[] rows;
     private int minEffect=Integer.MAX_VALUE;
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
	public int[] getDeta() {
		return deta;
	}
	public void setDeta(int[] deta) {
		this.deta = deta;
	}
	public int getMinEffect() {
		return minEffect;
	}
	public int getMinUsebleEffect(int turn) {
		int min=this.minEffect;
		while (min<deta.length) {
			
			for (int i=0;i<this.getByEffect()[min].getColumns().size();i++) {
				    if (Math.abs(this.getByEffect()[min].getColumns().get(i).moveTurn-turn)>6)
				    	
				    	return min;
				    	}
			
			min++;
			
		}
		return -1;
	}
	public void setMinEffect(int minEffect) {
		this.minEffect = minEffect;
	}
	
	
     
}
