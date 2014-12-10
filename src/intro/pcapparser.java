package intro;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.gatech.sjpcap.*;

public class pcapparser {

	
	public static String __constructAbsolutePath( int month, int day, int hour, int webpageId ) throws IOException
	{
		File f = new File(config.PCAP_ROOT);
               
		
		if (!f.exists())
		{
			throw new IOException("Directory ("+config.PCAP_ROOT+") does not exist");
		}
		
		String monthStr = String.format("%02d", month); // format the integer with 2 digits: 2 --> 02
		String dayStr = String.format("%02d", day);
		String hourStr = String.format("%02d", hour);
		
		String strDateHour = "2006-"+monthStr
			                +"-"+dayStr
			                +"T"+hourStr;
		
		Utils.getPathFiles(strDateHour, "" + webpageId);
		
		List<String> pathList = Utils.getPathFiles(strDateHour, "" + webpageId);
		String absFilePath = "";
		
		if (pathList.size() > 0)
			absFilePath = pathList.get(0); // Get first element in the returned ArrayList<String>
		
		return absFilePath;
	}
	
	// Read instances(traces)
        
        
	public static Trace readfile(int month, int day, int hour, int webpageId) throws IOException
	{
		Trace trace = new Trace(webpageId);
		long start = 0, ts = 0;
		int delta = 0;
		int direction = 0;
		int length = 0;
		
		String absPath =  __constructAbsolutePath( month, day, hour, webpageId );
		
		 PcapParser pcapParser = new PcapParser();
		
		 /// debug
		 if (!new File(absPath).exists())
			 System.out.println(absPath + " doesn't not exist!");
		 /// end debug
		 
		if (pcapParser.openFile(absPath) < 0)
		{
			System.err.println("Failed to open pcap file: " + absPath + ", exiting.");
            System.exit(0);
		}
		
		edu.gatech.sjpcap.Packet packet = pcapParser.getPacket();
				
		while (packet != edu.gatech.sjpcap.Packet.EOF) {
			if (!(packet instanceof IPPacket)) {
				packet = pcapParser.getPacket();
				continue;
			}
								
			//System.out.println("--PACKET--");
			IPPacket ipPacket = (IPPacket) packet;
			
			ts = ipPacket.timestamp; // Time since epoch (1970-01-01) xxxxxxxxxx.xxxxxx
			if (start==0) 
				start = ts; // called once; first packet in the trace as a reference since time is epoch
			
			delta = (int) (ts -start);
			
			//length = ipPacket.
			//ip  = eth.data
			//length    = ip.len + Packet.HEADER_ETHERNET
			
			direction = Packet.UP;
			
			if (ipPacket instanceof TCPPacket) {
				TCPPacket tcpPacket = (TCPPacket) ipPacket;
				length = tcpPacket.len;
				if (tcpPacket.src_port == 22)
					direction = Packet.DOWN;

			}

			trace.addPacket( new Packet(direction, delta, length ) );
			
			packet = pcapParser.getPacket();
		}
		pcapParser.closeFile();
		
		return trace;
		
	}
        
        
        public static Trace readfile( int webpageId,String name) throws IOException
	{
		Trace trace = new Trace(webpageId);
		long start = 0, ts = 0;
		int delta = 0;
		int direction = 0;
		int length = 0;
		
		//String absPath =  __constructAbsolutePath( month, day, hour, webpageId );
		String absPath = config.PCAP_ROOT+name;
		 PcapParser pcapParser = new PcapParser();
		
		 /// debug
		 if (!new File(absPath).exists())
			 System.out.println(absPath + " doesn't not exist!");
		 /// end debug
		 
		if (pcapParser.openFile(absPath) < 0)
		{
			System.err.println("Failed to open pcap file: " + absPath + ", exiting.");
            System.exit(0);
		}
		
		edu.gatech.sjpcap.Packet packet = pcapParser.getPacket();
				
		while (packet != edu.gatech.sjpcap.Packet.EOF) {
			if (!(packet instanceof IPPacket)) {
				packet = pcapParser.getPacket();
				continue;
			}
								
			//System.out.println("--PACKET--");
			IPPacket ipPacket = (IPPacket) packet;
			
			ts = ipPacket.timestamp; // Time since epoch (1970-01-01) xxxxxxxxxx.xxxxxx
			if (start==0) 
				start = ts; // called once; first packet in the trace as a reference since time is epoch
			
			delta = (int) (ts -start);
			
			//length = ipPacket.
			//ip  = eth.data
			//length    = ip.len + Packet.HEADER_ETHERNET
			
			direction = Packet.UP;
			
			if (ipPacket instanceof TCPPacket) {
				TCPPacket tcpPacket = (TCPPacket) ipPacket;
				length = tcpPacket.len;
				if (tcpPacket.src_port == 22)
					direction = Packet.DOWN;

			}

			trace.addPacket( new Packet(direction, delta, length ) );
			
			packet = pcapParser.getPacket();
		}
		pcapParser.closeFile();
		
		return trace;
		
	}
	
}


















