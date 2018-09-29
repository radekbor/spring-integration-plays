package org.radekbor.parallelprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class ChannelsConfiguration {

    @Bean
    public MessageChannel httpInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel httpOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel logChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel splitClientChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel broadcastChannel() {
        return new PublishSubscribeChannel();
    }

}
