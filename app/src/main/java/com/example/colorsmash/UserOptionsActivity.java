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
    private Button buttonMyDiagnosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        buttonStartGame = (Button) findViewById(R.id.ButtonStartGameForRegisteredUser);
        buttonShowLeaderBoard = (Button) findViewById(R.id.ButtonShowLeaderBoard);
        buttonMyDiagnosis = (Button) findViewById(R.id.ButtonMyDiagnosis);

        buttonStartGame.setOnClickListener(this);
        buttonShowLeaderBoard.setOnClickListener(this);
        buttonMyDiagnosis.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonStartGame) {
            this.finish();
            Intent act = new Intent(UserOptionsActivity.this, UserGameActivity.class);
            startActivity(act);
        }
        else if ( v == buttonShowLeaderBoard){
            this.finish();
            Intent act = new Intent(UserOptionsActivity.this, LeaderBoardActivity.class);
            startActivity(act);
        }
        else if ( v == buttonMyDiagnosis){
            this.finish();
            Intent act = new Intent(UserOptionsActivity.this, MyDiagnosisActivity.class);
            startActivity(act);
        }
    }
}
