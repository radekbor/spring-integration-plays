package org.radebkro.app;

import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class MyController {


    @PostMapping(value = "/days", consumes = MediaType.TEXT_PLAIN_VALUE)
    public LocalDate plusDays(@RequestBody String body) {
        return LocalDate.now().plusDays(Integer.valueOf(body));
    }

    @PostMapping(value = "/day", consumes = MediaType.TEXT_PLAIN_VALUE)
    public Integer getYear(@RequestBody String body) {
        return LocalDate.parse(body).getDayOfYear();
    }

}
