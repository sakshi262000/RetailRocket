package com.example.retail_rocket.model;


import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "Cart", schema = "retail_schema")
@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    @SequenceGenerator(name = "cart_id_seq", sequenceName = "retail_schema.cart_id_seq", allocationSize = 1, initialValue = 1)
    private long id;

    private String customerId;
    private String productCode;
    private String productName;
    private String quantity;
    private String price;
    private String totalAmount;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    public Cart() {
        this.updatedAt = Timestamp.from(java.time.Instant.now());
    }

    public void calculateTotalAmount() {
        BigDecimal unitPrice = new BigDecimal(price);
        BigDecimal total = unitPrice.multiply(new BigDecimal(quantity));
        this.totalAmount = total.toString();
    }
}