//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.BPEvent;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class EventService {
    public static final Logger logger = Logger.getLogger(EventService.class);
    private Jedis jedis;
    private ObjectMapper mapper;

    public EventService() {
    }

    @PostConstruct
    public void init() {
        this.mapper = new ObjectMapper();
        this.jedis = new Jedis("localhost");
        if (!this.checkRedisServerConnection()) {
            logger.warn("no connection to redis server !");
        }

    }

    private boolean checkRedisServerConnection() {
        try {
            this.jedis.ping();
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public int getEventCount(String eventType) {
        String val = this.jedis.get(eventType);
        if (val == null) {
            return 0;
        } else {
            try {
                BPEvent bpEvent = (BPEvent)this.mapper.readValue(val, BPEvent.class);
                System.out.println(" BPEvent is " + bpEvent);
                return bpEvent.getNumOfInstances();
            } catch (IOException var5) {
                logger.error(var5);
                return 0;
            }
        }
    }

    public String getAllDataInEeventType(String eventType) {
        String val = this.jedis.get(eventType);
        if (val == null) {
            return "event is not exists!";
        } else {
            try {
                BPEvent bpEvent = (BPEvent)this.mapper.readValue(val, BPEvent.class);
                System.out.println(" BPEvent is " + bpEvent);
                return this.dataToString(bpEvent);
            } catch (IOException var5) {
                var5.printStackTrace();
                return "";
            }
        }
    }

    private String dataToString(BPEvent bpEvent) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> data1 = bpEvent.getData();
        data1.entrySet().forEach((e) -> {
            sb.append((String)e.getKey());
            sb.append("->");
            sb.append(e.getValue());
            sb.append(",");
        });
        String val = sb.toString();
        val = val.substring(0, val.length() - 1);
        return val;
    }
}
