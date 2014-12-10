package intro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class config {
	/***
	 *	Set the following to a directory that contains
	 * 	weka-X-Y-Z (see WEKA_ROOT to change the weka version)
	 * 	pcap-logs (a diretory that contains all of the LL pcap files)
	 * 	[optional] (a directory that contains custom/local python modules)
	***/
	
	//public static final String BASE_DIR = "/data/kld/papers/InferringSrcOfEncrHTTPCon/"; // Peekaboo paper
	public static final String BASE_DIR = "/home/gbaduz/NetBeansProjects/WfPacketSniffer/dist/"; // Peekaboo paper
	
	// Enviromental settings
	public static final String JVM_MEMORY_SIZE = "8384m"; // 4192m
	
	public static final String WEKA_ROOT = BASE_DIR + "weka-3-7-11/";
	public static final String WEKA_JAR = WEKA_ROOT + "weka.jar";
	public static final String PCAP_ROOT = BASE_DIR + "";
	//PYTHON_ROOT        = os.path.join(BASE_DIR   ,'python2.4')
	//PYTHONPATH         = os.path.join(PYTHON_ROOT,'lib/python')

	public static final String CACHE_DIR          = "./cache/";
	public static final String COUNTERMEASURE_DIR = "./countermeasures";
	public static final String CLASSIFIERS_DIR    = "./classifiers";
	public static final String OUTPUT_DIR         = "./output";
	public static final String TMP_OUTPUT_DIR         = "./tmpOutput/";
	
	public static final String CORRELATION_DIR         = "./correlation";
	
	// Specify options for Herrmann MySQL database
	public static final String MYSQL_HOST = "localhost";
	public static final String MYSQL_DB = "fingerprints";
	public static final String MYSQL_USER = "fingerprints";
	public static final String MYSQL_PASSWD = "fingerprints";

	// sys.path.append(PYTHONPATH)
	// sys.path.append(COUNTERMEASURE_DIR)
	// sys.path.append(CLASSIFIERS_DIR)

	public static int COUNTERMEASURE = 0;
	public static int CLASSIFIER = 0;
	public static int BUCKET_SIZE = 4;// def 2
	public static int DATA_SOURCE = 0; // 1 or 2 is for Herrmann data set
	public static int NUM_TRAINING_TRACES = 16;
	public static int NUM_TESTING_TRACES  = 4;
	public static int NUM_TRIALS = 1;
	public static int TOP_N = 775;
	public static int PACKET_PENALTY = 68;
	public static boolean IGNORE_ACK = true;
	
	// Liberatore and Levine Training and Testing configuration
	
	public static final List<HashMap<String, Integer>> DATA_SET = new ArrayList<HashMap<String, Integer>>() 
	{{
		
		///////////////////////////////////////////////////////////////////////////////////////
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 10); put("hour", 13);}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 11);  put("hour", 11);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 13);  put("hour", 8);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 13);  put("hour", 19);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 14);  put("hour", 9);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 14);  put("hour", 23);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 15);  put("hour", 8);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 16);  put("hour", 12);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 20);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 20);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 20);  put("hour", 22);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 22);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 22);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 23);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 23);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 20);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 20);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 20);  put("hour", 22);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 21);  put("hour", 22);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 22);  put("hour", 22);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 23);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 2); put("day", 23);  put("hour", 10);;}});
///////////////////////////////////////////////////////////////////////////////////////
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 6);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 6);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 7);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 7);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 7);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 7);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 8);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 8);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 8);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 8);  put("hour", 22);;}});	
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 9);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 9);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 9);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 10);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 10);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 10);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 10);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 11);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 11);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 11);  put("hour", 16);;}});	
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 11);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 12);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 12);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 12);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 12);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 13);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 13);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 14);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 14);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 14);  put("hour", 16);;}});	
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 14);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 15);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 15);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 15);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 15);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 16);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 16);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 16);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 16);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 17);  put("hour", 4);;}});	
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 17);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 17);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 17);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 20);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 20);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 20);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 21);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 21);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 21);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 21);  put("hour", 22);;}});	
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 22);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 22);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 22);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 22);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 23);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 23);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 23);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 23);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 24);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 24);  put("hour", 16);;}});	
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 24);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 25);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 25);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 25);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 25);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 26);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 26);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 26);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 26);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 27);  put("hour", 4);;}});	
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 27);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 27);  put("hour", 16);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 28);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 28);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 29);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 29);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 29);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 29);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 30);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 30);  put("hour", 10);;}});	
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 30);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 30);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 31);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 31);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 31);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 3); put("day", 31);  put("hour", 22);;}});
		
		
	
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 1);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 1);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 1);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 1);  put("hour", 22);;}});
		
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 2);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 2);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 2);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 2);  put("hour", 22);;}});	

		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 3);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 3);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 3);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 3);  put("hour", 22);;}});	

		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 4);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 4);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 4);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 4);  put("hour", 22);;}});	

		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 5);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 5);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 5);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 5);  put("hour", 22);;}});	

		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 6);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 6);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 6);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 6);  put("hour", 22);;}});	

		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 7);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 7);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 7);  put("hour", 16);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 7);  put("hour", 22);;}});	

		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 8);  put("hour", 4);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 8);  put("hour", 10);;}});
		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 8);  put("hour", 16);;}});
		
		//////////////////////////////////////////////////////////////////////////////////////////
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 13);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 14);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 14);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 14);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 14);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 15);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 15);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 15);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 15);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 16);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 16);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 16);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 18);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 18);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 19);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 19);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 19);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 19);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 20);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 20);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 20);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 20);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 21);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 21);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 21);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 21);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 22);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 22);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 22);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 23);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 23);  put("hour", 10);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 23);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 23);  put("hour", 22);;}});	
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 24);  put("hour", 4);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 24);  put("hour", 16);;}});
//		add(new HashMap<String, Integer>(){{put("month", 4); put("day", 24);  put("hour", 22);;}});	
//////////////////////////////////////////////////////////////////////////////////////////

		// Complete later in sha Allah SWT; done Alhamduli Allah SWT
	}};
	
	
	// packet range (LL)
	ArrayList<Integer> PACKET_RANGE = Utils.range(Packet.HEADER_LENGTH,Packet.MTU+1,8);
	ArrayList<Integer> PACKET_RANGE2 = Utils.range(Packet.HEADER_LENGTH,Packet.MTU+1,4);
	
	// packet range (H)
	
	// Security Strategy Enum
	public static final int NONE                     = 0;
	public static final int PAD_TO_MTU               = 1; // Pad to MTU 
	public static final int RFC_COMPLIANT_FIXED_PAD  = 2; // Session Random 255
	public static final int RFC_COMPLIANT_RANDOM_PAD = 3; // Packet Random 255
	public static final int RANDOM_PAD               = 4; // Packet Random MTU 
	public static final int PAD_ROUND_EXPONENTIAL    = 5; // Exponential
	public static final int PAD_ROUND_LINEAR         = 6; // Linear
	public static final int MICE_ELEPHANTS           = 7; // Mice Elephants
	public static final int DIRECT_TARGET_SAMPLING   = 8;
	public static final int WRIGHT_STYLE_MORPHING    = 9;
	public static final int FIXED_PAD                = 10;

	// Classifier enum
	public static final int LIBERATORE_CLASSIFIER    = 0;
	public static final int WRIGHT_CLASSIFIER        = 1;
	public static final int JACCARD_CLASSIFIER       = 2;
	public static final int PANCHENKO_CLASSIFIER     = 3;
	public static final int BANDWIDTH_CLASSIFIER     = 4;
	public static final int ESORICS_CLASSIFIER       = 5;
	public static final int HERRMANN_CLASSIFIER      = 6;
	public static final int TIME_CLASSIFIER          = 10;
	public static final int VNG_CLASSIFIER           = 14;
	public static final int VNG_PLUS_PLUS_CLASSIFIER = 15;
	public static final int VNG_PLUS_PLUS_CLASSIFIER_MODIFIED = 16; // Nov 03, 2014 applying setwise classification
	public static final int SETWISE_CLASSIFIER = 17; // Nov 04, 2014
	public static final int VNG_PLUS_PLUS_CLASSIFIER_DECISIONTREES = 18;
	

	public static int EXTRA = 0;
	
	public static int SPEARMANs_CORRELATION = 0;
	public static int SPEARMANs_CORRELATION_P_VALUE = 1; // pvalue = 0.05
	public static HashMap<String, Integer> correlationStatsAgg = new HashMap<String,Integer>();
	
	// http://psych.unl.edu/psycrs/handcomp/hcspear.PDF
	public static final HashMap<Integer, double[]> SPEARMANs_CORRELATION_CRITICAL_VALUES =
			new HashMap<Integer, double[]>()
			{{
				put(5, new double[]{.900, 1.000, 1.000});
				put(6, new double[]{.829, .886, 1.000});
				put(7, new double[]{.715, .786, .929});
				put(8, new double[]{.620, .715, .881});
				put(9, new double[]{.600, .700, .834});
				put(10, new double[]{.564, .649, .794});
				put(11, new double[]{.537, .619, .764});
				put(12, new double[]{.504, .588, .735});
				put(13, new double[]{.484, .561, .704});
				put(14, new double[]{.464, .539, .680});
				put(15, new double[]{.447, .522, .658});
				put(16, new double[]{.430, .503, .636});
				put(17, new double[]{.415, .488, .618});
				put(18, new double[]{.402, .474, .600});
				put(19, new double[]{.392, .460, .585});
				put(20, new double[]{.381, .447, .570});
				put(21, new double[]{.371, .437, .556});
				put(22, new double[]{.361, .426, .544});
				put(23, new double[]{.353, .417, .532});
				put(24, new double[]{.345, .407, .521});
				put(25, new double[]{.337, .399, .511});
				put(26, new double[]{.331, .391, .501});
				put(27, new double[]{.325, .383, .493});
				put(28, new double[]{.319, .376, .484});
				put(29, new double[]{.312, .369, .475});
				put(30, new double[]{.307, .363, .467});


				put(35, new double[]{.325, .325, .418});
				put(40, new double[]{.304, .304, .393});
				put(45, new double[]{.288, .288, .372});
				put(50, new double[]{.273, .273, .354});
				put(60, new double[]{.250, .250, .325});
				put(70, new double[]{.232, .232, .302});
				put(80, new double[]{.217, .217, .283});
				put(90, new double[]{.205, .205, .267});
				put(99999, new double[]{.195, .195, .254}); // df (degree of freedom = infinity)
			}};
}


















