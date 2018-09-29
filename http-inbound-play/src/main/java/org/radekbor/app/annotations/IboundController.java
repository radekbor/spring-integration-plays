package org.radekbor.app.annotations;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@MessagingGateway
@RestController
public interface IboundController {

    @Gateway(requestChannel = "httpRequestChannel2", replyChannel = "httpReplyChannel2")
    @PostMapping("/in")
    public String inaction(@RequestBody String payload);
}
