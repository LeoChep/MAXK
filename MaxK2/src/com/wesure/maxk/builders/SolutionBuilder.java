package com.wesure.maxk.builders;

import com.wesure.maxk.bean.AntiSolution;
import com.wesure.maxk.bean.Column;
import com.wesure.maxk.bean.Row;
import com.wesure.maxk.bean.SameEffectStruct;
import com.wesure.maxk.bean.Solution;

/*
 * @author CYQ
 * 
 */
public class SolutionBuilder {
	private int k;
	private int m;
	private Column[] allColumns;
	private Row[] rows;
	int time;
	AntiSolution antiSolution;
	private Solution solution;
	SA sa;
	boolean flag = false;

	/*
	 * @param k ѡ��k����
	 * 
	 * @param m һ����m��
	 * 
	 * @return ����һ����ʼ��
	 */
	public Solution getRawSolution() {
		int n = getAllColumns().length;
		Solution raw = new Solution();
		raw.setByEffect(new SameEffectStruct[getM() + 1]);
		for (int i = 0; i < getM() + 1; i++) {
			raw.getByEffect()[i] = new SameEffectStruct();
		} // ���ɽ�����ݽṹ
		raw.setDeta(new int[getM() + 1]);
		raw.setUnkonw(new SameEffectStruct());// ���ɳ�ʼ�����������ʱΪ��
		this.antiSolution = new AntiSolution();
		this.antiSolution.setByEffect(new SameEffectStruct[getM() + 1]);
		for (int i = 0; i < getM() + 1; i++) {
			this.antiSolution.getByEffect()[i] = new SameEffectStruct();
		} // ���ɷǽ�����ݽṹ
		this.antiSolution.setUnkonw(new SameEffectStruct());// ���ɳ�ʼ�ǽ����������ʱΪ��

		boolean[] chosen = new boolean[n];// ���ɱ�ʶ��
		for (int i = 0; i < n; i++) {
			chosen[i] = false;
			// raw.getDeta()[i] = 0;
		}
		for (int i = 0; i < getM(); i++)
			raw.getDeta()[i] = 0;
		chosen[0] = true;
		int random = 0;
		while (k > 0) {

			random = 1 + (int) (Math.random() * (n + 1 - 1));// ����1-n�������
			if (!chosen[random - 1]) {
				chosen[random - 1] = true;
				raw.getUnkonw().getColumns().add(allColumns[random - 1]);// ��Ϊn��ȡ���ǳ��ȣ���Ҫ-1��Ϊ��ǩ���ֵ+1����-1��
				allColumns[random - 1].setPick(true);;
				for (int i = 0; i < allColumns[random - 1].getIsConverRow().size(); i++) {
					// System.out.println( raw.getDeta().length);
					// System.out.println( getAllColumns().length);

					Object o = getAllColumns()[random - 1].getIsConverRow().get(i).getLebel();
					// System.out.println( (int) o);
					raw.getDeta()[(int) o]++;
				} // ��Ҫ��дadd��������Ϊ����
				k--;
			}

		}
		for (int i = 1; i < n; i++)
			if (!chosen[i])
				this.antiSolution.getUnkonw().getColumns().add(getAllColumns()[i]);
		this.setSolution(raw);
		return this.getSolution();
	}

