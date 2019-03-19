package com.example.colorsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminOptions extends AppCompatActivity implements View.OnClickListener {

    private Button buttonShowUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        buttonShowUsers = (Button) findViewById(R.id.ButtonShowUsers);
        buttonShowUsers.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonShowUsers) {
            Intent act = new Intent(AdminOptions.this, ShowUsers.class);
            startActivity(act);
        }
    }
}
