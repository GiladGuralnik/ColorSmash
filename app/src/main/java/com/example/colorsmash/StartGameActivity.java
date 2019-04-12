package com.example.colorsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

public class StartGameActivity extends AppCompatActivity {

    //GameFrame
    private FrameLayout gameFrame;
    private int frameHeight,frameWidth,initialFrameWidth;
    private LinearLayout startLayout;

    //Images
    private ImageView box,black,orange,pink;
    private Drawable imageBoxRight,imageBoxLeft;

    //Size
    private int boxSize;

    //position


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);


    }

    public void startGame(View view)
    {

    }


    public void exitGame(View view)
    {

    }


}
