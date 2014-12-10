package intro;

import java.io.File;
import java.io.IOException;
import java.util.*;

import MarkovLogicNetwork.SpearmansCorrelation;
import classifiers.wekaAPI;

import java.util.UUID;

public class Test2 {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		File directory = new File(config.PCAP_ROOT);
		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isDirectory()) {
				System.out.println(file);
			}
		}

		List<String> list = new ArrayList<String>() {
			{
				add("str1");
				add("str2");
			}
		};

		System.out.println("List : " + list.get(0));

		List<HashMap<String, Integer>> mapList = new ArrayList<HashMap<String, Integer>>() {
			{
				add(new HashMap<String, Integer>() {
					{
						put("month", 1);
						put("day", 2);
						put("hour", 3);
					}
				});
				add(new HashMap<String, Integer>() {
					{
						put("month", 4);
						put("day", 5);
						put("hour", 6);
						;
					}
				});
			}
		};

		System.out.println(mapList.get(1).get("day"));

		// /////
		// List<String> myArgs = new ArrayList<String>();
		//
		// myArgs.add("java");
		// myArgs.add("-Xmx" + config.JVM_MEMORY_SIZE);
		// myArgs.add("-classpath");
		// myArgs.add("$CLASSPATH:" + config.WEKA_JAR);
		// myArgs.add("weka.classifiers.bayes.NaiveBayes");
		// myArgs.add("-t");
		// myArgs.add("./train.arff");
		// myArgs.add("-T");
		// myArgs.add("./test.arff");
		// myArgs.add("-v");
		// myArgs.add("-classifications");
		// myArgs.add("weka.classifiers.evaluation.output.prediction.CSV");
		//
		// //for (String arg : args)
		// myArgs.add("-K");
		//
		// String strWekaProcess = Utils.Join(myArgs, " ");
		//
		// //strWekaProcess = "java -version ";
		// System.out.println("strWekaProcess: " + strWekaProcess);
		// //String[] cmds = { "/bin/sh", "-c", strWekaProcess +
		// " | tee /data/kld/papers/PeekaBoo/outputFiles/tmp.out" }; // working
		// String[] cmds = { "/bin/sh", "-c", strWekaProcess + " | tee " +
		// config.TMP_OUTPUT_DIR + "tmp.out" }; // working
		// // String[] cmds = { "/bin/sh", "-c",
		// " ls -l | tee /data/kld/papers/PeekaBoo/outputFiles/tmp.out" };
		// Process p = Runtime.getRuntime().exec(cmds);
		// p.waitFor();
		// System.out.println("Done.");

		List<String> argus = new ArrayList<String>() {
			{
				add("-K"); // for NaiveBayes
			}
		};
		// List<String> exeWekaList = wekaAPI.execute("./train.arff",
		// "./test.arff", "weka.classifiers.bayes.NaiveBayes", argus);
		// System.out.println(exeWekaList);

		String uuid = UUID.randomUUID().toString();
		System.out.println("uuid = " + uuid);

		for (int i : Utils.range(5)) {
			System.out.println("intArray[" + i + "] = " + i);
		}

		int base = 600;
		int x = 100;
		int[] xArr = { 100, 300, 400, 600, 700, 800, 1000, 1200, 1300, 1400,
				1800, 1900, 2400, 2500 };

		for (int x2 : xArr) {
			System.out.println(x2 + " , rounded = "
					+ (int) Math.round((float) x2 / base));
			System.out.println("resutl = " + (int) base
					* Math.round((float) x2 / base));
		}
		// System.out.println("rounded = " + (int) Math.round((float)x / base));
		// System.out.println("resutl = " + (int) base * Math.round((float)x /
		// base));

		ArrayList<Integer> rList = Utils.range(8, 256, 8); // r belogs to {0, 8,
															// 16 . . . , 248}
		int rand = rList.get(new Random().nextInt(rList.size()));
		System.out.println("rand = " + rand);

		HashMap<Double, Double> hm = new HashMap<Double, Double>();
		hm.put(34.0, 1.0);
		hm.put(34.0, 5.0);
		System.out.println("hm: " + hm);

		TreeMap<Double, Double> tm = new TreeMap<Double, Double>();
		tm.put(34.0, 1.0);
		tm.put(34.0, 5.0);
		System.out.println("tm: " + tm);

		ArrayList<Double> al = new ArrayList<Double>();
		al.add(34.0);
		al.add(34.0);
		System.out.println("al: " + al);

		ArrayList<Double> originalList = new ArrayList<Double>();
		// originalList.add(3.0);
		originalList.add(777.0);
		originalList.add(2.0);
		originalList.add(0.0);
		originalList.add(5.0);
		originalList.add(2.0);
		originalList.add(5.0);
		originalList.add(4.0);
		originalList.add(2.0);
		originalList.add(4.0);
		originalList.add(89.0);

		// originalList.add(0.0);
		// originalList.add(0.0);
		// originalList.add(0.0);
		// originalList.add(1.0);
		// originalList.add(0.0);
		// originalList.add(0.0);
		// originalList.add(0.0);
		// originalList.add(0.0);

		ArrayList<Double> sortedList = new ArrayList<Double>();
		for (Double val : originalList)
			sortedList.add(val);

		System.out.println("Original List: " + originalList);
		System.out.println("Sorted List (before sort: " + sortedList);

		java.util.Collections.sort(sortedList);
		System.out.println("Sorted List (after sort: " + sortedList);

		ArrayList<Double> rankedList = new ArrayList<Double>();

		// for (int index = 0; index < originalList.size(); index++)
		// {
		// double val = originalList.get(index);
		// rankedList.add((double)(sortedList.indexOf(val)+1));
		// }

		for (int index = 0; index < originalList.size(); index++) 
		{
			
			double val = originalList.get(index);

			int i = sortedList.indexOf(val); // get index from the sorted list
			double sum = sortedList.indexOf(val) + 1; // numerator to find avg
			double count = sortedList.indexOf(val) + 1;
			double k = 1.0; // denominator to find avg
			while (i + 1 < sortedList.size()
					&& (double) sortedList.get(i) == (double) sortedList
							.get(i + 1)) {
				k++;
				count++;
				sum += count;
				i++;

			}

			if (k > 1) {
				double avg = sum / k;
				rankedList.add(avg);
			} else {
				rankedList.add((double) (sortedList.indexOf(val) + 1));
			}

		}

		System.out.println("Ranked List: " + rankedList);

		for (int i = 0; i < 5; i++) {
			System.out.println("salam");
			i++;
		}
		
		String output = "train2.arff";
		SpearmansCorrelation.readArff(output);
		output = "train3.arff";
		SpearmansCorrelation.readArff(output);
		
		HashMap<Integer, double[]> hms = new HashMap<Integer, double[]>();
		
		hms.put(1, new double[]{.98, .55, .44});
		
		//System.out.println(hms.get(1)[1]);
		
		String classes = "{webpage123,webpage321}";
		String[] classesArray = classes.split("[,\\{]");
		System.out.println(classesArray[0]);
		
		String s = "";
		StringTokenizer stok = new StringTokenizer(",{}");
		if (stok.hasMoreTokens())
			s = stok.nextToken();
			
			System.out.println(s);

	}

}
