package com.wesure.maxk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.wesure.maxk.bean.Column;
import com.wesure.maxk.builders.RowReader;
import com.wesure.maxk.builders.SolutionBuilder;

public class Read {
	static ArrayList<Integer> numbers=new ArrayList<>();

	public static void readTxt(String filePath) throws IOException {
		String string = null;
		try {
			// 在给定从中读取数据的文件名的情况下创建一个新 FileReader
			FileReader fr = new FileReader(filePath);

			// 创建一个使用默认大小输入缓冲区的缓冲字符输入流
			BufferedReader br = new BufferedReader(fr);

			while (null != (string = br.readLine())) {
				String[] numbersArray = string.split(" ");
				//System.out.println(numbersArray.length);
				for (int i = 1; i < numbersArray.length; i++) {
					//System.out.println(numbersArray[i]);
					//System.out.println(i);
					numbers.add(Integer.valueOf(numbersArray[i]));
				}
				//System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "G:\\scpcyc08.txt";
		readTxt(filePath);
		int at = 0;
		int n = numbers.get(at);
		at++;
		int m = numbers.get(at);
		Column[] allColumns = new Column[m + 1];
		for (int i = 0; i <= m; i++) {
			allColumns[i] = new Column();
			allColumns[i].setLebel(i);
			at++;
		} // 生成初始列
       System.out.println(numbers.get(at));
		RowReader rd = RowReader.consist(allColumns);// 生成读取器

		for (int i = 1; i <= n; i++) {
			int num = numbers.get(at);
			at++;
			int[] columnLebels = new int[num];
			for (int j = 0; j < num; j++) {
				columnLebels[j] = numbers.get(at);
				at++;
			} // 读取行数据

			System.out.println(i + "is covered by" + columnLebels[0]);// 打印
			rd.read(columnLebels);// 读取行

		}
		int time=0;
		int max=0;
		int [] results=new int[10000];
		while (time<1) {
			time++;
		
		SolutionBuilder sb = new SolutionBuilder();
		sb.setK(308);
		sb.setM(n);
		sb.setAllColumns(allColumns);
		sb.setRows(rd.getRows());
		sb.getRawSolution();
		sb.reconsit();
	/*	for (int i = 1; i <= m; i++)
			System.out.print(sb.getSolution().getDeta()[i] + " ");
		System.out.println(); // sb.trans();
		*/
		
		System.out.println(new Date().getTime());
		sb.solve();
		System.out.println(new Date().getTime());
		
		/*for (int i = 1; i <= m; i++)
			System.out.print(sb.getSolution().getDeta()[i] + " ");
		System.out.println();
		*/
		int result=0;
		/*for (int i = 0; i <= m; i++)
			for (int j = 0; j < sb.getSolution().getByEffect()[i].getColumns().size(); j++) {
				System.out.print(sb.getSolution().getByEffect()[i].getColumns().get(j).getLebel() + " ");
			}
		*/
		for (int i=1;i<=n;i++)
			if (sb.getSolution().getDeta()[i]!=0)
				result++;
		System.out.println(result);
		/*results[time]=result;
		int avg=0;
		for (int i=1;i<1000;i++)
			avg+=results[i];
		   avg/=1000;
		   System.out.println(avg);
	System.out.println();
	
		if (result>max) max=result;
		System.out.println(time);*/
	}
		System.out.println(max);
	}

}
