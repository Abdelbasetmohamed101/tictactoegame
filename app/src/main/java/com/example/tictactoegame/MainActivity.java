package com.example.tictactoegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTitle = findViewById(R.id.ticTacToeText);
        Button btnMain = findViewById(R.id.startButton);
        ImageButton btnSettings = findViewById(R.id.settingsButton);

        startMusicService();

        btnMain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
            startActivity(intent);
        });


        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void startMusicService() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isMusicPlaying = prefs.getBoolean("Music_Playing", false);
        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("shouldPlay", isMusicPlaying);
        startService(serviceIntent);
    }
}