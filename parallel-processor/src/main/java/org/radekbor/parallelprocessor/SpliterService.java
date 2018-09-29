package org.radekbor.parallelprocessor;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SpliterService {

    @Splitter(inputChannel = "httpInputChannel", outputChannel = "splitClientChannel")
    public Collection split(Message<String> message) {
        String content = message.getPayload();
        String[] words = content.split(" ");
        List messages = new ArrayList();
        for(String word: words) {
            messages.add(new GenericMessage<>(word, message.getHeaders()));
        }
        return messages;
    }

}
