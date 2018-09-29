package org.radekbor.app.annotations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Beans {

    @Bean
    public DirectChannel httpRequestChannel2() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel httpReplyChannel2() {
        return new DirectChannel();
    }

    private static final AtomicInteger value = new AtomicInteger(0);

    @ServiceActivator(inputChannel = "httpRequestChannel2", outputChannel = "httpReplyChannel2")
    public String shopAssistant1(String payload, @Headers Map<String, Object> headerMap) {
        final Logger log = LoggerFactory.getLogger("shop_assistant_1");
        log.info(payload);
        return payload + value.incrementAndGet();
    }

}
