package com.example.thbuoi8sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtContact, edtDob;
    Button btnInsert, btnDelete, btnUpdate, btnView;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        db = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String contact = edtContact.getText().toString();
                String dob = edtDob.getText().toString();

                Boolean checkInsertdata = db.insertUserdata(name, contact, dob);
                if(checkInsertdata == true) {
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No new entry inserted!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String contact = edtContact.getText().toString();
                String dob = edtDob.getText().toString();

                Boolean checkUpdatedata = db.updateUserdata(name, contact, dob);
                if(checkUpdatedata == true) {
                    Toast.makeText(MainActivity.this, "New Entry Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No new entry Updated!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();

                Boolean checkDeletedata = db.deleteUserdata(name);
                if(checkDeletedata == true) {
                    Toast.makeText(MainActivity.this, "Deleted!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Delete Failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getData();
                if(res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Name: " + res.getString(0) + "\n");
                    buffer.append("Contact: " + res.getString(1) + "\n");
                    buffer.append("Date of Birth: " + res.getString(2) + "\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }

    private void AnhXa() {
        edtName = findViewById(R.id.edtName);
        edtContact = findViewById(R.id.edtContact);
        edtDob = findViewById(R.id.edtDob);

        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnView = findViewById(R.id.btnView);
    }
}