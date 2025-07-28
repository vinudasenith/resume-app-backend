package com.example.resumeapp.resume_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resume")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String extractedText;

    private String skills;

    private String experience;

    private String education;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
