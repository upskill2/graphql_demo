package com.course.graphql.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "problems")
@Data
public class Problems {

    @Id
    private UUID id;
    private String content;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    private List<String> tags;
    private String title;

    @OneToMany (mappedBy = "problems")
    private List<Solutions> solutions;

    @ManyToOne
    @JoinColumn (name = "created_by", nullable = false)
    private Users createdBy;

}
