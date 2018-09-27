package org.radekbor.splitterintegration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface Shop {

    @Gateway(requestChannel = "clientInput")
    void placeOrder(Object object);

}
