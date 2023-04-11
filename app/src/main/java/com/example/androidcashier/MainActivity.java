package com.example.androidcashier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidcashier.ui.SelectionActivity;
import com.example.androidcashier.ui.auth.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i =new Intent(MainActivity.this,SelectionActivity.class);
        startActivity(i);
        finish();


    }
}