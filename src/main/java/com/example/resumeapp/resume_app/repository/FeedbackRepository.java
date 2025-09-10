package com.example.resumeapp.resume_app.repository;

import com.example.resumeapp.resume_app.model.Feedback;
import com.example.resumeapp.resume_app.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByResume(Resume resume);

}
