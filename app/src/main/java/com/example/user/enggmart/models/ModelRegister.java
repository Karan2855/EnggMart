package com.example.user.enggmart.models;

public class ModelRegister {
    public String name = "";
    public String email = "";
    public String phone = "";
    public String image = "";

    public String getPass() {
        return pass;
    }

    public String pass = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
