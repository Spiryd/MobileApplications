package com.example.gradoktorazawadyjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int r1 = 0;
    private int r2 = 0;
    private int score = 0;

    private TextView points;
    private Button left;
    private Button right;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        left = findViewById(R.id.leftButton);
        right = findViewById(R.id.rightButton);
        points = findViewById(R.id.points);
        score = 0;
        random = new Random();
        roll();
    }

    private void roll(){
        points.setText("Points: " + score);
        r1 = random.nextInt(16);
        r2 = random.nextInt(16);
        left.setText(Integer.toString(r1));
        right.setText(Integer.toString(r2));
    }

    public void leftClick(View view) {
        if(r1 >= r2){
            score++;
            Toast.makeText(this, "Dobrze!!", Toast.LENGTH_SHORT).show();
        }
        roll();
    }

    public void rightClick(View view) {
        if(r1 <= r2){
            score++;
            Toast.makeText(this, "Dobrze!!", Toast.LENGTH_SHORT).show();
        }
        roll();
    }
}