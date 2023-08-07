package com.example.thbuoi5_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtClass, txtAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (EditText) findViewById(R.id.edit1);
        txtClass= (EditText) findViewById(R.id.edit2);
        txtAge= (EditText) findViewById(R.id.edit3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String strName = sh.getString("name", "");
        String strClass = sh.getString("class", "");
        int Age = sh.getInt("age", 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEditor = sh.edit();

        myEditor.putString("name", txtName.getText().toString());
        myEditor.putString("class", txtClass.getText().toString());
        myEditor.putInt("age", Integer.parseInt(txtAge.getText().toString()));
    }


}