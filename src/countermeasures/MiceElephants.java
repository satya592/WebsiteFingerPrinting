package countermeasures;

import intro.Packet;
import intro.Trace;

public class MiceElephants {

	public static Trace applyCountermeasure(Trace trace)
	{
		Trace newTrace = new Trace(trace.getId());
		
		for (Packet packet : trace.getPackets(999))
		{
			Packet newPacket = new Packet( packet.getDirection(),
											packet.getTime(),
											calcLength(packet.getLength()));  
			newTrace.addPacket(newPacket);
		}
		return newTrace;
	}
	
	public static int calcLength(int packetLength)
	{
		int[] VALID_PACKETS = {128, 1500};
		int retVal = 0;
		
		for (int val : VALID_PACKETS)
		{
			if (packetLength <= val)
			{
				retVal = val;
				break;
			}
		}
		return retVal;
	}
	
}
		
    		
    		
    		
