package intro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Webpage {

	
	//class Webpage:
	
	private int __id;
	public List<Trace> __traceSet;
	
	public Webpage(int id)
	{
		this.__id = id;
		this.__traceSet = new ArrayList<Trace>();
	}
	
	public void addTrace(Trace trace)
	{
		this.__traceSet.add(trace);
	}

	public Trace getTrace(int n)
	{
		return this.__traceSet.get(n);
	}
	
	public List<Trace> getTraces()
	{
		return this.__traceSet;
	}
	
	public int getId()
	{
		return this.__id;
	}
	
	public int getBandwidth()
	{
		int totalBandwidth = 0;
		for (Trace trace : this.getTraces())
		{
			totalBandwidth += trace.getBandwidth(999); // 999 is None (to mimic Python)
		}
		return totalBandwidth;
	}
	
	// to get the webpage histogram
	public Map<String, Double> getHistogram(int direction, Boolean normalize)
	{
		Map<String, Double> histogram = new HashMap<String, Double>();
		Double totalPackets = 0.0; // as the histograms values are of type Double and we sum them up
		Map<String, Double> traceHistogram;
		
		for (Trace trace : this.getTraces())
		{
			traceHistogram = new HashMap<String, Double>(trace.getHistogram(direction, false));
			for (String key : traceHistogram.keySet())
			{
				if (histogram.get(key) == null){
					histogram.put(key, 0.0);
				}
				histogram.put(key, histogram.get(key) + traceHistogram.get(key));
				totalPackets += traceHistogram.get(key);
			}
		}
		
    	if (normalize == true)
    	{
    		for (String key : histogram.keySet())
    		{
    			histogram.put(key, (histogram.get(key) * 1.0)  / totalPackets);
    		}
    	}
    	
    	return histogram;
		
	}
	
}











































