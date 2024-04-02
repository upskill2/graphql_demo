package com.course.graphql.datasource.entity;

import com.course.graphql.datasource.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private UUID id;
    private String username;
    private boolean active;
    private URL avatar;
    @CreationTimestamp
    @Column (name = "creation_timestamp")
    private LocalDateTime creationTimestamp;
    private String displayName;
    private String email;
    private String hashedPassword;
    @Enumerated (EnumType.STRING)
    private UserRole userRole;
}
