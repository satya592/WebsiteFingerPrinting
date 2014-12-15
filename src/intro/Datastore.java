package intro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Datastore {

	public static ArrayList<Webpage> getWebpagesLL(ArrayList<Integer> webpageIds, int traceIndexStart, int traceIndexEnd) throws IOException
	{
		ArrayList<Webpage> webpages = new ArrayList<Webpage>();
		for (int webpageId : webpageIds)
		{
			Webpage webpage = new Webpage(webpageId);
			/// debug
//			for (int i : Utils.range(traceIndexStart, traceIndexEnd))
//				System.out.println(i);
			/// end debug
			for (String name : wfpacketsniffer.WfPacketSniffer.myfilenames.get(webpageId))
			{
				Trace trace = Datastore.getTraceLL( webpageId, name );
				webpage.addTrace(trace);
			}
			webpages.add(webpage);
		}
		
		return webpages;
	}
	
	
        public static Trace getTraceLL(int webpageId, int traceIndex) throws IOException
	{
		// dateTime = config.DATA_SET[traceIndex]
		HashMap<String, Integer> dateTime = config.DATA_SET.get(traceIndex);
		
		Trace  trace = pcapparser.readfile(dateTime.get("month"),
                        dateTime.get("day"),
                        dateTime.get("hour"),
                        webpageId);
		
		return trace;
	}
        
        public static Trace getTraceLL(int webpageid,String name) throws IOException
	{
		Trace  trace = pcapparser.readfile(webpageid,name);
		
		return trace;
	}
		
}
