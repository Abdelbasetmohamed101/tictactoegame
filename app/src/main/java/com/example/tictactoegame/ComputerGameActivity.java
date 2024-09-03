package com.example.tictactoegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Random;


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

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Random;

public class ComputerGameActivity extends AppCompatActivity {




        boolean gameActive = true;

        // Player representation
        // 0 - X (User)
        // 1 - O (Computer)
        int activePlayer = 0;
        int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

        // State meanings:
        // 0 - X
        // 1 - O
        // 2 - Null
        int[][] winPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        public static int counter = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_friend_game);

            Button exit = findViewById(R.id.exit_button);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ComputerGameActivity.this, ComputerIntroActivity.class);
                    int REQUEST_CODE_ADD_NOTE = 1;
                    startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
                }
            });

            TextView status = findViewById(R.id.status);
            status.setText(getString(R.string.Turn_x));
        }

        public void playerTap(View view) {
            ImageView img = (ImageView) view;
            int tappedImage = Integer.parseInt(img.getTag().toString());

            if (!gameActive) {
                gameReset(view);
                counter = 0;
            }

            if (gameState[tappedImage] == 2 && activePlayer == 0) {
                counter++;
                gameState[tappedImage] = activePlayer;
                img.setTranslationY(-1000f);
                img.setImageResource(R.drawable.x);
                img.animate().translationYBy(1000f).setDuration(300);
                activePlayer = 1;

                TextView status = findViewById(R.id.status);
                status.setText(getString(R.string.Turn_O));

                checkWinner();

                if (gameActive && counter < 9) {
                    // Computer's move
                    new Handler().postDelayed(this::computerMove, 1000);
                }
            }
        }

        private void computerMove() {
            Random random = new Random();
            int tappedImage;

            // Find an empty spot for the computer to place its mark
            do {
                tappedImage = random.nextInt(9);
            } while (gameState[tappedImage] != 2);

            ImageView img = findViewById(getResources().getIdentifier("block" + (tappedImage + 1), "id", getPackageName()));
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            img.setImageResource(R.drawable.o);
            img.animate().translationYBy(1000f).setDuration(300);
            activePlayer = 0;
            counter++;

            TextView status = findViewById(R.id.status);
            status.setText(getString(R.string.Turn_x));

            checkWinner();
        }

        private void checkWinner() {
            int flag = 0;

            if (counter > 4) {
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;
                        gameActive = false;

                        String winnerStr;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = getString(R.string.x_won);
                            updateScore("score_player");
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar snackbar = Snackbar.make(parentLayout, "Congratulations X Player! Win the game!", Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundColor(Color.GREEN);
                            snackbar.show();
                        } else {
                            winnerStr = getString(R.string.o_won);
                            updateScore("score_Computer");
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar snackbar = Snackbar.make(parentLayout, "Congratulations O Player! Win the game!", Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundColor(Color.GREEN);
                            snackbar.show();
                        }

                        TextView status = findViewById(R.id.status);
                        status.setText(winnerStr);
                        break;
                    }
                }

                if (counter == 9 && flag == 0) {
                    TextView status = findViewById(R.id.status);
                    status.setText(getString(R.string.Draw));
                    gameActive = false;
                }
            }
        }

        public void gameReset(View view) {
            gameActive = true;
            activePlayer = 0;
            counter = 0;
            Arrays.fill(gameState, 2);

            for (int i = 1; i <= 9; i++) {
                ImageView img = findViewById(getResources().getIdentifier("block" + i, "id", getPackageName()));
                img.setImageResource(0);
            }

            TextView status = findViewById(R.id.status);
            status.setText(getString(R.string.Turn_x));
        }

        private void updateScore(String player) {
            SharedPreferences prefs = getSharedPreferences("game_prefs", MODE_PRIVATE);
            int currentScore = prefs.getInt(player, 0);
            currentScore++;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(player, currentScore);
            editor.apply();
        }
    }
