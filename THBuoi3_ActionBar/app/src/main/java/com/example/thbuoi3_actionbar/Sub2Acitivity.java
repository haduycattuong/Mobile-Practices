package com.example.thbuoi3_actionbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Sub2Acitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2_acitivity);
        getSupportActionBar().hide();
    }
}