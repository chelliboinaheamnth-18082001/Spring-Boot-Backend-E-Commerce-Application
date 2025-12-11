package com.example.monolithicbackend.Repositories;


import com.example.monolithicbackend.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {


    boolean existsByEmailOrMobileNumber(String email,String mobileNumber);
}
