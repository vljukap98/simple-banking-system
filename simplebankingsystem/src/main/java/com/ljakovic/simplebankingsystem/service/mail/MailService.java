package com.ljakovic.simplebankingsystem.service.mail;

import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Value("${mailing.default-sender}")
    private String defaultSender;
    @Value("${mailing.enabled}")
    private boolean mailingEnabled;

    private final JavaMailSender emailSender;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    public void sendMail(Transaction transaction) {
        if (mailingEnabled) {
            SimpleMailMessage messageToSender = mailToSender(transaction);
            SimpleMailMessage messageToReceiver = mailToReceiver(transaction);

            messageToSender.setFrom(defaultSender);
            messageToSender.setSubject("Transaction just processed");

            messageToReceiver.setFrom(defaultSender);
            messageToReceiver.setSubject("Transaction just processed");

            emailSender.send(messageToSender);
            emailSender.send(messageToReceiver);

        }
    }

    private SimpleMailMessage mailToSender(Transaction transaction) {
        final String email = transaction.getSender().getCustomer().getEmail();
        LOGGER.info("Sending mail to: {}", email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Transaction was just processed on your account\n" +
                "Your balance was lowered for" + transaction.getAmount() + " " + transaction.getCurrency().toString() + "\n" +
                "Your new balance: " + transaction.getSender().getBalance());
        LOGGER.info("Mail sent to: {}", email);
        return message;
    }

    private SimpleMailMessage mailToReceiver(Transaction transaction) {
        final String email = transaction.getReceiver().getCustomer().getEmail();
        LOGGER.info("Sending mail to: {}", email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Transaction was just processed on your account\n" +
                "Your balance was increased for" + transaction.getAmount() + " " + transaction.getCurrency().toString() + "\n" +
                "Your new balance: " + transaction.getReceiver().getBalance());
        LOGGER.info("Mail sent to: {}", email);
        return message;
    }
}
