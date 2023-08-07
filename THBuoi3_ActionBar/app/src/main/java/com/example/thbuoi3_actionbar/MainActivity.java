package com.example.thbuoi3_actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.logo_dev);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_help) {
            Toast.makeText(getApplicationContext(), "Action Help", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Action Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.action_refresh) {
            Toast.makeText(getApplicationContext(), "Action Refresh", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.sub_activity1) {
            Toast.makeText(getApplicationContext(), "Sub Activity 1", Toast.LENGTH_SHORT).show();
            Intent sub1 = new Intent(this, Sub1Activity.class);
            startActivity(sub1);
            return true;
        }
        if(item.getItemId() == R.id.sub_activity2) {
            Toast.makeText(getApplicationContext(), "Sub Activity 2", Toast.LENGTH_SHORT).show();
            Intent sub2 = new Intent(this, Sub2Acitivity.class);
            startActivity(sub2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}