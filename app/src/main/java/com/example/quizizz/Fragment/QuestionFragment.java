package com.example.quizizz.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizizz.Model.QuestionModel;
import com.example.quizizz.R;
import com.example.quizizz.ViewModel.QuestionViewModel;
import com.example.quizizz.ViewModel.QuizViewModel;
import com.example.quizizz.databinding.FragmentQuestionBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private QuestionViewModel model;
    private NavController navController;
    private FragmentQuestionBinding binding;

    private String quizID, answer;
    private long questionCount, timer;
    private int currentQuestionNo, notAnswered, correctAnswer, wrongAnswer;
    private boolean canAnswer;
    private CountDownTimer countDownTimer;
    long millis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(QuestionViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        quizID = QuestionFragmentArgs.fromBundle(getArguments()).getQuizID();
        questionCount = QuestionFragmentArgs.fromBundle(getArguments()).getQestionsCount();
        model.setQuizID(quizID);

        loadData();

        binding.optionA.setOnClickListener(this);
        binding.optionB.setOnClickListener(this);
        binding.optionC.setOnClickListener(this);
        binding.nextQuestion.setOnClickListener(this);

    }

    private void loadData() {
        binding.nextQuestion.setVisibility(View.GONE);
        loadQuestions(1);
    }

    private void loadQuestions(int i) {

        currentQuestionNo = i;

        model.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                binding.question.setText("Q"+i+": "+questionModels.get(i - 1).getQuestion());
                binding.optionA.setText(questionModels.get(i - 1).getOption_a());
                binding.optionB.setText(questionModels.get(i - 1).getOption_b());
                binding.optionC.setText(questionModels.get(i - 1).getOption_c());
                binding.questionCount.setText(String.valueOf("Total Q: " +questionCount));
                timer = questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i - 1).getAnswer();
                startTimer();
            }
        });
        canAnswer = true;
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long l) {
//                binding.timer.setText(l / 1000 + "");
                millis = l;

                int minute = (int) ((l / 1000) / 60);
                int second = (int) ((l / 1000) % 60);

                String decoration = String.format(Locale.ENGLISH, "%02d : %02d", minute, second);
                binding.timer.setText(decoration);

                if (l < 6000){
                    binding.timer.setTextColor(Color.RED);
                    binding.image.setImageResource(R.drawable.ic_timer_red);
                }

            }

            @Override
            public void onFinish() {
                canAnswer = false; // To not allow the user can choose answer after time finish
                notAnswered++;
                showBtnNext();
            }
        }.start();
    }

    private void showBtnNext() {
        if (currentQuestionNo == questionCount) {
            binding.nextQuestion.setText("Submit");
        }
        binding.nextQuestion.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_A:
                verifyAnswer(binding.optionA);
                break;
            case R.id.option_B:
                verifyAnswer(binding.optionB);
                break;
            case R.id.option_C:
                verifyAnswer(binding.optionC);
                break;
            case R.id.next_Question:
                if (currentQuestionNo == questionCount) {
                    submitResult();
                } else {
                    currentQuestionNo++;
                    loadQuestions(currentQuestionNo);
                    resetOptions();
                }
                break;

        }
    }

    private void resetOptions() {
        binding.nextQuestion.setVisibility(View.GONE);
        binding.optionA.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.answer_background));
        binding.optionB.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.answer_background));
        binding.optionC.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.answer_background));
        binding.timer.setTextColor(Color.WHITE);
        binding.image.setImageResource(R.drawable.ic_timer);

    }

    private void submitResult() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("correct", correctAnswer);
        result.put("wrong", wrongAnswer);
        result.put("notAnswered", notAnswered);

    }

    private void verifyAnswer(Button button) {

        if (canAnswer) {
            if (answer.equalsIgnoreCase(button.getText().toString())) {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.correct_answer));
                correctAnswer++;
            } else {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.wrong_answer));
                wrongAnswer++;
            }
        }

        canAnswer = false; // to not allow the use choose multi answer in the same time (this var force user choose 1 answer just)
        countDownTimer.cancel();
        showBtnNext();

    }

}