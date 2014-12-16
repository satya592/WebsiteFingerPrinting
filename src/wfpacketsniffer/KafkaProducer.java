package wfpacketsniffer;
import java.util.Properties;

import kafka.javaapi.producer.Producer;

import kafka.message.Message;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


public class KafkaProducer {
	Producer<String, String> producer;
	int count = 1;
    public void init() {
    	
    	Properties props = new Properties();
    	 props.put("metadata.broker.list", "127.0.0.1:9092");
    	props.put("zk.connect", "127.0.0.1:2181");
    	props.put("serializer.class", "kafka.serializer.StringEncoder");
    	ProducerConfig config = new ProducerConfig(props);
    	 producer = new Producer<String, String>(config);

         

    }
    
    public void sendKafkaData(String mydata){
    	
    	KeyedMessage<String, String> data = new KeyedMessage<String, String>("tester1", ""+count, mydata);
        count++;
    	 producer.send(data);	
    }
   
}