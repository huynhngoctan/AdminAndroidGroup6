package com.example.adminandroidgroup6.model;


import java.io.Serializable;

public class User implements Serializable {
    String idUser;
    String name;
    long yearOfBirth;
    String email;
    String phone;
    String gender;
    String firstLogin;
    String lastLogin;
    String username;
    String password;
    String role;
    String linkImage;
    String status;

    public User(){

    }

    public User(String idUser, String name, long yearOfBirth, String email, String phone, String gender,
                String firstLogin, String lastLogin, String username, String password, String role, String linkImage,String status) {
        this.idUser = idUser;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkImage = linkImage;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", firstLogin='" + firstLogin + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", linkImage='" + linkImage + '\'' +
                '}';
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(long yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
