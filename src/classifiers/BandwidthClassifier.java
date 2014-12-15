package classifiers;

import intro.Packet;
import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BandwidthClassifier {
	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, String> instance = new HashMap<String, String>();
		
		instance.put("bandwidthUp",  String.valueOf(trace.getBandwidth( Packet.UP )));
		instance.put("bandwidthDown",  String.valueOf(trace.getBandwidth( Packet.DOWN )));
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
