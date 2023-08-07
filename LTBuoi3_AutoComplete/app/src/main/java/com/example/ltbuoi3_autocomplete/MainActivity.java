package com.example.ltbuoi3_autocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    TextView txtMsg;
    AutoCompleteTextView autoCompleteTextView;

    String[] items = {"words", "starting", "with", "set", "Setback", "Setline", "Setoffs", "Setouts", "Setters", "Setting", "Settled"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMsg = findViewById(R.id.txtMsg);
        autoCompleteTextView = findViewById(R.id.autoCompleteTxtView);

        autoCompleteTextView.addTextChangedListener(this);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, items));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        txtMsg.setText(autoCompleteTextView.getText());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}