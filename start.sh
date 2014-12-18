#! /bin/sh
echo "Total inputs $#"
echo "Inputs: $*"

if [ $# != 5 ]; then
echo "Usage: start.sh jarfile kafka_path spark_streaming_path output_path topicname"
exit 1
fi

# Add any process need tobe killed 
kill -9 $(ps | grep $1".jar"  | awk '{ print $1 }')
kill -9 $(ps | grep "QuorumPeerMain"  | awk '{ print $1 }')

echo "Running sniffer.."
sudo java -jar $1".jar"

echo "Running zookeeper&kafka-server in background.."
$2"/bin/zookeeper-server-start.sh" $2"/config/zookeeper.properties" &
$2"/bin/kafka-server-start.sh" $2"/config/server.properties" &

echo "Creating the topic $5"
$2"/bin/kafka-topics.sh" "--create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic" $5

echo "spark setup"
"$3/sbt/bin/sbt" clean
"$3/sbt/bin/sbt" package

echo "spark setup"
"$3/sbt/bin/sbt" "run localhost:2181 my-group $1 1"

# Clean the output_path
rm -rf $4 
mkdir $4

# Get the files from HDFS
hadoop dfs -get / $4

# add scala code with reads the files from $4