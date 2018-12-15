//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.common.ConfigLoader;
import org.producer.util.BlackBoxOutputJSONReader;

 
 
public class ProducerApp {
    private static KafkaProducer<String, String> bpProducer = null;
    private static final Logger logger = Logger.getLogger(ProducerApp.class);

    public ProducerApp() {
    }

    public static void main(String[] args) {
    	logger.info("starting app ..");
    	System.out.println("starting app: arg is "+args[0]);
        try {
            String path = args[0];
            if (path == null) {
                logger.fatal("No path for BlackBox app provided..exit program");
                System.exit(1);
            }
            System.out.println("ProducerApp.main(): before calling props ..");
            Properties props = ConfigLoader.getProducerConfigProps();
            System.out.println("ProducerApp.main(): prop is "+props);
            bpProducer = new KafkaProducer(props);
            Runnable r = () -> {
                logger.info("BlackBox  is running");

                try {
                    BlackBoxOutputJSONReader.initBlackBoxReader(path);
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            };
            (new Thread(r)).start();
            String topic1 = "bptopic1";
            String topic2 = "bptopic2";
            String topic3 = "bptopic3";
            long sleepTimer = 3000L;

            while(true) {
                while(BlackBoxOutputJSONReader.initated) {
                    produceCurrentLineToKafkaTopic(topic1);
                    produceCurrentLineToKafkaTopic(topic2);
                    produceCurrentLineToKafkaTopic(topic3);
                    Thread.sleep(sleepTimer);
                }

                logger.info("BlackBoxOutputJSONReader is still not initiated .waiting");
                Thread.sleep(2000L);
            }
        } catch (Exception var12) {
            logger.error("Exception occur:{}", var12);
        } finally {
            logger.info("Producer App Closed");
            bpProducer.close();
        }

    }

    private static void produceCurrentLineToKafkaTopic(String topicName) {
        String currentLine = BlackBoxOutputJSONReader.readBlackBoxOutput();
        bpProducer.send(new ProducerRecord(topicName, currentLine));
        logger.info(String.format("sent mssage %s",currentLine));
    }
}
