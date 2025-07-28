package com.example.resumeapp.resume_app.repository;

import com.example.resumeapp.resume_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
