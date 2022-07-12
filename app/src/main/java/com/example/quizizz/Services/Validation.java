package com.example.quizizz.Services;

import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validationEmail(String value, TextInputLayout layout) {
        if (TextUtils.isEmpty(value)) {
            layout.setError("Please Enter Your Email");
            return false;
        } else if (!isEmailValid(value)) {
            layout.setError("Please Enter Correct Email example@example.com");
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }

    public boolean validationPassword(String value, TextInputLayout layout) {
        if (TextUtils.isEmpty(value)) {
            layout.setError("Please Enter Your Password");
            return false;
        } else if (value.length() < 6) {
            layout.setError("The Password Should Be More Than 6 Digits");
            return false;
        } else {
            layout.setError(null);
            return true;
        }
    }


}
