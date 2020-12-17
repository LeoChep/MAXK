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
	 * @param k 选择k个列
	 * 
	 * @param m 一共有m行
	 * 
	 * @return 返回一个初始解
	 */
	public Solution getRawSolution() {
		int n = getAllColumns().length;
		Solution raw = new Solution();
		raw.setByEffect(new SameEffectStruct[getM() + 1]);
		for (int i = 0; i < getM() + 1; i++) {
			raw.getByEffect()[i] = new SameEffectStruct();
		} // 生成解的内容结构
		raw.setDeta(new int[getM() + 1]);
		raw.setUnkonw(new SameEffectStruct());// 生成初始解的容器，此时为空
		this.antiSolution = new AntiSolution();
		this.antiSolution.setByEffect(new SameEffectStruct[getM() + 1]);
		for (int i = 0; i < getM() + 1; i++) {
			this.antiSolution.getByEffect()[i] = new SameEffectStruct();
		} // 生成非解的内容结构
		this.antiSolution.setUnkonw(new SameEffectStruct());// 生成初始非解的容器，此时为空

		boolean[] chosen = new boolean[n];// 生成标识器
		for (int i = 0; i < n; i++) {
			chosen[i] = false;
			// raw.getDeta()[i] = 0;
		}
		for (int i = 0; i < getM(); i++)
			raw.getDeta()[i] = 0;
		chosen[0] = true;
		int random = 0;
		while (k > 0) {

			random = 1 + (int) (Math.random() * (n + 1 - 1));// 生成1-n的随机数
			if (!chosen[random - 1]) {
				chosen[random - 1] = true;
				raw.getUnkonw().getColumns().add(allColumns[random - 1]);// 因为n获取的是长度，故要-1，为标签最大值+1，故-1；
				allColumns[random - 1].setPick(true);;
				for (int i = 0; i < allColumns[random - 1].getIsConverRow().size(); i++) {
					// System.out.println( raw.getDeta().length);
					// System.out.println( getAllColumns().length);

					Object o = getAllColumns()[random - 1].getIsConverRow().get(i).getLebel();
					// System.out.println( (int) o);
					raw.getDeta()[(int) o]++;
				} // 需要重写add方法，此为测试
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

		// 重新调整解的结构
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

		} // 重新调整非解的结构
	}

	private void trans() {

		int gain_a = getSolution().getMinUsebleEffect(sa.allCount);// 计入a的影响值，并移除a
		if (gain_a == -1) {// 寻找不到可移动列,退出本次交换
			getInstruct(0);
			return;
		}
		int n = getSolution().getByEffect()[gain_a].getColumns().size();
		int random = 0 + (int) (Math.random() * (n + 1 - 1));
//		System.out.println(getSolution().getByEffect()[gain_a].getColumns().size());
		Column outColumn = getSolution().getByEffect()[gain_a].getColumns().get(random);
		getSolution().getByEffect()[gain_a].getColumns().remove(random);// A出列
		this.reconsit();//重新调整解结构
		for (int i = 0; i < outColumn.getIsConverRow().size(); i++)//remove时要修改解的覆盖情况
			getSolution().getDeta()[outColumn.getIsConverRow().get(i).getLebel()]--;// 需要重写remove方法，此为测试
		int gain_b = antiSolution.getMaxUsebleEffect(sa.allCount);// 计算b的影响值
		if (gain_b == -1) {// 寻找不到可移动列
			getInstruct(0);
			this.getSolution().getUnkonw().getColumns().add(outColumn);
			this.reconsit();
			for (int i = 0; i < outColumn.getIsConverRow().size(); i++)//remove时要修改解的覆盖情况
				getSolution().getDeta()[outColumn.getIsConverRow().get(i).getLebel()]++;// 需要重写remove方法，此为测试
			return;
		}
		

		
		if (getInstruct(gain_b - gain_a)) {// 如果符合条件，则进行交换，否则复原
			this.antiSolution.getUnkonw().getColumns().add(outColumn);//将A加入非解
			outColumn.setMoveTurn(sa.allCount);// 写入交换回合
			{n = this.antiSolution.getByEffect()[gain_b].getColumns().size();
			random = 0 + (int) (Math.random() * (n + 1 - 1));
			Column inColumn = this.antiSolution.getByEffect()[gain_b].getColumns().get(random);
			inColumn.setMoveTurn(sa.allCount);//写入交换回合
			getSolution().getUnkonw().getColumns().add(inColumn);
			this.antiSolution.getByEffect()[gain_b].getColumns().remove(random);
			for (int i = 0; i < inColumn.getIsConverRow().size(); i++)
				getSolution().getDeta()[inColumn.getIsConverRow().get(i).getLebel()]++;// 需要重写add方法，此为测试
			}
			//随机抽取B，将B出列并加入解中
			
			this.reconsit();// 重新调整解结构
			
		} // 如果符合条件，则完成交换
		else {// 不符合条件，还原
			this.getSolution().getUnkonw().getColumns().add(outColumn);
			this.reconsit();
			for (int i = 0; i < outColumn.getIsConverRow().size(); i++)// 复原deta
				getSolution().getDeta()[outColumn.getIsConverRow().get(i).getLebel()]++;// 需要重写ADD方法，此为测试

		}
	}

	private int antiEvaluet(Column column) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < column.getIsConverRow().size(); i++)
			if (getSolution().getDeta()[column.getIsConverRow().get(i).getLebel()] == 0)
				count++;// 找到0的个数就是非解中列的影响力
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
				count++;// 找到1的个数就是解中列的影响力
		if (count < this.getSolution().getMinEffect())
			this.getSolution().setMinEffect(count);
		column.setEvaluetion(count);
		return count;
	}

	public void solve() {
		sa = new SA();
		flag = true;
		while (sa.T>=0.01) { // 运算指示器，需要使用退火指示器代替

			trans();
		}

	}

	public static void main(String[] args) {

		int n = 10;
		Column[] allColumns = new Column[n + 1];
		for (int i = 0; i <= n; i++) {
			allColumns[i] = new Column();
			allColumns[i].setLebel(i);
		} // 生成初始列
		RowReader rd = RowReader.consist(allColumns);// 生成读取器,来读取行信息，并生成列

		for (int k = 1; k <= 5; k++) {
			int[] columnLebels = { 1 + (int) (Math.random() * (n + 1 - 1)) };// 生成随机行数据
			System.out.println(k + "is covered by" + columnLebels[0]);// 打印
			rd.read(columnLebels);// 读取行

		}
		for (int i = 0; i <= n; i++)
			for (int j = 0; j < allColumns[i].getIsConverRow().size(); j++)
				System.out.println(i + " cover " + allColumns[i].getIsConverRow().get(j).getLebel() + " ");// 打印数据

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
