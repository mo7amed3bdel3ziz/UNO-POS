package com.example.androidcashier.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    DateInterface dateInterface;
    private String dateString;

    public TimePickerFragment(DateInterface dateInterface) {
        this.dateInterface = dateInterface;
    }

    @NonNull
    @Override
    public DatePickerDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour =calendar.get(Calendar.HOUR);
        int minute =calendar.get(Calendar.MINUTE);
        int second =calendar.get(Calendar.SECOND);

        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        //dateString = DateFormat.getDateInstance().format(calendar.getTime());
        //dateInterface.getDateString(dateString);
        dateInterface.getDateString((month+1)+"/"+dayOfMonth +"/"+year);
    }


    public interface DateInterface {
        void getDateString(String date);
    }
}