package com.example.retail_rocket.controller;

import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody Users userdata){
        service.saveUsersRawData(userdata);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
