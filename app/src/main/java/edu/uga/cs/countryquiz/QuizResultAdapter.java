package edu.uga.cs.countryquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.QuizResultViewHolder> {
    private List<QuizResult> quizResults;

    public QuizResultAdapter(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    @NonNull
    @Override
    public QuizResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_result, parent, false);
        return new QuizResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizResultViewHolder holder, int position) {
        QuizResult quizResult = quizResults.get(position);
        holder.textViewScore.setText("Score: " + quizResult.getScore());
        holder.textViewDate.setText("Date: " + quizResult.getDate());
    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }

    public static class QuizResultViewHolder extends RecyclerView.ViewHolder {
        TextView textViewScore;
        TextView textViewDate;

        public QuizResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewScore = itemView.findViewById(R.id.textViewScore);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
