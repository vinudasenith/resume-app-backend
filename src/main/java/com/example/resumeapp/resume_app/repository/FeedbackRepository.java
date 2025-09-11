package com.example.resumeapp.resume_app.repository;

import com.example.resumeapp.resume_app.model.Feedback;
import com.example.resumeapp.resume_app.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByResume(Resume resume);

    @Query("SELECT f FROM Feedback f WHERE f.resume.user.email = :email")
    List<Feedback> findAllByResumeUserEmail(@Param("email") String email);

}