	private boolean getInstruct(int gain) {
		// TODO Auto-generated method stub
		flag = sa.getInstruct(gain);
		return flag;
	}
    public void reconsitByRow(int rowLable) {
    	for (int i=0;i<rows[rowLable].getBeCoverBy().size();i++) {
    		Column column=rows[rowLable].getBeCoverBy().get(i);
    		if (column.isPick()) {
    			int evaluetion = evaluet(column);
    			getSolution().getByEffect()[evaluetion].getColumns().add(column);
    			getSolution().getUnkonw().getColumns().remove(getSolution().getUnkonw().getColumns().get(i));
    		}
    	}
    }
	public void reconsit() {
		getSolution().setMinEffect(Integer.MAX_VALUE);
		this.antiSolution.setMaxEffect(Integer.MIN_VALUE);
		for (int i = 0; i < getSolution().getUnkonw().getColumns().size(); i++) {
			int evaluetion = evaluet(getSolution().getUnkonw().getColumns().get(i));
			getSolution().getByEffect()[evaluetion].getColumns().add(getSolution().getUnkonw().getColumns().get(i));
			getSolution().getUnkonw().getColumns().remove(getSolution().getUnkonw().getColumns().get(i));
			i--;
		} // unkonw
		for (int k = 0; k < getSolution().getByEffect().length; k++) {
			for (int i = 0; i < getSolution().getByEffect()[k].getColumns().size(); i++) {
				int evaluetion = evaluet(getSolution().getByEffect()[k].getColumns().get(i));
				if (evaluetion != k) {
					// System.out.println(getSolution().getByEffect()[k].getColumns().size());
					getSolution().getByEffect()[evaluetion].getColumns()
							.add(getSolution().getByEffect()[k].getColumns().get(i));
					getSolution().getByEffect()[k].getColumns()
							.remove(getSolution().getByEffect()[k].getColumns().get(i));
					i--;
				}
				// System.out.println("evaluet");
			}

		}

		// ���µ�����Ľṹ
		for (int i = 0; i < antiSolution.getUnkonw().getColumns().size(); i++) {
			int evaluetion = antiEvaluet(antiSolution.getUnkonw().getColumns().get(i));
			// System.out.println(evaluetion);
			// System.out.println(antiSolution.getUnkonw().getColumns().get(i).getLebel());
			antiSolution.getByEffect()[evaluetion].getColumns().add(antiSolution.getUnkonw().getColumns().get(i));
			antiSolution.getUnkonw().getColumns().remove(antiSolution.getUnkonw().getColumns().get(i));
			i--;
		}
		for (int k = 0; k < antiSolution.getByEffect().length; k++) {
			for (int i = 0; i < antiSolution.getByEffect()[k].getColumns().size(); i++) {
				int evaluetion = antiEvaluet(antiSolution.getByEffect()[k].getColumns().get(i));
				if (evaluetion != k) {
					// System.out.println(solution.getByEffect()[k].getColumns().size());
					antiSolution.getByEffect()[evaluetion].getColumns()
							.add(antiSolution.getByEffect()[k].getColumns().get(i));
					antiSolution.getByEffect()[k].getColumns()
							.remove(antiSolution.getByEffect()[k].getColumns().get(i));
					i--;
				}
				// System.out.println("evaluet");
			}

		} // ���µ����ǽ�Ľṹ
	}

	private void trans() {

		int gain_a = getSolution().getMinUsebleEffect(sa.allCount);// ����a��Ӱ��ֵ�����Ƴ�a
		if (gain_a == -1) {// Ѱ�Ҳ������ƶ���,�˳����ν���
			getInstruct(0);
			return;
		}
		int n = getSolution().getByEffect()[gain_a].getColumns().size();
		int random = 0 + (int) (Math.random() * (n + 1 - 1));
//		System.out.println(getSolution().getByEffect()[gain_a].getColumns().size());
		Column outColumn = getSolution().getByEffect()[gain_a].getColumns().get(random);
		getSolution().getByEffect()[gain_a].getColumns().remove(random);// A����
		this.reconsit();//���µ�����ṹ
		for (int i = 0; i < outColumn.getIsConverRow().size(); i++)//removeʱҪ�޸Ľ�ĸ������
			getSolution().getDeta()[outColumn.getIsConverRow().get(i).getLebel()]--;// ��Ҫ��дremove��������Ϊ����
		int gain_b = antiSolution.getMaxUsebleEffect(sa.allCount);// ����b��Ӱ��ֵ
		if (gain_b == -1) {// Ѱ�Ҳ������ƶ���
			getInstruct(0);
			this.getSolution().getUnkonw().getColumns().add(outColumn);
			this.reconsit();
			for (int i = 0; i < outColumn.getIsConverRow().size(); i++)//removeʱҪ�޸Ľ�ĸ������
				getSolution().getDeta()[outColumn.getIsConverRow().get(i).getLebel()]++;// ��Ҫ��дremove��������Ϊ����
			return;
		}
		

		
		if (getInstruct(gain_b - gain_a)) {// �����������������н���������ԭ
			this.antiSolution.getUnkonw().getColumns().add(outColumn);//��A����ǽ�
			outColumn.setMoveTurn(sa.allCount);// д�뽻���غ�
			{n = this.antiSolution.getByEffect()[gain_b].getColumns().size();
			random = 0 + (int) (Math.random() * (n + 1 - 1));
			Column inColumn = this.antiSolution.getByEffect()[gain_b].getColumns().get(random);
			inColumn.setMoveTurn(sa.allCount);//д�뽻���غ�
			getSolution().getUnkonw().getColumns().add(inColumn);
			this.antiSolution.getByEffect()[gain_b].getColumns().remove(random);
			for (int i = 0; i < inColumn.getIsConverRow().size(); i++)
				getSolution().getDeta()[inColumn.getIsConverRow().get(i).getLebel()]++;// ��Ҫ��дadd��������Ϊ����
			}
			//�����ȡB����B���в��������
			
			this.reconsit();// ���µ�����ṹ
			
		} // �����������������ɽ���
		else {// ��������������ԭ
			this.getSolution().getUnkonw().getColumns().add(outColumn);
			this.reconsit();
			for (int i = 0; i < outColumn.getIsConverRow().size(); i++)// ��ԭdeta
				getSolution().getDeta()[outColumn.getIsConverRow().get(i).getLebel()]++;// ��Ҫ��дADD��������Ϊ����

		}
	}

