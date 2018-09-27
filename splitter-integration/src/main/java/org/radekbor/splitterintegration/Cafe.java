package org.radekbor.splitterintegration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface Cafe {

    @Gateway(requestChannel = "input")
    void placeOrder(Object object);

}
