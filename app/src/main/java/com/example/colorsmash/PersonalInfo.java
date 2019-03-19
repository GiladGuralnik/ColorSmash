package com.example.colorsmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfo extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button submitButton;
    EditText editName;
    EditText editAge;

    private DatabaseReference mRef;

    private FirebaseDatabase mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        mDataBase=FirebaseDatabase.getInstance();
        mRef = mDataBase.getReference("Users");

        radioGroup=findViewById((R.id.radioGroup));
        editName=findViewById((R.id.editName));
        editAge=findViewById((R.id.editAge));
        submitButton  =findViewById(R.id.ButtonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton  = findViewById(radioId);
                String gender = radioButton.getText().toString();
                String name= editName.getText().toString().trim();
                int age;
                try {
                    age = Integer.parseInt(editAge.getText().toString().trim());
                }
                catch(Exception a){ //check here convertion problems
                    Toast.makeText(PersonalInfo.this,"Bad Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = getIntent();
                String index= intent.getStringExtra("INDEX"); ///INDEX of new user for data update
                updateUserData(index,name,gender,age);
                Toast.makeText(PersonalInfo.this,"Data Updated",Toast.LENGTH_SHORT).show();
                Intent act = new Intent(PersonalInfo.this, PersonalInfo.class);
                startActivity(act);

            }
        });

    }


    public void updateUserData(String index,String name,String gender,int age){
        mRef.child(index).child("name").setValue(name);
        mRef.child(index).child("age").setValue(age);
        mRef.child(index).child("gender").setValue(gender);
    }

    public void checkButton(View v){
        int radioId=radioGroup.getCheckedRadioButtonId();

        radioButton=findViewById(radioId);
        Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show();
    }
}
