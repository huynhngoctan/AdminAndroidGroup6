package com.example.adminandroidgroup6.model;

public class UserLogin {
    private static UserLogin userLogin;
    private static User user;

    private UserLogin() {
    }

    public static UserLogin getInstance() {
        if (userLogin == null)
            userLogin =new UserLogin();
        return userLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
