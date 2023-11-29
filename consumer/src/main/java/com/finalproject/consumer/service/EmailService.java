package com.finalproject.consumer.service;


import com.finalproject.consumer.dto.OTPData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.subject}")
    String subject;

    @Value("${mail.body}")
    String body;

    @Async
    public void sendEmail(String to, String from, ByteArrayOutputStream attachment) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(body);

        //Creating a resource
        ByteArrayResource resource = new ByteArrayResource(attachment.toByteArray());

        helper.addAttachment("product.pdf", resource);


        javaMailSender.send(message);
    }

    public void sendOtpEmail(OTPData otpData) throws MessagingException {
        //Create the title

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);


        //Set the data in email
        helper.setTo(otpData.getEmail());
        helper.setFrom("biplaw.codehimalaya@gmail.com");
        helper.setSubject("Reset Password Otp");

        String body ="This email is to confirm your request to reset your password. Below is the One-Time Password (OTP) required to proceed with the password reset process:\n\n" +

                " OTP: " + otpData.getOtp() + "\n\n" +
                """                              
                Please use this OTP to reset your password. For security purposes, please ensure that this OTP is kept confidential and not shared with anyone. If you did not initiate this request, please disregard this email.
                                
                If you encounter any issues or need further assistance, feel free to reach out to our support team.
                                
                Thank you,
                Trainee Project
                """;

        helper.setText(body);

        javaMailSender.send(message);
    }
}
