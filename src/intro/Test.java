package intro;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class Test {
	public static void main(String[] args) throws IOException{
//		System.out.println(Utils.range(0, 13, 3));
//		
//		System.out.println(Utils.range(0, 10));
//		
//		for (int i : Utils.range(10)){
//			System.out.println((i));
//		}
//		
//		for (int i : Utils.range(0, 10)){
//			System.out.println((i));
//		}
//		
		
		Map<String, Double> map1 = new HashMap<String, Double>();
		map1.put("0-32", 1.0);
		map1.put("0-5", 6.0);
		map1.put("0-6", 12.0);
		map1.put("0-8", 18.0);
		map1.put("0-56", 24.0);
		
		Map<String, Double> map2 = new HashMap<String, Double>(map1);
		
		System.out.println("map1: " + map1);
		System.out.println("map2: " + map2);
		
		System.out.println(map1.get("0-32"));
		Double l = (map1.get("0-5") == null ? null : map1.get("0-5"));
		Double r = (map2.get("0-32") == null ? null : map2.get("0-32"));
		System.out.println("l - r= " + Math.abs(r - l));
		
		List<String> copyMapKeys = new ArrayList<String>(map1.keySet());
		System.out.println("Copied Map Keys: " + copyMapKeys);
		System.out.println("Copied Map Keys[3]: " + copyMapKeys.get(3));
		
		String worstKey = "1-15";
		String[] bits = worstKey.split("-");
		
		System.out.println("bits[0] = " + bits[0] + " bits[1] = " + bits[1]);
		
		Double b = map1.get("0-5322 e"); // null
		System.out.println("b = " + b);
		
		String absFilePath = pcapparser.__constructAbsolutePath(2, 10, 13, 8); // month, day, hour, webpageId
		
		System.out.println("absFilePath = " + absFilePath);
		
//		String pattern = "2006-02-10T13*-0";
//		String joinedPath = new File(config.PCAP_ROOT, "2006-"+021 // 17 
//                +"-"+10
//                +"T"+10
//                +"*/*"
//                +"-"+1).toString();
//		System.out.println("Path: " + joinedPath);
		
//		Map<String, Integer> testMap = new HashMap<>();
		
//		String pattern = new File("", "2006-"+02
//                +"-"+10
//                +"T"+10
//                +"*/*"
//                +"-"+1).toString();
		
//		String pattern = "2006-"+02
//                +"-"+10
//                +"T"+13
//                +"*/*"
//                +"-"+1;
		
//		System.out.println("PCAP_ROOT = " + config.PCAP_ROOT);
//		
//		System.out.println("pattern = " + pattern);
//
////		PathMatcher matcher =
////		    FileSystems.getDefault().getPathMatcher("glob:" + pattern);
////		System.out.println("Matcher: " + matcher.toString());
//	
//		Path startDir = Paths.get(config.PCAP_ROOT);
//
//		//String pattern2 = "*.{txt,doc}";
//
//		FileSystem fs = FileSystems.getDefault();
//		final PathMatcher matcher = fs.getPathMatcher("glob:" + "*-1");
//
//		System.out.println();
//		
//		final ArrayList<String> result = new ArrayList<String>();
//		
//		FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<Path>() {
//		    @Override
//		    public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) {
//		        Path name = file.getFileName();
//		        if (matcher.matches(name)) {
//		            System.out.print(String.format("Found matched file: '%s'.%n", file));
//		            result.add(file.toString());
//		        } 
////		        else 
////		        {
////		        	System.out.print("Not Found matched file:");
////		        }
//		        return FileVisitResult.CONTINUE;
//		    }
//		};
//		
//		File directory = new File(config.PCAP_ROOT);
//		File[] fList = directory.listFiles();
//		
//		for (File file : fList)
//		{
//			if (file.isDirectory() && file.toString().matches(".*2006-02-10T13.*"))
//			{
//				System.out.println("Folder " + file.toString());
//				Files.walkFileTree(Paths.get(file.toString()), matcherVisitor);
//			}
//		}
//		
//		//Files.walkFileTree(startDir, matcherVisitor);
//
//		System.out.println("result: " + result.get(0));
		//System.out.println();
		
		List<String> strSeq = new ArrayList<String>()
				{{
					add("class1");
					add("class2");
					add("class3");
					add("class4");
					add("class5");
				}};
		
		//System.out.println("Joinded Strings: " + Utils.Join(strSeq, ",").toString());
		
		int[] roundArray = {200, 350, 600, 700, 1000, 1200, 1400, 1800, 2000, 2399, 2500};
		
		for (int x : roundArray)
			System.out.println("x = " + x + ", round = " + roundArbitrary(x, 5000));
		
	}
	
	public static int roundArbitrary(int x, int base)
	{
		int value = (int) base * Math.round((float)x / base); // 0, 600, 1200, 1800, 2400, ... best resutls according to Panchenko et al
		return value;
	}
}



























