package com.example.quizizz.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.quizizz.Repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    private AuthViewModel INSTANCE;
//    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    private AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
//        firebaseUserMutableLiveData = authRepository.getFirebaseUserMutableLiveData();
        currentUser = authRepository.getCurrentUser();

    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return authRepository.getFirebaseUserMutableLiveData();
    }

    public FirebaseUser getCurrentUser() {
        return authRepository.getCurrentUser();
    }

    public void signUp(String email, String pass){
        authRepository.signUp(email, pass);
    }

    public void signIn(String email, String pass){
        authRepository.signIn(email, pass);
    }

    public void signOut(){
        authRepository.signOut();
    }

}
