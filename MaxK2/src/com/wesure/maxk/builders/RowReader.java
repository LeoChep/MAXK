package com.wesure.maxk.builders;

import java.util.ArrayList;

import com.wesure.maxk.bean.Column;
import com.wesure.maxk.bean.Row;

/*
 * @param count 行数计数器
 * @param allCoulumns 所有的列
 * @param rows 已经加入的行
 */
public class RowReader {
	int count = 0;
	Column[] allColumns;
	Row[] rows;
	int length=0;
	//ArrayList<Row> rows = new ArrayList<Row>();
/*
 * 用于构造行的读取器，输入为解的列的集
 */
	public static RowReader consist(Column[] allColumns) {
		RowReader rd = new RowReader();
		rd.allColumns = allColumns;
		rd.rows=new Row[100000];
		return rd;
	}
/*
 * @param 新的行对象
 */
	public void read(int[] columnLebels) {
		this.count++;
		Row row = new Row();
		row.setLebel(count);
		for (int i = 0; i < columnLebels.length; i++) {
			this.allColumns[columnLebels[i]].getIsConverRow().add(row);
       //rows.add(row);
			length++;
			rows[length]=row;
			row.getBeCoverBy().add(this.allColumns[columnLebels[i]]);
       }
	}

	public static void main(String[] args) {
		int n = 10;
		Column[] allColumns = new Column[n + 1];
		for (int i = 0; i <= n; i++) {
			allColumns[i] = new Column();
			allColumns[i].setLebel(i + 1);
		}//生成初始列
		RowReader rd = RowReader.consist(allColumns);//生成读取器

		for (int k = 1; k <= 5; k++) {
			int[] columnLebels = { 1 + (int) (Math.random() * (n + 1 - 1))};//生成随机行数据
			System.out.println(k + "is covered by" + columnLebels[0]);//打印
			rd.read(columnLebels);//读取行

		}
		for (int i = 0; i <= n; i++)
			for (int j = 0; j < allColumns[i].getIsConverRow().size(); j++)
				System.out.println(i+ " cover " + allColumns[i].getIsConverRow().get(j).getLebel() + " ");//打印数据
	}
	public Row[] getRows() {
		return rows;
	}
	public void setRows(Row[] rows) {
		this.rows = rows;
	}
}
