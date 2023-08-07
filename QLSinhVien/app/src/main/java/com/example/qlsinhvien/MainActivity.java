package com.example.qlsinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editHoTen, editMaSV, editNganh;
    ListView listSinhVien;
    Button btnThem, btnSua, btnXoa, btnHien;
    ArrayList<String> listSV;
    ArrayAdapter<String> adapter;

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editHoTen = (EditText) findViewById(R.id.editHoTen);
        editMaSV = (EditText) findViewById(R.id.editMaSV);
        editNganh = (EditText) findViewById(R.id.editNganh);
        listSinhVien = (ListView) findViewById(R.id.listSinhVien);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnSua = (Button) findViewById(R.id.btnSua);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnHien = (Button) findViewById(R.id.btnHien);

        listSV = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSV);
        listSinhVien.setAdapter(adapter);

        database = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE SinhVien(maSV TEXT primary key,hoTen TEXT,nganh TEXT)";
            database.execSQL(sql);
        }catch (Exception e) {
            Log.e("Error", "Table exists");
        }


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSV = editMaSV.getText().toString();
                String hoTen = editHoTen.getText().toString();
                String nganh = editNganh.getText().toString();

                ContentValues values = new ContentValues();
                values.put("maSV",maSV);
                values.put("hoTen", hoTen);
                values.put("nganh", nganh);

                String msg = "";
                if(database.insert("SinhVien", null, values) == -1) {
                    msg = "Failed to insert";
                }
                else {
                    msg = "Insert successful";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSV = editMaSV.getText().toString();
                String hoTen = editHoTen.getText().toString();
                String nganh = editNganh.getText().toString();

                ContentValues values = new ContentValues();
                values.put("hoTen", hoTen);
                values.put("nganh", nganh);
                int n = database.update("SinhVien", values, "maSV = ?", new String[]{maSV});

                String msg = "";
                if(n == 0) {
                    msg = "No record to Update";
                }
                else {
                    msg = "Update successful";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSV = editMaSV.getText().toString();
                int n = database.delete("SinhVien", "maSV = ?", new String[]{maSV});
                String msg = "";
                if(n == 0) {
                    msg = "No record to delete";
                }
                else {
                    msg = "Delete successful";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSV.clear();
                Cursor c = database.query("SinhVien", null, null, null, null, null, null);

                c.moveToFirst();
                String data = "";
                while(c.isAfterLast() == false) {
                    data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2);
                    c.moveToNext();
                    listSV.add(data);
                }
                c.close();
                adapter.notifyDataSetChanged();
            }
        });

        listSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String maSV = editMaSV.getText().toString();
                String hoTen = editHoTen.getText().toString();
                String nganh = editNganh.getText().toString();
            }
        });

    }
}