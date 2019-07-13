package com.joy.bmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;

public class YourResult extends AppCompatActivity {

    TextView textView;

    private EditText ftx;
    private EditText inx;
    private EditText namex;
    private RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_result);

        textView = findViewById(R.id.textView);



        Bundle bundle = getIntent().getExtras();
        String s1 = bundle.getString("kg"), s2 = bundle.getString("ft"), s3 = bundle.getString("in"), name = bundle.getString("name");
        String maleOrFemle = bundle.getString("tag");

        double kg = 0, fit = 0, inc = 0;
        double result = 5.5;
        boolean exception = false;

        try {
            kg = Double.parseDouble(s1);
            fit = Double.parseDouble(s2) * 0.3048; //Metre Convert
            inc = Double.parseDouble(s3) * 0.0254; //Metre Convert
            result = kg / pow((fit + inc), 2);
            //result = kg / (fit+inc);

        } catch (Exception e) {
            //Toast.makeText(this,Double.toString(result), Toast.LENGTH_SHORT).show();
            textView.setText("Please Fillup All Option's With Proper Input");
            exception = true;
        }

        if (!exception) {
            DecimalFormat df2 = new DecimalFormat(".##");

            String BMI_Category = null;
            if (result < 18.5) {
                BMI_Category = "And it's Underweight !!";
            } else if (result >= 18.5 && result <= 24.9) {
                BMI_Category = "And it's Normal !!";
            } else if (result >= 25 && result <= 29.9) {
                BMI_Category = "And it's Overweight !!";
            } else if (result > 30) {
                BMI_Category = "And it's Obese !!";
            }

            if(bundle.getString("tag").equals("Male"))
            {
                textView.setText("Hello Mr. "+name+"\nYour Body Mass Index result is: " + df2.format(result) + " kg/m2\n" + BMI_Category);
            }
            else if(bundle.getString("tag").equals("Female"))
            {
                textView.setText("Hello Mrs. "+name+"\nYour Body Mass Index result is: " +  df2.format(result) + " kg/m2\n" + BMI_Category);
            }


            DatabaseHandler databaseHandler = new DatabaseHandler(this);

            databaseHandler.addData(name, result, maleOrFemle, BMI_Category, s1, s2, s3, getCurrentDate());




        }



    }
    public String getCurrentDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }
}
