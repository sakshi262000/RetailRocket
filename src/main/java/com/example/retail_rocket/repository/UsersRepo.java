package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepo extends CrudRepository<Users, Long> {
   @Query(value = "select * from retail_schema.users where username=:username AND password=:password",nativeQuery = true)
    Users getUserDetail(String username,String password);

    Users findByUsername(String username);
}
