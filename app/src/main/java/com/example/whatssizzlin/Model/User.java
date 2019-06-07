package com.example.whatssizzlin.Model;

public class User {
    private String name, address, email, preference, two, phoneNumber;

    public User() {

    }

    public User(String name, String address, String email, String preference, String two, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.preference = preference;
        this.two = two;
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}