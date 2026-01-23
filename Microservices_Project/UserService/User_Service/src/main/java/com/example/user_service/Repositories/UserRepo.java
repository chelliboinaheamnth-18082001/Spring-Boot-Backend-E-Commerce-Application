package com.example.user_service.Repositories;


import com.example.user_service.Entites.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {


    boolean existsByEmailOrMobileNumber(String email,String mobileNumber);
}
