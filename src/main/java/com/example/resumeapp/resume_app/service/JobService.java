package com.example.resumeapp.resume_app.service;

import com.example.resumeapp.resume_app.model.Job;
import com.example.resumeapp.resume_app.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class JobService {

    private final JobRepository jobRepository;

    // Create a new job
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get job by ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // get jobs posted
    public List<Job> getJobsByPoster(String email) {
        return jobRepository.findByPostedBy(email);
    }

    // Delete job
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // Search by title
    public List<Job> searchJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }
}
