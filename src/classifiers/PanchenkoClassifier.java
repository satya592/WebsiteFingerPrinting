package classifiers;

import intro.Packet;
import intro.Trace;
import intro.arffWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanchenkoClassifier {
	public static Map<String, String> traceToInstance(Trace trace)
	{

		Map<String, Double> tempInstance = trace.getHistogram(999, false); // instance for arff file
		Map<String, String> instance = new HashMap<String, String>();
				
		if (trace.getPacketCount(999) == 0)
		{
			instance.put("class", "webpage"+trace.getId());
			return instance;
		}

		// 1- Histogram Features
		tempInstance = trace.getHistogram(999, false);
		
		for (String key : tempInstance.keySet())
			instance.put(key,  tempInstance.get(key).toString());

		// 2- Size and Number Markers Features
		// Size/Number Markers
		int directionCursor = 999; // Up = 1 or Down = 0.   999 means no direction yet (as null or none)
		int dataCursor = 0; // bytes in bursts
		int numberCursor = 0;
		
		for (Packet packet : trace.getPackets(999))
		{
			if (directionCursor == 999)
				directionCursor = packet.getDirection();
			
			if (packet.getDirection() != directionCursor)
			{
				String dataKey = "S" + String.valueOf(directionCursor) + "-" + String.valueOf(roundArbitrary(dataCursor, 600));
				if (instance.get(dataKey) == null)
					instance.put(dataKey, String.valueOf(0));
				
				instance.put(dataKey, String.valueOf(Integer.parseInt(instance.get(dataKey)) + 1));
				
				String numberKey = "N" + String.valueOf(directionCursor) + "-" + String.valueOf(roundNumberMarker(numberCursor));
				if (instance.get(numberKey) == null)
					instance.put(numberKey, String.valueOf(0));
				
				instance.put(numberKey, String.valueOf(Integer.parseInt(instance.get(numberKey)) + 1));
				
				directionCursor = packet.getDirection();
				dataCursor = 0;
				numberCursor = 0;
			}
			
			dataCursor += packet.getLength();
			numberCursor += 1;
		}
		
		// for last burst
		if (dataCursor > 0)
		{
			String key = "S" + String.valueOf(directionCursor) + "-" + String.valueOf(VNGClassifier.roundArbitrary(dataCursor, 600));
			if (instance.get(key) == null)
				instance.put(key, String.valueOf(0));
			instance.put(key, String.valueOf(Integer.parseInt(instance.get(key)) + 1));	
		}
		
		// for last burst
		if (numberCursor > 0)
		{
			String numberKey = "N" + String.valueOf(directionCursor) + "-" + String.valueOf(roundNumberMarker(numberCursor));
			
			if (instance.get(numberKey) == null)
				instance.put(numberKey, String.valueOf(0));
			instance.put(numberKey, String.valueOf(Integer.parseInt(instance.get(numberKey)) + 1));	
		}
		
		// 3- HTML Markers
        int counterUP = 0;
        int counterDOWN = 0;
        int htmlMarker = 0;
        for (Packet packet : trace.getPackets(999))
        {
        	if (packet.getDirection() == Packet.UP)
        	{
        		counterUP += 1;
        		if (counterUP > 1 && counterDOWN > 0)
        			break;
        	} else if ((packet.getDirection() == Packet.DOWN))
        	{
        		counterDOWN +=1;
        		htmlMarker += packet.getLength(); // see Panchenko's paper: HTML Markers in 4.1 Features
        										  // as first down link packets will give us HTML length
        										  // before loading other objects
        	}
        }
        
        htmlMarker = roundArbitrary( htmlMarker, 600 );
        instance.put("H" + htmlMarker, String.valueOf(1));
		
        // 4- Occurring Packet Sizes (unique packet size)
        List<Integer> packetsUp = new ArrayList<Integer>();
        List<Integer> packetsDown = new ArrayList<Integer>();
        
        for (Packet packet : trace.getPackets(999))
        {
        	if (packet.getDirection() == Packet.UP && !packetsUp.contains(packet.getLength()))
        		packetsUp.add(packet.getLength());
        	if (packet.getDirection() == Packet.DOWN && !packetsDown.contains(packet.getLength()))
        		packetsDown.add(packet.getLength());
        }
        
        instance.put("uniquePacketSizesUp", String.valueOf(roundArbitrary( packetsUp.size(), 2)));
        instance.put("uniquePacketSizesDown", String.valueOf(roundArbitrary( packetsDown.size(), 2)));
        
        // 5- Percentage Incoming Packets
        instance.put("percentageUp", String.valueOf(roundArbitrary( (int) 100.0 * trace.getPacketCount( Packet.UP ) / trace.getPacketCount(999), 5)));
        instance.put("percentageDown", String.valueOf(roundArbitrary( (int) 100.0 * trace.getPacketCount( Packet.DOWN ) / trace.getPacketCount(999), 5)));
      
        // 6- Number of Packets
        instance.put("numberUp", String.valueOf(roundArbitrary( trace.getPacketCount( Packet.UP ), 15)));
        instance.put("numberDown", String.valueOf(roundArbitrary( trace.getPacketCount( Packet.DOWN ), 15)));
     
        // 7- Total Bytes Transmitted
        int bandwidthUp   = roundArbitrary( trace.getBandwidth( Packet.UP ),   10000);
        int bandwidthDown = roundArbitrary( trace.getBandwidth( Packet.DOWN ), 10000);
        instance.put("0-B" + bandwidthUp, String.valueOf(1));
        instance.put("1-B" + bandwidthDown, String.valueOf(1));

		// adding label
		instance.put("class", "webpage"+trace.getId());
		
		return instance;
	}
	
	public static int roundArbitrary(int x, int base)
	{
		int value = (int) base * Math.round((float)x / base); // 0, 600, 1200, 1800, 2400, ... best resutls according to Panchenko et al
		return value;
	}
	
	public static int roundNumberMarker(int n)
	{
		if (n == 4 || n ==5)
			return 3;
		else if (n==7 || n==8)
			return 6;
		else if (n==10 || n==11 || n==12 || n==13)
			return 9;
		else
			return n;
		
	}
	
	public static List<String> classify(String runID, List<HashMap<String, String>> trainingSet, List<HashMap<String, String>> testingSet) throws Exception
	{
		String[] trainingFile_testingFile = arffWriter.writeArffFiles( runID, trainingSet, testingSet );
		
		List<String> argus = new ArrayList<String>()
				{{
					add("-K"); add("2"); // RBF kernel as in Panchenko paper
					add("-G"); add("0.0000019073486328125"); // Gamma as in Panchenko paper
					add("-C"); add("131072"); // Cost as in Panchenko paper
					
				}};
		
		return wekaAPI.execute( trainingFile_testingFile[0], trainingFile_testingFile[1], "weka.Run weka.classifiers.functions.LibSVM", argus );
		

	}
}























