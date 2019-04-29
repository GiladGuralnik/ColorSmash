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
    private int frameHeight,frameWidth,initialFrameWidth;
    private LinearLayout startLayout;

    //Images Boxes
    private ImageView box;
    //Images Bricks
    private ImageView black,orange,pink,blue,bonus,gray,green,purple,red,yellow;

    private Drawable imageBoxRight,imageBoxLeft;

    //Size
    private int boxSize;

    //Position
    private float boxX,boxY;
    private float blackX,blackY;
    private float orangeX,orangeY;
    private float pinkX,pinkY;
    private float blueX,blueY;
    private float bonusX,bonusY;
    private float grayX,grayY;
    private float greenX,greenY;
    private float purpleX,purpleY;
    private float redX,redY;
    private float yellowX,yellowY;

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
    private Map<String, List<Integer>> boxColors = new HashMap<String,List<Integer>>();

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






}