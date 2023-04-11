package com.example.androidcashier.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidcashier.R;
import com.example.androidcashier.databinding.ActivityReportsBinding;

public class ReportsItemsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_items);
        //  binding = DataBindingUtil.setContentView(this, R.layout.activity_report);



        // DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker

        // simpleDatePicker.setSpinnersShown(false);

    }
}