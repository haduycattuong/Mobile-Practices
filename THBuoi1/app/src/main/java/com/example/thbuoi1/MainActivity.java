package com.example.thbuoi1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;
    TableLayout tb;
    TextView tv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablelayout);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tb = (TableLayout) findViewById(R.id.tb);
        tv = (TextView) findViewById(R.id.time);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss a");
        dtf.format(localTime);
        tv.setText(String.valueOf(localTime));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                Log.d("EMAIL", email);
                Log.d("PASSWORD", password);
                if(!isValid(txtEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Email invalid or Password is empty", Toast.LENGTH_LONG).show();
                }
                addToTable();
            }

        });
    }
    private boolean isValid(String email) {
        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void addToTable() {
        TableRow row = new TableRow(getApplicationContext());
        TextView t1 = new TextView(getApplicationContext());
        t1.setText(txtEmail.getText().toString());
        TextView t2 = new TextView(getApplicationContext());
        t2.setText(" " + txtPassword.getText().toString());
        row.addView(t1);
        row.addView(t2);
        tb.addView(row);
    }
}