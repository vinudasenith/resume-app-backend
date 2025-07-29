package com.example.resumeapp.resume_app.controller;

import com.example.resumeapp.resume_app.model.Job;
import com.example.resumeapp.resume_app.service.JobService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor

public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return ResponseEntity.ok(createdJob);
    }

    // List all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Jobs posted by admin
    @GetMapping("/poster/{email}")
    public ResponseEntity<List<Job>> getJobsByPoster(@PathVariable String email) {
        return ResponseEntity.ok(jobService.getJobsByPoster(email));
    }

    // Delete job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // Search by title
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Job>> searchJobs(@PathVariable String title) {
        return ResponseEntity.ok(jobService.searchJobsByTitle(title));
    }

}
