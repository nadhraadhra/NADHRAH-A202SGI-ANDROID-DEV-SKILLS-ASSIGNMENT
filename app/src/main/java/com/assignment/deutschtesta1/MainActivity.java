package com.assignment.deutschtesta1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_TEST = 1;

    public static final String SHARED_PREFERENCE = "sharedPreference";
    public static final String KEY_HIGHSCORE = "keyHighScore";

    private TextView textViewHighScore;

    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHighScore = findViewById(R.id.textViewHighScore);
        loadHighScore();

        Button buttonStartTest = findViewById(R.id.button_start_test);
        buttonStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
    }

    private void startTest() {
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TEST) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(TestActivity.ADDITIONAL_SCORE, 0);
                //If new score is higher than current highscore only it will rewrite and record new score
                if (score > highScore) {
                    updateHighScore(score);
                }
            }
        }
    }

    private void loadHighScore() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        highScore = preferences.getInt(KEY_HIGHSCORE, 0);
        textViewHighScore.setText("Highscore: " + highScore);


    }

    //update current score to new score when current score is high than previous score. else maintain the previous record
    private void updateHighScore(int highScoreNew) {
        highScore = highScoreNew;
        textViewHighScore.setText("Highscore: " + highScore);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_HIGHSCORE,highScore);
        editor.apply();

    }
}
