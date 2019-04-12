package com.example.colorsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminOptions extends AppCompatActivity implements View.OnClickListener {

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
            Intent act = new Intent(AdminOptions.this, ShowUsers.class);
            startActivity(act);
        }
        else if ( v == buttonResetLeaderBoard){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("LeaderBoard");
            mRef.removeValue();
            Toast.makeText(this, "The leader score cleared!", Toast.LENGTH_LONG).show();

        }
        else if (v == buttonChangeUserColors){
            Intent act = new Intent(AdminOptions.this, ChangeUserColorsActivity.class);
            startActivity(act);
        }
    }
}
