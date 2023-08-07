package com.example.thbuoi3_listcountry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Country> listCountry;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        getCountryList();

        CountryAdapter adapter = new CountryAdapter(this, listCountry);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = "Position: " + position + "\n";
                msg += "Quốc gia: " + listCountry.get(position).getName();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(msg)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "OK!!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Cancel!!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
            }
        });
    }

    protected void getCountryList() {
        listCountry = new ArrayList<Country>();
        listCountry.add(new Country("India", R.drawable.in));
        listCountry.add(new Country("China", R.drawable.cn));
        listCountry.add(new Country("Australia", R.drawable.au));
        listCountry.add(new Country("Los Angeles", R.drawable.la));
        listCountry.add(new Country("Korean", R.drawable.kr));
        listCountry.add(new Country("Japan", R.drawable.jp));

    }
}