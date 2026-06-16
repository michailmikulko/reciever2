package com.notificationservice.reciever.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public void sendToAll(List<String> emails, String subject, String body) {
        for (String email : emails) {
            send(email, subject, body);
        }
    }

    public void send(String to, String subject, String body){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);

    }
}
