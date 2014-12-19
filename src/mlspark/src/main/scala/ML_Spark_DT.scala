import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.evaluation._

object ML_Spark_DT {

	def main (args: Array[String]) {

		val conf = new SparkConf().setAppName("ML Spark")
		conf.setMaster("local[2]")
    		val sc = new SparkContext(conf)
		val data = sc.textFile("hdfs://127.0.0.1:9000/input/trainingdata")

		//data.collect.foreach(println)

		val parsedData = data.map { 
			line => val parts = line.split(',')
  			LabeledPoint(parts(0).split("page")(1).toInt, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
		}
		
		val evaluations =
  for (impurity <- Array("gini", "entropy");
       depth    <- Array(1, 5, 6, 7);
       bins     <- Array(10, 20, 30, 40)) yield {
      val model = DecisionTree.trainClassifier(
        parsedData, 4, Map[Int,Int](), impurity, depth, bins)
      val predictionsAndLabels = parsedData.map(example =>
        (model.predict(example.features), example.label)
      )
      val accuracy =
        new MulticlassMetrics(predictionsAndLabels).precision
      ((impurity, depth, bins), accuracy)
    }

evaluations.sortBy(_._2).reverse.foreach(println)		

//		val numClasses = 4
//		val categoricalFeaturesInfo = Map[Int, Int]()
//		val impurity = "gini"
//		val maxDepth = 5
//		val maxBins = 40
//		
//		val model = DecisionTree.trainClassifier(parsedData, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)

		// Evaluate model on training instances and compute training error
//		val labelAndPreds = parsedData.map { point =>
//			val prediction = model.predict(point.features)
//			(point.label, prediction)
//		}
//		val trainErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / data.count
//		println("Training Error = " + trainErr)
//		println("Learned classification tree model:\n" + model)
	}
}
