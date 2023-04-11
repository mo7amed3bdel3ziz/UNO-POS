package com.example.androidcashier.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.androidcashier.R;

public class reportActivity extends AppCompatActivity {

   // ActivityReportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
      //  binding = DataBindingUtil.setContentView(this, R.layout.activity_report);



      // DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker

      // simpleDatePicker.setSpinnersShown(false);

        // set false value for the spinner shown function

        /*Add in Oncreate() funtion after setContentView()*/
       // DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker); // initiate a date picker
     //  int day = simpleDatePicker.getDayOfMonth();
     //  // get the selected day of the month
     //  int month = simpleDatePicker.getMonth();
     //  // get the selected month
     //  int year = simpleDatePicker.getYear();
     //  int firstDay=simpleDatePicker.getFirstDayOfWeek();

        // get the values for day of month , month and year from a date picker
     // String day = "Day = " + simpleDatePicker.getDayOfMonth();
     // String month = "Month = " + (simpleDatePicker.getMonth() + 1);
     // String year = "Year = " + simpleDatePicker.getYear();
     // // display the values by using a toast
     // Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();

    }
}