//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.consumer.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import model.BPEvent;

import java.util.Map;
import org.apache.log4j.Logger;
 import redis.clients.jedis.Jedis;

public class RedisDataExractor {
    public static final Logger logger = Logger.getLogger(RedisDataExractor.class);
    private static Jedis jedis;
    private static ObjectMapper mapper;
    private static JsonParser parser;

    public RedisDataExractor() {
        mapper = new ObjectMapper();
        jedis = new Jedis("localhost");
        parser = new JsonParser();
    }

    public boolean connectToRedisServer() {
        String pong = null;

        try {
            pong = jedis.ping();
            logger.info("Connection to server sucessfully");
        } catch (Exception var3) {
            logger.error("error during connect to redis server");
            return false;
        }

        logger.info(String.format("Server is running :%s ", pong));
        return true;
    }

    public void processData(String data) throws Exception {
        JsonElement je = parser.parse(data);
        JsonElement je1 = je.getAsJsonObject().get("event_type");
        String eventType = je1.getAsString();
        je1 = je.getAsJsonObject().get("data");
        String dataVal = je1.getAsString();
        String eventTypeJson = jedis.get(eventType);
        if (eventTypeJson == null) {
            createNewBPEventAndupdateRedis(eventType, dataVal);
        } else {
            System.out.println("alreday has event");
            BPEvent bpEvent = (BPEvent)mapper.readValue(eventTypeJson, BPEvent.class);
            System.out.println(" BPEvent is " + bpEvent);
            updateExistingEvent(dataVal, bpEvent);
        }

    }

    private static void updateExistingEvent(String dataVal, BPEvent bpEvent) throws JsonProcessingException {
        Map<String, Integer> dataEvent = bpEvent.getData();
        Integer val = (Integer)dataEvent.get(dataVal);
        if (val == null) {
            dataEvent.put(dataVal, 1);
        } else {
            dataEvent.put(dataVal, val + 1);
        }

        bpEvent.incrementNumOfInstances();
        String json = mapper.writeValueAsString(bpEvent);
        System.out.println(" json is " + json);
        jedis.set(bpEvent.getType(), json);
    }

    private static void createNewBPEventAndupdateRedis(String eventType, String dataVal) throws JsonProcessingException {
        BPEvent bpEvent = new BPEvent();
        bpEvent.setType(eventType);
        Map<String, Integer> dataEvent = bpEvent.getData();
        dataEvent.put(dataVal, 1);
        bpEvent.incrementNumOfInstances();
        String json = mapper.writeValueAsString(bpEvent);
        System.out.println(" json is " + json);
        jedis.set(eventType, json);
    }
}
