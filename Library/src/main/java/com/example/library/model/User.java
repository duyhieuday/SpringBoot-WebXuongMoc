package com.example.library.model;

import com.example.library.utils.annotation.IgnoreField;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "firstname")
    //@NotNull("Enter fullname")
//    @NotBlank(message = "Enter fullname")
    private String firstname;

    @Column(name = "lastname")
    //@NotNull("Enter fullname")
//    @NotBlank(message = "Enter fullname")
    private String lastname;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Enums.Gender gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
//    @Email
    private String email;

    @Column(name = "username")
    //@NotNull("Enter username")
//    @NotBlank(message = "Enter username")
    private String username;

    @Column(name = "password")
    //@NotNull("Enter password")
//    @NotBlank(message = "Enter password")
    private String password;


    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Enums.Status status;

    @Column(name = "created_at")
    @IgnoreField
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    @IgnoreField
    private LocalDateTime updatedAt;


    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public  String getFirstname() {
        return firstname;
    }

    public void setFirstname( String firstname) {
        this.firstname = firstname;
    }

    public  String getLastname() {
        return lastname;
    }

    public void setLastname( String lastname) {
        this.lastname = lastname;
    }

    public Enums.Gender getGender() {
        return gender;
    }

    public void setGender(Enums.Gender gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public  String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public  String getPassword() {
        return password;
    }

    public String bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder().encode(getPassword());
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public Enums.Status getStatus() {
        return status;
    }

    public void setStatus(Enums.Status status) {
        this.status = status;
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
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
