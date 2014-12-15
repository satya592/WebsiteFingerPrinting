package classifiers;

import intro.Packet;
import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNGClassifier {
	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, String> instance = new HashMap<String, String>();
		
		// Size Markers
		int directionCursor = 999; // Up = 1 or Down = 0
		int dataCursor = 0; // bytes in bursts
		
		for (Packet packet : trace.getPackets(999))
		{
			if (directionCursor == 999)
				directionCursor = packet.getDirection();
			
			if (packet.getDirection() != directionCursor)
			{
				String dataKey = "S" + String.valueOf(directionCursor) + "-" + String.valueOf(VNGClassifier.roundArbitrary(dataCursor, 600));
				if (instance.get(dataKey) == null)
					instance.put(dataKey, String.valueOf(0));
				
//				System.out.println("Debugging here: " + instance.get(dataKey));
//				System.out.println("value to be updated: " + Integer.getInteger(instance.get(dataKey)) + 1));
				instance.put(dataKey, String.valueOf(Integer.parseInt(instance.get(dataKey)) + 1));
				directionCursor = packet.getDirection();
				dataCursor = 0;
			}
			
			dataCursor += packet.getLength();
		}
		
		// for last burst
		if (dataCursor > 0)
		{
			String key = "S" + String.valueOf(directionCursor) + "-" + String.valueOf(VNGClassifier.roundArbitrary(dataCursor, 600));
			if (instance.get(key) == null)
				instance.put(key, String.valueOf(0));
			instance.put(key, String.valueOf(Integer.parseInt(instance.get(key)) + 1));	
		}
		
		// adding label
		instance.put("class", "webpage"+trace.getId());
		
		return instance;
	}
	
	public static int roundArbitrary(int x, int base)
	{
		int value = (int) base * Math.round((float)x / base); // 0, 600, 1200, 1800, 2400, ... best resutls according to Panchenko et al
		return value;
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
