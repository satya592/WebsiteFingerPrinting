package MarkovLogicNetwork;

import intro.Utils;
import intro.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class SpearmansCorrelation {
	// Nov 6, 2014
	// Spearman Correlation
	private static HashMap<String, Integer> correlationStats = new HashMap<String,Integer>();
	
//	private static HashMap<String, Integer> correlationStatsAgg = new HashMap<String,Integer>();
	
	// Output file name
	private static List<String> outputFilenameArray = new ArrayList<String>() {
		{
			add("results");
			add("k" + config.BUCKET_SIZE);
			add("c" + config.COUNTERMEASURE);
			add("d" + config.DATA_SOURCE);
			add("C" + config.CLASSIFIER);
			add("N" + config.TOP_N);
			add("t" + config.NUM_TRAINING_TRACES);
			add("T" + config.NUM_TESTING_TRACES);
		}
	};

	private static String outputFilename = new File(config.CORRELATION_DIR, Utils.Join(
			outputFilenameArray, ".")).toString();
	
	public static void readArff(String file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String strLine = "";
		ArrayList<RandomVariableData> columnsList = new ArrayList<RandomVariableData>();
		
		while ((strLine = reader.readLine()) != null) 
		{
			if (strLine.startsWith("@ATTRIBUTE"))
			{
				String[] lineArray = strLine.split(" ");
				if (!lineArray[1].equals("class"))
					columnsList.add(new RandomVariableData(lineArray[1])); // Attribute name
			}
			
			if (!strLine.startsWith("@"))
			{
				String[] instanceValues = strLine.split(",");
				for (int i = 0; i < instanceValues.length - 1; i++) // excluding class
				{
					columnsList.get(i).addValue(Double.parseDouble(instanceValues[i]));
				}
			}
		}
		
		
		// set the ranks for all columns (Random Variables)
		for (RandomVariableData rv : columnsList)
		{
//			set rank for each column
			rv.setColumnValuesRanked();
			//rv.printColumn();
		}
		
		correlationStats.clear(); // From previous runs
		// check Spearman's correlation
		for (int i =0; i < columnsList.size(); i++)
		{
//			
			for (int j = i+1; j < columnsList.size(); j++)
			{
				RandomVariableData X = columnsList.get(i);
				RandomVariableData Y = columnsList.get(j);
				if (X.dependsOn(Y))
				{
					String xyKey = X.getColumnName() + " ^ " + Y.getColumnName(); // X ^ Y is same as Y ^ X
					String yxKey = Y.getColumnName() + " ^ " + X.getColumnName();
					
					if (correlationStats.get(yxKey) != null) // Y ^ X already exist
						correlationStats.put(yxKey, correlationStats.get(yxKey) + 1);
					else 
					{
						if (correlationStats.get(xyKey) == null)
							correlationStats.put(xyKey, 0);
							
						correlationStats.put(xyKey, correlationStats.get(xyKey) + 1);
							//System.out.println(columnsList.get(i).getColumnName() + " and " + columnsList.get(j).getColumnName() + " have a correlation.");
					}
					
				}
					
		
			}
		}
		
		//System.out.println(correlationStats);
		//outputFilename = outputFilename + ".NoAgg";
		
		File fOut = new File(outputFilename + ".NoAgg.correlation");
		if (!fOut.exists()) {
			List<String> banner = new ArrayList<String>() {
				{
					add("Sample size");
					add("#Nodes");
					add("#Edges");
					
//					add("rho");
//					add("critical point");
				}
			};
			PrintWriter write = new PrintWriter(new File(outputFilename
					+ ".NoAgg.correlation"));
			write.println(Utils.Join(banner, ","));
			write.close();
		}
		
		List<String> output = new ArrayList<String>();
		output.add(columnsList.get(0).getColumnValues().size() + ""); // sample size
		output.add(columnsList.size() + ""); // "Feature Set Size (#Nodes): "
		output.add(correlationStats.size() + ""); // "Correlaton Set Size (#Edges): "
		//output.add("Correlaton Set = " + correlationStats.toString());
		
		String summary = Utils.Join(output, ",");
		
		PrintWriter writeOutput = new PrintWriter(new BufferedWriter(
				new FileWriter(outputFilename + ".NoAgg.correlation", true))); // append mode
		writeOutput.println(summary);
		writeOutput.close();
				
	}
	
	// Finding correlation for website aggregated columns
	public static void readArffWithAggregation(String file, String aggregationType) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String strLine = "";
		
		ArrayList<WebpageRandomVariables> webpageRVList = new ArrayList<WebpageRandomVariables>();
		
		ArrayList<RandomVariableData> columnsList = new ArrayList<RandomVariableData>(); // all features=columns
		ArrayList<RandomVariableData> columnsListWebpage = new ArrayList<RandomVariableData>(); // all features=columns per website
		ArrayList<RandomVariableData> columnsListAgg = new ArrayList<RandomVariableData>();
		
		String currWebpage = "";
		String nextWebpage = "";
		
		
		while ((strLine = reader.readLine()) != null) 
		{
			if (strLine.startsWith("@ATTRIBUTE"))
			{
				String[] lineArray = strLine.split(" ");
				if (!lineArray[1].equals("class"))
				{
					columnsList.add(new RandomVariableData(lineArray[1])); // Attribute name
					columnsListWebpage.add(new RandomVariableData(lineArray[1]));
					columnsListAgg.add(new RandomVariableData(lineArray[1]));
				} else
				{
					String classes = lineArray[2];
					StringTokenizer stok = new StringTokenizer(classes, ",{}");
					if (stok.hasMoreTokens())
						currWebpage = stok.nextToken(); 
//					String classesList[] = classes.split("[{,}]"); // {class1,class2, ...}
//					int classesCtr = 0;
//					currWebpage = classesList[classesCtr].trim(); // one time, first webpage name
				}
			}
			
			if (!strLine.startsWith("@"))
			{
				String[] instanceValues = strLine.split(",");
				nextWebpage = instanceValues[instanceValues.length - 1]; // class name
				
				
				if (!currWebpage.equals(nextWebpage))
				{
					WebpageRandomVariables wprvs = new WebpageRandomVariables(currWebpage, columnsListWebpage);
					webpageRVList.add(wprvs);

					// clear and initialize
					columnsListWebpage = new ArrayList<RandomVariableData>();
					for (RandomVariableData rv : columnsList)
						columnsListWebpage.add(new RandomVariableData(rv.getColumnName()));
					currWebpage = nextWebpage;
				} //else
				{
					for (int i = 0; i < instanceValues.length - 1; i++) // excluding class
					{
						columnsList.get(i).addValue(Double.parseDouble(instanceValues[i]));
						columnsListWebpage.get(i).addValue(Double.parseDouble(instanceValues[i]));
					}
				}
				
			}
		}
		// For last website
		WebpageRandomVariables wprvs = new WebpageRandomVariables(currWebpage, columnsListWebpage);
		webpageRVList.add(wprvs);
		columnsListWebpage = new ArrayList<RandomVariableData>(); // clear
		

		for (WebpageRandomVariables wprv : webpageRVList)
		{
			columnsListWebpage = wprv.getColumnsListWebpage(); // get all columns for each webpage
			for (int i = 0; i < columnsListWebpage.size(); i++)// rv : wprv.getColumnsListWebpage())
			{
				if (aggregationType.equals("avg"))
					columnsListAgg.get(i).addValue(columnsListWebpage.get(i).getColumnValuesAverage());
				else if (aggregationType.equals("sum"))
					columnsListAgg.get(i).addValue(columnsListWebpage.get(i).getColumnValuesSum());
				else if (aggregationType.equals("max"))
					columnsListAgg.get(i).addValue(columnsListWebpage.get(i).getColumnValuesMax());
				else if (aggregationType.equals("min"))
					columnsListAgg.get(i).addValue(columnsListWebpage.get(i).getColumnValuesMin());
				else if (aggregationType.equals("median"))
					columnsListAgg.get(i).addValue(columnsListWebpage.get(i).getColumnValuesMedian());
			}
		}
		
		
		// set the ranks for all columns (Random Variables)
		for (RandomVariableData rv : columnsListAgg)
		{
//			set rank for each column
			rv.setColumnValuesRanked();
//			switch (aggregationType)
//			{
//				case "avg":
//					System.out.println(".avg.");
//					break;
//				case "sum":
//					System.out.println(".sum.");
//					break;
//				case "max":
//					System.out.println(".max.");
//					break;
//				case "min":
//					System.out.println(".min.");
//					break;
//				case "median":
//					System.out.println(".median.");
//					break;
//			}
			//rv.printColumn();
		}
		
		correlationStats.clear(); // From previous runs
		// check Spearman's correlation
		for (int i =0; i < columnsListAgg.size(); i++)
		{
			// Debug
			switch (aggregationType)
			{
				case "avg":
					System.out.println(".avg.");
					break;
				case "sum":
					System.out.println(".sum.");
					break;
				case "max":
					System.out.println(".max.");
					break;
				case "min":
					System.out.println(".min.");
					break;
				case "median":
					System.out.println(".median.");
					break;
			}
			//columnsListAgg.get(i).printColumn();
			// End Debug
			for (int j = i+1; j < columnsListAgg.size(); j++)
			{
				RandomVariableData X = columnsListAgg.get(i);
				RandomVariableData Y = columnsListAgg.get(j);
				
				// skip if one of the variables avg is < a value (as some of the columns may have zeros)
				if (X.getColumnValuesAverage() < 3 || Y.getColumnValuesAverage() < 3)
					break;
				
				if (X.dependsOn(Y))
				{
					String xyKey = X.getColumnName() + " ^ " + Y.getColumnName(); // X ^ Y is same as Y ^ X
					String yxKey = Y.getColumnName() + " ^ " + X.getColumnName();
					
					if (correlationStats.get(yxKey) != null) // Y ^ X already exist
						correlationStats.put(yxKey, correlationStats.get(yxKey) + 1);
					else 
					{
						if (correlationStats.get(xyKey) == null)
							correlationStats.put(xyKey, 0);
							
						correlationStats.put(xyKey, correlationStats.get(xyKey) + 1);
						//System.out.println(columnsList.get(i).getColumnName() + " and " + columnsList.get(j).getColumnName() + " have a correlation.");
					}
					
					// 
					if (config.correlationStatsAgg.get(yxKey) != null) // Y ^ X already exist
						config.correlationStatsAgg.put(yxKey, config.correlationStatsAgg.get(yxKey) + 1);
					else 
					{
						if (config.correlationStatsAgg.get(xyKey) == null)
							config.correlationStatsAgg.put(xyKey, 0);
							
						config.correlationStatsAgg.put(xyKey, config.correlationStatsAgg.get(xyKey) + 1);
						//System.out.println(columnsList.get(i).getColumnName() + " and " + columnsList.get(j).getColumnName() + " have a correlation.");
					}
					
				}
					
		
			}
		}
		
		//System.out.println("Correlation Stats: " + correlationStats);
		String fileExt = "";
		switch (aggregationType)
		{
			case "avg":
				fileExt = ".avg.correlation";
				break;
			case "sum":
				fileExt = ".sum.correlation";
				break;
			case "max":
				fileExt = ".max.correlation";
				break;
			case "min":
				fileExt = ".min.correlation";
				break;
			case "median":
				fileExt = ".median.correlation";
				break;
		}
			
		File fOut = new File(outputFilename + fileExt);
		if (!fOut.exists()) {
			List<String> banner = new ArrayList<String>() {
				{
					add("Sample size");
					add("#Nodes");
					add("#Edges");
					
//					add("rho");
//					add("critical point");
				}
			};
			PrintWriter write = new PrintWriter(new File(outputFilename
					+ fileExt));
			write.println(Utils.Join(banner, ","));
			write.close();
		}
		
		List<String> output = new ArrayList<String>();
		output.add(columnsListAgg.get(0).getColumnValues().size() + ""); // sample size
		output.add(columnsListAgg.size() + ""); // "Feature Set Size (#Nodes): "
		output.add(correlationStats.size() + ""); // "Correlaton Set Size (#Edges): "
		//output.add("Correlaton Set = " + correlationStats.toString());
		
		String summary = Utils.Join(output, ",");
		
		PrintWriter writeOutput = new PrintWriter(new BufferedWriter(
				new FileWriter(outputFilename + fileExt, true))); // append mode
		writeOutput.println(summary);
		writeOutput.close();
		
		System.out.println("Accumulated Agg. Correlation List: "+config.correlationStatsAgg);
				
		writeMLN(columnsListAgg);
	}
	
	public static void writeMLN(ArrayList<RandomVariableData> columnsListAgg) throws FileNotFoundException
	{
		ArrayList<String> mlnListTuffy = new ArrayList<String>();
		ArrayList<String> mlnListAlchemy = new ArrayList<String>();
		mlnListTuffy.add("Class(inst,wp)");
		mlnListAlchemy.add("Class(inst,wp)");
		for (RandomVariableData rv : columnsListAgg)
		{
			String feature = rv.getColumnName();
			feature = Character.toUpperCase(feature.charAt(0)) + feature.substring(1); // capitalize first letter
			mlnListTuffy.add("*" + feature + "(inst,val)");
			mlnListAlchemy.add("" + feature + "(inst,val)");
		}
		mlnListTuffy.add("");
		for (String rule : config.correlationStatsAgg.keySet())
		{
			String[] ruleSplit = rule.split(" ");
			ruleSplit[0] = Character.toUpperCase(ruleSplit[0].charAt(0)) + ruleSplit[0].substring(1); // capitalize first letter
			ruleSplit[2] = Character.toUpperCase(ruleSplit[2].charAt(0)) + ruleSplit[2].substring(1);
			mlnListTuffy.add("1\t" + "!" + ruleSplit[0] + "(x,y) v !" + ruleSplit[2] + "(x,z)\t" + "v Class(x,w)" );
			mlnListAlchemy.add("1\t" + "" + ruleSplit[0] + "(x,y) ^ " + ruleSplit[2] + "(x,z)\t" + "=> Class(x,w)" );
		}

		Utils.writeFile(mlnListTuffy, System.getProperty("user.dir") + "/MLN/websiteTuffy.mln");
		Utils.writeFile(mlnListAlchemy, System.getProperty("user.dir") + "/MLN/websiteAlchemy.mln");
			
		
	}
	
	public static void writeMLN()
	{
		// write MLN file based on top most values in config.correlationStatsAgg
	}

}























