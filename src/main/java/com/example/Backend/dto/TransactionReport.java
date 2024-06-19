package com.example.Backend.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReport {
    
    private Date orderDate;
    private String customerName;
    private String itemName;
    private Double price;
    private Integer quantity;
    private Double totalPrice;
}
