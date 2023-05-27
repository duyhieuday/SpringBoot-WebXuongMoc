package com.example.library.dto;

import lombok.Data;

@Data
public class ContactDto {

    private String fullname;

    private String email;

    private String phone;

    private String content;

    public ContactDto() {
    }

    public ContactDto(String fullname, String email, String phone, String content) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.content = content;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
