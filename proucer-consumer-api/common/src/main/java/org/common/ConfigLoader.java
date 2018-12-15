package org.common;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConfigLoader {
    private static Properties consumerProps = null;
    private static Properties producerProps = null;
    private static final Logger logger = Logger.getLogger(ConfigLoader.class);

    private ConfigLoader() {
    }

    public static void init() {
        consumerProps = new Properties();
        producerProps = new Properties();

        try {
            consumerProps.load(ConfigLoader.class.getClassLoader().getResourceAsStream("consumer-config.properties"));
            producerProps.load(ConfigLoader.class.getClassLoader().getResourceAsStream("producer-config.properties"));
        } catch (IOException var1) {
            logger.error("IOException occur:{}", var1);
        }

    }

    public static Properties getProducerConfigProps() {
        if (producerProps == null) {
            init();
        }
        System.out.println("ConfigLoader.getProducerConfigProps(): props is "+producerProps);
        return producerProps;
    }

    public static Properties getConsumerConfigProps() {
        if (consumerProps == null) {
            init();
        }

        return consumerProps;
    }
}
