package com.course.graphql.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "users")
@Data
public class Users {

    @Id
    private UUID id;
    private String username;
    private boolean active;
    private URL avatar;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    private String displayName;
    private String email;
    private String hashedPassword;

}