package com.example.thbuoi5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Button btnSave, btnLoad;
    TextView tvResult;
    EditText edt;

    String data;
    private String file = "myFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnSave = (Button) findViewById(R.id.btnSave);
        edt = (EditText) findViewById(R.id.edt);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = edt.getText().toString();
                try {
                    FileOutputStream fileOut = openFileOutput(file, MODE_APPEND);
                    fileOut.write(data.getBytes(StandardCharsets.UTF_8));
                    fileOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = edt.getText().toString();
                try {
                    FileInputStream fileIn = openFileInput(file);
                    int c;
                    String temp = "";
                    while((c = fileIn.read()) != -1) {
                        temp = temp + (char) c;
                    }
                    fileIn.close();
                    tvResult.setText(temp);
                    Toast.makeText(MainActivity.this, "Luu thanh cong", Toast.LENGTH_LONG).show();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}