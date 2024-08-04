package com.pleyfair.mipmip.helper;


import com.pleyfair.mipmip.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
public class SchedulingHelper {
    private final TransactionService transactionService;

    public SchedulingHelper(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Scheduled(cron = "${scheduler.transactionCron}") // every 5th minute on the 0th second of the hour
    public void callProcessTransactionsScheduler(){
        log.info("Calling processTransactionsScheduler with time " + LocalDateTime.now());
        transactionService.processTransactions();
    }
}