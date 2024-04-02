package com.course.graphql.datasource.entity;

import com.course.graphql.datasource.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "solutions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Solutions {

    @Id
    private UUID id;
    @Enumerated (EnumType.STRING)
    private Category category;
    private String content;
    @CreationTimestamp
    @Column (name = "creation_timestamp")
    private LocalDateTime creationTime;
    private int voteBadCount;
    private int voteGoodCount;

    @ManyToOne
    @JoinColumn (name = "created_by", nullable = false)
    private Users createdBy;

    @ManyToOne
    @JoinColumn (name = "problems_id", nullable = false)
    private Problems problems;
}
