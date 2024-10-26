package com.example.retail_rocket.service;

import com.example.retail_rocket.Utils.RandomValues;
import com.example.retail_rocket.dto.UserRequestDto;
import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UsersRepo repo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

   // @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<Users> saveUsersRawData(Users user){
    //    user.setType("general");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCustomerid(RandomValues.generateRandomHex());
       return new ResponseEntity(repo.save(user), HttpStatus.ACCEPTED);
        //return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ArrayList<Users>> getAllUsers() {
        ArrayList<Users> users = new ArrayList<>();
        repo.findAll().forEach(user -> {
            users.add(user);
        });
        return new ResponseEntity(users, HttpStatus.ACCEPTED);
    }

    public Map<String,Object> verifyuser(UserRequestDto userRequest) {
/*Users abc = repo.getUserDetail(userRequest.getUsername(), userRequest.getPassword());
        if(Objects.nonNull(abc)){
           return new ResponseEntity("Sucess", HttpStatus.OK);
        }else{
           return new ResponseEntity("Failed", HttpStatus.NOT_FOUND);
        }*/

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(),userRequest.getPassword()
        ));
        if(authentication.isAuthenticated()){
            System.out.println("authentication roles: "+authentication.getAuthorities());
            return jwtService.generateTokenForUser(userRequest.getUsername(),authentication);
        }
        else
            return Map.of("token", "failed");
    }
}
