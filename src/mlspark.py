import sys
from pyspark import SparkContext
from pyspark.mllib.util import MLUtils
from pyspark.mllib.classification import NaiveBayes
from pyspark.mllib.linalg import Vectors
from pyspark.mllib.regression import LabeledPoint 

def print_it (line):
        print line

sc = SparkContext("local", "App Name")
sc.textFile("hdfs://localhost:9000/spark/hehe-1418862700000.haha").foreach(print_it)

