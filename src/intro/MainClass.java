package intro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import org.apache.commons.cli.*;

import countermeasures.*;
import classifiers.*;

public class MainClass {

	public static void  process(String[] args) throws Exception {
		String runID = Utils.getRandomAlphaNumericString();

		// / Command Line Parsing ///
		CommandLineParser parser = new BasicParser();
		Options options = new Options();

		options.addOption("N", "N", true,
				"[int] : use [int] websites from the dataset "
						+ "\n from which we will use to sample a privacy "
						+ "\n set k in each experiment \n (default 775)");
		options.addOption("k", "k", true,
				"[int] : the size of the privacy set (default 2)");
		options.addOption("d", "d", true, "[int]: dataset to use"
				+ "\n 0: Liberatore and Levine Dataset (OpenSSH)"
				+ "\n 1: Herrmann et al. Dataset (OpenSSH)"
				+ "\n 2: Herrmann et al. Dataset (Tor)" + "\n (default 1) ");
		options.addOption("C", "C", true, "[int] : classifier to run"
				+ "\n 0: Liberatore Classifer "
				+ "\n 1: Wright et al. Classifier "
				+ "\n 2: Jaccard Classifier "
				+ "\n 3: Panchenko et al. Classifier "
				+ "\n 5: Lu et al. Edit Distance Classifier "
				+ "\n 6: Herrmann et al. Classifier "
				+ "\n 4: Dyer et al. Bandwidth (BW) Classifier "
				+ "\n 10: Dyer et al. Time Classifier "
				+ "\n 14: Dyer et al. Variable n-gram (VNG) Classifier "
				+ "\n 15: Dyer et al. VNG++ Classifier "
				+ "\n 16: VNG++ Modified (Setwise Classification - Entities) using NaiveBayse Classifier. Should be with option -x 1."
				+ "\n 17: Setwise Classifier (Entities) using J48 Decision Trees. Should be with option -x 1."
				+ "\n 18: Dyer et al. VNG++ Classifier Using J48 Decision Trees"
				+ "\n (default 0)");
		options.addOption("c", "c", true, "[int]: countermeasure to use "
				+ "\n 0: None " + "\n 1: Pad to MTU "
				+ "\n 2: Session Random 255 " + "\n 3: Packet Random 255 "
				+ "\n 4: Pad Random MTU " + "\n 5: Exponential Pad "
				+ "\n 6: Linear Pad " + "\n 7: Mice-Elephants Pad "
				+ "\n 8: Direct Target Sampling " + "\n 9: Traffic Morphing "
				+ "\n (default 0) ");
		options.addOption("n", "n", true,
				"[int]: number of trials to run per experiment (default 1)");
		options.addOption("t", "t", true,
				"[int]: number of training traces to use per experiment (default 16)");
		options.addOption("T", "T", true,
				"[int]: number of testing traces to use per experiment (default 4)");
		options.addOption("r", "r", true, "Run ID");

		options.addOption("h", "help", false, "Shows this help");

		options.addOption("x", "Extra", true, "Extra: Setwise Classification"
				+ "\n 0: no Extra " + "\n 1: Extra " + "\n (default 0)");
		
		options.addOption("s", "spearman", true, "Spearman's Correlation."
				+ "\n 0: No correlation required " + "\n 1: Find Correlation " + "\n (default 0)");
		
		options.addOption("p", "pvalue", true, "Spearman's Correlation pvalue."
				+ "\n 0: p-value = 0.10" 
				+ "\n 1: p-value = 0.05 " 
				+ "\n 2: p-value = 0.01 " 
				+ "\n (default 1)");

		try {
			CommandLine commandLine = parser.parse(options, args);

			if (commandLine.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("Command Line Parameters Help", options);
				System.exit(0); // exit
			}

			if (commandLine.hasOption("k"))
				config.BUCKET_SIZE = Integer.parseInt((commandLine
						.getOptionValue("k")));

			if (commandLine.hasOption("C"))
				config.CLASSIFIER = Integer.parseInt((commandLine
						.getOptionValue("C")));

			if (commandLine.hasOption("d"))
				config.DATA_SOURCE = Integer.parseInt((commandLine
						.getOptionValue("d")));

			if (commandLine.hasOption("c"))
				config.COUNTERMEASURE = Integer.parseInt((commandLine
						.getOptionValue("c")));

			if (commandLine.hasOption("N"))
				config.TOP_N = Integer.parseInt((commandLine
						.getOptionValue("N")));

			if (commandLine.hasOption("t"))
				config.NUM_TRAINING_TRACES = Integer.parseInt((commandLine
						.getOptionValue("t")));

			if (commandLine.hasOption("T"))
				config.NUM_TESTING_TRACES = Integer.parseInt((commandLine
						.getOptionValue("T")));

			if (commandLine.hasOption("n"))
				config.NUM_TRIALS = Integer.parseInt((commandLine
						.getOptionValue("n")));

			if (commandLine.hasOption("r"))
				runID = commandLine.getOptionValue("r");

			if (commandLine.hasOption("x"))
				config.EXTRA = Integer
						.parseInt(commandLine.getOptionValue("x"));
			
			if (commandLine.hasOption("s"))
				config.SPEARMANs_CORRELATION = Integer
						.parseInt(commandLine.getOptionValue("s"));
			
			if (commandLine.hasOption("p"))
				config.SPEARMANs_CORRELATION_P_VALUE = Integer
						.parseInt(commandLine.getOptionValue("p"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Command Line Parameters Help", options);
		}
		// / End Command Line Parsing ///

		List<String> outputFilenameArray = new ArrayList<String>() {
			{
				add("results");
				add("k" + config.BUCKET_SIZE);
				add("c" + config.COUNTERMEASURE);
				add("d" + config.DATA_SOURCE);
				add("C" + config.CLASSIFIER);
				add("N" + config.TOP_N);
				add("t" + config.NUM_TRAINING_TRACES);
				add("T" + config.NUM_TESTING_TRACES);
			}
		};

		String outputFilename = new File(config.OUTPUT_DIR, Utils.Join(
				outputFilenameArray, ".")).toString();

		// Check config files
		List<File> configFiles = new ArrayList<File>() {
			{
				add(new File(config.CACHE_DIR));
				add(new File(config.COUNTERMEASURE_DIR));
				add(new File(config.CLASSIFIERS_DIR));
				add(new File(config.OUTPUT_DIR));
				add(new File(config.TMP_OUTPUT_DIR));
				add(new File(config.CORRELATION_DIR));
			}
		};

		for (File f : configFiles)
			if (!f.exists())
				f.mkdir();

		File fOut = new File(outputFilename + ".output");
		if (!fOut.exists()) {
			List<String> banner = new ArrayList<String>() {
				{
					add("accuracy");
					add("overhead");
					add("timeElapsedTotal");
					add("timeElapsedClassifier");
				}
			};
			PrintWriter write = new PrintWriter(new File(outputFilename
					+ ".output"));
			write.println(Utils.Join(banner, ","));
			write.close();
		}

		File fDebug = new File(outputFilename + ".debug");
		if (!fDebug.exists()) {
			PrintWriter write = new PrintWriter(new File(outputFilename
					+ ".debug"));
			write.close();
		}

		int startIndex = 0;
		int endIndex = 0;
		int maxTracesPerWebsiteH = 0;

		if (config.DATA_SOURCE == 0) {
			startIndex = config.NUM_TRAINING_TRACES;
			endIndex = config.DATA_SET.size() - config.NUM_TESTING_TRACES;
		} else if (config.DATA_SOURCE == 1) {
			maxTracesPerWebsiteH = 160;
			startIndex = config.NUM_TRAINING_TRACES;
			endIndex = maxTracesPerWebsiteH - config.NUM_TESTING_TRACES;
		} else if (config.DATA_SOURCE == 2) {
			maxTracesPerWebsiteH = 18;
			startIndex = config.NUM_TRAINING_TRACES;
			endIndex = maxTracesPerWebsiteH - config.NUM_TESTING_TRACES;
		}

		long startStart;

		int preCountermeasureOverhead;
		int postCountermeasureOverhead;

		for (int i : Utils.range(config.NUM_TRIALS)) {
			startStart = System.currentTimeMillis();

			int[] originalWebpageIds = Utils.range(0, config.TOP_N - 1);
			//Utils.shuffleArray(originalWebpageIds);

			int[] webpageIds = Arrays.copyOfRange(originalWebpageIds, 0,
					config.BUCKET_SIZE); // Privacy set; get first
											// config.BUCKET_SIZE elements

			//int seed = Utils.randInt(startIndex, endIndex);
                        int seed = 2;
			preCountermeasureOverhead = 0;
			postCountermeasureOverhead = 0;

			String classifier = intToClassifier(config.CLASSIFIER);
			String countermeasure = intToCountermeasure(config.COUNTERMEASURE);

			List<HashMap<String, String>> trainingSet = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, String>> testingSet = new ArrayList<HashMap<String, String>>();

			Webpage targetWebpage = null; // For distribution-based padding, later in sha Allah SWT

			int extraCtr = 0; // entity counter for setwise clustering Nov 04,
								// 2014.
								// each trace is a unique entity

			for (int webpageId : webpageIds) {
				List<Webpage> webpagesTrain = new ArrayList<Webpage>(); // Actually
																		// get
																		// training
																		// traces
																		// for
																		// that
																		// webpage
				List<Webpage> webpagesTest = new ArrayList<Webpage>(); // get
																		// testing
																		// traces
																		// for
																		// that
																		// webpage
				ArrayList<Integer> webpageIdAsList = new ArrayList<Integer>();
				webpageIdAsList.add(webpageId);

				if (config.DATA_SOURCE == 0) // LL dataset
				{
					/*webpagesTrain = Datastore.getWebpagesLL(webpageIdAsList,
							seed - config.NUM_TRAINING_TRACES, seed);//16
					webpagesTest = Datastore.getWebpagesLL(webpageIdAsList,
							seed, seed + config.NUM_TESTING_TRACES);//4*/
                                    webpagesTrain = Datastore.getWebpagesLL(webpageIdAsList,
							0, 1);//16
					webpagesTest = Datastore.getWebpagesLL(webpageIdAsList,
							2, 3);//4
                                    
				} else if (config.DATA_SOURCE == 1 || config.DATA_SOURCE == 2) {
					// System.out.println();
					// Herrmann data set (MySQL). Later in sha Allah
				}
				Webpage webpageTrain = webpagesTrain.get(0); // Though
																// webpagesTrain
																// has one
																// Webpage :)//actually one website only
				Webpage webpageTest = webpagesTest.get(0);

				if (targetWebpage == null)
					targetWebpage = webpageTrain;

				preCountermeasureOverhead += webpageTrain.getBandwidth();
				preCountermeasureOverhead += webpageTest.getBandwidth();

				// metadata = None
				// if config.COUNTERMEASURE in [config.DIRECT_TARGET_SAMPLING,
				// config.WRIGHT_STYLE_MORPHING]:
				// metadata = countermeasure.buildMetadata( webpageTrain,
				// targetWebpage )
				i = 0;
				List<Webpage> trainTestWebpages = new ArrayList<Webpage>();//
				trainTestWebpages.add(webpageTrain);
				trainTestWebpages.add(webpageTest);

				for (Webpage w : trainTestWebpages) {//actually just 2 webpages
					for (Trace trace : w.getTraces()) {
						Trace traceWithCountermeasure;
						// if countermeasure:
						// if config.COUNTERMEASURE in
						// [config.DIRECT_TARGET_SAMPLING,
						// config.WRIGHT_STYLE_MORPHING]:
						// if w.getId()!=targetWebpage.getId():
						// traceWithCountermeasure =
						// countermeasure.applyCountermeasure( trace, metadata )
						// else:
						// traceWithCountermeasure = trace
						// else:
						// traceWithCountermeasure =
						// countermeasure.applyCountermeasure( trace )
						// else:

						if (!countermeasure.equals("")) {
							traceWithCountermeasure = getTraceWithCountermeasure(
									countermeasure, trace);
						} else {
							traceWithCountermeasure = trace; // no
																// countermeasure
						}

						postCountermeasureOverhead += traceWithCountermeasure
								.getBandwidth(999); // None

						if (config.EXTRA == 0) {
							HashMap<String, String> instance = new HashMap<String, String>();

							instance = getClassifierInstance(classifier,
									traceWithCountermeasure);

							if (instance.size() > 0) {
								if (i == 0)
									trainingSet.add(instance);
								else if (i == 1)
									testingSet.add(instance);

							}

						} else if (config.EXTRA == 1) {
							extraCtr++;
							ArrayList<HashMap<String, String>> instances = getClassifierInstances(
									classifier, traceWithCountermeasure,
									extraCtr);
							if (instances.size() > 0) {
								if (i == 0)
									for (HashMap<String, String> inst : instances)
										trainingSet.add(inst);
								else if (i == 1)
									for (HashMap<String, String> inst : instances)
										testingSet.add(inst);

							}
						}
					}
					i += 1;
				}

			}

			long startClass = System.currentTimeMillis();
			// List<String> accuracy_debugInfo = LiberatoreClassifier.classify(
			// runID, trainingSet, testingSet);
			List<String> accuracy_debugInfo = getClassification(classifier,
					runID, trainingSet, testingSet);
			long end = System.currentTimeMillis();

			String overhead = postCountermeasureOverhead + "/"
					+ preCountermeasureOverhead;

			List<String> output = new ArrayList<String>();
			output.add(accuracy_debugInfo.get(0)); // accuracy
			output.add(overhead);
			output.add("" + (end - startStart));
			output.add("" + (end - startClass));

			String summary = Utils.Join(output, ", ");

			// Append to .output file
			PrintWriter writeOutput2 = new PrintWriter(new BufferedWriter(
					new FileWriter(outputFilename + ".output", true)));
			writeOutput2.println(summary);
			writeOutput2.close();

			// Append to .debug file
			PrintWriter writeDebug2 = new PrintWriter(new BufferedWriter(
					new FileWriter(outputFilename + ".debug", true)));
			for (String entry : accuracy_debugInfo) {
				String[] strDebug = entry.split(",");
				if (strDebug.length > 1) // pass first string which is accuracy.
											// [acc, (atcual,predicted),
											// (atcual,predicted) ...]
											// [43.269230769230774,
											// (1011289,1000866),
											// (1011289,1011311), ...]
					writeDebug2.println(entry);
			}
			writeDebug2.close();

		}
		
		// MLN based on Spearman correlation
		if (config.SPEARMANs_CORRELATION == 1)
		{
			//SpearmansCorrelation.writeMLN();
		}
		
//		PrintWriter writeBurst = new PrintWriter(new File(outputFilename
//				+ ".burst"));
//		writeBurst.println("MLN Features: Size = "
//				+ VNGPlusPlusClassifier.markovDependentFeatures.size());
//
//		for (String burst : VNGPlusPlusClassifier.markovDependentFeatures)
//			writeBurst.println(burst);
//
//		writeBurst.close();

		// System.out.println("MLN Features: Size = " +
		// VNGPlusPlusClassifier.markovDependentFeatures.size() + "\n" +
		// VNGPlusPlusClassifier.markovDependentFeatures);

	}

	public static String intToClassifier(int n) {
		String classifier = "";
		if (n == config.LIBERATORE_CLASSIFIER)
			classifier = "LiberatoreClassifier";
		else if (n == config.WRIGHT_CLASSIFIER)
			classifier = "WrightClassifier";
		else if (n == config.BANDWIDTH_CLASSIFIER)
			classifier = "BandwidthClassifier";
		else if (n == config.HERRMANN_CLASSIFIER)
			classifier = "HerrmannClassifier";
		else if (n == config.TIME_CLASSIFIER)
			classifier = "TimeClassifier";
		else if (n == config.PANCHENKO_CLASSIFIER)
			classifier = "PanchenkoClassifier";
		else if (n == config.VNG_PLUS_PLUS_CLASSIFIER)
			classifier = "VNGPlusPlusClassifier";
		else if (n == config.VNG_CLASSIFIER)
			classifier = "VNGClassifier";
		else if (n == config.JACCARD_CLASSIFIER)
			classifier = "JaccardClassifier";
		else if (n == config.ESORICS_CLASSIFIER)
			classifier = "ESORICSClassifier";
		else if (n == config.VNG_PLUS_PLUS_CLASSIFIER_MODIFIED)
			classifier = "VNGPlusPlusClassifierModified";
		else if (n == config.SETWISE_CLASSIFIER)
			classifier = "SetwiseClassifier";
		else if (n == config.VNG_PLUS_PLUS_CLASSIFIER_DECISIONTREES)
			classifier = "VNGPlusPlusClassifierDecisionTrees";

		return classifier;
	}

	public static String intToCountermeasure(int n) {
		String countermeasure = "";

		switch (n) {
		case config.PAD_TO_MTU:
			countermeasure = "PadToMTU";
			break;
		case config.RFC_COMPLIANT_FIXED_PAD:
			countermeasure = "RFC_COMPLIANT_FIXED_PAD";
			break;
		case config.RFC_COMPLIANT_RANDOM_PAD:
			countermeasure = "RFC_COMPLIANT_RANDOM_PAD";
			break;
		case config.RANDOM_PAD:
			countermeasure = "RANDOM_PAD";
			break;
		case config.PAD_ROUND_EXPONENTIAL:
			countermeasure = "PAD_ROUND_EXPONENTIAL";
			break;
		case config.PAD_ROUND_LINEAR:
			countermeasure = "PAD_ROUND_LINEAR";
			break;
		case config.MICE_ELEPHANTS:
			countermeasure = "MICE_ELEPHANTS";
			break;
		}

		return countermeasure;
	}

	public static HashMap<String, String> getClassifierInstance(
			String classifier, Trace traceWithCountermeasure) {
		HashMap<String, String> instance = new HashMap<String, String>();

		switch (classifier) {
		case "LiberatoreClassifier":
			instance = (HashMap<String, String>) LiberatoreClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "WrightClassifier":
			instance = (HashMap<String, String>) WrightClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "BandwidthClassifier":
			instance = (HashMap<String, String>) BandwidthClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "TimeClassifier":
			instance = (HashMap<String, String>) TimeClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "VNGClassifier":
			instance = (HashMap<String, String>) VNGClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "VNGPlusPlusClassifier":
			instance = (HashMap<String, String>) VNGPlusPlusClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "HerrmannClassifier":
			instance = (HashMap<String, String>) HerrmannClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "PanchenkoClassifier":
			instance = (HashMap<String, String>) PanchenkoClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "VNGPlusPlusClassifierModified":
			instance = (HashMap<String, String>) VNGPlusPlusClassifierModified
					.traceToInstance(traceWithCountermeasure);
			break;
		case "SetwiseClassifier":
			instance = (HashMap<String, String>) SetwiseClassifier
					.traceToInstance(traceWithCountermeasure);
			break;
		case "VNGPlusPlusClassifierDecisionTrees":
			instance = (HashMap<String, String>) VNGPlusPlusClassifierDecisionTrees
					.traceToInstance(traceWithCountermeasure);
			break;

		default:
			System.out.println("No classifier chosen");
			System.exit(0);

			//
		}

		return instance;
	}

	// Extra option -x 1 comes here
	public static ArrayList<HashMap<String, String>> getClassifierInstances(
			String classifier, Trace traceWithCountermeasure, int entity) {
		ArrayList<HashMap<String, String>> instances = new ArrayList<HashMap<String, String>>();

		switch (classifier) {
		case "VNGPlusPlusClassifierModified": // Setwise clustering Nov 04, 14
			instances = VNGPlusPlusClassifierModified.traceToBurstInstances(
					traceWithCountermeasure, entity);
			break;
		case "SetwiseClassifier": // Setwise clustering Nov 04, 14
			instances = SetwiseClassifier.traceToBurstInstances(
					traceWithCountermeasure, entity);
			break;

		default:
			System.out.println("No classifier chosen");
			System.exit(0);

			//
		}

		return instances;
	}

	public static Trace getTraceWithCountermeasure(String countermeasure,
			Trace trace) {
		switch (countermeasure) {
		case "PadToMTU":
			return PadToMTU.applyCountermeasure(trace);
		case "RFC_COMPLIANT_FIXED_PAD":
			return PadRFCFixed.applyCountermeasure(trace);
		case "RFC_COMPLIANT_RANDOM_PAD":
			return PadRFCRand.applyCountermeasure(trace);
		case "RANDOM_PAD":
			return PadRand.applyCountermeasure(trace);
		case "PAD_ROUND_EXPONENTIAL":
			return PadRoundExponential.applyCountermeasure(trace);
		case "PAD_ROUND_LINEAR":
			return PadRoundLinear.applyCountermeasure(trace);
		case "MICE_ELEPHANTS":
			return MiceElephants.applyCountermeasure(trace);
		}

		return trace;
	}

	public static List<String> getClassification(String classifier,
			String runID, List<HashMap<String, String>> trainingSet,
			List<HashMap<String, String>> testingSet) throws Exception {

		List<String> accuracy_debugInfo = new ArrayList<String>();

		switch (classifier) {
		case "LiberatoreClassifier":
			accuracy_debugInfo = LiberatoreClassifier.classify(runID,
					trainingSet, testingSet);
			break;
		case "WrightClassifier":
			accuracy_debugInfo = WrightClassifier.classify(runID, trainingSet,
					testingSet);
			break;
		case "BandwidthClassifier":
			accuracy_debugInfo = BandwidthClassifier.classify(runID,
					trainingSet, testingSet);
			break;
		case "TimeClassifier":
			accuracy_debugInfo = TimeClassifier.classify(runID, trainingSet,
					testingSet);
			break;
		case "VNGClassifier":
			accuracy_debugInfo = VNGClassifier.classify(runID, trainingSet,
					testingSet);
			break;
		case "VNGPlusPlusClassifier":
			accuracy_debugInfo = VNGPlusPlusClassifier.classify(runID,
					trainingSet, testingSet);
			break;
		case "HerrmannClassifier":
			accuracy_debugInfo = HerrmannClassifier.classify(runID,
					trainingSet, testingSet);
			break;
		case "PanchenkoClassifier":
			accuracy_debugInfo = PanchenkoClassifier.classify(runID,
					trainingSet, testingSet);
			break;
		case "VNGPlusPlusClassifierModified":
			accuracy_debugInfo = VNGPlusPlusClassifierModified.classify(runID,
					trainingSet, testingSet);
			break;
		case "SetwiseClassifier":
			accuracy_debugInfo = SetwiseClassifier.classify(runID,
					trainingSet, testingSet);
			break;
		case "VNGPlusPlusClassifierDecisionTrees":
			accuracy_debugInfo = VNGPlusPlusClassifierDecisionTrees.classify(runID,
					trainingSet, testingSet);
			break;
		default:
			System.out.println("No classifier chosen");
			System.exit(0);
		}

		return accuracy_debugInfo;
	}
}
