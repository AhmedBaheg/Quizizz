package com.example.quizizz.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.quizizz.Model.QuestionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private OnQuestionComplete onQuestionComplete;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Quiz");
    private MutableLiveData<List<QuestionModel>> listMutableLiveData;
    private String quizID;

    public QuestionRepository(OnQuestionComplete onQuestionComplete) {
        this.onQuestionComplete = onQuestionComplete;
    }

    public MutableLiveData<List<QuestionModel>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
        Log.i("tag", "Repository Setter: " + quizID);
    }

    public void getQuestions() {

        Log.i("tag", "Repository: " + quizID);

        ArrayList<QuestionModel> questionModelArrayList = new ArrayList<>();

        ref.child(quizID).child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuestionModel model = dataSnapshot.getValue(QuestionModel.class);
                    questionModelArrayList.add(model);
                }
                onQuestionComplete.onSuccess(questionModelArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onQuestionComplete.onFailure(error);
            }
        });
    }

    public interface OnQuestionComplete {
        void onSuccess(ArrayList<QuestionModel> questionArrayList);

        void onFailure(DatabaseError error);
    }

}
