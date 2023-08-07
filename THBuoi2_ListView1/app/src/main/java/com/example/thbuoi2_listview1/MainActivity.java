package com.example.thbuoi2_listview1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoText;
    TextView txtCountry;
    private static final String[] COUNTRIES = new String[]{
            "Australia", "China","Canada", "Belgium", "Germany", "Spain", "Japan", "Korea", "United States", "United Kingdom", "Vietnam"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoText = findViewById(R.id.autoText);
        txtCountry = findViewById(R.id.txtCountry);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, COUNTRIES);
        autoText.setAdapter(adapter);

        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg =  (String) parent.getItemAtPosition(position);
                msg = "Welcome to country of [" + msg + "]";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

    }
}