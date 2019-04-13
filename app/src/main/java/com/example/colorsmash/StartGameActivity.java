package com.example.colorsmash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
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
import java.util.concurrent.TimeUnit;

public class StartGameActivity extends AppCompatActivity {

    // https://stackoverflow.com/questions/37873608/how-do-i-detect-if-a-user-is-already-logged-in-firebase

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
    private int score,highScore,timeCount; //Those values will be sent to the DB
    private SharedPreferences settings; // Local High Score -- will be moved to DB

    //Class
    private Timer timer;
    private Handler handler = new Handler();
    private SoundPlayer soundPlayer;

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

        //High Score
        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText("High Score : " + highScore);

        //SoundPlayer
        soundPlayer = new SoundPlayer(this);



    }

    public void changePos()
    {
        //Add Time Count
        timeCount += 20;

        //_______________________________________________Orange Box_______________________________
        orangeY +=12;

        float orangeCenterX = orangeX + orange.getWidth() / 2;
        float orangeCenterY = orangeY  + orange.getHeight() / 2;

        //if we Smash the orange box
        if(hitCheck(orangeCenterX,orangeCenterY))
        {
            orangeY = frameHeight + 100;
            score += 10; // update score
            soundPlayer.playHitOrangeSound();

        }

        if(orangeY > frameHeight)
        {
            orangeY = -50; // speed of fall
            orangeX = (float) Math.floor(Math.random() * (frameWidth - orange.getWidth())); //Randomize number between 0 and frameWidth
        }

        orange.setX(orangeX);
        orange.setY(orangeY);


        //_______________________________________________Pink_______________________________

        if(!pink_flg && timeCount%10000 == 0 ) // timer for pink to appear
        {
            pink_flg = true;
            pinkY = -20;
            pinkX = (float) Math.floor(Math.random() * (frameWidth - pink.getWidth()));

        }

        if(pink_flg){

            pinkY += 20;

            float pinkCenterX = pinkX + pink.getWidth() / 2;
            float pinkCenterY = pinkY + pink.getHeight() / 2;

            if(hitCheck(pinkCenterX,pinkCenterY))
            {
                pinkY = frameHeight + 30;
                score += 30;
                soundPlayer.playHitPinkSound();

                //Change Frame Width ( pink bonus )
                if(initialFrameWidth > frameWidth * 110/100) //if the frame got shrunk
                {
                    frameWidth = frameWidth * 110/100;
                    changeFrameWidth(frameWidth);
                }
            }

            if(pinkY > frameHeight )
                pink_flg = false;

            pink.setX(pinkX);
            pink.setY(pinkY);

        }

        //_______________________________________________Black Box_______________________________
        blackY += 18;

        float blackCenterX = blackX +black.getWidth() / 2;
        float blackCenterY = blackY +black.getHeight() / 2;

        if(hitCheck(blackCenterX,blackCenterY))
        {
            blackY = frameHeight + 100;

            //Change Frame Width
            frameWidth = frameWidth * 80/100; // 80% of original size
            changeFrameWidth(frameWidth);
            soundPlayer.playHitBlackSound();

            if(frameWidth <= boxSize) // End of Game
            {
                gameOver();
            }
        }

        if(blackY > frameHeight )
        {
            blackY = -100;
            blackX = (float)Math.floor(Math.random() * (frameWidth - black.getWidth()));

        }

        black.setX(blackX);
        black.setY(blackY);


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
        scoreLabel.setText("Score : " + score); //update score

    }

    public boolean hitCheck(float x, float y)
    {
        if(boxX <= x && x <= boxX + boxSize && boxY <= y && y<= frameHeight)
        {
            return true;
        }

        return false;
    }


    public void changeFrameWidth(int frameWidth)
    {
        ViewGroup.LayoutParams params = gameFrame.getLayoutParams();
        params.width = frameWidth;
        gameFrame.setLayoutParams(params);
    }

    public void gameOver(){
        //Stop Time
        timer.cancel();
        timer = null;
        start_flg = false;
        soundPlayer.playGameOverSound();

        //Before Showing startLayout sleep for 1 second
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        changeFrameWidth(initialFrameWidth);

        startLayout.setVisibility(View.VISIBLE);
        box.setVisibility(View.INVISIBLE);
        pink.setVisibility(View.INVISIBLE);
        orange.setVisibility(View.INVISIBLE);
        black.setVisibility(View.INVISIBLE);

        //Update High Score Field
        if(score > highScore)
        {
            highScore = score;
            highScoreLabel.setText("High Score : " + highScore);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", highScore);
            editor.commit();

        }
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

            frameWidth = initialFrameWidth;

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
