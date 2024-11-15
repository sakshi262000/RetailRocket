package com.example.retail_rocket.service;

import com.example.retail_rocket.model.UserPrincipal;
import com.example.retail_rocket.model.Users;
import com.example.retail_rocket.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users  = usersRepo.findByUsername(username);
        if(users==null){
            System.out.println("User not found"+username);
            throw new UsernameNotFoundException("User not found");
        }else{
         //   GrantedAuthority authority = new SimpleGrantedAuthority(users.getType());

return new UserPrincipal(users);
        }

    }
}
