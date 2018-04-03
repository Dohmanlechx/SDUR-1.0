package com.dohman.sdur;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dohman on 2018-03-17.
 */

public class Tab1Membership extends Fragment {
    private static final String TAG = "Tab1Membership";

    private FragmentActivity myContext;

    private Member member;

    private String regexString = "^[0-9]*$";

    private EditText etForename;
    private EditText etSurname;
    private EditText etIdentitynumber;
    private EditText etStreetaddress;
    private EditText etPostcode;
    private EditText etCity;
    private EditText etPhonenumber;
    private EditText etEmail;
    private String foreName;
    private String surName;
    private int identityNumber;
    private String streetAddress;
    private String postCode;
    private String city;
    private String phoneNumber;
    private String email;
    private Button mButton;

    // Creating an "myContext" from this method.
    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Connects to the right layout file.
        View tab1view = inflater.inflate(R.layout.tab1_membership, container, false);

        // Finding the edittexts.
        etForename = tab1view.findViewById(R.id.et_forename);
        etSurname = tab1view.findViewById(R.id.et_surname);
        etIdentitynumber = tab1view.findViewById(R.id.et_identitynumber);
        etStreetaddress = tab1view.findViewById(R.id.et_identitynumber);
        etPostcode = tab1view.findViewById(R.id.et_postcode);
        etCity = tab1view.findViewById(R.id.et_city);
        etPhonenumber = tab1view.findViewById(R.id.et_phonenumber);
        etEmail = tab1view.findViewById(R.id.et_email);

        // Finding the button and setting listener.
        mButton = tab1view.findViewById(R.id.button_send);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saving what the user had written.
                setValues();
                // Setting the values if everything was correctly written,
                // especially the identity number.
                if (identitynumberCheck()) {
                    Log.d(TAG, "onClick: CONGRATS!");
                    // Creating the member.
                    member = new Member(foreName, surName, identityNumber,
                            streetAddress, postCode, city, phoneNumber, email);
                } else {
                    Toast.makeText(myContext, "incorrect personnr", Toast.LENGTH_SHORT).show();
                }
//                testShow();
            }
        });

        return tab1view;
    }

//    void testShow() {
//        Log.d(TAG, "testShow: " + member.getEmail());
//    }

    private void setValues() {
        // Needs try/catch statement because of the NumberFormatException error (null)
        // since I don't want the app to crash at any point.
        try {
            foreName = etForename.getText().toString();
            surName = etSurname.getText().toString();
            streetAddress = etStreetaddress.getText().toString();
            postCode = etPostcode.getText().toString();
            city = etCity.getText().toString();
            phoneNumber = etPhonenumber.getText().toString();
            email = etEmail.getText().toString();
            identityNumber = Integer.parseInt(etIdentitynumber.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "onCreateView: NumberFormatException", e);
        }
//
//        // Creating the member.
//        member = new Member(foreName, surName, identityNumber,
//                streetAddress, postCode, city, phoneNumber, email);
    }

    private boolean identitynumberCheck() {
        int month, day, lastDigit;
        boolean validation;

        //TODO Prova skriva in 10 siffror först för att se om validationen funkar
        //TODO Hitta på ett sätt att ta bort de två första siffrorna innan
        //TODO validationen körs!
        int tempIdentityNumber = Integer.parseInt(Integer.toString(identityNumber).substring(2));
        Log.d(TAG, "identitynumberCheck: " + tempIdentityNumber);

        // First validation. If valid proceed to next validation.
        if (Integer.toString(identityNumber).length() == 10) {
            month = Integer.parseInt(Integer.toString(identityNumber).substring(2, 4));
            day = Integer.parseInt(Integer.toString(identityNumber).substring(4, 6));
            lastDigit = Integer.parseInt(Integer.toString(identityNumber).substring(9, 10));

            // Second validation. If valid proceed to next validation.
            if (month > 00 && day > 00 && month <= 12 && day <= 31) {
                int securityNumber;
                int residualNumber;
                int addedNumbers = 0;
                String multipliedNumbers = "";

                // Calculates the security number with the following code segments
                // Alternately multiplies then numbers in the identitynumber with 2 and 1
                // excluding the last number. Finally the result is stored.
                for (int i = 0; i < Integer.toString(identityNumber).length() - 1; i++) {
                    int n = Character.getNumericValue(Integer.toString(identityNumber).charAt(i));

                    if ((i % 2) != 0) {
                        //Odd
                        n = n * 1;
                    } else if ((i % 2) == 0) {
                        //Even
                        n = n * 2;
                    }
                    multipliedNumbers += n;
                }
                // Loops through each digit and adds them together into an integer
                for (int k = 0; k < multipliedNumbers.length(); k++) {
                    addedNumbers += Character.getNumericValue(multipliedNumbers.charAt(k));
                }
                // Subtracts (the last digit from the added integer above) from 10
                residualNumber = 10 - (addedNumbers % 10);

                // If residual number equals 10 then security number will be 0,
                // else it will be assigned the residual number.
                if (residualNumber == 10) {
                    securityNumber = 0;
                } else {
                    securityNumber = residualNumber;
                }
                // Final validation. If the last digit from persnr
                // equals the calculated security number then it is valid.
                // else it is invalid.
                if (lastDigit == securityNumber) {
                    validation = true;
                } else {
                    validation = false;
                }
            } else {
                validation = false;
            }
        } else {
            validation = false;
        }

        return validation;
    }
}
