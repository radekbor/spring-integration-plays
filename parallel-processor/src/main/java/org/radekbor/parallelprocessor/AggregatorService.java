package org.radekbor.parallelprocessor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AggregatorService {

    @Aggregator(inputChannel = "splitClientChannel", outputChannel = "broadcastChannel")
    public String aggregateMessages(List<String> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message: messages) {
            stringBuilder.append(message.length());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
