package com.example.quizizz.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizizz.Model.QuestionModel;
import com.example.quizizz.Repository.QuestionRepository;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionComplete {

    private QuestionRepository questionRepository;

    public QuestionViewModel(){
        questionRepository = new QuestionRepository(this);
    }

    public MutableLiveData<List<QuestionModel>> getListMutableLiveData() {
        return questionRepository.getListMutableLiveData();
    }

    public void setQuizID(String quizID) {
        questionRepository.setQuizID(quizID);
        questionRepository.getQuestions();
    }

    @Override
    public void onSuccess(ArrayList<QuestionModel> questionArrayList) {
        getListMutableLiveData().setValue(questionArrayList);
    }

    @Override
    public void onFailure(DatabaseError error) {
        Log.i("QuestionError", error.getMessage());
    }
}
