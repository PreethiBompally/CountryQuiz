package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class QuizActivity extends AppCompatActivity implements QuestionFragment.OnAnswerSelectedListener {
    private ViewPager2 viewPagerQuestions;
    private List<Question> questions;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        questions = dbHelper.getRandomQuestions(db);  // Ensure this method is adapted to fetch two related questions per screen
        db.close();

        viewPagerQuestions = findViewById(R.id.viewPagerQuestions);
        viewPagerQuestions.setAdapter(new QuizPagerAdapter(this, groupQuestions(questions)));
        viewPagerQuestions.setUserInputEnabled(true);
        viewPagerQuestions.registerOnPageChangeCallback(new QuizPageChangeCallback());
    }

    // Group questions to display two per screen
    private List<List<Question>> groupQuestions(List<Question> allQuestions) {
        List<List<Question>> groupedQuestions = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i += 2) {
            List<Question> pair = new ArrayList<>();
            pair.add(allQuestions.get(i));
            if (i + 1 < allQuestions.size()) {
                pair.add(allQuestions.get(i + 1));
            }
            groupedQuestions.add(pair);
        }
        return groupedQuestions;
    }

    private void navigateToResults() {
        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnswerSelected(int correctAnswersCount) {
        score += correctAnswersCount;
    }

    private class QuizPagerAdapter extends FragmentStateAdapter {
        private final List<List<Question>> groupedQuestions;

        public QuizPagerAdapter(@NonNull FragmentActivity fa, List<List<Question>> groupedQuestions) {
            super(fa);
            this.groupedQuestions = groupedQuestions;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            QuestionFragment fragment = QuestionFragment.newInstance(groupedQuestions.get(position));
            fragment.setOnAnswerSelectedListener(QuizActivity.this);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return groupedQuestions.size();
        }
    }


    private class QuizPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private int lastPosition = -1;  // Track the last page index
        private boolean attemptingToSwipePastEnd = false;  // Flag to indicate attempt to swipe past the end

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            lastPosition = position;
            attemptingToSwipePastEnd = false;  // Reset on page selection change
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (state == ViewPager2.SCROLL_STATE_DRAGGING && lastPosition == viewPagerQuestions.getAdapter().getItemCount() - 1) {
                // User starts dragging on the last page
                attemptingToSwipePastEnd = true;
            }
            else if (state == ViewPager2.SCROLL_STATE_SETTLING && attemptingToSwipePastEnd) {
                // State when starting to settle after a drag, but before idle
                attemptingToSwipePastEnd = false; // Reset as user might settle on the last page without swiping past
            }
            else if (state == ViewPager2.SCROLL_STATE_IDLE && attemptingToSwipePastEnd) {
                // When the page becomes idle after a drag, and the last drag was an attempt to swipe past end
                navigateToResults();
            }
        }
    }
}