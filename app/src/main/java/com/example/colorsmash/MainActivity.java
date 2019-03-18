package com.example.colorsmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private Button buttonLogin;
    private Button buttonAdminLogin;
    private Button buttonStartGame;
    private Button buttonExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonExit = (Button) findViewById(R.id.ButtonExit);
        buttonLogin = (Button) findViewById(R.id.ButtonLogin);
        buttonAdminLogin = (Button) findViewById(R.id.ButtonAdminLogin);
        buttonRegister = (Button) findViewById(R.id.ButtonRegister);
        buttonStartGame = (Button) findViewById(R.id.ButtonStartGame);

        buttonRegister.setOnClickListener(this);
        buttonStartGame.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonAdminLogin.setOnClickListener(this);
        buttonExit.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        if(view==buttonStartGame) {
            return;
        }
        else if(view == buttonRegister) {
            Intent startGame = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(startGame);
        }

        else if(view == buttonLogin) {
            Intent startGame = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(startGame);
        }

        else if (view == buttonAdminLogin) {
            Intent startGame = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(startGame);
        }
        else if (view == buttonExit) {
            return;
        }
    }






}
