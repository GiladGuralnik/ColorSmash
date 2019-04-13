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
        setContentView(R.layout.activity_admin_options);

        buttonStartGame = (Button) findViewById(R.id.ButtonShowUsers);
        buttonShowLeaderBoard = (Button) findViewById(R.id.ButtonResetLeaderBoard);
        buttonShowHighscore = (Button) findViewById(R.id.ButtonChangeUserColors);

        buttonStartGame.setOnClickListener(this);
        buttonShowLeaderBoard.setOnClickListener(this);
        buttonShowHighscore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonStartGame) {
            Intent act = new Intent(UserOptionsActivity.this, StartGameActivity.class);
            startActivity(act);
        }
        else if ( v == buttonShowLeaderBoard){
            Intent act = new Intent(UserOptionsActivity.this, LeaderBoardActivity.class);
            startActivity(act);
        }
        else if (v == buttonShowHighscore){
            Intent act = new Intent(UserOptionsActivity.this, ChangeUserColorsActivity.class);
            startActivity(act);
        }
    }
}
