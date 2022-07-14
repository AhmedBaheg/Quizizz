package com.example.quizizz.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizizz.R;
import com.example.quizizz.databinding.FragmentResultBinding;

public class ResultFragment extends Fragment {

    private int correctAnswer, wrongAnswer, notAnswered, totalResult, percent;
    private FragmentResultBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        correctAnswer = ResultFragmentArgs.fromBundle(getArguments()).getCorrectAnswer();
        wrongAnswer = ResultFragmentArgs.fromBundle(getArguments()).getWrongAnswer();
        notAnswered = ResultFragmentArgs.fromBundle(getArguments()).getNotAnswered();

        binding.correctAnswer.setText(String.valueOf(correctAnswer));
        binding.wrongtAnswer.setText(String.valueOf(wrongAnswer));
        binding.notAnswer.setText(String.valueOf(notAnswered));

        totalResult = correctAnswer + wrongAnswer + notAnswered;
        percent = (correctAnswer*100)/totalResult;

        binding.tvResult.setText(percent+"%");
        binding.progressBar.setProgress(percent);

        binding.btnTakeNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_resultFragment_to_listFragment);
            }
        });

    }
}