package com.example.resumeapp.resume_app.controller;

import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.service.ResumeService;
import com.example.resumeapp.resume_app.model.User;
import com.example.resumeapp.resume_app.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor

public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    // save resume
    @PostMapping("/save")
    public ResponseEntity<Resume> saveResume(@RequestBody Resume resume) {
        Resume saved = resumeService.saveResume(resume);
        return ResponseEntity.ok(saved);
    }

    // get all resumes
    @GetMapping("/all")
    public ResponseEntity<List<Resume>> getAllResume() {
        return ResponseEntity.ok(resumeService.getAllResume());
    }

    // get resume by email
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Resume>> getResumeByUserEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resumeService.getResumeByUser(user));
    }

    // get resume by file name
    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resume> getResumeByFileName(@PathVariable String fileName) {
        Resume resume = resumeService.getResumeByFileName(fileName);
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resume);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file,
            @RequestParam("userEmail") String userEmail) {
        try {
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            Resume resume = Resume.builder()
                    .fileName(file.getOriginalFilename())
                    .user(user)
                    .build();

            resumeService.saveResume(resume);

            return ResponseEntity.ok("Resume uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload resume");
        }
    }

}
