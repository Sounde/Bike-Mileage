package com.example.bikesmileagejava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get data of precedent form

        Intent intent = getIntent();
        Intent IntentCalo = new Intent(SecondActivity.this, ThirdActivity.class);
        Intent IntentTime = new Intent(SecondActivity.this, FifthActivity.class);



        String name = intent.getStringExtra("keyname");
        String gender = intent.getStringExtra("radiochosen");
        int weight = intent.getIntExtra("keyweight",0);
        int age = intent.getIntExtra("keyage",0);
        int height = intent.getIntExtra("keyheight",0);

        IntentCalo.putExtra("name", name);
        IntentCalo.putExtra("gender", gender);
        IntentCalo.putExtra("weight", weight);
        IntentCalo.putExtra("age", age);
        IntentCalo.putExtra("height", height);

        IntentTime.putExtra("name", name);
        IntentTime.putExtra("gender", gender);
        IntentTime.putExtra("weight", weight);
        IntentTime.putExtra("age", age);
        IntentTime.putExtra("height", height);

        // Display results
        TextView result = findViewById(R.id.result);
        result.setText("Hello "+gender+ " " +name+",");

        Button ButCalo = findViewById(R.id.BtnCalo);
        Button ButTime = findViewById(R.id.BtnTime);


        ButCalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(IntentCalo);
            }
        });

        ButTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(IntentTime);            }
        });




    }

}