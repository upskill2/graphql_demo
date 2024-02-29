package com.course.graphql.datasource.entity;

import com.course.graphql.datasource.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "solutions")
@Data
public class Solution {

    @Id
    private UUID id;

    @Enumerated (EnumType.ORDINAL)
    private Category category;
    private String content;
    @CreationTimestamp
    @Column (name = "creation_timestamp")
    private LocalDateTime creationTime;
    private Integer voteBadCount;
    private Integer voteGoodCount;

    @ManyToOne
    @JoinColumn (name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn (name = "problem_id", nullable = false)
    private Problem problems;
}
