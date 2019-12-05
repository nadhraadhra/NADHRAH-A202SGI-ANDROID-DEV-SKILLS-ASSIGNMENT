package com.assignment.deutschtesta1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        startActivity(intent);
    }
}
