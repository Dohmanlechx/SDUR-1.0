package com.dohman.sdur;

public class Member {
    private String forename;
    private String surname;
    private String identitynumber;
    private String streetaddress;
    private String postcode;
    private String city;

    public Member(String forename, String surname, String identitynumber, String streetaddress, String postcode, String city) {
        this.forename = forename;
        this.surname = surname;
        this.identitynumber = identitynumber;
        this.streetaddress = streetaddress;
        this.postcode = postcode;
        this.city = city;
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
}
