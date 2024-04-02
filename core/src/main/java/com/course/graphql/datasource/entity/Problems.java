package com.course.graphql.datasource.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "problems")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Problems {

    @Id
    private UUID id;
    private String content;
    @CreationTimestamp
    @Column (name = "creation_timestamp")
    private LocalDateTime creationTimestamp;
    private String tags;
    private String title;

    @OneToMany (mappedBy = "problems")
    @OrderBy("creation_timestamp desc")
    private List<Solutions> solutions;

    @ManyToOne
    @JoinColumn (name = "created_by", nullable = false)
    private Users createdBy;

}
