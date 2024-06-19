package com.example.Backend.entity;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "last_order")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastOrder;

    @Column(name = "pic")
    private String pic;
}