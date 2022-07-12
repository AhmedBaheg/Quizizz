package com.example.quizizz.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizizz.Model.QuizModel;
import com.example.quizizz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private QuizModel quizModel;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private TextView quiz_Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            quiz_Name = itemView.findViewById(R.id.quiz_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            OnItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    private List<QuizModel> quizModelArrayList;
    private OnItemClickListener OnItemClickListener;

    public void setQuizModelArrayList(List<QuizModel> quizModelArrayList) {
        this.quizModelArrayList = quizModelArrayList;
    }

    public QuizAdapter(QuizAdapter.OnItemClickListener onItemClickListener) {
        this.OnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        quizModel = quizModelArrayList.get(position);
        holder.quiz_Name.setText(quizModel.getTitle());
//        Picasso.get()
//                .load(quizModel.getImage())
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.placeholder)
//                .into(holder.image);

        Glide.with(holder.itemView)
                .load(quizModel.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        if (quizModelArrayList == null) {
            return 0;
        } else {
            return quizModelArrayList.size();
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

}
