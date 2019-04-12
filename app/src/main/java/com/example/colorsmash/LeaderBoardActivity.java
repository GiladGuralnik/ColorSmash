package com.example.colorsmash;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    private ArrayList<LeaderBoardScore> scores;
    private DatabaseReference mRef;
    TextView [] views = new TextView [5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        views[0] = findViewById(R.id.textViewScore1);
        views[1] = findViewById(R.id.textViewScore2);
        views[2] = findViewById(R.id.textViewScore3);
        views[3] = findViewById(R.id.textViewScore4);
        views[4] = findViewById(R.id.textViewScore5);

        mRef = FirebaseDatabase.getInstance().getReference("LeaderBoard");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void showData(DataSnapshot dataSnapshot) {
        int scoreCounter = 0;
        for(DataSnapshot ds:dataSnapshot.getChildren()){
            //LeaderBoardScore score = new LeaderBoardScore(ds.getKey(),(String)ds.getValue());
            //scores.add(score);
            views[scoreCounter].setText(String.valueOf(scoreCounter+1)+". " +ds.getKey()+" " +(String)ds.getValue());
            scoreCounter++;

        }
    }

}
