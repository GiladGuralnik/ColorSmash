package com.example.colorsmash;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ChangeUserColosActivity extends AppCompatActivity {

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
                showUsers();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    //Initiates the table
    public void showUsers(){

        //This part defines the layout to be used for creating new rows
        TableLayout ll = (TableLayout) findViewById(R.id.UsersTable);
        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setBackgroundColor(Color.parseColor("#F1F1F1"));
        TextView tUsername = new TextView(this);
        TextView tColors = new TextView(this);

        //This generates the caption row
        tUsername.setText("Username");
        tUsername.setPadding(3, 3, 3, 3);


        tColors.setText("Bad colors");
        tColors.setPadding(3, 3, 3, 3);

        row.addView(tUsername);
        row.addView(tColors);

        ll.addView(row,0);

        //This loop adds the database content to the table (using hardcoded values here for demonstration)
        for (int i = 0; i < users.size(); i++) {

            row= new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tUsername = new TextView(this);
            tColors = new TextView(this);

            tUsername.setText(users.get(i).getUsername());
            tUsername.setPadding(3, 3, 3, 3);
            ArrayList<String> arr = users.get(i).getBadColors();

            tColors.setText(arr.toString());
            tColors.setPadding(3, 3, 3, 3);


            row.addView(tUsername);
            row.addView(tColors);
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            ll.addView(row,i+1);

        }
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

