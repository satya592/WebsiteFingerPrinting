package intro;
import java.io.IOException;


public class test_Trace {

	public static void main(String[] args) throws IOException
	{
		Trace actualTrace = pcapparser.readfile( 2, 10, 13, 8 );

		Trace expectedTrace = new Trace(8);
        expectedTrace.addPacket( new Packet( Packet.UP  , 0  , 148 ) ); // expectedTrace time is not the same as in the above actualTrace
        expectedTrace.addPacket( new Packet( Packet.DOWN, 0  , 100 ) );
        expectedTrace.addPacket( new Packet( Packet.UP  , 0  , 52  ) );
        expectedTrace.addPacket( new Packet( Packet.UP  , 3  , 500 ) );
        expectedTrace.addPacket( new Packet( Packet.DOWN, 18 , 244 ) );
        expectedTrace.addPacket( new Packet( Packet.UP  , 35 , 436 ) );
        expectedTrace.addPacket( new Packet( Packet.DOWN, 75 , 52  ) );
        expectedTrace.addPacket( new Packet( Packet.DOWN, 118, 292 ) );
        expectedTrace.addPacket( new Packet( Packet.UP  , 158, 52  ) );
	}
}
