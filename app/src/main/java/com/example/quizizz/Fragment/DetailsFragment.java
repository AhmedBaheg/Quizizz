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

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.quizizz.Model.QuizModel;
import com.example.quizizz.R;
import com.example.quizizz.ViewModel.QuizViewModel;
import com.example.quizizz.databinding.FragmentDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsFragment extends Fragment {

    private QuizViewModel model;
    private NavController navController;
    private FragmentDetailsBinding binding;
    private int position;
    private long questionsCount;
    private String quizID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(QuizViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        position = DetailsFragmentArgs.fromBundle(getArguments()).getPosition();

        model.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizModel>>() {
            @Override
            public void onChanged(List<QuizModel> quizModelList) {
                QuizModel model = quizModelList.get(position);

                binding.quizName.setText(model.getTitle());
                binding.tvLevel.setText(model.getLevel());
                binding.tvQuestions.setText(String.valueOf(model.getQuestions()));
                Glide.with(view)
                        .load(model.getImage())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(binding.image);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                },2000);

                quizID = model.getQuizID();
                questionsCount = model.getQuestions();

            }
        });

        binding.btnStarQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsFragmentDirections.ActionDetailsFragmentToQuestionFragment action =
                        DetailsFragmentDirections.actionDetailsFragmentToQuestionFragment();

                action.setQuizID(quizID);
                action.setQestionsCount(questionsCount);
                navController.navigate(action);
            }
        });

    }
}