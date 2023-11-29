package com.finalproject.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.consumer.dto.ReceivedData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDataConsumer {

    private final EmailService emailService;
    private final OpenPDFService openPDFService;


    Logger LOGGER = LoggerFactory.getLogger(ProductDataConsumer.class);

    @Value("${spring.mail.username}")
    private String from;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void getProductsDetails(Message message){
        LOGGER.info("Data received: " + message);

        try {
            String receivedMessage = new String(message.getBody());
            LOGGER.info("Raw Message received: " + receivedMessage);

            // Attempt to deserialize the message payload if needed
            LOGGER.info("Data received: " + receivedMessage);

            ReceivedData data =new  ObjectMapper().readValue(receivedMessage, ReceivedData.class);

            LOGGER.info("Data after conversion: " + data);

            emailService.sendEmail(data.getUseremail(), from, openPDFService.createPDF(data));

            LOGGER.info("Email sent Successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing received message: " + e.getMessage());
        }
    }
}
