package com.example.user.enggmart.models;

/**
 * Created by KaranVerma on 07-Mar-18.
 */

public class ModelUserClass {

    private String phone = "";
    private String email = "";
    private String name = "";
    private String image = "";

    public String getChatWithuser() {
        return chatWithuser;
    }

    public void setChatWithuser(String chatWithuser) {
        this.chatWithuser = chatWithuser;
    }

    private String chatWithuser="";

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
