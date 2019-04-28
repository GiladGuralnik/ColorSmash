package com.example.colorsmash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminStatisticsActivity extends AppCompatActivity {


    private TextView textViewAverageAge;
    private TextView textViewMostCommonGender;
    private TextView textViewMostCommonColors;
    private TextView textViewColorblindUsers;
    private ArrayList<User> users;
    private HashMap<String,Integer> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        users = new ArrayList<User>();
        colors = new HashMap<String,Integer>();
        colors.put("RED",0);
        colors.put("ORANGE",0);
        colors.put("YELLOW",0);
        colors.put("GREEN",0);
        colors.put("BLUE",0);
        colors.put("PURPLE",0);
        colors.put("BROWN",0);
        colors.put("PINK",0);
        colors.put("GRAY",0);
        textViewAverageAge = (TextView)findViewById(R.id.textViewAverageAge);
        textViewMostCommonGender = (TextView)findViewById(R.id.textViewMostCommonGender);
        textViewMostCommonColors = (TextView)findViewById(R.id.textViewMostCommonColors);
        textViewColorblindUsers = (TextView)findViewById(R.id.textViewColorblindUsers);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    User user=ds.getValue(User.class);
                    users.add(user);

                }

                // filter only colorblind users
                List<User> colorblidUsers = new ArrayList<>();
                for(User u: users) {
                    if(u.getColorblind().equals("true")) {
                        colorblidUsers.add(u);
                    }
                }

                // print amount of diagnosed color blindness users
                textViewColorblindUsers.setText(textViewColorblindUsers.getText()+" "+ colorblidUsers.size());

                // calc age average
                float age = 0;
                if(colorblidUsers.size()>0) {
                    for (User u : colorblidUsers) {
                        age += Integer.valueOf(u.getAge());
                    }
                    age = age / colorblidUsers.size();
                }

                // print age average
                textViewAverageAge.setText(textViewAverageAge.getText()+" "+ age);


                // calc common gender

                // calc common colors
                if(colorblidUsers.size()>0) {
                    for (User u : colorblidUsers) {
                        age += Integer.valueOf(u.getAge());
                    }
                    age = age / colorblidUsers.size();
                }


                // CHECK IF ALL IS 0 SO PRINT NONE


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
