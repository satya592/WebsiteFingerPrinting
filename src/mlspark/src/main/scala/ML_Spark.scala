import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

object ML_Spark {

	def main (args: Array[String]) {

		val conf = new SparkConf().setAppName("ML Spark")
		conf.setMaster("local[2]")
    		val sc = new SparkContext(conf)
		val data = sc.textFile("hdfs://127.0.0.1:9000/input/trainingdata")

		data.collect.foreach(println)

		val parsedData = data.map { 
			line => val parts = line.split(',')
  			LabeledPoint(parts(0).split("page")(1).toInt, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
		}

		val errors = Array(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
		val splits = parsedData.randomSplit(Array(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9), seed = 11L)
		for( a <- 0 to 8){
			val test = splits(a)
			val training = parsedData.subtract(splits(a))	
			val model = NaiveBayes.train(training, lambda = 1.0)
			val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
			val wronglyClassified = 1.0 * predictionAndLabel.filter(x => x._1 != x._2).count()
			errors(a) = wronglyClassified/test.count
		}

                val test = parsedData
                val training = parsedData
                val model = NaiveBayes.train(training, lambda = 1.0)
                val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
                val wronglyClassified = 1.0 * predictionAndLabel.filter(x => x._1 != x._2).count()
                errors(9) = wronglyClassified/test.count


		println("Naive Bayes training error: ")
		for(b <- 0 to 8){
			var testing = b * 0.1 + 0.1
			var training = 1 - testing
			println("train:test (" + training + ":" + testing + ")= " + errors(b) + "\n")
		}
		println("train:test (1:1)= " + errors(9) + "\n")
	}
}
