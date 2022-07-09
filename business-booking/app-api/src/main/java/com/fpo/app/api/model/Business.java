package com.fpo.app.api.model;

public class Business {

    Business() {}

    private String businessId;
    private String name;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private String description;
    private String address;
    private Integer categoryId;
    private Integer profilePictureAttachment;
    private String signUpDate ;
    private Address addressObj;
    private Boolean isUserProfile;

    public Address getAddressObj() {
        return addressObj;
    }

    public void setAddressObj(Address addressObj) {
        this.addressObj = addressObj;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProfilePictureAttachment() {
        return profilePictureAttachment;
    }

    public void setProfilePictureAttachment(Integer profilePictureAttachment) {
        this.profilePictureAttachment = profilePictureAttachment;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }

    public Boolean getIsUserProfile() {
        return isUserProfile;
    }

    public void setIsUserProfile(Boolean isUserProfile) {
        this.isUserProfile = isUserProfile;
    }

    
}
