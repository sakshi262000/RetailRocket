package com.example.retail_rocket.service;

import com.example.retail_rocket.dto.UserRequestDto;
import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UsersRepo repo;

    public ResponseEntity<String> saveUsersRawData(Users user){
    //    user.setType("general");
        repo.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ArrayList<Users>> getAllUsers() {
        ArrayList<Users> users = new ArrayList<>();
        repo.findAll().forEach(user -> {
            users.add(user);
        });
        return new ResponseEntity(users, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> verifyuser(UserRequestDto userRequest) {
Users abc = repo.getUserDetail(userRequest.getUsername(), userRequest.getPassword());
        if(Objects.nonNull(abc)){
           return new ResponseEntity("Sucess", HttpStatus.OK);
        }else{
           return new ResponseEntity("Failed", HttpStatus.NOT_FOUND);
        }
    }
}
