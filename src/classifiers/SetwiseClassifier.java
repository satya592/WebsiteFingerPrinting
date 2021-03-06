package classifiers;

import intro.Packet;
import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// same as VNGPlusPlusClassifierModified.java but J48
public class SetwiseClassifier {
	public static ArrayList<String> markovDependentFeatures = new ArrayList<String>(); // Oct 26, 2014 for MLN
	
	public static Map<String, String> traceToInstance(Trace trace)
	{
		Map<String, String> instance = new HashMap<String, String>();
		
		// Size/Number Markers
		int directionCursor = 999; // Up = 1 or Down = 0
		int dataCursor = 0; // bytes in bursts
		
		markovDependentFeatures.add("--------"+"webpage"+trace.getId()+"----------"); // Oct 26, 2014 for MLN
		
		for (Packet packet : trace.getPackets(999))
		{
			if (directionCursor == 999)
				directionCursor = packet.getDirection();
			
			if (packet.getDirection() != directionCursor)
			{
				String dataKey = "S" + String.valueOf(directionCursor) + "-" + String.valueOf(roundArbitrary(dataCursor, 15000)); // changed on Oct 24, 14. def 600
				if (instance.get(dataKey) == null)
					instance.put(dataKey, String.valueOf(0));
				
//				System.out.println("Debugging here: " + instance.get(dataKey));
//				System.out.println("value to be updated: " + Integer.getInteger(instance.get(dataKey)) + 1));
				instance.put(dataKey, String.valueOf(Integer.parseInt(instance.get(dataKey)) + 1));
				markovDependentFeatures.add(dataKey); // // Oct 26, 2014 for MLN
				System.out.println("Feature selected : "+dataKey);
				directionCursor = packet.getDirection();
				dataCursor = 0;
			}
			
			dataCursor += packet.getLength();
		}
		
		// for last burst
		if (dataCursor > 0)
		{
			String key = "S" + String.valueOf(directionCursor) + "-" + String.valueOf(roundArbitrary(dataCursor, 15000)); // changed on Oct 24, 14. def 600
			if (instance.get(key) == null)
				instance.put(key, String.valueOf(0));
			instance.put(key, String.valueOf(Integer.parseInt(instance.get(key)) + 1));	
			markovDependentFeatures.add(key); // Oct 26, 2014 for MLN
			System.out.println("Feature selected : "+key);
		}
		
		// BW
		instance.put("bandwidthUp",  String.valueOf(trace.getBandwidth( Packet.UP )));
		instance.put("bandwidthDown",  String.valueOf(trace.getBandwidth( Packet.DOWN )));
		
		// Time
		int maxTime = 0;
		
		for (Packet packet : trace.getPackets(999)) // 999 get Up and Down packets
			if (packet.getTime() > maxTime)
				maxTime = packet.getTime();
		
		instance.put("time",  String.valueOf(maxTime));
		
		// adding label
		instance.put("class", "webpage"+trace.getId());
		
		return instance;
	}
	
	public static ArrayList<HashMap<String, String>> traceToBurstInstances(Trace trace, int entity)
	{
		// This method is to be used for setwise clustering, Nov 03, 2014
		
		ArrayList<HashMap<String, String>> instances = new ArrayList<HashMap<String, String>>();
		
		// Size/Number Markers
		int directionCursor = 999; // Up = 1 or Down = 0
		int dataCursor = 0; // bytes in bursts
		int timeCursor = 0; // time for each burst
		int countCursor = 0; // each burst packets count
		
		//markovDependentFeatures.add("--------"+"webpage"+trace.getId()+"----------"); // Oct 26, 2014 for MLN
		HashMap<String, String> instance = new HashMap<String, String>();;
		
		for (Packet packet : trace.getPackets(999))
		{
			if (directionCursor == 999)
				directionCursor = packet.getDirection();
			
			if (packet.getDirection() != directionCursor)
			{
				instance = new HashMap<String, String>();
				instance.put("entity",  String.valueOf(entity));
				
				String dataKey = "S" + String.valueOf(directionCursor); // + "-" + String.valueOf(roundArbitrary(dataCursor, 15000)); // changed on Oct 24, 14. def 600
//				if (instance.get(dataKey) == null)
//					instance.put(dataKey, String.valueOf(0));
				
//				System.out.println("Debugging here: " + instance.get(dataKey));
//				System.out.println("value to be updated: " + Integer.getInteger(instance.get(dataKey)) + 1));
				instance.put(dataKey, String.valueOf(dataCursor));
				//markovDependentFeatures.add(dataKey); // // Oct 26, 2014 for MLN
				//System.out.println("Feature selected : "+dataKey);
				
				directionCursor = packet.getDirection();
				dataKey = "S" + String.valueOf(directionCursor); // + "-" + String.valueOf(roundArbitrary(dataCursor, 15000)); // changed on Oct 24, 14. def 600
				//if (instance.get(dataKey) == null)
				instance.put(dataKey, String.valueOf(0));
				
				instance.put("time",  String.valueOf(timeCursor));
				instance.put("count",  String.valueOf(countCursor));
				
				instance.put("class", "webpage"+trace.getId());
				
				instances.add(instance);
				
				dataCursor = 0;
				timeCursor = 0;
				countCursor = 0;
			}
			
			
			
			dataCursor += packet.getLength();
			timeCursor += packet.getTime();
			countCursor++;
		}
		
		// for last burst
		if (dataCursor > 0 || timeCursor > 0 || countCursor > 0)
		{
			instance = new HashMap<String, String>();
			instance.put("entity",  String.valueOf(entity));
			
			String key = "S" + String.valueOf(directionCursor); // + "-" + String.valueOf(roundArbitrary(dataCursor, 15000)); // changed on Oct 24, 14. def 600
			//if (instance.get(key) == null)
			instance.put(key, String.valueOf(dataCursor));
			
			if (directionCursor == 0)
				key = "S" + String.valueOf(1); // + "-" + String.valueOf(roundArbitrary(dataCursor, 15000)); // changed on Oct 24, 14. def 600
			else
				key = "S" + String.valueOf(0);
			//if (instance.get(dataKey) == null)
			instance.put(key, String.valueOf(0));
			
			instance.put("time",  String.valueOf(timeCursor));
			instance.put("count",  String.valueOf(countCursor));
			
			instance.put("class", "webpage"+trace.getId());
			//markovDependentFeatures.add(key); // Oct 26, 2014 for MLN
			//System.out.println("Feature selected : "+key);
			instances.add(instance);
		}
		
		return instances;
	}
	
	public static int roundArbitrary(int x, int base)
	{
		int value = (int) base * Math.round((float)x / base); // if base = 600, then 0, 600, 1200, 1800, 2400, ... best resutls according to Panchenko et al
		return value;
	}
	
	public static List<String> classify(String runID, List<HashMap<String, String>> trainingSet, List<HashMap<String, String>> testingSet) throws Exception
	{
		String[] trainingFile_testingFile = arffWriter.writeArffFiles( runID, trainingSet, testingSet );
		//weka.classifiers.trees.J48 -C 0.25 -M 2
		List<String> argus = new ArrayList<String>()
				{{
					add("-C"); add("0.25");
					add("-M"); add("2");
				}};
		
		return wekaAPI.execute( trainingFile_testingFile[0], trainingFile_testingFile[1], "weka.classifiers.trees.J48", argus );
		
	}
}
