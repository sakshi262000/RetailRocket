package com.example.retail_rocket.controller;

import com.example.retail_rocket.dto.UserRequestDto;
import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/register")
    ResponseEntity<Users> registerUser(@RequestBody Users userdata){
       return service.saveUsersRawData(userdata);
    }
    @GetMapping("/users")
    ResponseEntity<ArrayList<Users>> getAllUsers(){
        return service.getAllUsers();
    }
    @PostMapping("/login")
    Map<String,Object> userLogin(@RequestBody UserRequestDto userRequest, HttpServletRequest request){
        return (Map<String, Object>) service.verifyuser(userRequest);
    }
    //This is for CSFR token 😎
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }
}
