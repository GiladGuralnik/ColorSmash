package com.example.colorsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class UserOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonShowUsers;
    private Button buttonResetLeaderBoard;
    private Button buttonChangeUserColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        buttonShowUsers = (Button) findViewById(R.id.ButtonShowUsers);
        buttonResetLeaderBoard = (Button) findViewById(R.id.ButtonResetLeaderBoard);
        buttonChangeUserColors = (Button) findViewById(R.id.ButtonChangeUserColors);

        buttonShowUsers.setOnClickListener(this);
        buttonResetLeaderBoard.setOnClickListener(this);
        buttonChangeUserColors.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonShowUsers) {
            Intent act = new Intent(UserOptionsActivity.this, StartGameActivity.class);
            startActivity(act);
        }
        else if ( v == buttonResetLeaderBoard){
            Intent act = new Intent(UserOptionsActivity.this, LeaderBoardActivity.class);
            startActivity(act);
        }
        else if (v == buttonChangeUserColors){
            Intent act = new Intent(UserOptionsActivity.this, ChangeUserColorsActivity.class);
            startActivity(act);
        }
    }
}
