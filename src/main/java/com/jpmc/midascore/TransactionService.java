package com.jpmc.midascore;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incetive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    private final RestTemplate restTemplate;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository,RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    public void processTransaction(Transaction transaction) {
        UserRecord sender=userRepository.findById(transaction.getSenderId());
        UserRecord recipient=userRepository.findById(transaction.getRecipientId());

        float incentiveAmount = 0;

        Incetive incetive=restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incetive.class
        );

        if(incetive != null && incetive.getAmount()!=null) {
            incentiveAmount = incetive.getAmount();
        }
        //if (sender.getId())
        if(sender==null || recipient==null) {
            return;
        }
        if(sender.getBalance()<transaction.getAmount()) {
            return;
        }

        sender.setBalance(sender.getBalance()-transaction.getAmount());
        recipient.setBalance(recipient.getBalance()+transaction.getAmount()+incentiveAmount);
        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord record=new TransactionRecord();
        record.setUser(sender);
        record.setIncetive(incentiveAmount);
        record.setUser2(recipient);
        record.setAmount(transaction.getAmount());

        transactionRepository.save(record);

        if(sender.getName().equals("waldorf"))
        {
            System.out.println(sender.getBalance());
        }
        else if (recipient.getName().equals("waldorf"))
        {
            System.out.println(recipient.getBalance());
        }

    }



}
