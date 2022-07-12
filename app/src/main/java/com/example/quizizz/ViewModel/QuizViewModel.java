package com.example.quizizz.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizizz.Model.QuizModel;
import com.example.quizizz.Repository.QuizRepository;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class QuizViewModel extends ViewModel implements QuizRepository.onFirebaseComplete {

    private QuizRepository quizRepository;

    public QuizViewModel(){
        quizRepository = new QuizRepository(this);
        quizRepository.getINSTANCE().fetchData();
    }

    public MutableLiveData<List<QuizModel>> getListMutableLiveData() {
        return quizRepository.getListMutableLiveData();
    }

    @Override
    public void onSuccess(List<QuizModel> quizModelList) {
        getListMutableLiveData().setValue(quizModelList);
    }

    @Override
    public void onFailure(DatabaseError e) {
        Log.i("QuizError" , e.getMessage());
    }


}
