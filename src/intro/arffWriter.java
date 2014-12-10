package intro;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import MarkovLogicNetwork.SpearmansCorrelation;

public class arffWriter {

	public static String[] writeArffFiles(String runID, List<HashMap<String, String>> trainingSet, List<HashMap<String, String>> testingSet) throws IOException
	{
		String trainingFilename = "datafile-"+runID+"-train";
		String testingFilename = "datafile-"+runID+"-test";
		
		List<String> classes = new ArrayList<String>();
		
		for (HashMap<String, String> instance : trainingSet)
			if (!classes.contains(instance.get("class")))
				classes.add(instance.get("class"));
		for (HashMap<String, String> instance : testingSet)
			if (!classes.contains(instance.get("class")))
				classes.add(instance.get("class"));		
		
		List<String> attributes = new ArrayList<String>();
		for (HashMap<String, String> instance : trainingSet)
			for (String key : instance.keySet()) // keys are the attribute names, last key is class attribute
				if (!attributes.contains(key))
					attributes.add(key);
		for (HashMap<String, String> instance : testingSet)
			for (String key : instance.keySet())
				if (!attributes.contains(key))
					attributes.add(key);
		
		String trainingFile = __writeArffFile( trainingSet, trainingFilename, classes, attributes );
		String testingFile = __writeArffFile( testingSet, testingFilename, classes, attributes );
		
		String[] trainingFile_testingFile = {trainingFile, testingFile};
		
		return trainingFile_testingFile;
	}
	
	public static String __writeArffFile( List<HashMap<String, String>> inputArray, String outputFile, List<String> classes, List<String>  attributes ) throws IOException
	{
		List<String> arffFile = new ArrayList<String>();
		
		arffFile.add("@RELATION sites");
		for (String attribute : attributes)
			if (!attribute.equals("class"))
				arffFile.add("@ATTRIBUTE " + attribute + " real");
		arffFile.add("@ATTRIBUTE class {" + Utils.Join(classes, ",") + "}");
		arffFile.add("@DATA");
		
		for (HashMap<String, String> instance : inputArray)
		{
			List<String> tmpBuf = new ArrayList<String>();
			for (String attribute : attributes)
			{
				if (!attribute.equals("class"))
				{
					String val = "0";
					if (instance.get(attribute) != null) // check && !instance.get(attribute).equals("0")
						val = instance.get(attribute);
					tmpBuf.add(val);
				}
			}
			tmpBuf.add(instance.get("class"));
			arffFile.add(Utils.Join(tmpBuf, ","));
		}
		
		String output = config.CACHE_DIR + "/" + outputFile + ".arff";
		
		PrintWriter writer = new PrintWriter(new FileWriter(output));
		
		for (String line : arffFile)
			writer.println(line);
		
		writer.close();
		
		// Nov 6, 2014
		// Spearman's Correlation
		if (config.SPEARMANs_CORRELATION == 1)
			if (output.contains("train"))
			{
				SpearmansCorrelation.readArff(output);
				SpearmansCorrelation.readArffWithAggregation(output, "avg");
				SpearmansCorrelation.readArffWithAggregation(output, "sum");
				SpearmansCorrelation.readArffWithAggregation(output, "max");
				SpearmansCorrelation.readArffWithAggregation(output, "min");
				SpearmansCorrelation.readArffWithAggregation(output, "median");
				// Try quantiles
			}
				
		
		return outputFile;
	}
}






























