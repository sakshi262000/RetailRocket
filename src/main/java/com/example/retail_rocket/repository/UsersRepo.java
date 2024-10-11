package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
}
