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
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
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

public class UserGameActivity extends AppCompatActivity {
    //yoel----------
    private int blue_purple_collision=0;
    private int yellow_green_collision=0;
    private int pink_gray_collision=0;
    private boolean need_diagnosis=false;
    //--------------

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

    //Active User
    private String uID;
    private FirebaseUser user;


    //Difficulty levels
    private int boxSpeed = 12;

    //Current Player Color
    private String currentColor = "BLUE";

    //boxColorsHashmap
    private Map<String, List<Integer>> boxColors = new HashMap<String, List<Integer>>();

    //Random variable
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_game);

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

        imageBoxLeft = getResources().getDrawable(R.drawable.box_left_blue);
        imageBoxRight = getResources().getDrawable(R.drawable.box_right_blue);

//////////////////////////////// PULL LOGGED IN USER FROM FIREBASE //////////////////////////////////////

        user = FirebaseAuth.getInstance().getCurrentUser();
        uID = "";
        if (user != null) {
            uID = user.getUid();
        } else {
            // No user is signed in ?? add Exception ??
        }

/////////////////////////////// PULL HIGH SCORE FROM FIRE BASE BY USER NAME //////////////////////////////
//High Score
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(uID).child("highscore");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    highScore = 0;
                }
                else {
                    highScore = Integer.parseInt((String) dataSnapshot.getValue());
                }
                highScoreLabel.setText("High Score : " + highScore);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
                add(R.drawable.box_left_pink);
                add(R.drawable.box_right_pink);
            }

        });

        boxColors.put("BLUE", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_blue);
                add(R.drawable.box_right_blue);
            }

        });

        boxColors.put("ORANGE", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_orange);
                add(R.drawable.box_right_orange);
            }

        });

        boxColors.put("BLACK", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_black);
                add(R.drawable.box_right_black);
            }

        });

        boxColors.put("GRAY", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_gray);
                add(R.drawable.box_right_gray);
            }

        });

        boxColors.put("GREEN", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_green);
                add(R.drawable.box_right_green);
            }

        });

        boxColors.put("PURPLE", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_purple);
                add(R.drawable.box_right_purple);
            }

        });

        boxColors.put("RED", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_red);
                add(R.drawable.box_right_red);
            }

        });

        boxColors.put("YELLOW", new ArrayList<Integer>() {
            {
                add(R.drawable.box_left_yellow);
                add(R.drawable.box_right_yellow);
            }

        });
    }

    private String randomizeColor() {
        int r = rand.nextInt(9);

        switch (r) {
            case 0: {
                return "BLACK";
            }
            case 1: {
                return "BLUE";
            }
            case 2: {
                return "GRAY";
            }
            case 3: {
                return "GREEN";
            }
            case 4: {
                return "ORANGE";
            }
            case 5: {
                return "PINK";
            }
            case 6: {
                return "PURPLE";
            }
            case 7: {
                return "RED";
            }
            case 8: {
                return "YELLOW";
            }
            default: {
                return "BLUE";
            }
        }
    }

    private void setNewPlayerColor() {
        String color = randomizeColor();
        int firstImage, secondImage;
        currentColor = color; //update current color
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
                if(currentColor == "ORANGE")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("ORANGE");
                }

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

            float blueCenterX = blueX + blue.getWidth()/2;
            float blueCenterY = blueY + blue.getHeight()/2;

            if (hitCheck(blueCenterX, blueCenterY)) {
                blueY = frameHeight + 100;
                if(currentColor == "BLUE")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("BLUE");
                }
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
                if(currentColor == "GREEN")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("GREEN");
                }
            }

            if (greenY > frameHeight)
                green_flg = false;

            green.setX(greenX);
            green.setY(greenY);

        }

        return;
    }

    private void positiveHit()
    {
        score += 10;
        soundPlayer.playHitOrangeSound();
        setNewPlayerColor();
    }

    private void negativeHit(String impactColor)
    {
        decreaseWindowSize();
        soundPlayer.playHitBlackSound();
        decreaseGameDifficulty();
        collectCollisionData(currentColor,impactColor);
        if(frameWidth <= black.getWidth()*2)
        {
            gameOver();
        }
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
                if(currentColor == "YELLOW")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("YELLOW");
                }

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
                if(currentColor == "GRAY")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("GRAY");
                }

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
                if(currentColor == "RED")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("RED");
                }
            }

            if (redY > frameHeight)
                red_flg = false;

            red.setX(redX);
            red.setY(redY);

        }

        return;
    }


    private void bonusBox() {

        if (!bonus_flg && timeCount%850 == 0) // timer for bonus to appear
        {
            bonus_flg = true;
            bonusY = -20;
            bonusX = (float) Math.floor(Math.random() * (frameWidth - bonus.getWidth()));

        }

        if (bonus_flg) {

            bonusY += 25;

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
                if(currentColor == "PINK")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("PINK");
                }

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
                if(currentColor == "PURPLE")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("PURPLE");
                }

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
                if(currentColor == "BLACK")
                {
                    positiveHit();
                }
                else
                {
                    negativeHit("BLACK");
                }

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
        if(timeCount%2000 == 0)
        {
            boxSpeed = (int)(boxSpeed * 1.2);
        }
    }

    private void decreaseGameDifficulty()
    {
        boxSpeed = (int)(boxSpeed * 0.95);

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
    //yoel-------------
    public void collectCollisionData(String myColor,String smashColor)
    {
        if(myColor=="BLUE"&& smashColor=="PURPLE"||myColor=="PURPLE"&& smashColor=="BLUE"){
            blue_purple_collision++;
        }
        if(myColor=="YELLOW"&& smashColor=="GREEN"||myColor=="GREEN"&& smashColor=="YELLOW"){
            yellow_green_collision++;
        }
        if(myColor=="GRAY"&& smashColor=="PINK"||myColor=="PINK"&& smashColor=="GRAY"){
            pink_gray_collision++;
        }


        return;
    }
    //end yoel-----------

    //yoel------
    private boolean needDiagnosis(){
        if(blue_purple_collision>2){
            return true;
        }
        if(yellow_green_collision>2){
            return true;
        }
        if(pink_gray_collision>4){
            return true;
        }
        return false;
    }
    //end yoel--------

    public void gameOver(){
        //Stop Time
        timer.cancel();
        timer = null;
        start_flg = false;
        soundPlayer.playGameOverSound();
        boxSpeed = 12; //reset game speed back to original state

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
        black.setVisibility(View.INVISIBLE);
        orange.setVisibility(View.INVISIBLE);
        pink.setVisibility(View.INVISIBLE);
        bonus.setVisibility(View.INVISIBLE);
        blue.setVisibility(View.INVISIBLE);
        red.setVisibility(View.INVISIBLE);
        gray.setVisibility(View.INVISIBLE);
        purple.setVisibility(View.INVISIBLE);
        yellow.setVisibility(View.INVISIBLE);
        green.setVisibility(View.INVISIBLE);

// Get user ref from db
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(uID);
        mRef.child("scores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> scores = new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String sc=ds.getValue(String.class);
                    if(sc!=null) {
                        scores.add(sc);
                    }
                }

                // add the new score
                scores.add(String.valueOf(score));

                //add the score to users scores list
                mRef.child("scores").setValue(scores);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//Update High Score Field
        if(score > highScore)
        {
            highScore = score;
            highScoreLabel.setText("High Score : " + highScore);

            //update users highscore
            mRef.child("highscore").setValue(String.valueOf(highScore));

            //check it need to update the leaderboard
            final DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference("LeaderBoard");
            mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap<String,String> leaders = (HashMap<String,String>)dataSnapshot.getValue();
                    if(leaders==null){
                        leaders = new HashMap<String,String>();
                    }
                    if(leaders.size()<5){
                        leaders.put(user.getUid(),String.valueOf(score));
                        mRef2.setValue(leaders);
                    }
                    else if(leaders.size()==5) {
                        double min = Double.POSITIVE_INFINITY;
                        for (String value : leaders.values()) {
                            if (Integer.parseInt(value) < min) {
                                min = Integer.parseInt(value);
                            }
                        }

                        if ( score > (int) min) {

                            String val = "";
                            String key = "";

                            for (Map.Entry<String, String> entry : leaders.entrySet()) {
                                key = entry.getKey();
                                val = entry.getValue();
                                if (Integer.parseInt(val) == (int) min) {
                                    break;
                                }
                            }

                            Log.d("TTTT",String.valueOf(min));
                            leaders.remove(key);
                            leaders.put(user.getUid(),String.valueOf(score));

                            mRef2.setValue(leaders);

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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

    public FrameLayout getGameFrame() //Dont delete, Used for tests
    {
        return gameFrame;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            this.finish();
            Intent act = new Intent(UserGameActivity.this, UserOptionsActivity.class);
            startActivity(act);
        }
        return super.onKeyDown(keyCode, event);
    }



    public void startGame(View view)
    {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);

        ////////////////////// update db -> games counter //////////////////////////////
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Counter");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String counter = (String)dataSnapshot.getValue();
                mRef.setValue(String.valueOf(Integer.parseInt(counter)+1));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /////////////////////////


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
            bonus.setY(3000.0f);
            blue.setY(3000.0f);
            red.setY(3000.0f);
            gray.setY(3000.0f);
            purple.setY(3000.0f);
            yellow.setY(3000.0f);
            green.setY(3000.0f);

            blackY = black.getY();
            orangeY = orange.getY();
            pinkY = pink.getY();
            bonusY = bonus.getY();
            blueY = blue.getY();
            redY = red.getY();
            grayY = gray.getY();
            purpleY = purple.getY();
            yellowY = yellow.getY();
            greenY = green.getY();


            box.setVisibility(View.VISIBLE);
            black.setVisibility(View.VISIBLE);
            orange.setVisibility(View.VISIBLE);
            pink.setVisibility(View.VISIBLE);
            bonus.setVisibility(View.VISIBLE);
            blue.setVisibility(View.VISIBLE);
            red.setVisibility(View.VISIBLE);
            gray.setVisibility(View.VISIBLE);
            purple.setVisibility(View.VISIBLE);
            yellow.setVisibility(View.VISIBLE);
            green.setVisibility(View.VISIBLE);

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


    public void viewScores(View view)
    {
        Intent act = new Intent(UserGameActivity.this, ShowPreviousScoresActivity.class);
        startActivity(act);
    }

    public void exitGame(View view)
    {
        this.finish();
        Intent act = new Intent(UserGameActivity.this, UserOptionsActivity.class);
        startActivity(act);
    }

    public void gameInstructions(View view) {
        Intent act = new Intent(UserGameActivity.this, GameInstructionsActivity.class);
        startActivity(act);

    }
}






