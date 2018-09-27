package org.radekbor.splitterintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class SplitterIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitterIntegrationApplication.class, args);
    }

}
