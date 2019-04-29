package com.example.colorsmash;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiagnosisActivity extends AppCompatActivity implements View.OnClickListener {


    private Button submit ;//buttonSubmitDiagnosis
    private Button question1 ;//buttonQuestion6Diagnosis
    private Button question2 ;
    private Button question3 ;
    private Button question4 ;
    private Button question5 ;
    private Button question6 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        submit=(Button)findViewById(R.id.buttonSubmitDiagnosis);
        question1=(Button)findViewById(R.id.buttonQuestion1Diagnosis);
        question2=(Button)findViewById(R.id.buttonQuestion2Diagnosis);
        question3=(Button)findViewById(R.id.buttonQuestion3Diagnosis);
        question4=(Button)findViewById(R.id.buttonQuestion4Diagnosis);
        question5=(Button)findViewById(R.id.buttonQuestion5Diagnosis);
        question6=(Button)findViewById(R.id.buttonQuestion6Diagnosis);

        submit.setOnClickListener(this);
        question1.setOnClickListener(this);
        question2.setOnClickListener(this);
        question3.setOnClickListener(this);
        question4.setOnClickListener(this);
        question5.setOnClickListener(this);
        question6.setOnClickListener(this);




    }



    @Override
    public void onClick(View view){
        if(view == submit){
            String url = "https://enchroma.com/pages/test";

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        else if(view == question1){

            if(question1.getText().toString()=="Enter the number you see?"){
                question1.setBackgroundColor(getResources().getColor(R.color.color_red));
                question1.setText("Remove");

            }
            else{
                question1.setBackgroundColor(getResources().getColor(R.color.color_blue));
                question1.setText("Enter the number you see?");

            }
            //need to check !!!!!
        }

        else if(view == question2){

        }
        else if(view == question3){

        }
        else if(view == question4){

        }
        else if(view == question5){

        }
        else if(view == question6){

        }

    }

}
