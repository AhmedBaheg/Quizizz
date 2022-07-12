package com.example.quizizz.Services;

import com.google.firebase.auth.FirebaseAuth;

public class Constant {

    public static String getUID(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
