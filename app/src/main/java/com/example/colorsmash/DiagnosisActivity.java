package com.example.colorsmash;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DiagnosisActivity extends AppCompatActivity implements View.OnClickListener {


    private Button submit ;//buttonSubmitDiagnosis
    private Button buttonQuestion1 ,buttonQuestion2,buttonQuestion3,buttonQuestion4,buttonQuestion5,buttonQuestion6;//button Question Diagnosis
    boolean[] questionButtonFlags = new boolean[6];
    private EditText textQuestion1,textQuestion2,textQuestion3,textQuestion4,textQuestion5,textQuestion6 ;//editTextQuestion1Diagnosis




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        Arrays.fill(questionButtonFlags, Boolean.FALSE);

        submit=(Button)findViewById(R.id.buttonSubmitDiagnosis);
        buttonQuestion1=(Button)findViewById(R.id.buttonQuestion1Diagnosis);
        buttonQuestion2=(Button)findViewById(R.id.buttonQuestion2Diagnosis);
        buttonQuestion3=(Button)findViewById(R.id.buttonQuestion3Diagnosis);
        buttonQuestion4=(Button)findViewById(R.id.buttonQuestion4Diagnosis);
        buttonQuestion5=(Button)findViewById(R.id.buttonQuestion5Diagnosis);
        buttonQuestion6=(Button)findViewById(R.id.buttonQuestion6Diagnosis);

        submit.setOnClickListener(this);
        buttonQuestion1.setOnClickListener(this);
        buttonQuestion2.setOnClickListener(this);
        buttonQuestion3.setOnClickListener(this);
        buttonQuestion4.setOnClickListener(this);
        buttonQuestion5.setOnClickListener(this);
        buttonQuestion6.setOnClickListener(this);




    }



    @Override
    public void onClick(View view){
        if(view == submit){

        }
        else if(view == buttonQuestion1){
            questionButtonFlags[1]=changeButton(buttonQuestion1);
            Log.d("TTT", String.valueOf(questionButtonFlags[1]));
        }

        else if(view == buttonQuestion2){
            questionButtonFlags[2]=changeButton(buttonQuestion2);
        }
        else if(view == buttonQuestion3){
            questionButtonFlags[3]=changeButton(buttonQuestion3);
        }
        else if(view == buttonQuestion4){
            questionButtonFlags[4]=changeButton(buttonQuestion4);
        }
        else if(view == buttonQuestion5){
            questionButtonFlags[5]=changeButton(buttonQuestion5);
        }
        else if(view == buttonQuestion6){
            questionButtonFlags[6]=changeButton(buttonQuestion6);
        }

    }
    Boolean changeButton(Button button)
    {
        //This function it to change

        if(button.getText().toString()=="Can not see anything"){
            button.setBackgroundColor(getResources().getColor(R.color.color_red));
            button.setText("I see");
            return true;

        }
        else{
            button.setBackgroundColor(getResources().getColor(R.color.color_blue));
            button.setText("Can not see anything");
            return false;
        }
    }

}
