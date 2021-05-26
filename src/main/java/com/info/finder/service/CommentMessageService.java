package com.info.finder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.finder.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentMessageService.class);

    private RabbitTemplate template;

    public CommentMessageService(RabbitTemplate template) {
        this.template = template;
    }

    public void send(Message message) {
        String messageJson = "";
        String exchange = "amq.topic";
        try {
            ObjectMapper mapper = new ObjectMapper();
            messageJson = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.info("JsonProcessingException", e);
        }
        String routingKey = String.format("message.%s", message.getTo());
        LOGGER.info("routing key: " + routingKey);
        template.convertAndSend(exchange, routingKey, messageJson);
    }

}
