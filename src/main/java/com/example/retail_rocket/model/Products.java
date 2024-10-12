package com.example.retail_rocket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "products",schema = "retail_schema")
@Data
@Entity
public class Products {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "retail_schema.user_id_seq", allocationSize = 1,initialValue = 1)
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;


}
