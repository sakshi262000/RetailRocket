package com.example.retail_rocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartResponseDto {
   //from product table
    private String productCode;
    private String name;
    private String description;
    private BigDecimal price;

    //from cart table
    private String quantity;

}
