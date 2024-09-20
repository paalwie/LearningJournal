package com.Journal.LearningJournal.dto;

public class UserDto {

    public UserDto() {
        // Parameterloser Konstruktor
    }

    private String email;
    private String password;
    private String role;
    private String fullname;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public UserDto(String email, String fullname, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }
}
