package com.example.joousope.bakdinle.Model;

public class User {
    private String userName;
    private String password;
    private String eMail;
    public User() {}
    public User(String userName, String password, String eMail)
    {
        this.userName=userName;
        this.password=password;
        this.eMail=eMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
