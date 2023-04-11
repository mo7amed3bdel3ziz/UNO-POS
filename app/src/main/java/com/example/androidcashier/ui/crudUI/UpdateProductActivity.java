package com.example.androidcashier.ui.crudUI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.androidcashier.R;
import com.example.androidcashier.databinding.ActivityUpdateProductBinding;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.crudOperationProduct.UpdateProductModel;
import com.example.androidcashier.ui.scanner.capture;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateProductActivity extends AppCompatActivity {
ActivityUpdateProductBinding binding;
Double x;
    String barcode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_product);

        binding.btnQrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(
                        UpdateProductActivity.this
                );
                intentIntegrator.setPrompt("For flash use Volump up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.initiateScan();
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvaled()==true) {

                    String qr = (String) binding.textView3.getText();
                    Double x = Double.valueOf(String.valueOf(binding.newPrice.getText()));

                    UpdateProductModel updateProductModel = new UpdateProductModel(qr, x);
                    Single ss = RetrofItItems.getInstance().getApiCalls().udateProduct(updateProductModel)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                    SingleObserver<ItemsModel> singleObservers = new SingleObserver<ItemsModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull ItemsModel itemsModel) {
                            Toast.makeText(UpdateProductActivity.this, itemsModel.getSelling_Price() + "تم بنجاح", Toast.LENGTH_SHORT).show();


                             binding.textView8.setText("");
                             binding.textView3.setText("");
                             binding.textView2.setText("");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(UpdateProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    };
                    ss.subscribe(singleObservers);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentIntegrator= IntentIntegrator.parseActivityResult(

                requestCode, resultCode, data
        );
        if (intentIntegrator.getContents()!=null){



          Single s= RetrofItItems.getInstance().getApiCalls().getItemsQr(intentIntegrator.getContents())
                  . subscribeOn(Schedulers.io())
                  . observeOn(AndroidSchedulers.mainThread());
          SingleObserver<ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
              @Override
              public void onSubscribe(@NonNull Disposable d) {

              }

              @Override
              public void onSuccess(@NonNull ItemsModel itemsModel) {
                // Toast.makeText(UpdateProductActivity .this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();


                // binding.textView8.setText(itemsModel.getItemName());
                // binding.textView3.setText(itemsModel.getBarcode());
                // binding.textView2.setText(itemsModel.getSelling_Price()+"");

                  if (itemsModel.getState()==200) {
                       binding.textView8.setText(itemsModel.getItemName());
                       binding.textView3.setText(itemsModel.getBarcode());
                       binding.textView2.setText(itemsModel.getSelling_Price()+"");
                  }else {
                      Toast.makeText(UpdateProductActivity.this,  itemsModel.getMessage()+"المنتج غير موجود", Toast.LENGTH_LONG).show();

                  }

              }

              @Override
              public void onError(@NonNull Throwable e) {
                  Toast.makeText(UpdateProductActivity .this, e.getMessage(), Toast.LENGTH_SHORT).show();

              }
          };
          s.subscribe(singleObserver);
            // Toast.makeText(ItemsActivity.this, intentIntegrator.getContents(), Toast.LENGTH_SHORT).show();

        }

    }
    public Boolean isvaled() {


        if (  binding.textView8.getText().toString().isEmpty()) {
            binding.editTextTextPersonName.setError("من فضلك استخدم الماسح الضوئي");
            Toast.makeText(UpdateProductActivity.this, "من فضلك استخدم الماسح الضوئي", Toast.LENGTH_LONG).show();
            return false;
        }

        if (  binding.textView3.getText().toString().isEmpty()) {
            binding.textView3.setError("من فضلك استخدم الماسح الضوئي");
            return false;
        }

        if (  binding.textView2.getText().toString().isEmpty()) {
            binding.textView2.setError("من فضلك استخدم الماسح الضوئي");
            return false;
        }

        if (  binding.newPrice.getText().toString().isEmpty()) {
            binding.newPrice.setError("ادخل السعر الجديد");
            return false;
        }



        {
            return true;
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if(e.getAction()==KeyEvent.ACTION_DOWN){
            // getItem(e.toString().trim());
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("TAG","dispatchKeyEvent: "+e.toString());
            char pressedKey = (char) e.getUnicodeChar();
            barcode += pressedKey;
        }
        if (e.getAction()==KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            // Toast.makeText(getApplicationContext(),
            //                 "barcode--->>>" + barcode, Toast.LENGTH_LONG)
            //         .show();
            Toast.makeText(this, barcode.trim(), Toast.LENGTH_SHORT).show();
            //   textview.setText(barcode.trim());
            Log.d("TAG",barcode.trim());
            getItem(barcode.trim());
            barcode="";

        }

        return super.dispatchKeyEvent(e);
    }
    public void getItem(String ss){
        Single s= RetrofItItems.getInstance().getApiCalls().getItemsQr(ss)
                . subscribeOn(Schedulers.io())
                . observeOn(AndroidSchedulers.mainThread());
        SingleObserver<ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull ItemsModel itemsModel) {
                // Toast.makeText(UpdateProductActivity .this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();


                // binding.textView8.setText(itemsModel.getItemName());
                // binding.textView3.setText(itemsModel.getBarcode());
                // binding.textView2.setText(itemsModel.getSelling_Price()+"");

                if (itemsModel.getState()==200) {
                    binding.textView8.setText(itemsModel.getItemName());
                    binding.textView3.setText(itemsModel.getBarcode());
                    binding.textView2.setText(itemsModel.getSelling_Price()+"");
                }else {
                    Toast.makeText(UpdateProductActivity.this,  itemsModel.getMessage()+"المنتج غير موجود", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(UpdateProductActivity .this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        s.subscribe(singleObserver);

    }
}