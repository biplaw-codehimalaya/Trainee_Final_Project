package com.finalproject.consumer.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.consumer.dto.OTPData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpConsumerService {
    private final EmailService emailService;

    Logger LOGGER = LoggerFactory.getLogger(OtpConsumerService.class);

    @RabbitListener(queues = {"${rabbitmq.otp.queue.name}"})
    public void listenAndSendOtp(Message message){
        LOGGER.info("Otp Consumer Called with msg: " + message);

        try{
            String receivedMessage = new String(message.getBody());
            LOGGER.info("Message Received: " + receivedMessage);
            OTPData otpData = new ObjectMapper().readValue(receivedMessage, OTPData.class);

            LOGGER.info("Conversion from String to OTPData: "+otpData);
//            Send the otp to the user
            emailService.sendOtpEmail(otpData);
            //Now send the email.
        }catch (Exception e){
            LOGGER.error("Error occurred while conversion from : " + e.getMessage());
        }

    }
}
