package countermeasures;

import intro.*;

public class PadToMTU {
	
	public static Trace applyCountermeasure(Trace trace)
	{
		Trace newTrace = new Trace(trace.getId());
		// pad all packets to the MTU
		for (Packet packet : trace.getPackets(999))
		{
			Packet newPacket = new Packet( packet.getDirection(),
											packet.getTime(),
											Packet.MTU);  // MTU = 1500
			newTrace.addPacket(newPacket);
		}
		return newTrace;
	}
	
}
