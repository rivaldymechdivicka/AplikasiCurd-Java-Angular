package com.example.Backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "last_restock")
    private Date lastRestock;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @JsonProperty("isAvailable")
    public Boolean isAvailable() {
        return isAvailable;
    }

}
