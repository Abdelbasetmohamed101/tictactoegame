package com.example.tictactoegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity {

    private Switch switchTheme, switchMusic;
    private ImageButton btnArabic, btnEnglish,linkedinButton,WhatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchTheme = findViewById(R.id.themeSwitch);
        switchMusic = findViewById(R.id.musicSwitch);
        btnArabic = findViewById(R.id.arabicButton);
        btnEnglish = findViewById(R.id.englishButton);

        loadSettings();
        setupListeners();

         linkedinButton = findViewById(R.id.imageButton);
         WhatsButton = findViewById(R.id.imageButton2);
        linkedinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The URL of your LinkedIn profile
                String url = "https://www.linkedin.com/in/abdelbaset-mohamed-25abaa1b3/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        WhatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToWhatsApp();
            }
        });
    }

    private void sendMessageToWhatsApp() {
        String phoneNumber = "+201019861425"; // Phone number including country code
         WhatsButton = findViewById(R.id.imageButton2);
        WhatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "201019861425"; // WhatsApp number with country code
                String message = "Hello, this is a test message!";

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(SettingsActivity.this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setupListeners() {
        btnArabic.setOnClickListener(v -> {
            changeLanguage("ar");
        });

        btnEnglish.setOnClickListener(v -> {
            changeLanguage("en");
        });

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeTheme(isChecked);
        });

        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            controlMusic(isChecked);
        });
    }

    private void changeLanguage(String lang) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        // Apply language change globally
        applyThemeAndLocale();

        // Restart all activities to apply the changes
        restartApp();
    }

    private void changeTheme(boolean isDarkMode) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Dark_Mode", isDarkMode);
        editor.apply();

        // Apply theme change globally
        applyThemeAndLocale();

        // Restart all activities to apply the changes
        restartApp();
    }

    private void controlMusic(boolean shouldPlay) {
        Intent serviceIntent = new Intent(SettingsActivity.this, MusicService.class);
        serviceIntent.putExtra("shouldPlay", shouldPlay);
        startService(serviceIntent);

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putBoolean("Music_Playing", shouldPlay);
        editor.apply();
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en");
        boolean isDarkMode = prefs.getBoolean("Dark_Mode", false);

        // Load settings and update UI
        setLocale(language);
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        switchTheme.setChecked(isDarkMode);

        boolean isMusicPlaying = prefs.getBoolean("Music_Playing", false);
        switchMusic.setChecked(isMusicPlaying);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}