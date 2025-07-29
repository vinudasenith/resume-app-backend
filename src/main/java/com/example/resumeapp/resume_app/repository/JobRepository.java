package com.example.resumeapp.resume_app.repository;

import com.example.resumeapp.resume_app.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByPostedBy(String email);

    List<Job> findByTitleContainingIgnoreCase(String title);

}
