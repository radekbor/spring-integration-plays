package org.radekbor.app.dsl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.List;
import java.util.Map;

@Configuration
@EnableIntegration
public class DslConfiguration {

    @Bean
    public DirectChannel httpRequestChannel1() {
        return new DirectChannel();
    }


    @Bean
    public IntegrationFlow httpInternalServiceFlow() {
        return IntegrationFlows
                .from(Http.inboundGateway("/service")
                        .requestMapping(r -> r.params("name"))
                        .payloadExpression("#requestParams.name"))
                .channel(httpRequestChannel1())
                .<List<String>, String>transform(p -> p.get(0).toUpperCase())
                .get();
    }


}
