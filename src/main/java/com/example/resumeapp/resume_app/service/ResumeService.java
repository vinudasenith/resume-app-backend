package com.example.resumeapp.resume_app.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.repository.ResumeRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;

import lombok.RequiredArgsConstructor;

import com.example.resumeapp.resume_app.model.User;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String fastApiUrl = "http://localhost:8000/parse_resume";

    // save resume
    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    // get resume
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

    // get all resumes
    public List<Resume> getAllResume() {
        return resumeRepository.findAll();
    }

    // get resume by user
    public List<Resume> getResumeByUser(User user) {
        return resumeRepository.findByUser(user);
    }

    // get resume by file name
    public Resume getResumeByFileName(String fileName) {
        return resumeRepository.findByFileName(fileName);
    }

    // mark resume as having feedback
    public void markResumeAsHavingFeedback(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + resumeId));

        resume.setHasFeedback(true);
        resumeRepository.save(resume);
    }

    // send resume to fast api
    public String sendResumeToFastApi(MultipartFile resumeFile) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource contetArrayResource = new ByteArrayResource(resumeFile.getBytes()) {
            @Override
            public String getFilename() {
                return resumeFile.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", contetArrayResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, requestEntity, String.class);

        return response.getBody();

    }
}
