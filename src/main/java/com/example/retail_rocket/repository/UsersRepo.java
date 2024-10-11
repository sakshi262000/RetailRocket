package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepo extends CrudRepository<Users,Long> {
}
