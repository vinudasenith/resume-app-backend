package com.example.resumeapp.resume_app.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.example.resumeapp.resume_app.dto.ParsedResumeResponse;

import com.example.resumeapp.resume_app.util.MultipartInputStreamFileResource;

@Service
public class ResumeParserClientService {

    private final RestTemplate restTemplate;

    public ResumeParserClientService() {
        this.restTemplate = new RestTemplate();
    }

    // send file to parser
    public ParsedResumeResponse sendFileToParser(MultipartFile file) throws IOException {
        String parserUrl = "http://localhost:8000/parse_resume";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ParsedResumeResponse> response = restTemplate.postForEntity(parserUrl, requestEntity,
                ParsedResumeResponse.class);

        return response.getBody();
    }
}
