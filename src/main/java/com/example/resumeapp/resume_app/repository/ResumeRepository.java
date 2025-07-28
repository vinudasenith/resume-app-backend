package com.example.resumeapp.resume_app.repository;

import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUser(User user);

    Resume findByFileName(String fileName);

}
