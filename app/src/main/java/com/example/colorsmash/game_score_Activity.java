package com.example.colorsmash;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class game_score_Activity extends AppCompatActivity {
    private ArrayList<LeaderBoardScore> scores;
    private DatabaseReference mRef,mRef2;
    TextView[] views = new TextView [5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score_);

        int count;
        scores = new ArrayList<LeaderBoardScore>();

        views[0] = findViewById(R.id.textViewScore1);
        views[1] = findViewById(R.id.textViewScore2);
        views[2] = findViewById(R.id.textViewScore3);
        views[3] = findViewById(R.id.textViewScore4);
        views[4] = findViewById(R.id.textViewScore5);

        for(int i=0 ;i<5 ; i++){
            addCouner(count);
        }
        private void showData(DataSnapshot dataSnapshot) {
            int scoreCounter = 0;
            for(DataSnapshot ds:dataSnapshot.getChildren()){
                LeaderBoardScore score = new LeaderBoardScore(ds.getKey(),(String)ds.getValue());
                scores.add(score);
                //views[scoreCounter].setText(String.valueOf(scoreCounter+1)+". " +ds.getKey()+" " +(String)ds.getValue());
                scoreCounter++;

            }
            for (int i=0;i<scores.size() && i<5 ;i++){

                mRef2 = FirebaseDatabase.getInstance().getReference("Users").child(scores.get(i).name);
                final int index = i;
                mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = (String)dataSnapshot.child("username").getValue();
                        views[index].setText(String.valueOf(index+1)+". " +name +" : " +scores.get(index).getValue());

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }



        }
}
