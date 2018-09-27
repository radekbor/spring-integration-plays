package org.radekbor.splitterintegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.http.config.HttpInboundEndpointParser;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Headers;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

//@Profile("disable")
@Configuration
public class TwoServantConfiguration {

    @Autowired
    public Cafe cafe;

    @PostConstruct
    public void doSth() throws InterruptedException {
        Thread.sleep(1000);
        final Logger log = LoggerFactory.getLogger("quests queue");
        AtomicInteger atomicInteger = new AtomicInteger(0);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("create");
                cafe.placeOrder(atomicInteger.getAndIncrement());
            }
        }, 1000, 1000);
    }

    @Bean
    public DirectChannel input() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "input")
    public void servant1(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("servant1");
        log.info(payload);
        return;
    }

    @ServiceActivator(inputChannel = "input")
    public void servant2(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("servant2");
        log.info(payload);
        return;
    }

}
