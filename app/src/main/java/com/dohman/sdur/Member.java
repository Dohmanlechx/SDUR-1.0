package com.dohman.sdur;

public class Member {
    private String forename;
    private String surname;
    private String gender;
    private String identitynumber;
    private String streetaddress;
    private String postcode;
    private String city;
    private String phonenumber;
    private String email;

    public Member() {
    }

    public Member(String forename, String surname, String gender, String identitynumber, String streetaddress, String postcode, String city, String phonenumber, String email) {
        this.forename = forename;
        this.surname = surname;
        this.gender = gender;
        this.identitynumber = identitynumber;
        this.streetaddress = streetaddress;
        this.postcode = postcode;
        this.city = city;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentitynumber() {
        return identitynumber;
    }

    public void setIdentitynumber(String identitynumber) {
        this.identitynumber = identitynumber;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
