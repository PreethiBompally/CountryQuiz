package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DatabaseInitializer(this).execute();

        Button buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        Button buttonViewResults = findViewById(R.id.buttonViewResults);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        // If you have a ResultsActivity to view past quizzes
        buttonViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PastQuizActivity.class);
                startActivity(intent);
            }
        });

    }
}
