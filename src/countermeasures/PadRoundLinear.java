package countermeasures;

import java.util.ArrayList;

import intro.Packet;
import intro.Trace;
import intro.Utils;

public class PadRoundLinear {

	
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
		ArrayList<Integer> VALID_PACKETS = Utils.range(128, 1500, 128);
		VALID_PACKETS.add(1500);
		
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