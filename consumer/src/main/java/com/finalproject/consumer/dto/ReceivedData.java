package com.finalproject.consumer.dto;

import lombok.Data;

@Data
public class ReceivedData {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    private String username;
    private String useremail;
}
