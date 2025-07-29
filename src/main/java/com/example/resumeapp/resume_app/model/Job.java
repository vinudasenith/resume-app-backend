package com.example.resumeapp.resume_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String company;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String requiredSkills;

    private String location;

    private String employmentType;

    private String salary;

    private String postedBy;
}
