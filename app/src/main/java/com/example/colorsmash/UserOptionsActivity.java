package com.example.colorsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class UserOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonStartGame;
    private Button buttonShowLeaderBoard;
    private Button buttonShowHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        buttonStartGame = (Button) findViewById(R.id.ButtonStartGameForRegisteredUser);
        buttonShowLeaderBoard = (Button) findViewById(R.id.ButtonShowLeaderBoard);

        buttonStartGame.setOnClickListener(this);
        buttonShowLeaderBoard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonStartGame) {
            Intent act = new Intent(UserOptionsActivity.this, UserGameActivity.class);
            startActivity(act);
        }
        else if ( v == buttonShowLeaderBoard){
            Intent act = new Intent(UserOptionsActivity.this, LeaderBoardActivity.class);
            startActivity(act);
        }
    }
}
