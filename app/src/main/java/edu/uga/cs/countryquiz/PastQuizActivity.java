package edu.uga.cs.countryquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PastQuizActivity extends AppCompatActivity {

    private RecyclerView recyclerViewResults;
    private QuizResultAdapter adapter;
    private List<QuizResult> quizResults;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Button buttonStartQuiz = findViewById(R.id.buttonRestartQuiz);

        recyclerViewResults = findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        quizResults = dbHelper.getAllQuizResults();
        Log.d("PastQuizActivity", "Quiz results retrieved: " + quizResults.size());

        adapter = new QuizResultAdapter(quizResults);
        recyclerViewResults.setAdapter(adapter);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PastQuizActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}