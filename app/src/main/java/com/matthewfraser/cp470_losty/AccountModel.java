package com.matthewfraser.cp470_losty;

public class AccountModel {

    private String name;
    private String email;
    private String username;
    private String password;
    private String id;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String Phone) {
        this.phone = Phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public AccountModel(String Name, String Email, String Phone, String Username, String Password, String id) {
        this.name = Name;
        this.email = Email;
        this.phone = Phone;
        this.username = Username;
        this.password = Password;
        this.id = id;
    }
}