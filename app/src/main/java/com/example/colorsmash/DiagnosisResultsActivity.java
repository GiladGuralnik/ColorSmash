package com.example.colorsmash;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiagnosisResultsActivity extends AppCompatActivity {

    private DatabaseReference mRef;
    private TextView name, result ,resultsDetails,cover;
    private Button fullTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_results);

        name=(TextView)findViewById(R.id.textViewNameDiagnosisResults);
        result=(TextView)findViewById(R.id.textViewResultsDiagnosisResults);
        resultsDetails=(TextView)findViewById(R.id.textViewResultsDetailsDiagnosisResults);
        cover=(TextView)findViewById(R.id.textViewCoverDiagnosisResults);
        fullTest=(Button) findViewById(R.id.buttonFullTestDiagnosisResults);



        // get current user uID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uID = "";
        if (user != null) {
            uID = user.getUid();
        } else {
            // No user is signed in ?? add Exception ??
        }

        mRef = FirebaseDatabase.getInstance().getReference("Users").child(uID);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = (User)dataSnapshot.getValue(User.class);
                String name=  "Hellow  " ;
                name=name+user.getName();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
