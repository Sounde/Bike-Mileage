package com.example.bikesmileagejava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        int weight = intent.getIntExtra("weight", 0);
        int age = intent.getIntExtra("age", 0);
        int height = intent.getIntExtra("height", 0);

        Intent intent3to4 = new Intent(ThirdActivity.this, FourthActivity.class);

        intent3to4.putExtra("name", name);
        intent3to4.putExtra("gender", gender);
        intent3to4.putExtra("weight", weight);
        intent3to4.putExtra("age", age);
        intent3to4.putExtra("height", height);

        RadioGroup bike = findViewById(R.id.radioGroup1);

        EditText nbcalories = findViewById(R.id.nbcalories);

        Button ButOk = findViewById(R.id.Ok);

        ButOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkedId = bike.getCheckedRadioButtonId();

                if (checkedId == -1 || TextUtils.isEmpty(nbcalories.getText().toString())){
                    Message.message(getApplicationContext(), "Please fill everything");

                }else{

                    //Get Data and Send to the other window
                    //View view = null;
                    int caloriesInput = Integer.parseInt(nbcalories.getText().toString());
                    String sport = onRadioButtonCLicked(bike);


                    intent3to4.putExtra("calories", caloriesInput);
                    intent3to4.putExtra("sportchosen", sport);

                    // Open the second window

                    startActivity(intent3to4);


                }

            }

            public String onRadioButtonCLicked(RadioGroup bike){

                String str = "no choice";
                //boolean checked = ((RadioButton) view).isChecked();

                switch(bike.getCheckedRadioButtonId()) {
                    case R.id.Mountain:
                        str = "Mountain";
                        break;
                    case R.id.BMX:
                        str = "BMX";
                        break;
                    case R.id.Leisure:
                        str = "Leisure";
                        break;
                    case R.id.ToFromWork:
                        str = "ToFromWork";
                        break;
                    case R.id.FarmRoad:
                        str = "FarmRoad";
                        break;
                    case R.id.Vigorous:
                        str = "Vigorous";
                        break;
                }
                return str;
            }
        });
    }

}