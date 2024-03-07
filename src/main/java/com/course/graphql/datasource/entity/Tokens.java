package com.course.graphql.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "users_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tokens {

    @Id
    private UUID userId;
    private String authToken;
    @CreationTimestamp
    @Column (name = "creation_timestamp")
    private LocalDateTime creationTime;
    @Column (name = "expiry_timestamp")
    private LocalDateTime expiryTime;
}
