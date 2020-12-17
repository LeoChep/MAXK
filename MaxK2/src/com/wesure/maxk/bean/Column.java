package com.wesure.maxk.bean;

import java.util.ArrayList;

public class Column {
     ArrayList<Row> isConverRow=new ArrayList<Row>();
     int moveTurn =-6;
     int weight=1;
     int lebel=0;
     int evaluetion=0;
     boolean isPick=false;
	public ArrayList<Row> getIsConverRow() {
		return isConverRow;
	}
	public void setIsConverRow(ArrayList<Row> isConverRow) {
		this.isConverRow = isConverRow;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getLebel() {
		return lebel;
	}
	public void setLebel(int lebel) {
		this.lebel = lebel;
	}
	public int getMoveTurn() {
		return moveTurn;
	}
	public void setMoveTurn(int moveTurn) {
		this.moveTurn = moveTurn;
	}
	public boolean isPick() {
		return isPick;
	}
	public void setPick(boolean isPick) {
		this.isPick = isPick;
	}
	public int getEvaluetion() {
		return evaluetion;
	}
	public void setEvaluetion(int evaluetion) {
		this.evaluetion = evaluetion;
	}

}
