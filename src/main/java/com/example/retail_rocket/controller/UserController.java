package com.example.retail_rocket.controller;

import com.example.retail_rocket.dto.UserRequestDto;
import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody Users userdata, HttpServletRequest request){
        service.saveUsersRawData(userdata);
        return new ResponseEntity("Session id: "+request.getSession().getId(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/users")
    ResponseEntity<ArrayList<Users>> getAllUsers(){
        return service.getAllUsers();
    }
    @PostMapping("/login")
    ResponseEntity<String> userLogin(@RequestBody UserRequestDto userRequest, HttpServletRequest request){
        return new ResponseEntity(service.verifyuser(userRequest)+" 😀 Session id: "+request.getSession().getId(),HttpStatus.OK);
    }
    //This is for CSFR token 😎
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }
}
