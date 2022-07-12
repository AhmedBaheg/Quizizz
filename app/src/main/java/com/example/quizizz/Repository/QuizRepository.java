package com.example.quizizz.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.quizizz.Model.QuizModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuizRepository {

    private onFirebaseComplete onFirebaseComplete;
    private CollectionReference reference = FirebaseFirestore.getInstance().collection("Quiz");
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Quiz");
    private MutableLiveData<List<QuizModel>> listMutableLiveData;
    private QuizRepository INSTANCE;

    public QuizRepository(QuizRepository.onFirebaseComplete onFirebaseComplete) {
        this.onFirebaseComplete = onFirebaseComplete;
    }

    public MutableLiveData<List<QuizModel>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public QuizRepository getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new QuizRepository(onFirebaseComplete);
        }
        return INSTANCE;
    }

    public void fetchData() {
        ArrayList<QuizModel> quizModelArrayList = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quizModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuizModel model = dataSnapshot.getValue(QuizModel.class);
                    quizModelArrayList.add(model);
                }
                onFirebaseComplete.onSuccess(quizModelArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onFirebaseComplete.onFailure(error);
            }
        });
    }

    public interface onFirebaseComplete {
        void onSuccess(List<QuizModel> quizModelList);

        void onFailure(DatabaseError e);
    }

}
