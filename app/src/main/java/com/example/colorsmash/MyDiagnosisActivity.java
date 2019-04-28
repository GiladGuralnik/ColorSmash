package com.example.colorsmash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDiagnosisActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView TVname ;
    private TextView TVresults ;
    private String name ;
    private Button FullTest ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diagnosis);

        TVname=(TextView)findViewById(R.id.textViewNameMyDiagnosis);
        TVresults=(TextView)findViewById(R.id.textViewTestResultsMyDiagnosis);
        FullTest=(Button) findViewById(R.id.textViewFullTestMyDiagnosis);
        FullTest.setOnClickListener(this);

        // get current user uID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uID = "";
        if (user != null) {
            uID = user.getUid();
        } else {
            // No user is signed in ?? add Exception ??
        }

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(uID);

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = (User)dataSnapshot.getValue(User.class);
                name=  "Wellcome : " ;
                name=name+user.getName();
                TVname.setText(name);

                TVresults.setText(DiagnosisResults(user.getBadColors()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    public void onClick(View view){
        if(view == FullTest){
            String url = "https://enchroma.com/pages/test";

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }
    String DiagnosisResults(ArrayList<String> type){
        String msg ="";
        if (type != null) {
            if(type.contains("PROTAN")){
                msg=msg+"You may have Protans color blindness,\n" +
                        " people with Protanomaly, have a type of red-green color blindness in which the red cones are not" +
                        "absent but do not detect enough red and are too sensitive to greens, yellows, and oranges." +
                        "As a result, greens, yellows, oranges, reds, and browns may appear similar, especially in low light." +
                        "Red and black might be hard to tell apart, especially when red text is against a black background.\n\n";
            }
            if(type.contains("DEUTAN")){
                if(!msg.equals("")){msg=msg+"\nOR\n";}
                msg=msg+"You may have Deutans color blindness,\n" +
                        "people with Deuteranomaly, have a type of red-green color blindness in which green cones are not" +
                        "absent but do not detect enough green and are too sensitive to yellows, oranges, and reds." +
                        "As a result, greens, yellows, oranges, reds, and browns may appear similar, especially in low light. " +
                        " It can also be difficult to tell the difference between blues and purples, or pinks and grays.\n\n";
            }
            if(type.contains("TRITAN")){
                if(!msg.equals("")){msg=msg+"\nOR\n";}
                msg=msg+"You may have Tritan color blindness,\n" +
                        "causing reduced blue sensitivity and Tritanopia, resulting in no blue sensitivity, " +
                        "can be inherited or acquired; the inherited form is a rare autosomal recessive condition." +
                        "More commonly, tritanomaly is acquired later in life due to age-related or environmental factors. " +
                        "Cataracts, glaucoma and age related macular degeneration could cause someone to test as a Tritan." +
                        "People with tritanomaly have reduced sensitivity in their blue “S” cone cells," +
                        " which can cause confusion between blue versus green and red from purple.\n\n";
            }
        }

        else{
            msg="\nNo color blindness / not tested\n";
        }

        return msg;
    }

}
