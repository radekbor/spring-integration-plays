package org.radebkro.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageProcessorSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.dsl.HttpMessageHandlerSpec;
import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.ContentTypeResolver;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableIntegration
public class MyConfiguration {

    @Bean
    public MessageSource<?> integerMessageSource() {
        MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(new AtomicInteger());
        source.setMethodName("getAndIncrement");
        return source;
    }

    @Autowired
    private MessageSource<?> integerMessageSource;

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow myFlow() {

        final Logger log = LoggerFactory.getLogger(IntegrationFlow.class);

        HttpMessageHandlerSpec addDays = Http
                .outboundGateway("http://localhost:8080/days")
                .httpMethod(HttpMethod.POST)
                .expectedResponseType(LocalDate.class);

        HttpMessageHandlerSpec getDay = Http
                .outboundGateway("http://localhost:8080/day")
                .httpMethod(HttpMethod.POST)
                .expectedResponseType(Integer.class);


        return IntegrationFlows.from(integerMessageSource, c ->
                c.poller(Pollers.fixedRate(2000)))
                .channel(this.inputChannel())
                .filter((Integer p) -> p > 0)

                .transform((Object o) -> {
                    Map<String, Object> headers = new HashMap<>();
                    headers.put(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                    return new GenericMessage<>(o.toString(), headers);
                })
                .handle(addDays.get())

                .transform((LocalDate day) -> {
                    Map<String, Object> headers = new HashMap<>();
                    headers.put(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                    return new GenericMessage<>(day.toString(), headers);
                })
                .handle(getDay.get())

                .handle(new MessageHandler() {
                    @Override
                    public void handleMessage(Message<?> message) throws MessagingException {
                        log.info("{}", message.getPayload());
                    }
                })
                .get();
    }


}
