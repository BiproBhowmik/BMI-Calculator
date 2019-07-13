package com.joy.bmicalculator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Double.parseDouble;
import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {

    private Button result, about, records, reset;
    private EditText kgx, ftx, inx, namex;
    private RadioButton selectedButton, male, female;
    private RadioGroup radioGroup;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        result = findViewById(R.id.result);
        kgx = findViewById(R.id.kg);
        ftx = findViewById(R.id.ft);
        inx = findViewById(R.id.in);
        namex = findViewById(R.id.egjoy);
        radioGroup = findViewById(R.id.radiobuttongroup);
        male = findViewById(R.id.male);
        //female = findViewById(R.id.female);
        about = findViewById(R.id.about);
        records = findViewById(R.id.records);
        reset = findViewById(R.id.reset);

        male.setChecked(true);

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                selectedButton = findViewById(selectedRadioButtonId);

                String selectedButtonText = selectedButton.getText().toString();

                Intent intent = new Intent(MainActivity.this, YourResult.class);

                if (selectedRadioButtonId == -1) {
                    intent.putExtra("tag", "none");
                    startActivity(intent);
                } else if (selectedButtonText.equals("Male")) {

                    String a = kgx.getText().toString(), b = ftx.getText().toString(), c = inx.getText().toString(), d = namex.getText().toString();

                    intent.putExtra("tag", "Male");
                    intent.putExtra("kg", a);
                    intent.putExtra("ft", b);
                    intent.putExtra("in", c);
                    intent.putExtra("name", d);

                    startActivity(intent);
                } else if (selectedButtonText.equals("Female")) {
                    String a = kgx.getText().toString(), b = ftx.getText().toString(), c = inx.getText().toString(), d = namex.getText().toString();

                    intent.putExtra("tag", "Female");
                    intent.putExtra("kg", a);
                    intent.putExtra("ft", b);
                    intent.putExtra("in", c);
                    intent.putExtra("name", d);

                    startActivity(intent);
                }


            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About.class);
                intent.putExtra("tag", "about");
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHandler.displayAllData();

                if(cursor.getCount() == 0){
                    //Nodata
                    showData("Error", "No Data Found");
                }
                else {
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext()){
                        stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                        stringBuffer.append("Name : "+cursor.getString(1)+"\n");
                        stringBuffer.append("Gender : "+cursor.getString(2)+"\n");
                        stringBuffer.append("Weight : "+cursor.getString(3)+"\n");
                        stringBuffer.append("Hight : "+cursor.getString(4)+"\n");
                        stringBuffer.append("BMI Result : "+cursor.getString(5)+"\n");
                        stringBuffer.append(cursor.getString(6)+"\n");
                        stringBuffer.append("Record's Date : "+cursor.getString(7)+"\n\n");
                        //stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n\n");
                    }
                    showData("The Record's", stringBuffer.toString());
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgx.setText(null);
                ftx.setText(null);
                inx.setText(null);
                namex.setText(null);
            }
        });

    }

    public void showData(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }


}
