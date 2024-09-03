package com.example.tictactoegame;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class ComputerIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_intro);

        Button gameButton=findViewById(R.id.start_button);
        // Load the saved scores from SharedPreferences
        TextView scorePlayerA = findViewById(R.id.score_player);
        TextView scorePlayerB = findViewById(R.id.score_computer);

        // Set click listener for the add note button
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FriendGameintent = new Intent(ComputerIntroActivity.this, ComputerGameActivity.class);
                int REQUEST_CODE_ADD_NOTE=1;
                startActivityForResult(FriendGameintent, REQUEST_CODE_ADD_NOTE);
            }
        });

        SharedPreferences prefs = getSharedPreferences("game_prefs", MODE_PRIVATE);
        int score = prefs.getInt("score_player", 0);
        int scoreComputer = prefs.getInt("score_COMPUTER", 0);

        scorePlayerA.setText(String.valueOf(score));
        scorePlayerB.setText(String.valueOf(scoreComputer));
    }
}
