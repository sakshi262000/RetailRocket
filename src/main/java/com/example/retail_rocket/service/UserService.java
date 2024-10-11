package com.example.retail_rocket.service;

import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UsersRepo repo;

    public ResponseEntity<String> saveUsersRawData(Users user){
    //    user.setType("general");
        repo.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
