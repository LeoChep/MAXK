package com.wesure.maxk.builders;

import com.wesure.maxk.bean.Column;

public class ColumnsBuilder {
       Column[] columns;
       public static ColumnsBuilder consit(int num) {
    	   ColumnsBuilder cb=new ColumnsBuilder();
    	  cb.columns=new Column[num];
    	  return cb;
    	  
       }
}
