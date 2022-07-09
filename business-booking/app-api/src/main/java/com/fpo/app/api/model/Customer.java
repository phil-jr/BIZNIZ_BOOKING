package com.fpo.app.api.model;

public class Customer {

    public Customer() {}

    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String profilePictureAttachment;
    private String signUpDate;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfilePictureAttachment() {
        return profilePictureAttachment;
    }

    public void setProfilePictureAttachment(String profilePictureAttachment) {
        this.profilePictureAttachment = profilePictureAttachment;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }
}
