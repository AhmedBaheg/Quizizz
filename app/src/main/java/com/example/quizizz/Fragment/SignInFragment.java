package com.example.quizizz.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizizz.R;
import com.example.quizizz.Services.Validation;
import com.example.quizizz.ViewModel.AuthViewModel;
import com.example.quizizz.databinding.FragmentSignInBinding;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private AuthViewModel model;
    private FragmentSignInBinding binding;
    private NavController navController;
    private Validation validation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
        validation = new Validation();

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        String email = binding.edEmail.getEditText().getText().toString();
        String password = binding.edPassword.getEditText().getText().toString();

        if (!validation.validationEmail(email, binding.edEmail)) {
            binding.edEmail.requestFocus();
        } else if (!validation.validationPassword(password, binding.edPassword)) {
            binding.edPassword.requestFocus();
        } else {

            model.signIn(email, password);

            model.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                @Override
                public void onChanged(FirebaseUser firebaseUser) {
                    if (firebaseUser != null) {
                        Toast.makeText(getContext(), "Successful Sign In", Toast.LENGTH_LONG).show();
                        navController.navigate(R.id.listFragment);
                    }
                }
            });

        }
    }
}