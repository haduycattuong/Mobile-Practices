package com.example.thbuoi7minigame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView p1_score, p2_score, p_win;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int p1_count, p2_count, round_count;
    boolean activePlayer;

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winingPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p1_score = findViewById(R.id.tv_score_p1);
        p2_score = findViewById(R.id.tv_score_p2);
        p_win = findViewById(R.id.tv_who_win);

        resetGame = findViewById(R.id.btn_reset_all);

        for(int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourID = getResources().getIdentifier(
                    buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourID);
            buttons[i].setOnClickListener(this);
        }
        round_count = 0;
        p1_count = 0;
        p2_count = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")) {
            return;
        }

        String buttonsIDS = view.getResources().getResourceEntryName(view.getId());
        int gamestater = Integer.parseInt(
                buttonsIDS.substring(buttonsIDS.length()-1, buttonsIDS.length()));
        if(activePlayer) {
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gamestater] = 0;
        }else {
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gamestater] = 1;
        }
        round_count++;
        if(checkWhoWin()) {
            if(activePlayer) {
                p1_count++;
                updatePlayerScore();
                Toast.makeText(this, "Player 1 won", Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                p2_count++;
                updatePlayerScore();
                Toast.makeText(this, "Player 2 won", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(round_count == 9) {
            playAgain();
            Toast.makeText(this, "Player 2 won", Toast.LENGTH_SHORT).show();
        }else {
            activePlayer = !activePlayer;

        }

        if(p1_count > p2_count) {
            p_win.setText("Player One is winning");
        }else if(p2_count > p1_count) {
            p_win.setText("Player Two is winning");
        }else {
            p_win.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                p1_count = 0;
                p2_count = 0;
                p_win.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWhoWin() {
        boolean winnerWho = false;
        for(int[] winingPosistions : winingPositions) {
            if(gameState[winingPosistions[0]] == gameState[winingPosistions[1]]
            && gameState[winingPosistions[1]] == gameState[winingPosistions[2]]
            && gameState[winingPosistions[0]] != 2) {
                winnerWho = true;
            }
        }
        return winnerWho;
    }

    public void updatePlayerScore() {
        p1_score.setText(Integer.toString(p1_count));
        p2_score.setText(Integer.toString(p2_count));
    }
    public void playAgain() {
        round_count = 0;
        activePlayer = true;
        for(int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}