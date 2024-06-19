package com.example.Backend.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private MultipartFile pic;
    
}
