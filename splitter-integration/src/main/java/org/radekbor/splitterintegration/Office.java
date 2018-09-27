package org.radekbor.splitterintegration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface Office {

    @Gateway(requestChannel = "officeInput")
    void placeOrder(Object object);

}
