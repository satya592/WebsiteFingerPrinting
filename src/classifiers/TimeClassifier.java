package classifiers;

import intro.Packet;
import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeClassifier {
	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, String> instance = new HashMap<String, String>();
		
		int maxTime = 0;
		
		for (Packet packet : trace.getPackets(999)) // 999 get Up and Down packets
			if (packet.getTime() > maxTime)
				maxTime = packet.getTime();
		
		instance.put("time",  String.valueOf(maxTime));
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
