package com.example.retail_rocket.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Table(name = "products",schema = "retail_schema")
@Data
@Entity
public class Products {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_seq")
        @SequenceGenerator(name = "products_id_seq", sequenceName = "retail_schema.products_id_seq", allocationSize = 1,initialValue = 1)
        private Long id;
        private String productCode;
        private String name;
        private String description;
        private BigDecimal price;
        private Long stock;
        private String category;
        @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") //created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        private Timestamp createdAt;
        public Products() {
                this.createdAt = Timestamp.from(java.time.Instant.now());
        } // Initialize with current timestamp }
}
