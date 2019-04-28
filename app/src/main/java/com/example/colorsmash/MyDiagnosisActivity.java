package com.example.colorsmash;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MyDiagnosisActivity extends AppCompatActivity {

    private TextView TVname ;
    private TextView TVresults ;
    private String name ;
    private ArrayList<String> colorBlindnessTypes ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diagnosis);

        TVname=(TextView)findViewById(R.id.textViewNameMyDiagnosis);
        TVresults=(TextView)findViewById(R.id.textViewTestResultsMyDiagnosis);

        //@@@@@@@@To HAVIV @@@@@@@@@@@@@@@@@@@@@@@@@
        //NO NEED THIS AFTER DB!!!!!!!!!!!!!!!!!!!!
        colorBlindnessTypes = new ArrayList<String>();
        colorBlindnessTypes.add("PROTAN");
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        name=  "Wellcome : Yoel vaizman" ;//+ user name from DB
        //ArrayList<String> colorBlindnessTypes = badColors from DB
        //@@@@@@@@To HAVIV @@@@@@@@@@@@@@@@@@@@@@@@@

        TVname.setText(name);
        TVresults.setText(DiagnosisResults(colorBlindnessTypes));


    }

    String DiagnosisResults(ArrayList<String> type){
        String msg ="";
        if(type.contains("PROTAN")){
            msg=msg+"You may have Protans color blindness," +
                    " people with Protanomaly, have a type of red-green color blindness in which the red cones are not" +
                    "absent but do not detect enough red and are too sensitive to greens, yellows, and oranges." +
                    "As a result, greens, yellows, oranges, reds, and browns may appear similar, especially in low light." +
                    "Red and black might be hard to tell apart, especially when red text is against a black background.";
        }
        if(type.contains("DEUTAN")){
            msg=msg+"You may have Deutans color blindness," +
                    "people with Deuteranomaly, have a type of red-green color blindness in which green cones are not" +
                    "absent but do not detect enough green and are too sensitive to yellows, oranges, and reds." +
                    "As a result, greens, yellows, oranges, reds, and browns may appear similar, especially in low light. " +
                    " It can also be difficult to tell the difference between blues and purples, or pinks and grays.";
        }
        if(type.contains("TRITAN")){

            msg=msg+"You may have Tritan color blindness," +
                    "causing reduced blue sensitivity and Tritanopia, resulting in no blue sensitivity, " +
                    "can be inherited or acquired; the inherited form is a rare autosomal recessive condition." +
                    "More commonly, tritanomaly is acquired later in life due to age-related or environmental factors. " +
                    "Cataracts, glaucoma and age related macular degeneration could cause someone to test as a Tritan." +
                    "People with tritanomaly have reduced sensitivity in their blue “S” cone cells," +
                    " which can cause confusion between blue versus green and red from purple.";
        }
        if(msg.equals("")){
            msg="No color blindness / not tested";
        }

        return msg;
    }

}
