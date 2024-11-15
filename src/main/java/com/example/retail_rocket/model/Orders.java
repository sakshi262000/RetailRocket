package com.example.retail_rocket.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Table(name = "orders",schema = "retail_schema")
@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "retail_schema.orders_id_seq", allocationSize = 1,initialValue = 1)
    private Long orderId;
    private String productCode;
    private Integer numberOfItems;
    private Long customerId;
    private Timestamp placedAt;
    public Orders() {
        this.placedAt = Timestamp.from(java.time.Instant.now());
    }
}
