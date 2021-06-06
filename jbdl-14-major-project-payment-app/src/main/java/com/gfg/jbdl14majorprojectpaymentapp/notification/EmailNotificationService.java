package com.gfg.jbdl14majorprojectpaymentapp.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.Transaction;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.RollbackEvent;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionEvent;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.WalletEvent;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.SignUp;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.UserResponse;
import com.sun.nio.sctp.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

@Service
public class EmailNotificationService implements NotificationManager {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    SimpleMailMessage simpleMailMessage;
    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendOnTransaction(String event) {

    }

    @Override
    @KafkaListener(topics = "wallet",groupId = "notification")
    public void sendOnWallet(String event) {
        WalletEvent walletEvent = null;
        try {
            walletEvent = objectMapper.readValue(event, WalletEvent.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        UserResponse userResponse1 = restTemplate.getForEntity("http://localhost:8080/user/".concat(walletEvent.getFromUser()),UserResponse.class).getBody();

        simpleMailMessage.setTo(userResponse1.getEmail());
        simpleMailMessage.setSubject("Transaction successful");
        simpleMailMessage.setText("Transaction ".concat(walletEvent.getTransactionId()).concat(" is successful"));
        javaMailSender.send(simpleMailMessage);

        UserResponse userResponse2 = restTemplate.getForEntity("http://localhost:8080/user/".concat(walletEvent.getToUser()),UserResponse.class).getBody();

        simpleMailMessage.setTo(userResponse2.getEmail());
        simpleMailMessage.setSubject("Amount Credited");
        simpleMailMessage.setText("Amount ".concat(String.valueOf(walletEvent.getTransactionAmount())).concat(" got credited"));
        javaMailSender.send(simpleMailMessage);

    }

    @Override
    @KafkaListener(topics = "user",groupId = "notification")

    public void sendOnUser(String event) {
        SignUp signUp = null;
        try {
            signUp = objectMapper.readValue(event, SignUp.class);
            simpleMailMessage.setTo(signUp.getEmail());
            simpleMailMessage.setSubject("Wallet created");
            simpleMailMessage.setText("Welcome");
            javaMailSender.send(simpleMailMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    @KafkaListener(topics = "rollbackTx",groupId = "notification")
    public void sendOnRollback(String event) {
        RollbackEvent rollbackEvent = null;
        try {
            rollbackEvent = objectMapper.readValue(event,RollbackEvent.class);
            UserResponse userResponse1 = restTemplate.getForEntity("http://localhost:8080/user/".concat(rollbackEvent.getFromUser()),UserResponse.class).getBody();

            rollbackEvent = objectMapper.readValue(event,RollbackEvent.class);
            InternetAddress internetAddress = new InternetAddress(userResponse1.getEmail(),"","UTF-8");
            simpleMailMessage.setTo(internetAddress.getAddress());
            simpleMailMessage.setSubject("Transaction Failed");
            simpleMailMessage.setText("Transaction ".concat(rollbackEvent.getTransactionId()).concat(" has failed for ").concat(rollbackEvent.getReason()));
            javaMailSender.send(simpleMailMessage);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
