package com.example.androidcashier.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.androidcashier.R;
import com.example.androidcashier.adabters.AdapterReport;
import com.example.androidcashier.databinding.ActivityReportsBinding;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.Report;
import com.example.androidcashier.pojo.close.Task1Closed;
import com.example.androidcashier.ui.closed.ClosedActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReportsActivity extends AppCompatActivity   implements
        View.OnClickListener{
    ActivityReportsBinding binding;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<Report> xx=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_reports);



  //     DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker
  //    DatePicker simpleDatePicker2 = (DatePicker)findViewById(R.id.simpleDatePicker2); // initiate a date picker



  //    simpleDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
  //        @Override
  //        public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
  //           //String day = "Day = " + simpleDatePicker.getDayOfMonth();
  //           //String month = "Month = " + (simpleDatePicker.getMonth() + 1);
  //           //String year = "Year = " + simpleDatePicker.getYear();
  //           //// display the values by using a toast
  //           //Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();

  //            int x=i1+1;
  //            binding.textView15.setText(i+"-"+x+"-"+i2);

  //        }
  //    });
  //   //  simpleDatePicker.setSpinnersShown(false);

  //    simpleDatePicker2.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
  //        @Override
  //        public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
  //            //String day = "Day = " + simpleDatePicker.getDayOfMonth();
  //            //String month = "Month = " + (simpleDatePicker.getMonth() + 1);
  //            //String year = "Year = " + simpleDatePicker.getYear();
  //            //// display the values by using a toast
  //            //Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();

  //            int x=i1+1;
  //            binding.textView16.setText(i+"-"+x+"-"+i2);

  //        }
  //    });


  //    View.OnClickListener showDatePicker = new View.OnClickListener() {
  //        @Override
  //        public void onClick(View v) {
  //            final View vv = v;

  //            int year=23;
  //            int month=32;
  //            int day = 32;
  //            DatePickerDialog datePickerDialog = new DatePickerDialog(ReportsActivity.this, new DatePickerDialog.OnDateSetListener() {
  //                @Override
  //                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
  //                    if (vv.getId() == R.id.textView16 ) {
  //                        Toast.makeText(ReportsActivity.this, "1", Toast.LENGTH_SHORT).show();
  //                    //do the stuff
  //                        binding.  textView16.setText(dayOfMonth+"-"+monthOfYear+"-"+year+"");

  //                } else{ //EndDate button was clicked {
  //                //do the stuff
  //                        binding.  textView15.setText(dayOfMonth+"-"+monthOfYear+"-"+year+"");

  //                        Toast.makeText(ReportsActivity.this, "2", Toast.LENGTH_SHORT).show();
        AdapterReport adapter =new AdapterReport(this);
  //                    }
  //        }
  //    }, year, month, day );
  //    datePickerDialog.show();
  //}
  //  };
  //  binding.  textView16.setOnClickListener(showDatePicker);
  //    binding.  textView15.setOnClickListener(showDatePicker);
        binding.payWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setList(xx);
                binding.SaleRecycler.setAdapter(adapter);
            String x=    binding. textView16.getText().toString();
                String y=    binding. textView15.getText().toString();
                Toast.makeText(ReportsActivity.this, x+""+y, Toast.LENGTH_SHORT).show();
                Single s= RetrofItItems.getInstance().getApiCalls().getReport(y,x,"1")
                        . subscribeOn(Schedulers.io())
                        . observeOn(AndroidSchedulers.mainThread());
                SingleObserver<Task1Closed> singleObserver=new SingleObserver<Task1Closed>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Task1Closed task1Closed) {
          //  Log.d("ssssssssss",task1Closed.getState()+""+task1Closed.getMessage()+task1Closed.getData().get(0).totalQty);

                        Toast.makeText(ReportsActivity.this, task1Closed.getMessage(), Toast.LENGTH_SHORT).show();
            if (task1Closed.getState()==1){
                adapter.setList(task1Closed.getData());
                binding.SaleRecycler.setAdapter(adapter);
                double amunt=0.0;
                for (int x=0;x<task1Closed.getData().size();x++){
                   amunt=amunt+ task1Closed.getData().get(x).TotalPrice;
                }
                binding.farora1.setText("اجمالى الفاتوره :  "+amunt+" جم");
            }else if (task1Closed.getState()==0) {
                adapter.setList(xx);
                binding.SaleRecycler.setAdapter(adapter);
                binding.farora1.setText(" الفاتوره ");

            }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
               Toast.makeText(ReportsActivity.this, e.getMessage()+"سسس", Toast.LENGTH_SHORT).show();
                        binding.farora1.setText(" الفاتوره ");

                    }
                };
//                SingleObserver<Task1Closed> singleObserver=new SingleObserver<Task1Closed>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull Task1Closed itemsModel) {
//                        Log.d("ssssssssss",itemsModel.getState()+""+itemsModel.getMessage());
//                        Toast.makeText(ReportsActivity.this,itemsModel.getState(), Toast.LENGTH_SHORT).show();
//
//                        Log.d("ssssssssss",itemsModel.getState()+"");
//                        // Toast.makeText(AddItemActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();
//                        // binding.textView3.setText(itemsModel.getBarcode());
//                        if (itemsModel.getState()==1) {
//
//
//                            //  binding.textView8.setText(itemsModel.getItemName());
//                            //  binding.textView3.setText(itemsModel.getBarcode());
//                            //  binding.textView2.setText(itemsModel.getSelling_Price()+"");
//                            //    Toast.makeText(ClosedActivity.this, "المنتج موجود بالفعل", Toast.LENGTH_SHORT).show();
//                        }else {
//
//                            Toast.makeText(ReportsActivity.this, "", Toast.LENGTH_SHORT).show();
//                            //    binding.textView3.setText(code);
//                            //Toast.makeText(UpdateProductActivity.this,  itemsModel.getMessage()+"المنتج غير موجود", Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Toast.makeText(ReportsActivity.this, e.getMessage()+"سسس", Toast.LENGTH_SHORT).show();
//
//                    }
//                };
                s.subscribe(singleObserver);
            }
        });

        binding.  cardView1.setOnClickListener(this);
        binding.  cardView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if ( view ==  binding.  cardView1){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                           binding. textView16.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else if ( view ==  binding.  cardView){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            binding. textView15.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}