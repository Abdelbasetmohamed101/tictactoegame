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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Random;

public class FriendGameActivity extends AppCompatActivity {


    boolean gameActive = true;

    // Player representation
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    // this function will be called every time a
    // players tap in an empty box of the grid
    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // game reset function will be called
        // if someone wins or the boxes are full
        if (!gameActive) {
            gameReset(view);
            //Reset the counter
            counter = 0;
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            // increase the counter
            // after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // this will give a motion
            // effect to the image
            img.setTranslationY(-1000f);

            // change the active player
            // from 0 to 1 or 1 to 0
            if (activePlayer == 0) {
                // set the image of x
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText(getString(R.string.Turn_O));
            } else {
                // set the image of o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText(getString(R.string.Turn_x));
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Check if any player has won if counter is > 4 as min 5 taps are
        // required to declare a winner
        if (counter > 4) {
            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != 2) {
                    flag = 1;

                    // Somebody has won! - Find out who!
                    String winnerStr;

                    // game reset function be called
                    gameActive = false;
                    if (gameState[winPosition[0]] == 0) {
                        winnerStr = getString(R.string.x_won);
                        updateScore("score_playerA");
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar snackbar = Snackbar.make(parentLayout, "Congratulations X Player! Win the game!", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.GREEN);
                        snackbar.show();
                    } else {
                        winnerStr = getString(R.string.o_won);
                        updateScore("score_playerB");
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar snackbar = Snackbar.make(parentLayout, "Congratulations O Player! Win the game!", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.GREEN);
                        snackbar.show();
                    }
                    // Update the status bar for winner announcement
                    TextView status = findViewById(R.id.status);
                    status.setText(winnerStr);
                }
            }
            // set the status if the match draw
            if (counter == 9 && flag == 0) {
                TextView status = findViewById(R.id.status);
                status.setText(getString(R.string.Draw));
            }
        }
    }

    // reset the game
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;

        //set all position to Null
        Arrays.fill(gameState, 2);

        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.block1)).setImageResource(0);
        ((ImageView) findViewById(R.id.block2)).setImageResource(0);
        ((ImageView) findViewById(R.id.block3)).setImageResource(0);
        ((ImageView) findViewById(R.id.block4)).setImageResource(0);
        ((ImageView) findViewById(R.id.block5)).setImageResource(0);
        ((ImageView) findViewById(R.id.block6)).setImageResource(0);
        ((ImageView) findViewById(R.id.block7)).setImageResource(0);
        ((ImageView) findViewById(R.id.block8)).setImageResource(0);
        ((ImageView) findViewById(R.id.block9)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_game);

        Button exit = findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendGameActivity.this,FriendIntroActivity.class);
                int REQUEST_CODE_ADD_NOTE = 1;
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });
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