package org.radekbor.parallelprocessor;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@MessagingGateway
@RestController
public interface GatewayController {

    @Gateway(requestChannel = "httpInputChannel", replyChannel = "httpOutputChannel")
    @PostMapping("/in")
    public String handle(@RequestBody String in);
}
