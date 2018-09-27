package org.radekbor.splitterintegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//@Profile("disable")
@Configuration
public class TwoServantAndSplitterConfiguration {

    @Component
    class ShopClientSplitter {

        @Splitter(inputChannel = "clientInput", outputChannel = "splitClientChannel")
        public Collection split(String payload) {
            List messages = new ArrayList();
            messages.add(payload);
            messages.add(payload);
            return messages;
        }
    }

    @Autowired
    public Shop shop;

    @PostConstruct
    public void doSth() throws InterruptedException {
        final Logger log = LoggerFactory.getLogger("shop client queue");
        AtomicInteger atomicInteger = new AtomicInteger(0);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("create");
                shop.placeOrder(atomicInteger.getAndIncrement());
            }
        }, 2000, 1000);
    }

    @Bean
    public DirectChannel clientInput() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel splitClientChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "splitClientChannel")
    public void shopAssistant1(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("shop_assistant_1");
        log.info(payload);
        return;
    }

    @ServiceActivator(inputChannel = "splitClientChannel")
    public void shopAssistant2(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("shop_assistant_2");
        log.info(payload);
        return;
    }

}
