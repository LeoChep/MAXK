package com.wesure.maxk.builders;

import java.util.ArrayList;

import com.wesure.maxk.bean.Column;
import com.wesure.maxk.bean.Row;

/*
 * @param count ����������
 * @param allCoulumns ���е���
 * @param rows �Ѿ��������
 */
public class RowReader {
	int count = -1;
	Column[] allColumns;
	Row[] rows;
	int length = -1;

	// ArrayList<Row> rows = new ArrayList<Row>();
	/*
	 * ���ڹ����еĶ�ȡ��������Ϊ����еļ�
	 */
	public static RowReader consist(Column[] allColumns) {
		RowReader rd = new RowReader();
		rd.allColumns = allColumns;
		rd.rows = new Row[100000];
		return rd;
	}

	/*
	 * @param �µ��ж���
	 */
	public void read(int[] columnLebels) {
		this.count++;
		length++;
		Row row = new Row();
		row.setLebel(count);
		rows[length] = row;
		for (int i = 0; i < columnLebels.length; i++) {
			this.allColumns[columnLebels[i]].getIsConverRow().add(row);
			row.getBeCoverBy().add(this.allColumns[columnLebels[i]]);
			System.out.println("row " + count + "is covered by" + columnLebels[i]);
		}
		// rows.add(row);

	}

	public static void main(String[] args) {
		int n = 10;
		Column[] allColumns = new Column[n + 1];
		for (int i = 0; i <= n; i++) {
			allColumns[i] = new Column();
			allColumns[i].setLebel(i + 1);
		} // ���ɳ�ʼ��
		RowReader rd = RowReader.consist(allColumns);// ���ɶ�ȡ��

		for (int k = 1; k <= 5; k++) {
			int[] columnLebels = { 1 + (int) (Math.random() * (n + 1 - 1)), 1 + (int) (Math.random() * (n + 1 - 1)) };// �������������
			System.out.println(k + "is covered by" + columnLebels[0]);// ��ӡ
			System.out.println(k + "is covered by" + columnLebels[1]);// ��ӡ
			rd.read(columnLebels);// ��ȡ��

		}
		for (int i = 0; i <= n; i++)
			for (int j = 0; j < allColumns[i].getIsConverRow().size(); j++)
				System.out.println(i + " cover " + allColumns[i].getIsConverRow().get(j).getLebel() + " ");// ��ӡ����
	}

	public Row[] getRows() {
		return rows;
	}

	public void setRows(Row[] rows) {
		this.rows = rows;
	}
}
