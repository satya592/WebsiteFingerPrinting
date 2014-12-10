package classifiers;

import intro.Trace;
import intro.arffWriter;

import java.io.IOException;
import java.util.*;

public class LiberatoreClassifier extends ClassifiersInt{

	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, Double> tempInstance = trace.getHistogram(999, false); // instance for arff file
		Map<String, String> instance = new HashMap<String, String>();
		
		for (String key : tempInstance.keySet())
			instance.put(key,  tempInstance.get(key).toString());
		
		instance.put("class", "webpage"+trace.getId());
		
		return instance;
	}
	
	public static List<String> classify(String runID, List<HashMap<String, String>> trainingSet, List<HashMap<String, String>> testingSet) throws Exception
	{
		String[] trainingFile_testingFile = arffWriter.writeArffFiles( runID, trainingSet, testingSet );
		
		List<String> argus = new ArrayList<String>()
				{{
					add("-K"); // for NaiveBayes
				}};
		
		return wekaAPI.execute( trainingFile_testingFile[0], trainingFile_testingFile[1], "weka.classifiers.bayes.NaiveBayes", argus );
	}

}
