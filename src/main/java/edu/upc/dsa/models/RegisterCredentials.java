package edu.upc.dsa.models;

public class RegisterCredentials {
    String name;
    String password;
    String username;
    String mail;

    public RegisterCredentials(){}

    public RegisterCredentials(String name, String password, String username, String mail) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
