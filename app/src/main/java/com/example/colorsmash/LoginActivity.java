package com.example.colorsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button singinButton;
    private Button registerButton;
    private EditText emailEditText;
    private EditText passwordEditText;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        singinButton = (Button) findViewById(R.id.singinButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        emailEditText = (EditText) findViewById(R.id.emailLogin);
        passwordEditText = (EditText) findViewById(R.id.passwordLogin);

        progressDialog = new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();
        //checking if the user is connected
        /*
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext() , StartGameActivity.class));
        }
        */
        singinButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    private void userLogin(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //Checking if email ane password are not empty
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password" , Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email" , Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Checking please wite ...");
        progressDialog.show();
        // Check for a valid email address.

        firebaseAuth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            public void onComplete(Task<AuthResult> task){
                progressDialog.dismiss();
                //GO TO MAIN ACTIVITY
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext() , StartGameActivity.class));

                }
            }
        });

    }

    @Override
    public void onClick(View view){
        if(view == singinButton){
            userLogin();
        }
        if(view == registerButton){
            finish();
            startActivity(new Intent(getApplicationContext() , RegisterActivity.class));
        }



    }

}

