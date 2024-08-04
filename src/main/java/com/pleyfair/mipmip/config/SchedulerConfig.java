package com.pleyfair.mipmip.config;

import com.pleyfair.mipmip.helper.SchedulingHelper;
import com.pleyfair.mipmip.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    TransactionService transactionService;

    @Bean
    public SchedulingHelper schedulingHelper() {
        return new SchedulingHelper(transactionService);
    }
}
