package MarkovLogicNetwork;

import intro.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RandomVariableData {
	private String columnName = "";
	private ArrayList<Double> columnValues;
	private ArrayList<Double> columnValuesSorted;
	private ArrayList<Double> columnValuesRanked;
	
	public RandomVariableData(String columnName)
	{
		this.columnName = columnName;
		columnValues = new ArrayList<Double>();
		columnValuesSorted = new ArrayList<Double>();
		columnValuesRanked = new ArrayList<Double>();
	}
	
	public String getColumnName()
	{
		return columnName;
	}
	
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	
	public ArrayList<Double> getColumnValues()
	{
		return columnValues;
	}
	
	public void addValue(Double val)
	{
		columnValues.add(val);
	}
	
	public ArrayList<Double> getColumnValuesSorted()
	{
		return columnValuesSorted;
	}
	
	public void setColumnValuesSorted()
	{
		for (Double val : columnValues)
			columnValuesSorted.add(val);
		
		Collections.sort(columnValuesSorted);
	}
	
	public ArrayList<Double> getColumnValuesRanked()
	{
		return columnValuesRanked;
	}
	
	public void setColumnValuesRanked()
	{
		setColumnValuesSorted();
//		for (Double val : columnValues)
//		{
//			columnValuesRanked.add((double)(columnValuesSorted.indexOf(val)+1));
//		}
		for (int index = 0; index < columnValues.size(); index++) {

			double val = columnValues.get(index);

			int i = columnValuesSorted.indexOf(val); // get index from the sorted list, if 1, 1, 1, always gives first index
			double sum = columnValuesSorted.indexOf(val) + 1; // numerator to find avg
			double count = columnValuesSorted.indexOf(val) + 1;
			double k = 1.0; // denominator to find avg
			while (i + 1 < columnValuesSorted.size()
					&& (double) columnValuesSorted.get(i) == (double) columnValuesSorted
							.get(i + 1)) {
				k++;
				count++;
				sum += count;
				i++;

			}

			if (k > 1) {
				double avg = sum / k;
				columnValuesRanked.add(avg);
			} else {
				columnValuesRanked.add((double) (columnValuesSorted.indexOf(val) + 1));
			}

		}
	}
	
	public void printColumn()
	{
		System.out.println("Random Variable Name: " + this.columnName + "\t" +
							this.columnValues );
		System.out.println("Random Variable Name (Ranked): " + this.columnName + "\t" +
				this.columnValuesRanked );
	}
	
	public boolean dependsOn(RandomVariableData Y)
	{
		// to calculation Spearman's Correlation between X and Y
		// X = this.columnValues.size()
		
		double[] d = new double[this.columnValues.size()];
		for (int i = 0; i < d.length; i++)
		{
			d[i] = this.columnValuesRanked.get(i) - Y.columnValuesRanked.get(i);
		}
		double[] d2 = new double[d.length];
		for (int i = 0; i < d2.length; i++)
		{
			d2[i] = Math.pow(d[i], 2);
		}
		double sum_d2 = 0;
		for (int i = 0; i < d2.length; i++)
		{
			sum_d2 += d2[i];
		}
		//               6 * Sum(d²)
		// rho = 1  -   -------------
		//              N * (N²-1) 
		double N = Y.columnValues.size();
		double rho = 1 - ((6 * sum_d2) / (N * (Math.pow(N, 2) - 1)));
		// http://psych.unl.edu/psycrs/handcomp/hcspear.PDF
		// H0: X and Y are independent
		// H1: X and Y are dependent (have a correlation)
		int df = (int) N; // degree of freedom
		if (df >= 0 && df < 5)
			df = 5;
		else if (df > 30 && df < 35)
			df = 35;
		else if (df > 35 && df < 40)
			df = 40;
		else if (df > 40 && df < 45)
			df = 45;
		else if (df > 45 && df < 50)
			df = 50;
		else if (df > 50 && df < 55)
			df = 55;
		else if (df > 55 && df < 60)
			df = 60;
		else if (df > 60 && df < 65)
			df = 65;
		else if (df > 65 && df < 70)
			df = 70;
		else if (df > 70 && df < 75)
			df = 75;
		else if (df > 75 && df < 80)
			df = 80;
		else if (df > 80 && df < 85)
			df = 85;
		else if (df > 85 && df < 90)
			df = 90;
		else if (df > 90)
			df = 99999; // inf
			

		double criticalPoint = 
				config.SPEARMANs_CORRELATION_CRITICAL_VALUES.get(df)[config.SPEARMANs_CORRELATION_P_VALUE];
		System.out.println("rho = " + rho + " \t critical point " + criticalPoint);
		if (Math.abs(rho) > criticalPoint)
			return true; // reject null hypothesis so X and Y are dependent (have a correlation)
		
		return false;
	}
	
	// Avg
	public double getColumnValuesAverage()
	{
		double sum = 0;
		
		for (double value : columnValues)
			sum += value;
		
		return sum / (double)columnValues.size();
	}
	
	// summation
	public double getColumnValuesSum()
	{
		double sum = 0;
		
		for (double value : columnValues)
			sum += value;
		
		return sum;
	}
	
	// Max
	public double getColumnValuesMax()
	{
		double max = Double.MIN_VALUE;
		
		for (double value : columnValues)
			if (value > max)
				max = value;
		
		return max;
	}
	
	// Min
	public double getColumnValuesMin()
	{
		double min = Double.MAX_VALUE;
		
		for (double value : columnValues)
			if (value < min)
				min = value;
		
		return min;
	}
	
	// median
	public double getColumnValuesMedian()
	{
		// sort
		setColumnValuesSorted();
		
		int n = columnValuesSorted.size();
		int medianIndex = 0;
		double median = 0;
		
		if (n % 2 == 1) // odd
		{
			medianIndex = (n + 1)/2;
			median = columnValuesSorted.get(medianIndex - 1);
		}
		else // even
		{
			medianIndex = (n/2); 
			median = (columnValuesSorted.get(medianIndex-1) + columnValuesSorted.get(medianIndex-1+1)) / 2.0;	// avg
		}
	
		return median;
	}
	
	
}







































































