package com.example.thbuoi5_registercoban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtPassword, edtEmail, edtPhone;
    Button btnCheck;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        AnhXa();

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])"
                + "(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+"
                + "=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(MainActivity.this, R.id.edtName, "[a-zA-Z\\s]+", R.string.err_name);
        awesomeValidation.addValidation(MainActivity.this, R.id.edtPhone, RegexTemplate.TELEPHONE, R.string.err_tel);
        awesomeValidation.addValidation(MainActivity.this, R.id.edtEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        awesomeValidation.addValidation(MainActivity.this, R.id.edtPassword, regexPassword, R.string.password);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()) {
                    String name = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String pass = edtPassword.getText().toString();
                    String phone = edtPhone.getText().toString();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Everything is fine", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        edtName = findViewById(R.id.edtName);
        edtPhone= findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);

        btnCheck = findViewById(R.id.btnCheck);
    }
}