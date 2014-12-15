package countermeasures;

import intro.Packet;
import intro.Trace;
import intro.Utils;

import java.util.ArrayList;
import java.util.Random;

public class PadRFCRand {
	public static Trace applyCountermeasure(Trace trace)
	{
		// see peek-a-boo paper IV. COUNTERMEASURES, Packet Random 255 padding:
		ArrayList<Integer> rList = Utils.range(8, 256, 8); // r belogs to {0, 8, 16 . . . , 248};
		
		Trace newTrace = new Trace(trace.getId());
		
		for (Packet packet : trace.getPackets(999))
		{
			int rand = rList.get(new Random().nextInt(rList.size())); // get a random value for each packet from r
			
			int length = Math.min(packet.getLength() + rand, Packet.MTU);
			Packet newPacket = new Packet(packet.getDirection(),
                    						packet.getTime(),
                    						length); // padding technique
			newTrace.addPacket(newPacket);
		}
		return newTrace;
	
	}

}
