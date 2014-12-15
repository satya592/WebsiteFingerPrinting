package intro;
import edu.gatech.sjpcap.*;

public class PcapPrint {

	public static void main(String[] args) {
		PcapParser pcapParser = new PcapParser();
		// args[0] = ;
		if (pcapParser
				.openFile("/data/kld/papers/InferringSrcOfEncrHTTPCon/pcap-logs/2006-02-10T13:12:13.301721/2006-02-10T13:13:33.536157-site-8") < 0) {
			System.err.println("Failed to open " + args[0] + ", exiting.");
			return;
		}
		
		long start = 0, ts = 0;
		
		
		
		edu.gatech.sjpcap.Packet packet = pcapParser.getPacket();
		while (packet != edu.gatech.sjpcap.Packet.EOF) {
			if (!(packet instanceof IPPacket)) {

				continue;
			}

			//pcapParser.getIp
			
			System.out.println("--PACKET--");
			IPPacket ipPacket = (IPPacket) packet;
			
			//long timestamp = ipPacket.timestamp;
			ts = ipPacket.timestamp ;
			if (start == 0)
				start = ts;
			
			int delay = (int) (ts-start) * 1000;
			
			//int i = pcapParser.etherHeaderLength; // Constant always = 14
			
			System.out.println("TIME " + ipPacket.timestamp / 1000);
			System.out.println("SRC " + ipPacket.src_ip.getHostAddress());
			System.out.println("DST " + ipPacket.dst_ip.getHostAddress());

			if (ipPacket instanceof UDPPacket) {
				UDPPacket udpPacket = (UDPPacket) ipPacket;
				System.out.println("SRC PORT " + udpPacket.src_port);
				System.out.println("DST PORT " + udpPacket.dst_port);
				System.out.println("PAYLOAD LEN " + udpPacket.data.length);
				System.out.println("IP Total LEN " + udpPacket.len);
			}
			if (ipPacket instanceof TCPPacket) {
				TCPPacket tcpPacket = (TCPPacket) ipPacket;
				System.out.println("SRC PORT " + tcpPacket.src_port);
				System.out.println("DST PORT " + tcpPacket.dst_port);
				System.out.println("PAYLOAD LEN " + tcpPacket.data.length);
				System.out.println("IP Total  LEN " + tcpPacket.len);
			}

			packet = pcapParser.getPacket();
		}
		pcapParser.closeFile();
	}
}