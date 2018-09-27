package org.radekbor.splitterintegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.handler.annotation.Headers;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//@Profile("disable")
@Configuration
public class TwoOfficersAndRouterConfiguration {

    @Router(inputChannel = "officeInput")
    public String router(String pay) {
        Integer integer = Integer.valueOf(pay);
        if (integer % 2 == 0) {
            return "officeChannel1";
        }
        return "officeChannel2";
    }

    @Autowired
    public Office office;

    @PostConstruct
    public void doSth() throws InterruptedException {
        final Logger log = LoggerFactory.getLogger("office client queue");
        AtomicInteger atomicInteger = new AtomicInteger(0);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("create");
                office.placeOrder(atomicInteger.getAndIncrement());
            }
        }, 2000, 1000);
    }

    @Bean
    public DirectChannel officeInput() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel officeChannel1() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel officeChannel2() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "officeChannel1")
    public void shopAssistant1(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("shop_assistant_1");
        log.info(payload);
        return;
    }

    @ServiceActivator(inputChannel = "officeChannel2")
    public void shopAssistant2(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("shop_assistant_2");
        log.info(payload);
        return;
    }

}
