package com.example.ltbuoi4_alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView txtAlert;
    Button btnExit;
    Button btnTradional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtAlert = findViewById(R.id.txtAlert);
        btnExit = findViewById(R.id.btnExit);
        btnTradional = findViewById(R.id.btnTraditionalList);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Thoát").setMessage("Bạn có muốn thoát chương trình").setIcon(R.drawable.ic_exit)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtAlert.setText("YES", TextView.BufferType.valueOf(Integer.toString(which)));
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show();
            }
        });

        btnTradional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select color").setItems(R.array.color, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                txtAlert.setTextColor(Color.RED);
                                break;
                            case 1:
                                txtAlert.setTextColor(Color.GREEN);
                                break;
                            case 2:
                                txtAlert.setTextColor(Color.BLUE);
                                break;
                        }
                    }

                })
                        .create().show();
            }
        });

    }
}