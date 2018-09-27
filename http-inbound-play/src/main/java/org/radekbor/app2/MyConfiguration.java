package org.radekbor.app2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SplitterEndpointSpec;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.handler.MessageTriggerAction;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@EnableIntegration
public class MyConfiguration {

    @Bean
    public DirectChannel httpRequestChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow httpInternalServiceFlow() {
        return IntegrationFlows
                .from(Http.inboundGateway("/service")
                        .requestMapping(r -> r.params("name"))
                        .payloadExpression("#requestParams.name"))
                .channel(httpRequestChannel())
                .<List<String>, String>transform(p -> p.get(0).toUpperCase())
                .get();
    }
}
