package intro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trace {

	public ArrayList<Packet> __packetArray;
	public int __id;
	public Map<String, Double> __histogramUp; // histogram frequency as Double as some time it can be normalized
	public Map<String, Double> __histogramDown;
	public int __packetsUp;
	public int __packetsDown;
	public String __filePath;
	public int __year;
	public int __month;
	public int __day;
	public int __hour;
	
	public Trace(int id){
		this.__packetArray = new ArrayList<Packet>();
		this.__id = id; // webpage id
		this.__histogramUp = new HashMap<String, Double>();
		this.__histogramDown = new HashMap<String, Double>();
		this.__packetsUp = 0;
		this.__packetsDown = 0;
		this.__filePath = "";
		this.__year = 0;
		this.__month = 0;
		this.__day = 0;
		this.__hour = 0;	
	}
	
	public int getId(){return this.__id;}
	
	public int getPacketCount(int direction){
		return getPackets(direction).size();
	}
	
	public ArrayList<Packet> getPackets(int direction){
		ArrayList<Packet> retArray = new ArrayList<Packet>();
		for (Packet packet : this.__packetArray) {
			if (direction == 999 || packet.getDirection() == direction) // if direction == None or packet.getDirection() == direction:
					retArray.add(packet);
		}
		return retArray;	
	}
	
	public ArrayList<Packet> addPacket(Packet packet){
	     // Completely ignore ACK packets
		if (config.IGNORE_ACK && packet.getLength() == Packet.HEADER_LENGTH){
			return this.__packetArray;
		}
		//str(packet.getDirection())+'-'+str(packet.getLength())
		String key = String.valueOf(packet.getDirection()) + "-" + String.valueOf(packet.getLength());
		
        if (packet.getDirection()==Packet.UP){
        	this.__packetsUp += 1;
            if (!this.__histogramUp.containsKey( key ))
                this.__histogramUp.put(key, 0.0);
            this.__histogramUp.put(key, this.__histogramUp.get(key) + 1);
        } else if (packet.getDirection()==Packet.DOWN){
        	this.__packetsDown += 1;
            if (!this.__histogramDown.containsKey(key))
            	 this.__histogramDown.put(key, 0.0);
            this.__histogramDown.put(key,  this.__histogramDown.get(key) + 1);
                          
        }
        
        this.__packetArray.add(packet);
        
        return this.__packetArray;
               	
	}
   
    public int getBandwidth(int direction){
         int totalBandwidth = 0;  	
         for (Packet packet : this.getPackets(999)){ // 999 resembles None (all packets up and down)
        	 if ((direction == 999 || direction == packet.getDirection()) && packet.getLength() != Packet.HEADER_LENGTH){
        		 totalBandwidth += packet.getLength();
        	 }
         }
         
         return totalBandwidth;
    }
    
    public int getTime(int direction){
    	int timeCursor = 0; 
    	for (Packet packet : this.getPackets(999)){
    		if (direction == 999 || direction == packet.getDirection())
    			timeCursor = packet.getTime();
    	}
    	return timeCursor;
    }
    
    public Map<String, Double> getHistogram(int direction, boolean normalize){ // direction == None, normalize == False by def
    	Map<String, Double> histogram;
    	int totalPackets = 0;
    	if (direction == Packet.UP)
    	{
    		histogram = new HashMap<String, Double>(this.__histogramUp); // copy Uplink histo hashmap
    		totalPackets = this.__packetsUp;
    	} else if (direction == Packet.DOWN) 
    	{
    		histogram = new HashMap<String, Double>(this.__histogramDown); // copy Downlink histo hashmap
    		totalPackets = this.__packetsDown;
    	} else // both histograms
    	{
    		histogram = new HashMap<String, Double>(this.__histogramUp); // copy Uplink histo first
    		for (String key : this.__histogramDown.keySet()) // then copy Downlink histogram
    		{
    			histogram.put(key, this.__histogramDown.get(key));
    			
    		}
    		totalPackets = this.__packetsDown + this.__packetsUp;
    	}
    	
    	if (normalize == true)
    	{
    		for (String key : histogram.keySet())
    		{
    			histogram.put(key, histogram.get(key) * 1.0  / totalPackets);
    		}
    	}
    	return histogram;
    }
    
  // distance between two histograms
    public Double calcL1Distance(HashMap<String, Double> targetDistribution, int filterDirection)
    {
    	Map<String, Double> localDistribution = new HashMap<String, Double>(this.getHistogram(filterDirection, true));
    	List<String> keys = new ArrayList<String>(localDistribution.keySet());
    	
    	for (String key : targetDistribution.keySet())
    	{
    		if (!keys.contains(key))
    			keys.add(key);
    	}
    	
    	Double distance = 0.0;
    	Double l, r;
    	for (String key : keys)
    	{
    		l = (localDistribution.get(key) == null ? null : localDistribution.get(key)); // BTW, could be just l = localDistribution.get(key);
    	    r = (targetDistribution.get(key) == null ? null : targetDistribution.get(key));
    	    
    	    if (l == null && r == null)
    	    	continue;
    	    
    	    if (l == null)
    	    	l = 0.0;
    	    
    	    if (r == null)
    	    	r = 0.0;
    	    
    	    distance += Math.abs(l - r);
    	}
    	
    	return distance;
    }
    
    
    public String[] getMostSkewedDimension(HashMap<String, Double> targetDistribution)
    {
    	Map<String, Double> localDistribution = new HashMap<String, Double>(this.getHistogram(999, true));
    	
    	List<String> keys = new ArrayList<String>(targetDistribution.keySet());
    	
    	String worstKey = "";
    	Double worstKeyDistance = 0.0;
    	Double l, r;
    	
    	for (String key : keys)
    	{
    		l = (localDistribution.get(key) == null ? null : localDistribution.get(key));
    	    r = (targetDistribution.get(key) == null ? null : targetDistribution.get(key));
    	    
    	    if (l == null)
    	    	l = 0.0;
    	    
    	    if (r == null)
    	    	r = 0.0;
    	    
    	    if (worstKey == "" || (r - l) > worstKeyDistance)
    	    {
    	    	worstKeyDistance = r - l;
    	        worstKey = key;
    	    }
    	}
    	
    	String[] bits = worstKey.split("-");
    	
    	return bits;
    }
    
   
    
	
}



















