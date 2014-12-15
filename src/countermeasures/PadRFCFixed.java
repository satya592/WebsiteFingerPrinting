package countermeasures;

import java.util.ArrayList;
import java.util.Random;

import intro.Packet;
import intro.Trace;
import intro.Utils;

public class PadRFCFixed {
	
	public static Trace applyCountermeasure(Trace trace)
	{
		// see peek-a-boo paper IV. COUNTERMEASURES, Session Random 255 padding:
		ArrayList<Integer> rList = Utils.range(8, 256, 8); // r belogs to {0, 8, 16 . . . , 248}
		int rand = rList.get(new Random().nextInt(rList.size())); // get a fixed random value for all packets from r
		
		Trace newTrace = new Trace(trace.getId());
		for (Packet packet : trace.getPackets(999))
		{
			int length = Math.min(packet.getLength() + rand, Packet.MTU);
			Packet newPacket = new Packet(packet.getDirection(),
                    						packet.getTime(),
                    						length); // padding technique
			newTrace.addPacket(newPacket);
		}
		return newTrace;
	
	}

}
