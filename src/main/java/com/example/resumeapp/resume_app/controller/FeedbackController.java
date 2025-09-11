package com.example.resumeapp.resume_app.controller;

import com.example.resumeapp.resume_app.model.Feedback;
import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.service.FeedbackService;
import com.example.resumeapp.resume_app.service.ResumeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")

public class FeedbackController {

    private final FeedbackService feedbackService;
    private final ResumeService resumeService;

    // Send feedback for a resume
    @PostMapping("/send")
    public ResponseEntity<?> sendFeedback(@RequestParam Long resumeId, @RequestParam String feedbackText) {

        Resume resume = resumeService.getResumeById(resumeId);
        if (resume == null) {
            return ResponseEntity.badRequest().body("Resume not found");
        }

        Feedback feedback = Feedback.builder()
                .resume(resume)
                .feedbackText(feedbackText)
                .isRead(false)
                .build();

        Feedback saved = feedbackService.saveFeedback(feedback);

        resumeService.markResumeAsHavingFeedback(resumeId);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/my-feedback")
    public ResponseEntity<?> getMyFeedback(@RequestParam String email) {
        List<Feedback> feedbacks = feedbackService.getFeedbackByUserEmail(email);

        if (feedbacks.isEmpty()) {
            return ResponseEntity.ok("No feedback found for this user.");
        }
        return ResponseEntity.ok(feedbacks);
    }

    // Get all feedback
    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedbackOpt = feedbackService.getFeedbackById(id);
        return feedbackOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get feedback by resume ID
    @GetMapping("/resume/{resumeId}")
    public ResponseEntity<List<Feedback>> getFeedbackByResume(@PathVariable Long resumeId) {

        Resume resume = resumeService.getResumeById(resumeId);
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }

        List<Feedback> feedbacks = feedbackService.getFeedbackByResume(resume);
        return ResponseEntity.ok(feedbacks);
    }

}