	private int antiEvaluet(Column column) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < column.getIsConverRow().size(); i++)
			if (getSolution().getDeta()[column.getIsConverRow().get(i).getLebel()] == 0)
				count++;// �ҵ�0�ĸ������Ƿǽ����е�Ӱ����
		if (count > this.antiSolution.getMaxEffect())
			this.antiSolution.setMaxEffect(count);
		column.setEvaluetion(count);
		return count;
	}

	private int evaluet(Column column) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < column.getIsConverRow().size(); i++)
			if (getSolution().getDeta()[column.getIsConverRow().get(i).getLebel()] == 1)
				count++;// �ҵ�1�ĸ������ǽ����е�Ӱ����
		if (count < this.getSolution().getMinEffect())
			this.getSolution().setMinEffect(count);
		column.setEvaluetion(count);
		return count;
	}

	public void solve() {
		sa = new SA();
		flag = true;
		while (sa.T>=0.01) { // ����ָʾ������Ҫʹ���˻�ָʾ������

			trans();
		}

	}

	public static void main(String[] args) {

		int n = 10;
		Column[] allColumns = new Column[n + 1];
		for (int i = 0; i <= n; i++) {
			allColumns[i] = new Column();
			allColumns[i].setLebel(i);
		} // ���ɳ�ʼ��
		RowReader rd = RowReader.consist(allColumns);// ���ɶ�ȡ��,����ȡ����Ϣ����������

		for (int k = 1; k <= 5; k++) {
			int[] columnLebels = { 1 + (int) (Math.random() * (n + 1 - 1)) };// �������������
			System.out.println(k + "is covered by" + columnLebels[0]);// ��ӡ
			rd.read(columnLebels);// ��ȡ��

		}
		for (int i = 0; i <= n; i++)
			for (int j = 0; j < allColumns[i].getIsConverRow().size(); j++)
				System.out.println(i + " cover " + allColumns[i].getIsConverRow().get(j).getLebel() + " ");// ��ӡ����

		SolutionBuilder sb = new SolutionBuilder();
		sb.setK(3);
		sb.setM(5);
		sb.setAllColumns(allColumns);
		sb.getRawSolution();
		sb.reconsit();
		sb.reconsit();
		// System.out.println(sb.solution.getByEffect()[1].getColumns().size());
		for (int i = 1; i <= 5; i++)
			System.out.print(sb.getSolution().getDeta()[i] + " ");
		System.out.println();
		// sb.trans();
		sb.solve();
		for (int i = 1; i <= 5; i++)
			System.out.print(sb.getSolution().getDeta()[i] + " ");
		System.out.println();
		for (int i = 0; i <= 5; i++)
			for (int j = 0; j < sb.getSolution().getByEffect()[i].getColumns().size(); j++)
				System.out.print(sb.getSolution().getByEffect()[i].getColumns().get(j).getLebel() + " ");
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public Column[] getAllColumns() {
		return allColumns;
	}

	public void setAllColumns(Column[] allColumns) {
		this.allColumns = allColumns;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}



	public Row[] getRows() {
		return rows;
	}

	public void setRows(Row[] rows) {
		this.rows = rows;
	}

}
