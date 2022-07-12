package com.example.quizizz.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.quizizz.Adapter.QuizAdapter;
import com.example.quizizz.Model.QuizModel;
import com.example.quizizz.R;
import com.example.quizizz.ViewModel.QuizViewModel;
import com.example.quizizz.databinding.FragmentListBinding;
import com.example.quizizz.databinding.FragmentSignInBinding;

import java.util.List;


public class ListFragment extends Fragment implements QuizAdapter.OnItemClickListener {

    private QuizViewModel model;
    private NavController navController;
    private FragmentListBinding binding;
    private QuizAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
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
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizAdapter(this);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setHasFixedSize(true);

        model.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizModel>>() {
            @Override
            public void onChanged(List<QuizModel> quizModelList) {
                binding.progress.setVisibility(View.GONE);
                adapter.setQuizModelArrayList(quizModelList);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void OnItemClick(int position) {
        ListFragmentDirections.ActionListFragmentToDetailsFragment action =
                ListFragmentDirections.actionListFragmentToDetailsFragment();
        action.setPosition(position);
        navController.navigate(action);
    }
}