package com.example.tictactoegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChoiceActivity extends AppCompatActivity {
    Button FriendButton,ComputerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
         FriendButton=findViewById(R.id.FriendsButton);
         ComputerButton=findViewById(R.id.ComputerButton);

        FriendButton.setOnClickListener(v -> {
            Intent Friendsintent = new Intent(ChoiceActivity.this, FriendIntroActivity.class);
            startActivity(Friendsintent);
        });

        ComputerButton.setOnClickListener(v -> {
            Intent Friendsintent = new Intent(ChoiceActivity.this, ComputerIntroActivity.class);
            startActivity(Friendsintent);
        });
    }
}