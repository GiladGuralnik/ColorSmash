package com.example.colorsmash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class StartGameActivity extends AppCompatActivity {

    //GameFrame
    private FrameLayout gameFrame;
    private int frameHeight, frameWidth, initialFrameWidth;
    private LinearLayout startLayout;

    //Images Boxes
    private ImageView box;
    //Images Bricks
    private ImageView black, orange, pink, blue, bonus, gray, green, purple, red, yellow;

    private Drawable imageBoxRight, imageBoxLeft;

    //Size
    private int boxSize;

    //Position
    private float boxX, boxY;
    private float blackX, blackY;
    private float orangeX, orangeY;
    private float pinkX, pinkY;
    private float blueX, blueY;
    private float bonusX, bonusY;
    private float grayX, grayY;
    private float greenX, greenY;
    private float purpleX, purpleY;
    private float redX, redY;
    private float yellowX, yellowY;

    //Score
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount; //Those values will be sent to the DB
    private SharedPreferences settings; // Local High Score -- will be moved to DB

    //Class
    private Timer timer;
    private Handler handler = new Handler();
    private SoundPlayer soundPlayer;

    //Status
    private boolean start_flg = false;
    private boolean action_flg = false;
    private boolean pink_flg = false;
    private boolean bonus_flg = false;
    private boolean black_flg = false;
    private boolean orange_flg = false;
    private boolean blue_flg = false;
    private boolean red_flg = false;
    private boolean gray_flg = false;
    private boolean purple_flag = false;
    private boolean yellow_flg = false;
    private boolean green_flg = false;

    //Difficulty levels
    private int boxSpeed = 12;

    //boxColorsHashmap
    private Map<String, List<Integer>> boxColors = new HashMap<String, List<Integer>>();

    //Random variable
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);

        //Colors
        box = findViewById(R.id.box);
        black = findViewById(R.id.black);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        blue = findViewById(R.id.blue);
        bonus = findViewById(R.id.bonus);
        gray = findViewById(R.id.gray);
        green = findViewById(R.id.green);
        purple = findViewById(R.id.purple);
        red = findViewById(R.id.red);
        yellow = findViewById(R.id.yellow);

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

        //initializeColorsHash
        initializeColors();

    }

    public void changePos() {
        //Add Time Count
        timeCount += 1;

        //orangeBox
        orangeBox();

        //pinkBox
        pinkBox();

        //blackBox
        blackBox();

        //bonusBox
        bonusBox();

        //purple
        purpleBox();

        //blue
        blueBox();

        //red
        redBox();

        //gray
        grayBox();

        //yellow
        yellowBox();

        //green
        greenBox();

        //_______________________________________________Main Player Box_______________________________
        mainPlayerBox();

        increaseGameDifficulty(); //make the game harder by time

        scoreLabel.setText("Score : " + score); //update score

    }

    private void initializeColors() {
        boxColors.put("PINK", new ArrayList<Integer>() {
            {
                add(R.drawable.pinkbox_left);
                add(R.drawable.pinkbox_right);
            }

        });

        boxColors.put("BLUE", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left);
                add(R.drawable.box_right);
            }

        });

        boxColors.put("ORANGE", new ArrayList<Integer>() {
            {
                add(R.drawable.orangebox_left);
                add(R.drawable.orangebox_right);
            }

        });

        boxColors.put("TURQUOISE", new ArrayList<Integer>() {
            {
                add(R.drawable.turquoisebox_left);
                add(R.drawable.turquoisebox_right);
            }

        });
    }

    private String randomizeColor() {
        int r = rand.nextInt(4);

        switch (r) {
            case 0: {
                return "PINK";
            }
            case 1: {
                return "BLUE";
            }
            case 2: {
                return "ORANGE";
            }
            case 3: {
                return "TURQUOISE";
            }
            default: {
                return "BLUE";
            }
        }
    }

    private void setNewPlayerColor() {
        String color = randomizeColor();
        int firstImage, secondImage;
        firstImage = boxColors.get(color).get(0);
        secondImage = boxColors.get(color).get(1);
        imageBoxLeft = getResources().getDrawable(firstImage);
        imageBoxRight = getResources().getDrawable(secondImage);

    }

    private void orangeBox() {

        if (!orange_flg && timeCount % 700 == (rand.nextInt(700))) // timer for orange to appear
        {
            orange_flg = true;
            orangeY = -100;
            orangeX = (float) Math.floor(Math.random() * (frameWidth - orange.getWidth()));

        }

        if (orange_flg) {

            orangeY += boxSpeed;

            float orangeCenterX = orangeX + orange.getWidth() / 2;
            float orangeCenterY = orangeY + orange.getHeight() / 2;

            if (hitCheck(orangeCenterX, orangeCenterY)) {
                orangeY = frameHeight + 100;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (orangeY > frameHeight)
                orange_flg = false;

            orange.setX(orangeX);
            orange.setY(orangeY);

        }

        return;
    }

    private void blueBox() {

        if (!blue_flg && timeCount % 700 == (rand.nextInt(700))) // timer for blue to appear
        {
            blue_flg = true;
            blueY = -100;
            blueX = (float) Math.floor(Math.random() * (frameWidth - blue.getWidth()));

        }

        if (blue_flg) {

            blueY += boxSpeed;

            float blueCenterX = blueX + blue.getWidth() / 2;
            float blueCenterY = blueY + blue.getHeight() / 2;

            if (hitCheck(blueCenterX, blueCenterY)) {
                blueY = frameHeight + 100;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (blueY > frameHeight)
                blue_flg = false;

            blue.setX(blueX);
            blue.setY(blueY);

        }

        return;
    }

    private void greenBox() {

        if (!green_flg && timeCount % 700 == (rand.nextInt(700))) // timer for blue to appear
        {
            green_flg = true;
            greenY = -100;
            greenX = (float) Math.floor(Math.random() * (frameWidth - green.getWidth()));

        }

        if (green_flg) {

            greenY += boxSpeed;

            float greenCenterX = greenX + green.getWidth() / 2;
            float greenCenterY = greenY + green.getHeight() / 2;

            if (hitCheck(greenCenterX, greenCenterY)) {
                greenY = frameHeight + 100;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (greenY > frameHeight)
                green_flg = false;

            green.setX(greenX);
            green.setY(greenY);

        }

        return;
    }


    private void yellowBox() {

        if (!yellow_flg && timeCount % 700 == (rand.nextInt(700))) // timer for blue to appear
        {
            yellow_flg = true;
            yellowY = -100;
            yellowX = (float) Math.floor(Math.random() * (frameWidth - yellow.getWidth()));

        }

        if (yellow_flg) {

            yellowY += boxSpeed;

            float yellowCenterX = yellowX + yellow.getWidth() / 2;
            float yellowCenterY = yellowY + yellow.getHeight() / 2;

            if (hitCheck(yellowCenterX, yellowCenterY)) {
                yellowY = frameHeight + 100;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (yellowY > frameHeight)
                yellow_flg = false;

            yellow.setX(yellowX);
            yellow.setY(yellowY);

        }

        return;
    }


    private void grayBox() {

        if (!gray_flg && timeCount % 700 == (rand.nextInt(700))) // timer for blue to appear
        {
            gray_flg = true;
            grayY = -100;
            grayX = (float) Math.floor(Math.random() * (frameWidth - gray.getWidth()));

        }

        if (gray_flg) {

            grayY += boxSpeed;

            float grayCenterX = grayX + gray.getWidth() / 2;
            float grayCenterY = grayY + gray.getHeight() / 2;

            if (hitCheck(grayCenterX, grayCenterY)) {
                grayY = frameHeight + 100;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (grayY > frameHeight)
                gray_flg = false;

            gray.setX(grayX);
            gray.setY(grayY);

        }

        return;
    }

    private void redBox() {

        if (!red_flg && timeCount % 700 == (rand.nextInt(700))) // timer for blue to appear
        {
            red_flg = true;
            redY = -100;
            redX = (float) Math.floor(Math.random() * (frameWidth - red.getWidth()));

        }

        if (red_flg) {

            redY += boxSpeed;

            float redCenterX = redX + red.getWidth() / 2;
            float redCenterY = redY + red.getHeight() / 2;

            if (hitCheck(redCenterX, redCenterY)) {
                redY = frameHeight + 100;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (redY > frameHeight)
                red_flg = false;

            red.setX(redX);
            red.setY(redY);

        }

        return;
    }


    private void bonusBox() {

        if (!bonus_flg && timeCount % 700 == (rand.nextInt(700))) // timer for bonus to appear
        {
            bonus_flg = true;
            bonusY = -20;
            bonusX = (float) Math.floor(Math.random() * (frameWidth - bonus.getWidth()));

        }

        if (bonus_flg) {

            bonusY += 20;

            float bonusCenterX = bonusX + bonus.getWidth() / 2;
            float bonusCenterY = bonusY + bonus.getHeight() / 2;

            if (hitCheck(bonusCenterX, bonusCenterY)) {
                bonusY = frameHeight + 100;
                score += 50;
                soundPlayer.playHitBonusSound();

                //Change Frame Width ( bonus )
                if (initialFrameWidth > frameWidth * 120 / 100) //if the frame got shrunk
                {
                    increaseWindowSize();
                }
            }

            if (bonusY > frameHeight)
                bonus_flg = false;

            bonus.setX(bonusX);
            bonus.setY(bonusY);

        }
        return;
    }


    private void pinkBox() {

        if (!pink_flg && timeCount % 700 == (rand.nextInt(700))) // timer for pink to appear
        {
            pink_flg = true;
            pinkY = -100;
            pinkX = (float) Math.floor(Math.random() * (frameWidth - pink.getWidth()));

        }

        if (pink_flg) {

            pinkY += boxSpeed;

            float pinkCenterX = pinkX + pink.getWidth() / 2;
            float pinkCenterY = pinkY + pink.getHeight() / 2;

            if (hitCheck(pinkCenterX, pinkCenterY)) {
                pinkY = frameHeight + 30;
                score += 10;
                soundPlayer.playHitOrangeSound();

            }

            if (pinkY > frameHeight)
                pink_flg = false;

            pink.setX(pinkX);
            pink.setY(pinkY);

        }

        return;
    }

    private void purpleBox() {

        if (!purple_flag && timeCount % 700 == (rand.nextInt(700))) // timer for pink to appear
        {
            purple_flag = true;
            purpleY = -100;
            purpleX = (float) Math.floor(Math.random() * (frameWidth - purple.getWidth()));

        }

        if (purple_flag) {

            purpleY += boxSpeed;

            float purpleCenterX = purpleX + purple.getWidth() / 2;
            float purpleCenterY = purpleY + purple.getHeight() / 2;

            if (hitCheck(purpleCenterX, purpleCenterY)) {
                purpleY = frameHeight + 30;
                score += 30;
                soundPlayer.playHitOrangeSound();

            }

            if (purpleY > frameHeight)
                purple_flag = false;

            purple.setX(purpleX);
            purple.setY(purpleY);

        }

        return;

    }

    private void blackBox()
    {

        if(!black_flg && timeCount%700 == (rand.nextInt(700)) ) // timer for black to appear
        {
            black_flg = true;
            blackY = -100;
            blackX = (float) Math.floor(Math.random() * (frameWidth - black.getWidth()));

        }

        if(black_flg){

            blackY += boxSpeed;

            float blackCenterX = blackX +black.getWidth() /2 ;
            float blackCenterY = blackY +black.getHeight() /2 ;

            if(hitCheck(blackCenterX,blackCenterY))
            {
                blackY = frameHeight + 30;
                score += 30;
                soundPlayer.playHitOrangeSound();

            }

            if(blackY > frameHeight )
            {
                black_flg = false;

            }

            black.setX(blackX);
            black.setY(blackY);

        }

        return;
    }

    private void decreaseWindowSize()
    {
        frameWidth = frameWidth * 80/100; // 80% of original size
        changeFrameWidth(frameWidth);
        soundPlayer.playHitBlackSound();
    }

    private void increaseWindowSize()
    {
        frameWidth = frameWidth * 120/100;
        changeFrameWidth(frameWidth);
    }


    private void mainPlayerBox()
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
        return;
    }

    private void increaseGameDifficulty()
    {
        if(timeCount%3000 == 0)
        {
            boxSpeed = (int)(boxSpeed * 1.2);
        }
    }

    private void decreaseGameDifficulty()
    {
        boxSpeed = (int)(boxSpeed * 0.9);

    }


    public boolean hitCheck(float x, float y)
    {
        if(boxX <= x && x <= boxX + boxSize && boxY <= y && y<= frameHeight)
        {
            setNewPlayerColor(); //REMOVE THIS FUNCTION FROM HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
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



}