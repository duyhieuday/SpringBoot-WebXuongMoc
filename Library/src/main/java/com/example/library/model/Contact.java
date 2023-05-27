package com.example.library.model;

import com.example.library.utils.annotation.IgnoreField;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "full_name")
    private String fullname;


    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "content")
    private String content;


    @Column(name = "created_at")
    @IgnoreField
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    @IgnoreField
    private LocalDateTime updatedAt;

    public Contact() {
    }

    public Contact(Long id, String fullname, String phone, String email,String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String firstname) {
        this.fullname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstname='" + fullname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

