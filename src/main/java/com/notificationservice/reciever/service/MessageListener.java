package com.notificationservice.reciever.Listener;

import com.notificationservice.reciever.dto.UserEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    @RabbitListener(queues = "${queue.name}")
    public void receive(UserEvent message) {
        System.out.println("Получено сообщение: " + message);
    }
}