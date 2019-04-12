package com.example.colorsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

public class StartGameActivity extends AppCompatActivity {


    private Button startGameButton;
    private Button logoutButton;
    private TextView scoreViewText;


    //private FirebaseAuth firebaseAuth;
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        //firebaseAuth=FirebaseAuth.getInstance();

        startGameButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                    startGame();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

    }
    private void startGame(){
        scoreViewText.setText("999");
    }



}
