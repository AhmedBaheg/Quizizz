package com.example.quizizz.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.quizizz.R;
import com.example.quizizz.Services.Validation;
import com.example.quizizz.ViewModel.AuthViewModel;
import com.example.quizizz.databinding.FragmentSignUpBinding;
import com.example.quizizz.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {

    private AuthViewModel model;
    private FragmentSplashBinding binding;
    private NavController navController;
    private Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        View view = binding.getRoot();

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation);
        binding.logo.startAnimation(animation);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (model.getCurrentUser() != null) {
                    Toast.makeText(getContext(), model.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.action_splashFragment_to_listFragment);
                } else {
                    navController.navigate(R.id.action_splashFragment_to_signInFragment);
                }
            }
        }, 5000);

    }
}