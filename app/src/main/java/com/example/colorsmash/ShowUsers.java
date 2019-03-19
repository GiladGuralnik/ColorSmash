package com.example.colorsmash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ShowUsers extends AppCompatActivity {

    private DatabaseReference mRef;

    private FirebaseDatabase mDataBase;
    private List<User> users =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);


        mDataBase= FirebaseDatabase.getInstance();
        mRef = mDataBase.getReference("Users");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        showUsers();
    }

    public void showUsers() {

        TableLayout prices = (TableLayout)findViewById(R.id.UsersTable);
        prices.setStretchAllColumns(true);
        prices.bringToFront();
        for(int i = 0; i < 2; i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(users.get(i).getName());
            TextView c2 = new TextView(this);
            c2.setText(users.get(i).getUsername());
            TextView c3 = new TextView(this);
            c3.setText(users.get(i).getAge());
            TextView c4 = new TextView(this);
            c4.setText(users.get(i).getGender());

            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tr.addView(c4);
            prices.addView(tr);
        }

    }

    private void createUser(){
        mRef.child("2").child("username").setValue("ddd");
        mRef.child("2").child("name").setValue("test");
        mRef.child("2").child("age").setValue(4);
        mRef.child("2").child("gender").setValue("tst");
    }

    private void showData(DataSnapshot dataSnapshot) {
        List<String> keys=new ArrayList<>();
        users.clear();
        for(DataSnapshot ds:dataSnapshot.getChildren()){
            User user=ds.getValue(User.class);
            users.add(user);

        }
    }
}
