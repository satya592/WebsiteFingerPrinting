package classifiers;

import intro.Utils;
import intro.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class wekaAPI {

	public static List<String> execute(String trainingFile, String testingFile, String classifier, List<String> args ) throws IOException, InterruptedException
	{
		List<String> myArgs = new ArrayList<String>();
		
		myArgs.add("java");
		myArgs.add("-Xmx" + config.JVM_MEMORY_SIZE);
		myArgs.add("-classpath");
		myArgs.add("$CLASSPATH:" + config.WEKA_JAR);
//		myArgs.add("" + config.WEKA_JAR);
		myArgs.add(classifier);
		myArgs.add("-t");
		myArgs.add(config.CACHE_DIR + trainingFile + ".arff");
//		myArgs.add("/home/varun/workspace/WF/cache/" + trainingFile + ".arff");
		myArgs.add("-T");
		myArgs.add(config.CACHE_DIR + testingFile + ".arff");
//		myArgs.add("/home/varun/workspace/WF/cache/" + testingFile + ".arff");
		myArgs.add("-v");
		myArgs.add("-classifications");
		myArgs.add("weka.classifiers.evaluation.output.prediction.CSV");
		
		for (String arg : args)
			myArgs.add(arg);
		
		String strWekaProcess = Utils.Join(myArgs, " ");
		
		String tmpWekaOutFile = config.TMP_OUTPUT_DIR + "tmp.out";
		
		String[] cmds = { "/bin/sh", "-c", strWekaProcess + " | tee " + tmpWekaOutFile };
		Process p = Runtime.getRuntime().exec(cmds);
		
//		Class Process
//
//		Because some native platforms only provide limited buffer size for standard input and output streams, failure to promptly write the input stream or read the output stream of the subprocess may cause the subprocess to block, and even deadlock.
//
//		Fail to clear the buffer of input stream (which pipes to the output stream of subprocess) from Process may lead to a subprocess blocking.
		BufferedReader read = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
		
		while ((read.readLine()) != null) {}
//		end Class Process
		
		p.waitFor();
		
		/// debug
//		 while (read.ready()) {
//             System.out.println(read.readLine());
//         }
		 /// end debug
		 
		System.out.println("Weka Process Done.");
		
		double totalPredictions = 0.0;
		double totalCorrectPredictions = 0.0;
		
		double accuracy = 0.0;
		List<String> debugInfo = new ArrayList<String>();
		List<String> accuracy_debugInfo = new ArrayList<String>();
		
		boolean parsing = false;
		
		BufferedReader reader = new BufferedReader(new FileReader(tmpWekaOutFile));
		String line = "";
		
		while ((line = reader.readLine().trim()) != null)
		{
			if (parsing == true)
			{
				if (line.equals(""))
					break;
				String[] lineBits = line.split(",");
				String actualClass = lineBits[1].split(":")[1];
				String predictedClass = lineBits[2].split(":")[1];
				debugInfo.add(actualClass + "," + predictedClass);
				totalPredictions += 1.0;
				if (actualClass.equalsIgnoreCase(predictedClass))
						totalCorrectPredictions += 1.0;			
			}
			if (line.equalsIgnoreCase("inst#,actual,predicted,error,prediction"))
				parsing = true;

		}
		
		accuracy = totalCorrectPredictions / totalPredictions * 100.0;
		
		accuracy_debugInfo.add("" + accuracy);
		
		for (String str : debugInfo)
			accuracy_debugInfo.add(str);
		
		return accuracy_debugInfo; // [acc, (atcual,predicted), (atcual,predicted) ...] [43.269230769230774, 1011289,1000866, 1011289,1011311, 1011289,1000866, 1011289,1011289 ...]
	}

}
