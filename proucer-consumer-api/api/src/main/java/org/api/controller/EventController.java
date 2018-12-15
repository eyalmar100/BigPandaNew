//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.api.controller;

import org.apache.log4j.Logger;
import org.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    public static final Logger logger = Logger.getLogger(EventController.class);
    @Autowired
    private EventService service;

    public EventController() {
    }

    @GetMapping({"/api/{event_type}"})
    public String events(@PathVariable String event_type) {
        logger.info("EventController.events(): event type is : " + event_type);
        int val = this.service.getEventCount(event_type);
        logger.info("EventController.events():val is " + val);

        return Integer.toString(val);
    }

    @GetMapping({"/api/{event_type}/all"})
    public String allEvents(@PathVariable String event_type) {
        logger.info("EventController.allEvents()");
        return this.service.getAllDataInEeventType(event_type);
    }
}
