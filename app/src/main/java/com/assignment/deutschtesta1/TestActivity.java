package com.assignment.deutschtesta1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity {
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


    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;



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

        textColorDefaultRb = rbtn1.getTextColors();

        TestDbHelper dbHelper = new TestDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

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
            buttonNext.setText("Confirm");
        } else {
            finishTest();
        }
    }

    private void checkAnswer(){
        answered = true;

        RadioButton rbChoosen = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNum = rbGroup.indexOfChild(rbChoosen) + 1;

        if (answerNum == currentQuestion.getAnswerNum()) {
            score++;
            textViewScoreTest.setText("Score: " + score);

        }

        showSolution();
    }

    private void showSolution() {
        rbtn1.setTextColor(Color.RED);
        rbtn2.setTextColor(Color.RED);
        rbtn3.setTextColor(Color.RED);

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
            buttonNext.setText("Next");

        } else {
            buttonNext.setText("Finish");
        }

    }

    private void finishTest() {
        finish();
    }
}
