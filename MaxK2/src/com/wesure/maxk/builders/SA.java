package com.wesure.maxk.builders;

public class SA {
	static int runTime = 150;
	double T = 1;
	double gama = 0.95;
	double random = 0;
	double check = 0;
	int count = 0;
	int allCount = 0;

	public void countRun() {
          allCount++;
          count++;
          if (count>=runTime) {
        	  count-=runTime;
        	  T=T*gama;
          }

	}

	public boolean getInstruct(double value) {
            countRun();
		if (value >= 0) {
			return true;
		} else {//如果影响值变小
			
				random = Math.random();
				value = -value;
				if (T * T + value * value > 0)
					this.check = T / (T * T + value * value);
				else
					check = 1;

				if (check < random)
					return true;//接受差的解
				else
					return false;//不接受差的解

			}
		}
	
}
