package com.example.colorsmash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShowPreviousScoresActivity extends AppCompatActivity {

    //Active User
    private String uID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_previous_scores);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uID = "";
        if (user != null) {
            uID = user.getUid();
        } else {
            // No user is signed in ?? add Exception ??
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.llayout);

        for (int i = 0; i < 3; i++) {

            TextView myText = new TextView(this);
            myText.setText("textview# "+ i);
            layout.addView(myText);

//            TextView dynamicTextView = new TextView(this);
//            dynamicTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT));
//            dynamicTextView.setText("NewYork");
//            layout.addView(tstate);

        }
    }
}
