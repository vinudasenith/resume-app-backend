package com.example.resumeapp.resume_app.dto;

import java.util.Map;

public class ParsedResumeResponse {
    private Map<String, Object> parsed_data;
    private Map<String, Object> resume_score;
    private String raw_text;

    // Getters and setters
    public Map<String, Object> getParsed_data() {
        return parsed_data;
    }

    public void setParsed_data(Map<String, Object> parsed_data) {
        this.parsed_data = parsed_data;
    }

    public Map<String, Object> getResume_score() {
        return resume_score;
    }

    public void setResume_score(Map<String, Object> resume_score) {
        this.resume_score = resume_score;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
    }
}
