package com.example.library.model;

import com.example.library.utils.annotation.IgnoreField;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "role_permission")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long role_id;

    @Column(name = "permission_id")
    private String title;

    @Column(name = "created_at")
    @IgnoreField
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    @IgnoreField
    private LocalDateTime updatedAt;
    
}
