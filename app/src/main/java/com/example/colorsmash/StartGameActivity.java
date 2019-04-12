package com.example.colorsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

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

    //Position
    private float boxX,boxY;
    private float blackX,blackY;
    private float orangeX,orangeY;
    private float pinkX,pinkY;

    //Score
    private TextView scoreLabel,highScoreLabel;
    private int score,highScore,timeCount;

    //Class
    private Timer timer;
    private Handler handler = new Handler();

    //Status
    private boolean start_flg = false;
    private boolean action_flg = false;
    private boolean pink_flg = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);


        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        box = findViewById(R.id.box);
        black = findViewById(R.id.black);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        imageBoxLeft = getResources().getDrawable(R.drawable.box_left);
        imageBoxRight = getResources().getDrawable(R.drawable.box_right);


    }

    public void changePos()
    {

        //Move Box
        if(action_flg)
        {
            //Touch screen
            boxX += 14;
            box.setImageDrawable(imageBoxRight);
        }
        else
        {
            //Release touch
            boxX -= 14;
            box.setImageDrawable(imageBoxLeft);
        }

        //Check Box Position
        if(boxX <= 0)
        {
            boxX = 0;
            box.setImageDrawable(imageBoxRight);
        }
        if(frameWidth - boxSize < boxX)
        {
            boxX = frameWidth - boxSize;
            box.setImageDrawable(imageBoxLeft);
        }

        box.setX(boxX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start_flg)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                action_flg = true;
            }
            else if (event.getAction() == MotionEvent.ACTION_UP)
            {
                action_flg = false;
            }
        }

        return true;
    }

    public void startGame(View view)
    {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);

        if( frameHeight == 0 )
        {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            initialFrameWidth = frameWidth;

            boxSize = box.getHeight();
            boxX = box.getX();
            boxY = box.getY();

            box.setX(0.0f); //initial location bottom-left
            black.setY(3000.0f);
            orange.setY(3000.0f);
            pink.setY(3000.0f);

            blackY = black.getY();
            orangeY = orange.getY();
            pinkY = pink.getY();


            box.setVisibility(View.VISIBLE);
            black.setVisibility(View.VISIBLE);
            orange.setVisibility(View.VISIBLE);
            pink.setVisibility(View.VISIBLE);

            timeCount = 0;
            score = 0;
            scoreLabel.setText("Score : 0");

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(start_flg)
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                changePos();
                            }
                        });
                    }
                }
            },0,20);
        }



    }


    public void exitGame(View view)
    {

    }


}
