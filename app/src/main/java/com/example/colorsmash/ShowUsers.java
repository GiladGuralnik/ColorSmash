package com.example.colorsmash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                func();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //showUsers();
    }

    //Initiates the table
    public void init(){

        //This part defines the layout to be used for creating new rows
        TableLayout ll = (TableLayout) findViewById(R.id.UsersTable);
        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tName = new TextView(this);
        TextView tUsername = new TextView(this);
        TextView tAge = new TextView(this);
        TextView tGender = new TextView(this);

        //This generates the caption row
        tName.setText("Name");
        tName.setPadding(3, 3, 3, 3);
        tUsername.setText("Username");
        tUsername.setPadding(3, 3, 3, 3);
        tAge.setText("Age");
        tAge.setPadding(3, 3, 3, 3);
        tGender.setText("Gender");
        tGender.setPadding(3, 3, 3, 3);
        row.addView(tName);
        row.addView(tUsername);
        row.addView(tAge);
        row.addView(tGender);
        ll.addView(row,0);

        //This loop adds the database content to the table (using hardcoded values here for demonstration)
        for (int i = 0; i < users.size(); i++) {

            row= new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tName = new TextView(this);
            tUsername = new TextView(this);
            tAge = new TextView(this);
            tGender = new TextView(this);
            tName.setText(users.get(i).getName());
            tName.setPadding(3, 3, 3, 3);
            tUsername.setText(users.get(i).getUsername());
            tUsername.setPadding(3, 3, 3, 3);
            tAge.setText(Integer.toString(users.get(i).getAge()));
            tAge.setPadding(3, 3, 3, 3);
            tGender.setText(users.get(i).getGender());
            tGender.setPadding(3, 3, 3, 3);

            row.addView(tName);
            row.addView(tUsername);
            row.addView(tAge);
            row.addView(tGender);
            ll.addView(row,i);
        }
    }

    public void func(){
        init();
        Log.d("Asdasdasd",users.get(0).getUsername());
    }
    public void showUsers() {

        TableLayout usersInfo = (TableLayout) findViewById(R.id.UsersTable);
        usersInfo.setStretchAllColumns(true);
        usersInfo.bringToFront();
        for (int i = 0; i < 1; i++) {
            TableRow tr = new TableRow(this);
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
            usersInfo.addView(tr);
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
