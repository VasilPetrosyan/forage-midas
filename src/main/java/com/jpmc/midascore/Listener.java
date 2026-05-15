package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public class Listener {

    private final TransactionService transactionService;

    public Listener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener (topics = "trader-updates",groupId = "spring-boot-kafka")
    public void handleMessage(Transaction t) {

        transactionService.processTransaction(t);

    }
}
