package com.example.resumeapp.resume_app.service;

import org.springframework.stereotype.Service;

import com.example.resumeapp.resume_app.model.Resume;
import com.example.resumeapp.resume_app.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

import com.example.resumeapp.resume_app.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResume() {
        return resumeRepository.findAll();
    }

    public List<Resume> getResumeByUser(User user) {
        return resumeRepository.findByUser(user);
    }

    public Resume getResumeByFileName(String fileName) {
        return resumeRepository.findByFileName(fileName);
    }

}
