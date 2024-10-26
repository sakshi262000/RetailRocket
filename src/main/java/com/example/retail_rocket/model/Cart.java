package com.example.retail_rocket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Table(name="Cart",schema="retail_schema.Cart")
@Entity
@Data
public class Cart {

@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="cart_id_seq")
@SequenceGenerator(name = "cart_id_seq",sequenceName = "retail_schema.cart_id_seq" ,allocationSize = 1,initialValue = 1)

private long id;
private String customerid;
private String productcode;
private String productname;
private String quantity;
private String amount;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") //created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    private Timestamp updatedAt;
    public Cart() {
        this.updatedAt = Timestamp.from(java.time.Instant.now());
    } // Initialize with current timestamp }

}
