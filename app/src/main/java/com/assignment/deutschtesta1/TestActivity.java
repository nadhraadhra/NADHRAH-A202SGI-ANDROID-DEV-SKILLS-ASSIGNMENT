package com.assignment.deutschtesta1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {
    public static final String ADDITIONAL_SCORE = "additionalScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";



    private TextView textViewQuestion;
    private TextView textViewScoreTest;
    private TextView textViewQuestionCount;
    private TextView textViewTimer;
    private RadioGroup rbGroup;
    private RadioButton rbtn1;
    private RadioButton rbtn2;
    private RadioButton rbtn3;
    private Button buttonNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColourDefaultTimer;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;


    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    //Big Value
    private long backPressedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScoreTest = findViewById(R.id.textViewScoreTest);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewTimer = findViewById(R.id.text_view_timer);
        rbGroup = findViewById(R.id.radioGroup);
        rbtn1 = findViewById(R.id.radio_ansButton1);
        rbtn2 = findViewById(R.id.radio_ansButton2);
        rbtn3 = findViewById(R.id.radio_ansButton3);
        buttonNext = findViewById(R.id.button_next);

        //text Color for radio Button
        textColorDefaultRb = rbtn1.getTextColors();

        //text Color for timer
        textColourDefaultTimer = textViewTimer.getTextColors();

        if (savedInstanceState == null) {

            TestDbHelper dbHelper = new TestDbHelper(this);
            questionList = dbHelper.getAllQuestions();
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();

        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            //If want to show the current timer if the answer not answer yet

            if (!answered) {
                startTimer();
            } else {
                updateTimerText();
                showSolution();
            }
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rbtn1.isChecked() || rbtn2.isChecked() || rbtn3.isChecked()) {
                        checkAnswer();

                    } else {
                        Toast.makeText(TestActivity.this, "Please choose an answer", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    showNextQuestion();
                }
            }
        });

    }

    private void showNextQuestion() {
        rbtn1.setTextColor(textColorDefaultRb);
        rbtn2.setTextColor(textColorDefaultRb);
        rbtn3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rbtn1.setText(currentQuestion.getChoice1());
            rbtn2.setText(currentQuestion.getChoice2());
            rbtn3.setText(currentQuestion.getChoice3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonNext.setText("Next");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startTimer();

        } else {
            finishTest();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateTimerText();
                checkAnswer();

            }
        }.start();
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int)(timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeFormatted);

        //Change colour if below than 10sec

        if (timeLeftInMillis < 10000) {
            textViewTimer.setTextColor(Color.RED);
        } else {
            textViewTimer.setTextColor(textColourDefaultTimer);
        }

    }

    private void checkAnswer(){
        answered = true;

        countDownTimer.cancel();

        RadioButton rbChoosen = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNum = rbGroup.indexOfChild(rbChoosen) + 1;

        if (answerNum == currentQuestion.getAnswerNum()) {
            score++;
            textViewScoreTest.setText("Score: " + score);

        }

        showSolution();
    }

    private void showSolution() {
        rbtn1.setTextColor(Color.WHITE);
        rbtn2.setTextColor(Color.WHITE);
        rbtn3.setTextColor(Color.WHITE);

        switch (currentQuestion.getAnswerNum()) {
            case 1:
                rbtn1.setTextColor(Color.YELLOW);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rbtn2.setTextColor(Color.YELLOW);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rbtn3.setTextColor(Color.YELLOW);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonNext.setText("OK");

        } else {
            buttonNext.setText("Finish");
        }

    }

    private void finishTest() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ADDITIONAL_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    //When user click back button and it will only leave the test apps after user click back button 2 times
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finishTest();

        } else {
            Toast.makeText(this,"Press back one more time to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);

    }
}
