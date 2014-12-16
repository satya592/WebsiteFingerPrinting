Follow tutorial to setup kafka
http://kafka.apache.org/documentation.html#quickstart 
step 1 to 3
for step 3 use
use topic = tester1 

No need to run producer or consumer commands

sbt/bin/sbt clean to clean

sbt/bin/sbt package


sbt/bin/sbt "run localhost:2181 my-group tester1 1" 


