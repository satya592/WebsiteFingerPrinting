package classifiers;

import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrightClassifier {
	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, Double> tempInstance = trace.getHistogram(999, true); // normalize histogram
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
