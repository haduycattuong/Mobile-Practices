package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener   {

    EditText editCrush, editBan;
    TextView txtNhanXet;
    Button btnBoiToan;
    ListView listView;
    TextView txtMon;
    TextView txtText;
    Spinner spinner1;
    String[] data = {"data1", "data2", "data3", "data4", "data5", "data6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buoi3_spinner);



//        String[] items = {"Cơ sở lập trình", "Cơ sở dữ liệu", "Cấu trúc dữ liệu", "Lập trình hướng đối tượng"};
        txtText = (TextView) findViewById(R.id.txtText);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> s = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data);
        spinner1.setAdapter(s);
        spinner1.setOnItemSelectedListener(this);
//        txtMon = (TextView) findViewById(R.id.txtMon);
//        listView = (ListView) findViewById(R.id.listview);
//
//        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(a);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String text = "Position: " + position + " " + items[position];
//                txtMon.setText(text);
//            }
//        });

        //BoiToan
//        editBan = (EditText) findViewById(R.id.editBan);
//        editCrush = (EditText) findViewById(R.id.editCrush);
//        txtNhanXet = (TextView) findViewById(R.id.txtNhanXet);
//        btnBoiToan = (Button) findViewById(R.id.btnBoiToan);
//
//        btnBoiToan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int tuoiCr = Integer.parseInt(String.valueOf(editCrush.getText()));
//                int tuoiBan = Integer.parseInt(String.valueOf(editBan.getText()));
//                int result = tuoiBan - tuoiCr;
//                if(tuoiBan < 18)
//                    txtNhanXet.setText("Duoi 18 tuoi thi lo hoc di");
//                else if(result <= 12)
//                    txtNhanXet.setText("Cuoi luon di");
//                else
//                    txtNhanXet.setText("Tinh lam suggar daddy?");
//
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = "Position: " + position + " " + data[position];
        txtText.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}