package com.example.resumeapp.resume_app.service;

import com.example.resumeapp.resume_app.model.Feedback;
import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // save feedback
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // get all feedback
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // get feedback by id
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    // get feedback by resume
    public List<Feedback> getFeedbackByResume(Resume resume) {
        return feedbackRepository.findByResume(resume);
    }

    // get feedback by user email
    public List<Feedback> getFeedbackByUserEmail(String email) {
        return feedbackRepository.findAllByResumeUserEmail(email);
    }

}
