package com.wesure.maxk.bean;

import java.util.ArrayList;

public class Row {
      int lebel=0;
      ArrayList<Column> beCoverBy=new ArrayList<Column>();
	public int getLebel() {
		return lebel;
	}

	public void setLebel(int lebel) {
		this.lebel = lebel;
	}

	public ArrayList<Column> getBeCoverBy() {
		return beCoverBy;
	}

	public void setBeCoverBy(ArrayList<Column> beCoverBy) {
		this.beCoverBy = beCoverBy;
	}
}
