package com.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table (name = "customer_documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue
    private UUID uuid;

/*    @ManyToOne
    @JoinColumn (name = "customer_uuid")
    private CustomerEntity customerEntity;*/
    private String documentType;
    private String documentPath;

}
