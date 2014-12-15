package intro;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Utils {
	
	// Mimic range function in Python from 0 to stop
	public static int[] range(int stop)
	{
	   int start = 0;
	   int[] result = new int[stop-start];

	   for(int i=0;i<stop-start;i++)
	      result[i] = start+i;

	   return result;
	}
	
	// Mimic range function in Python from start to stop (step is 1)
	public static int[] range(int start, int stop)
	{
	   int[] result = new int[stop-start];

	   for(int i=0;i<stop-start;i++)
	      result[i] = start+i;

	   return result;
	}
	
	// Mimic range function in Python from start to stop with a specified step
	public static ArrayList<Integer> range(int start, int stop, int step)
	{
	   ArrayList<Integer> result = new ArrayList<Integer>();

	   int x = 0;
	   
//	   for(int i=0;i<(stop-start)/step;i++){
//		   result.add(x);
//		   x += step;
//	   }
	   
	   do{
		   result.add(x);
		   x += step;
	   }while (x < stop - start);

	   return result;
	}
	
	// Folder traversal
	public static ArrayList<String> getPathFiles(String strDateHour, String strWebpageID) throws IOException
	{
		FileSystem fs = FileSystems.getDefault();
		final PathMatcher matcher = fs.getPathMatcher("glob:" + "*-" + strWebpageID); // match site 1 file: "*-1" 

		//System.out.println();
		
		final ArrayList<String> pathList = new ArrayList<String>();
		
		FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<Path>() {
		    @Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) {
		        Path name = file.getFileName();
		        if (matcher.matches(name)) {
		            //System.out.print(String.format("Found matched file: '%s'.%n", file));
		            pathList.add(file.toString());
		        } 
		        return FileVisitResult.CONTINUE;
		    }
		};
		
		File directory = new File(config.PCAP_ROOT);
		File[] fList = directory.listFiles();
		
		for (File file : fList)
		{
			if (file.isDirectory() && file.toString().matches(".*"+strDateHour+".*"))
			{
				//System.out.println("Folder " + file.toString());
				Files.walkFileTree(Paths.get(file.toString()), matcherVisitor); // Only traverse folders with *strDateHour* (Example: *2006-02-10T13*)
			}
		}
		
		//Files.walkFileTree(startDir, matcherVisitor);

		return pathList;
	}
	
	public static String Join(List<String> stringsSequence, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		for (String str : stringsSequence)
			sb.append(str + delimiter);
			
		return new String(sb.deleteCharAt(sb.length() - 1)); // without last comma and spave
	}
	

	public static String getRandomAlphaNumericString(){
		String uuid = UUID.randomUUID().toString();
		return uuid.split("-")[0];
	}
	
	public static void shuffleArray(int[] array)
	{
		    int index, temp;
		    Random random = new Random();
		    for (int i = array.length - 1; i > 0; i--)
		    {
		        index = random.nextInt(i + 1);
		        temp = array[index];
		        array[index] = array[i];
		        array[i] = temp;
		    }
	}
	
	public static void readIntArray(int[] array)
	{
		System.out.println("Array Reading: ");
		for (int i = 0; i < array.length; i++)
			System.out.println(array[i]);
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public  static void writeFile(ArrayList<String> list, String fileName) throws FileNotFoundException
	{
		PrintWriter write = new PrintWriter(new File(fileName));
		
		for (String line : list)
			write.println(line);
		
		write.close();
	}
}













