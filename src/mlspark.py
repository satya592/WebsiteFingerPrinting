import sys
from pyspark import SparkContext
from pyspark.mllib.util import MLUtils
from pyspark.mllib.classification import NaiveBayes
from pyspark.mllib.linalg import Vectors
from pyspark.mllib.regression import LabeledPoint 

def PrintIt (line):
        print line

def ParseData (line):
	parts = line.split(',')

	label = int(parts[0].split('page')[1])
	features = []
	for value in parts[1].split(' '):
		features.append(float(value))

	return LabeledPoint(label, features)

sc = SparkContext("local", "App Name")
data = sc.textFile("hdfs://localhost:9000/spark/hehe-1418862700000.haha")
parsed_data = data.map(lambda line: ParseData(line))
parsed_data.foreach(PrintIt)

# Train a naive Bayes model.
model = NaiveBayes.train(parsed_data, 1.0)

# Make prediction.
prediction = model.predict([0.0, 0.0])
