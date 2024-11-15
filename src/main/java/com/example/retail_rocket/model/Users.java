package com.example.retail_rocket.model;

import jakarta.persistence.*;
import lombok.Data;


@Table(name = "users",schema = "retail_schema")
@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "retail_schema.user_id_seq", allocationSize = 1,initialValue = 1)
    private Long id;
    private String type;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private String first_name;
    private String lastName;
    private String otp;
    private String localAddress;
    private String customerid;
   // private LocalTime createdAt;
}
