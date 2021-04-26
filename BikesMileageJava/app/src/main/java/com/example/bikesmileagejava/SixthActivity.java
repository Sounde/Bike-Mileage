package com.example.bikesmileagejava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SixthActivity extends AppCompatActivity {

    public void DisplayResult(double met, String gender, int nbtimes, double brmf, double brmg){

        if(gender.equals("Mrs")) {

            double dec = (double) nbtimes/60;
            int kcal = (int) ((brmf/24) * met * dec);
            TextView resultat = findViewById(R.id.resultat);
            resultat.setText("les calories brulées sont: " +kcal +" kcal");
        }
        if(gender.equals("Mr")) {

            double dec = (double) nbtimes/60;
            int kcal = (int) ((brmf/24) * met * dec);
            TextView resultat = findViewById(R.id.resultat);
            resultat.setText("les calories brulées sont " +kcal+ " kcal ");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        int weight = intent.getIntExtra("weight", 0);
        int age = intent.getIntExtra("age", 0);
        int nbtimes = intent.getIntExtra("times",0);
        int height = intent.getIntExtra("height",0);
        String sport = intent.getStringExtra("sportchosen");

        double brmf = (((9.56 * weight) + (1.85 * height) - (4.68 * age) + 655));
        double brmg = (((13.75 * weight) + (5 * height) - (6.76 * age) + 66));


        if (sport.equals("Mountain")){
            DisplayResult(10, gender, nbtimes, brmf, brmg);
        }else if(sport.equals("BMX")){
            DisplayResult(8.5, gender, nbtimes, brmf, brmg);
        }else if(sport.equals("Leisure")){
            DisplayResult(5.5, gender, nbtimes, brmf, brmg);
        }else if(sport.equals("Vigorous")){
            DisplayResult(10, gender, nbtimes, brmf, brmg);
        }else if(sport.equals("ToFromWork")){
            DisplayResult(6.8, gender, nbtimes, brmf, brmg);
        }else if(sport.equals("FarmRoad")){
            DisplayResult(5.8, gender, nbtimes, brmf, brmg);
        }


    }
}