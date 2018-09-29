package org.radekbor.parallelprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class BrodcastHandler {

    @ServiceActivator(inputChannel = "broadcastChannel", outputChannel = "httpOutputChannel")
    public Long count(String in) {
        String[] numbers = in.split(" ");
        long i = 0;
        for (String n : numbers) {
            Integer integer = Integer.valueOf(n);
            i += integer;
        }
        return i;
    }

    private static final Logger log = LoggerFactory.getLogger("logHandler");

    @ServiceActivator(inputChannel = "broadcastChannel")
    public void logHandler(String in) {
        log.info("Log data {}", in);
    }
}
