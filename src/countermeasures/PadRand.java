package countermeasures;

import intro.Packet;
import intro.Trace;
import intro.Utils;

import java.util.ArrayList;
import java.util.Random;

public class PadRand {

	public static Trace applyCountermeasure(Trace trace)
	{
		// see peek-a-boo paper IV. COUNTERMEASURES:
		ArrayList<Integer> rList = new ArrayList<Integer>();
		
		Trace newTrace = new Trace(trace.getId());
		
		for (Packet packet : trace.getPackets(999))
		{
			rList = Utils.range(0, Packet.MTU - packet.getLength(), 8);
			
			int length = Packet.MTU;
			if ( (Packet.MTU - packet.getLength()) > 0 )
				length = packet.getLength() + rList.get(new Random().nextInt(rList.size()));
			
			Packet newPacket = new Packet(packet.getDirection(),
                    						packet.getTime(),
                    						length); // padding technique
			newTrace.addPacket(newPacket);
		}
		return newTrace;
	
	}
}