package com.example.androidcashier.ui.closed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidcashier.R;
import com.example.androidcashier.databinding.ActivityClosedBinding;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.close.CloseModel;
import com.example.androidcashier.pojo.close.Task1Closed;
import com.example.androidcashier.ui.crudUI.UpdateProductActivity;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClosedActivity extends AppCompatActivity {

    ActivityClosedBinding binding;
    ArrayList<CloseModel> list1=new ArrayList<>();
    AdabterSale sale;
    AdabterReturns adabterReturns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_closed);
      //  binding.SaleRecycler.setAdapter();


        getsale();
        getReturns();
        binding.payWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Observable ss = RetrofItItems.getInstance().getApiCalls().setClosingDay()
                Single s= RetrofItItems.getInstance().getApiCalls().setClosingDay("1")
                        . subscribeOn(Schedulers.io())
                        . observeOn(AndroidSchedulers.mainThread());
                SingleObserver <ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ItemsModel itemsModel) {
                         Toast.makeText(ClosedActivity.this, itemsModel.getMessage(), Toast.LENGTH_SHORT).show();

                        // Toast.makeText(AddItemActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();
                        // binding.textView3.setText(itemsModel.getBarcode());
                        if (itemsModel.getState()==1) {

                            sale.setList(list1);

                            binding.SaleRecycler.setAdapter(sale);

                            adabterReturns.setList(list1);

                            binding.RestorRecycler.setAdapter(adabterReturns);
                         //  double amunt=0.0;
                         //  for (int x=0;x<task1Closed.getItems().size();x++){
                         //      amunt=amunt+ task1Closed.getItems().get(x).getTotalPrice();
                         //  }

                            //  binding.textView8.setText(itemsModel.getItemName());
                            //  binding.textView3.setText(itemsModel.getBarcode());
                            //  binding.textView2.setText(itemsModel.getSelling_Price()+"");
                        //    Toast.makeText(ClosedActivity.this, "المنتج موجود بالفعل", Toast.LENGTH_SHORT).show();
                        }else {

                        //    binding.textView3.setText(code);
                            //Toast.makeText(UpdateProductActivity.this,  itemsModel.getMessage()+"المنتج غير موجود", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(ClosedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                };
                s.subscribe(singleObserver);
            }
        });

    }
    public void getsale(){
        binding.farora1.setText("البيع");

        sale=new AdabterSale(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.SaleRecycler.setLayoutManager(linearLayoutManager);

        Observable ss = RetrofItItems.getInstance().getApiCalls().sale("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer<Task1Closed> singleObservers = new Observer<Task1Closed>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task1Closed task1Closed) {
                if (task1Closed.getItems() !=null) {
                    sale.setList(task1Closed.getItems());

                    binding.SaleRecycler.setAdapter(sale);
                    double amunt=0.0;
                    for (int x=0;x<task1Closed.getItems().size();x++){
                        amunt=amunt+ task1Closed.getItems().get(x).getTotalPrice();
                    }
                   // binding.farora1.setText();
                    binding.farora1.setText("اجمالى الفاتوره البيع اليوم :  "+amunt+" جم");

                }


            }



            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        ss.subscribe(singleObservers);

    }
    public void getReturns(){
        binding.farora2.setText("المرتجع");

        adabterReturns=new AdabterReturns(this);
      //  AdabterSale sale=new AdabterSale(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.RestorRecycler.setLayoutManager(linearLayoutManager);

        Observable ss = RetrofItItems.getInstance().getApiCalls().ReturnItem("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer<Task1Closed> singleObservers = new Observer<Task1Closed>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task1Closed task1Closed) {
                if (task1Closed.getItems() !=null) {
                    adabterReturns.setList(task1Closed.getItems());

                    binding.RestorRecycler.setAdapter(adabterReturns);
                    double amunt=0.0;
                    for (int x=0;x<task1Closed.getItems().size();x++){
                        amunt=amunt+ task1Closed.getItems().get(x).getTotalPrice();
                    }
                    binding.farora2.setText("اجمالى الفاتوره المرتجع اليوم :  "+amunt+" جم");

                }

            }



            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        ss.subscribe(singleObservers);
    }
}