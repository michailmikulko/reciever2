package com.notificationservice.reciever.service;

import com.notificationservice.reciever.dto.UserEvent;
import com.notificationservice.reciever.entity.RoleType;
import com.notificationservice.reciever.entity.UserEntity;
import com.notificationservice.reciever.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageListener {
    private final MailSenderService mailSender;
    private final UserRepository userRepository;

    @RabbitListener(queues = "${queue.name}")
    public void receive(UserEvent message) {

        List<UserEntity> admins = userRepository.findAllByRole(RoleType.ADMIN);

        List<String> emails = admins.stream()
                .map(UserEntity::getEmail)
                .toList();

        String action = switch (message.getType()) {
            case USER_CREATED -> "Создан";
            case USER_UPDATED -> "Изменен";
            case USER_DELETED -> "Удален";
            default -> "Изменен";
        };

        String subject = action + " пользователь " + message.getUsername();

        String body = action + " пользователь с именем - " + message.getUsername()
                + " и почтой - " + message.getEmail();

        mailSender.sendToAll(emails, subject, body);
    }

}