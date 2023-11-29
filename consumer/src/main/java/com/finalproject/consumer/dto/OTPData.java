package com.finalproject.consumer.dto;

import lombok.Data;

@Data
public class OTPData {
    private String email;
    private Long duration;
    private String otp;
}
