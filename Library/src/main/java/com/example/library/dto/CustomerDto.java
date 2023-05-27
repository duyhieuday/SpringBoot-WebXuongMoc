package com.example.library.dto;



import com.example.library.model.Role;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CustomerDto {


    private String username;

    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private String password;

    @Size(min = 3, max = 15, message = "First name should have 3-15 characters")
    private String firstname;

    @Size(min = 3, max = 15, message = "Last name should have 3-15 characters")
    private String lastname;

    private String email;

    private String phone;

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public CustomerDto() {
    }

    public CustomerDto(String username, String password, String firstname, String lastname, String email, String phone, Role role) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
