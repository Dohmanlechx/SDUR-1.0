package com.dohman.sdur;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Dohman on 2018-03-17.
 */

public class Tab1Membership extends Fragment {
    private static final String TAG = "Tab1Membership";

    private FragmentActivity myContext;

    private Member member;

    private static final String MAILTOSDUR = "nymedlemsdur@gmail.com";
    private TextView tv;
    private Spinner genderSpinner;
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
    private String gender;
    private String identityNumber;
    private String streetAddress;
    private String postCode;
    private String city;
    private String phoneNumber;
    private String email;
    private Button mButton;
    private boolean spam = false;

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

        // Finding the big textview and applying backgroundcolor.
        tv = tab1view.findViewById(R.id.tv_memberCheck);
        tv.setBackgroundResource(R.drawable.text_style_3);

        // Finding the gender-spinner and adding values.
        genderSpinner = tab1view.findViewById(R.id.spinner_gender);
        String[] genderList = new String[]{getString(R.string.gender_man), getString(R.string.gender_woman), getString(R.string.gender_intergender)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(myContext, R.layout.spinner_item, genderList);
        genderSpinner.setAdapter(adapter);

        // Finding the edittexts.
        etForename = tab1view.findViewById(R.id.et_forename);
        etSurname = tab1view.findViewById(R.id.et_surname);
        etIdentitynumber = tab1view.findViewById(R.id.et_identitynumber);
        etStreetaddress = tab1view.findViewById(R.id.et_streetaddress);
        etPostcode = tab1view.findViewById(R.id.et_postcode);
        etCity = tab1view.findViewById(R.id.et_city);
        etPhonenumber = tab1view.findViewById(R.id.et_phonenumber);
        etEmail = tab1view.findViewById(R.id.et_email);

        // Finding the button and setting listener.
        mButton = tab1view.findViewById(R.id.button_send);
        mButton.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDarkTransparent), PorterDuff.Mode.MULTIPLY);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spam) {
                    // So the user can't send again until reboot of the app, like anti-spam filter.
                    Toast.makeText(myContext, getString(R.string.toast_alreadysent), Toast.LENGTH_LONG).show();
                } else {
                    // Saving what the user had written.
                    setValues();
                    // Setting the values if everything was correctly written,
                    // especially the identity number.
                    // It checks first if all fields are filled before running those validations, to avoid crashes.
                    if (areAllFieldsFilled()) {
                        if (identitynumberCheck() && validEmail(email) && validPhoneNumber() && validPostCode()) {
                            // Creating the member.
                            member = new Member(foreName, surName, gender, identityNumber, streetAddress, postCode, city, phoneNumber, email);
                            doubleCheckAndPay();
                        }
                    }
                }
            }
        });

        return tab1view;
    }

    private void setValues() {
        Log.d(TAG, "setValues: Starts.");

        // Adding space for the post code if written without any.
        if (etPostcode.getText().toString().length() == 5) {
            StringBuilder addSpace;
            addSpace = new StringBuilder(etPostcode.getText().toString());

            for (int i = 3; i < addSpace.length(); i += 4) {
                addSpace.insert(i, " ");
            }

            etPostcode.setText(addSpace.toString());
        }

        // Adding - to the identity number if written without any.
        if (etIdentitynumber.getText().toString().length() == 12) {
            StringBuilder addMinus;
            addMinus = new StringBuilder(etIdentitynumber.getText().toString());

            for (int i = 8; i < addMinus.length(); i += 9) {
                addMinus.insert(i, "-");
            }

            etIdentitynumber.setText(addMinus.toString());
        }

        // Needs try/catch statement because of the NumberFormatException error (null)
        // since I don't want the app to crash at any point.
        try {
            foreName = etForename.getText().toString();
            surName = etSurname.getText().toString();
            gender = genderSpinner.getSelectedItem().toString();
            identityNumber = etIdentitynumber.getText().toString();
            streetAddress = etStreetaddress.getText().toString();
            postCode = etPostcode.getText().toString();
            city = etCity.getText().toString();
            phoneNumber = etPhonenumber.getText().toString();
            email = etEmail.getText().toString();
        } catch (NumberFormatException e) {
            Log.e(TAG, "onCreateView: NumberFormatException", e);
        }
    }

    private boolean identitynumberCheck() {
        Log.d(TAG, "identitynumberCheck: Starts.");
        int month, day, lastDigit;

        // Creating a temporary string where the - and the two first digits are removed.
        String tempIdentityNumber;
        tempIdentityNumber = identityNumber.replaceFirst("-", "");
        tempIdentityNumber = tempIdentityNumber.substring(2);

        // First validation. If valid proceed to next validation.
        if (identityNumber.length() == 13) {
            month = Integer.parseInt(tempIdentityNumber.substring(2, 4));
            day = Integer.parseInt(tempIdentityNumber.substring(4, 6));
            lastDigit = Integer.parseInt(tempIdentityNumber.substring(9, 10));

            // Second validation. If valid proceed to next validation.
            if (month > 00 && day > 00 && month <= 12 && day <= 31) {
                int securityNumber;
                int residualNumber;
                int addedNumbers = 0;
                String multipliedNumbers = "";

                // Calculates the security number with the following code segments.
                // Alternately multiplies then numbers in the identitynumber with 2 and 1
                // excluding the last number. Finally the result is stored.
                for (int i = 0; i < tempIdentityNumber.length() - 1; i++) {
                    int n = Character.getNumericValue(tempIdentityNumber.charAt(i));

                    if ((i % 2) != 0) {
                        //Odd
                        n = n * 1;
                    } else if ((i % 2) == 0) {
                        //Even
                        n = n * 2;
                    }
                    multipliedNumbers += n;
                }
                // Loops through each digit and adds them together into an integer.
                for (int k = 0; k < multipliedNumbers.length(); k++) {
                    addedNumbers += Character.getNumericValue(multipliedNumbers.charAt(k));
                }
                // Subtracts the last digit from the added integer above from 10.
                residualNumber = 10 - (addedNumbers % 10);

                // If residual number equals 10 then security number will be 0,
                // else it will be assigned the residual number.
                if (residualNumber == 10) {
                    securityNumber = 0;
                } else {
                    securityNumber = residualNumber;
                }
                // Final validation. If the last digit from the identitynumber
                // equals the calculated security number then it is valid.
                // Else it is invalid.
                if (lastDigit == securityNumber) {
                    return true;
                }
            }
        }
        Toast.makeText(myContext, getString(R.string.toast_incorrect_identitynumber), Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean areAllFieldsFilled() {
        Log.d(TAG, "areAllFieldsFilled: Starts.");
        if (!foreName.equals("")
                && !surName.equals("")
                && !identityNumber.equals("")
                && !streetAddress.equals("")
                && !postCode.equals("")
                && !city.equals("")
                && !phoneNumber.equals("")
                && !email.equals("")) {
            return true;
        } else {
            Toast.makeText(myContext, getString(R.string.toast_notAllFieldsAreFilled), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validEmail(CharSequence checkEmail) {
        if (checkEmail == null) {
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches()) {
            Toast.makeText(myContext, getString(R.string.toast_incorrect_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        return android.util.Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches();
    }

    private boolean validPhoneNumber() {
        if (phoneNumber.length() == 10) {
            return true;
        }

        Toast.makeText(myContext, getString(R.string.toast_incorrect_phone), Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean validPostCode() {
        if (postCode.length() == 6) {
            return true;
        }

        Toast.makeText(myContext, getString(R.string.toast_incorrect_postcode), Toast.LENGTH_SHORT).show();
        return false;
    }

    private void doubleCheckAndPay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext, AlertDialog.THEME_HOLO_DARK);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.alert_title));
        builder.setMessage(getString(R.string.alert_forename) + member.getForename() + "\n" + getString(R.string.alert_surname) + member.getSurname() + "\n"
                + getString(R.string.alert_gender) + member.getGender() + "\n" + getString(R.string.alert_identitynumber) + member.getIdentitynumber() + "\n"
                + getString(R.string.alert_streetaddress) + member.getStreetaddress() + "\n" + getString(R.string.alert_postcode) + member.getPostcode() + "\n"
                + getString(R.string.alert_city) + member.getCity() + "\n" + getString(R.string.alert_phonenumber) + member.getPhonenumber() + "\n"
                + getString(R.string.alert_email) + member.getEmail());
        builder.setNegativeButton(getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spam = true;
                sendEmail();
                if (isSwishAppInstalled(myContext, "se.bankgirot.swish")) {
                    //TODO token och callbackurl?
                    startSwish(myContext, "???", "sdur://connect", "res", 0);
                }
            }
        });
        builder.show();
    }

    private void sendEmail() {
        // Declaring all the member's information into a single String.
        StringBuilder memberinfo = new StringBuilder();

        memberinfo.append(member.getForename()).append("\n\n").append(member.getSurname()).append("\n\n").append(member.getGender()).append("\n\n")
                .append(member.getIdentitynumber()).append("\n\n").append(member.getStreetaddress()).append("\n\n").append(member.getPostcode())
                .append("\n\n").append(member.getCity()).append("\n\n").append(member.getPhonenumber()).append("\n\n").append(member.getEmail());

        // Sending the email with all the member's information.
        SendMail sm = new SendMail(myContext, MAILTOSDUR, getString(R.string.sendemail_subject)
                + " " + member.getForename() + " " + member.getSurname(), memberinfo.toString());
        sm.execute();
    }

    // Swish package name is "se.bankgirot.swish".
    protected boolean isSwishAppInstalled(Context context, String SwishPackageName) {
        boolean isSwishInstalled = false;
        try {
            context.getPackageManager().getApplicationInfo(SwishPackageName, 0);
            isSwishInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "isSwishAppInstalled: Error!", e);
        }

        return isSwishInstalled;
    }

    public static boolean startSwish(Activity activity, String URL_encoded_JSON_payload,
                                     String callbackUrl, String callbackResultParameter, int requestCode) {
        if (callbackUrl == null || callbackUrl.length() == 0 || activity == null) {
            return false;
        }
        Uri scheme = Uri.parse("swish://payment?data=" + URL_encoded_JSON_payload +
                "&callbackurl=" + callbackUrl + "&callbackresultparameter=" + callbackResultParameter);
        Intent intent = new Intent(Intent.ACTION_VIEW, scheme);
        intent.setPackage("se.bankgirot.swish");
        boolean started = true;

        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            started = false;
        }

        return started;
    }
}