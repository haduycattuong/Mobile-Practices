package com.example.thbuoi4_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtMsg;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("MESSAGE");
        txtMsg.setText(msg);

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);

                intent.putExtra("name", "value");
                setResult(RESULT_OK, intent);
                finish();
                startActivity(intent);
            }
        });
    }
}