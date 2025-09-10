package com.example.resumeapp.resume_app.controller;

import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.service.ResumeService;
import com.example.resumeapp.resume_app.model.User;
import com.example.resumeapp.resume_app.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import org.springframework.http.*;
import java.util.*;

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

    // upload resume
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadResume(@RequestParam("file") MultipartFile file,
            @RequestParam("userEmail") String userEmail) {
        try {
            System.out.println("Looking for user: " + userEmail);
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                System.out.println("User not found");
                return ResponseEntity.badRequest().body(Map.of("message", "User not found")); // ("User not found");
            }
            System.out.println("User found: " + user.getEmail());

            // save file to disk
            String uploadDir = "C:\\Users\\Vinuda\\Documents\\Project\\uploads\\";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                System.out.println("Creating directory: " + uploadDir);
                directory.mkdirs();
            }

            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + uniqueFilename;

            File dest = new File(filePath);
            System.out.println("Saving file to: " + filePath);
            file.transferTo(dest);

            // save resume metadata to db
            Resume resume = Resume.builder()
                    .fileName(uniqueFilename)
                    .user(user)
                    .build();
            resumeService.saveResume(resume);

            System.out.println("Saving resume metadata to DB");
            resumeService.saveResume(resume);

            RestTemplate restTemplate = new RestTemplate();
            String fastApiUrl = "http://localhost:8000/parse_resume";

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(dest));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> fastApiResponse = restTemplate.postForEntity(fastApiUrl, requestEntity, Map.class);

            if (fastApiResponse.getStatusCode() == HttpStatus.OK && fastApiResponse.getBody() != null) {
                Map parsedData = fastApiResponse.getBody();

                Map<String, Object> response = new HashMap<>();
                response.put("is_resume", parsedData.get("is_resume"));
                response.put("message", parsedData.get("message"));
                response.put("ats_compatibility", parsedData.get("ats_compatibility"));
                response.put("feedback", parsedData.get("feedback"));

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(500).body(
                        Map.of("message", "Failed to upload resume: " + fastApiResponse.getBody().get("message")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Failed to upload resume: " + e.getMessage()));
        }
    }

}
