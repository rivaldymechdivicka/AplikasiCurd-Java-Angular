package com.example.Backend.dto;

import lombok.Data;

@Data
public class CreateOrderRequest {
    
    private Long orderId;
    private Integer quantity;
    private Long customerId;
    private Long itemId;
}
