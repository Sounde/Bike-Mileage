package com.example.bikesmileagejava;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //static final UUID mUUID = UUID.fromString(...);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bluetooth
      /* BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(btAdapter.getBondedDevices());

        BluetoothDevice hc05 = btAdapter.getRemoteDevice(...);
        System.out.println(hc05.getName());

        BluetoothSocket btSocket = null;
        int counter = 0;
        do {
            try {
                btSocket = hc05.createInsecureRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                btSocket.connect();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        }while (!btSocket.isConnected()  && counter < 3);


        try{
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(48);
        }catch(IOException e){
            e.printStackTrace();
        }

        InputStream inputStream = null;

        try {

            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());

            for (int i = 0; i < 26; i++) {
                byte b = (byte) inputStream.read();
                System.out.println((char) b);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            btSocket.close();
            System.out.println(btSocket.isConnected());
        }catch (IOException e){
            e.printStackTrace();
        }*/

        EditText name = findViewById(R.id.nameInput);
        EditText weight =  findViewById(R.id.weightInput);
        EditText age =  findViewById(R.id.ageInput);
        EditText height =  findViewById(R.id.heightInput);
        RadioGroup gender = findViewById(R.id.radioGroup);

        Button button = findViewById(R.id.startButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if radiobutton has been selected

                int checkedId = gender.getCheckedRadioButtonId();

                if (checkedId == -1 || TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(weight.getText().toString()) || TextUtils.isEmpty(age.getText().toString())){
                    Message.message(getApplicationContext(), "Please fill everything");

                }else{

                    //Get Data and Send to the other window
                    //View view = null;
                    String nameInput = name.getText().toString();
                    int weightInput = Integer.parseInt(weight.getText().toString());
                    int ageInput = Integer.parseInt(age.getText().toString());
                    int heightInput = Integer.parseInt(height.getText().toString());
                    String str = onRadioButtonCLicked(gender);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("keyname", nameInput);
                    intent.putExtra("keyweight", weightInput);
                    intent.putExtra("keyage", ageInput);
                    intent.putExtra("keyheight", heightInput);
                    intent.putExtra("radiochosen", str);

                    // Open the second window
                    startActivity(intent);

                }


            }
        public String onRadioButtonCLicked(RadioGroup gender){

                String str = "no choice";
                //boolean checked = ((RadioButton) view).isChecked();

                switch(gender.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                            str = "Mrs";
                        break;
                    case R.id.radioButton2:
                            str = "Mr";
                        break;
                }
                return str;
        }
        });


    }


}