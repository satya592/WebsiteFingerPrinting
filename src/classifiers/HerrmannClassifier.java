package classifiers;

import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HerrmannClassifier {
// TF-IDF: Multinomial Naive Bayes Classifieron
//	Herrmann paper section 4.5.2

	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, Double> tempInstance = trace.getHistogram(999, false); // instance for arff file

		for (String attribute : tempInstance.keySet() )
			// Term Frequency
			tempInstance.put(attribute, Math.log(1 + tempInstance.get(attribute)) / Math.log(2));
		
		// Store Euclidean Length for Cosine Normalisation (Section 4.5.2)
		double euclideanLength = 0;
		for (String attribute : tempInstance.keySet() )
			euclideanLength += tempInstance.get(attribute) * tempInstance.get(attribute);
		
		euclideanLength = Math.sqrt(euclideanLength);
		
		// Cosine normalization
		for (String attribute : tempInstance.keySet() )
			tempInstance.put(attribute, tempInstance.get(attribute) / euclideanLength);
			
		Map<String, String> instance = new HashMap<String, String>();
		
		for (String key : tempInstance.keySet())
			instance.put(key,  tempInstance.get(key).toString());
		
		// class label
		instance.put("class", "webpage"+trace.getId());
		
		return instance;
	}
	
	public static List<String> classify(String runID, List<HashMap<String, String>> trainingSet, List<HashMap<String, String>> testingSet) throws Exception
	{
		String[] trainingFile_testingFile = arffWriter.writeArffFiles( runID, trainingSet, testingSet );
		
		List<String> argus = new ArrayList<String>()
				{{
					add(""); // nothing for Multinomial Naive Bayes
				}};
		
		return wekaAPI.execute( trainingFile_testingFile[0], trainingFile_testingFile[1], "weka.classifiers.bayes.NaiveBayesMultinomial", argus );

	}

}
