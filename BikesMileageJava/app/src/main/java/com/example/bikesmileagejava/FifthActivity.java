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

public class FifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        int weight = intent.getIntExtra("weight", 0);
        int age = intent.getIntExtra("age", 0);
        int height = intent.getIntExtra("height", 0);

        Intent intent5to6 = new Intent(FifthActivity.this, SixthActivity.class);

        intent5to6.putExtra("name", name);
        intent5to6.putExtra("gender", gender);
        intent5to6.putExtra("weight", weight);
        intent5to6.putExtra("age", age);
        intent5to6.putExtra("height", height);

        RadioGroup bike = findViewById(R.id.radioGroup1);

        EditText nbtimes = findViewById(R.id.nbtime);

        Button ButOk = findViewById(R.id.Ok);

        ButOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkedId = bike.getCheckedRadioButtonId();

                if (checkedId == -1 || TextUtils.isEmpty(nbtimes.getText().toString())){
                    Message.message(getApplicationContext(), "Please fill everything");

                }else{

                    //Get Data and Send to the other window
                    //View view = null;
                    int timesInput = Integer.parseInt(nbtimes.getText().toString());
                    String sport = onRadioButtonCLicked(bike);


                    intent5to6.putExtra("times", timesInput);
                    intent5to6.putExtra("sportchosen", sport);

                    // Open the second window

                    startActivity(intent5to6);


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
