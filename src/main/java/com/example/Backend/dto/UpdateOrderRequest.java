package com.example.Backend.dto;

import lombok.Data;

@Data
public class UpdateOrderRequest {

        private Long customerId;
        private Long itemId;
        private Integer quantity;
    
}
