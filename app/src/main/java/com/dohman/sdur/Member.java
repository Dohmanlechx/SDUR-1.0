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

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getIdentitynumber() {
        return identitynumber;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }
}
