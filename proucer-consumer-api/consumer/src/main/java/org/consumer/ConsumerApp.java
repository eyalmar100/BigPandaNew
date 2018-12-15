//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.consumer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.common.ConfigLoader;
import org.consumer.redis.RedisDataExractor;

public class ConsumerApp {
    private KafkaConsumer<String, String> bpConsumer = null;
    public static final Logger logger = Logger.getLogger(ConsumerApp.class);
    private RedisDataExractor dataExractor = new RedisDataExractor();

    public ConsumerApp(String topicName) {
        Properties props = ConfigLoader.getConsumerConfigProps();
        this.initKafkaConsumer(topicName, props);
    }

    boolean hasConnectionToRedis() {
        return this.dataExractor.connectToRedisServer();
    }

    public static void main(String[] args) {
        logger.info(" starting consumer program ..");
        String topicName = args[0];
        if (topicName == null) {
            logger.error("No topic name provided .. exit program");
            System.exit(1);
        }

        ConsumerApp consumerApp = new ConsumerApp(topicName);
        if (!consumerApp.hasConnectionToRedis()) {
            logger.error("No Connection to redis . exit program");
            System.exit(1);
        }

        consumerApp.consume();
    }

    private void consume() {
        while(true) {
        	System.out.println("ConsumerApp.consume()");
            ConsumerRecords<String, String> records = this.bpConsumer.poll(1000L);
            if (records != null && !records.isEmpty()) {
                this.processData(records);
            }
        }
    }

    private void initKafkaConsumer(String topicName, Properties props) {
        this.bpConsumer = new KafkaConsumer(props);
        ArrayList<TopicPartition> partitions = new ArrayList();
        partitions.add(new TopicPartition(topicName, 0));
        this.bpConsumer.assign(partitions);
        Set<TopicPartition> assignedPartitions = this.bpConsumer.assignment();
    }

    private void processData(ConsumerRecords<String, String> records) {
        System.out.println("ConsumerApp.processData()");
        Iterator var2 = records.iterator();

        while(var2.hasNext()) {
            ConsumerRecord<String, String> record = (ConsumerRecord)var2.next();
            
            logger.info(String.format("Value: %s", ((String)record.value()).toString()));

            try {
                String data = (String)record.value();
                this.dataExractor.processData(data);
            } catch (Exception var5) {
                logger.info("bad data : ignoring . ");
            }
        }

    }
}
