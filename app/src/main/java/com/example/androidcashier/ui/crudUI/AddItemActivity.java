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
import com.example.androidcashier.databinding.ActivityAddItemBinding;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.crudOperationProduct.AddProductModel;
import com.example.androidcashier.ui.scanner.capture;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {
ActivityAddItemBinding binding;
String  barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item);

        binding.btnQradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(
                        AddItemActivity.this
                );
                intentIntegrator.setPrompt("For flash use Volump up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.initiateScan();
            }
        });
        binding.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isvaled()==true){
               Double price=   Double.valueOf(binding.textView2.getText().toString().trim()) ;
                String Qr=  binding.textView3.getText().toString().trim();
                String name=binding.textView8.getText().toString().trim();
                    AddProductModel add=new AddProductModel(price,name,Qr);
                    Single ss = RetrofItItems.getInstance().getApiCalls().addProduct(add)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                    SingleObserver<ItemsModel> singleObservers = new SingleObserver<ItemsModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull ItemsModel itemsModel) {
                            Toast.makeText(AddItemActivity.this,itemsModel.getSelling_Price() +"تم بنجاح",Toast.LENGTH_SHORT).show();
                            Toast.makeText(AddItemActivity.this, itemsModel.getItemName() + "تم بنجاح", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AddItemActivity.this, itemsModel.getMessage() + "تم بنجاح", Toast.LENGTH_SHORT).show();


                      //      binding.textView3.setText(itemsModel.getBarcode());
                             binding.textView8.setText("");
                             binding.textView3.setText("");
                             binding.textView2.setText("");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(AddItemActivity.this, e.getMessage()+"on Error", Toast.LENGTH_SHORT).show();

                        }
                    };
                    ss.subscribe(singleObservers);
                }
            }
        });
    }
    public Boolean isvaled() {


        if (  binding.textView8.getText().toString().isEmpty()) {
            binding.textView8.setError("ادخل اسم المنتج");
          //  Toast.makeText(UpdateProductActivity.this, "من فضلك استخدم الماسح الضوئي", Toast.LENGTH_LONG).show();
            return false;
        }

        if (  binding.textView3.getText().toString().isEmpty()) {
            binding.textView3.setError("من فضلك استخدم الماسح الضوئي");
            return false;
        }

        if (  binding.textView2.getText().toString().isEmpty()) {
            binding.textView2.setError("سعر  المنتج ");
            return false;
        }

        {
            return true;
        }
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
            SingleObserver <ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ItemsModel itemsModel) {
                  // Toast.makeText(AddItemActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();
                  // binding.textView3.setText(itemsModel.getBarcode());
                    if (itemsModel.getState()==200) {
                      //  binding.textView8.setText(itemsModel.getItemName());
                      //  binding.textView3.setText(itemsModel.getBarcode());
                      //  binding.textView2.setText(itemsModel.getSelling_Price()+"");
                        Toast.makeText(AddItemActivity.this, "المنتج موجود بالفعل", Toast.LENGTH_SHORT).show();
                    }else if(itemsModel.getState()==400){

                        binding.textView3.setText(intentIntegrator.getContents());
                        //Toast.makeText(UpdateProductActivity.this,  itemsModel.getMessage()+"المنتج غير موجود", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Toast.makeText(AddItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };
            s.subscribe(singleObserver);
            // Toast.makeText(ItemsActivity.this, intentIntegrator.getContents(), Toast.LENGTH_SHORT).show();

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
            getItem(barcode);
    //        getItem(barcode.trim());
            barcode="";

        }

        return super.dispatchKeyEvent(e);
    }
    public void getItem(String code){
        Single s= RetrofItItems.getInstance().getApiCalls().getItemsQr(code)
                . subscribeOn(Schedulers.io())
                . observeOn(AndroidSchedulers.mainThread());
        SingleObserver <ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull ItemsModel itemsModel) {
                // Toast.makeText(AddItemActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();
                // binding.textView3.setText(itemsModel.getBarcode());
                if (itemsModel.getState()==200) {
                    //  binding.textView8.setText(itemsModel.getItemName());
                    //  binding.textView3.setText(itemsModel.getBarcode());
                    //  binding.textView2.setText(itemsModel.getSelling_Price()+"");
                    Toast.makeText(AddItemActivity.this, "المنتج موجود بالفعل", Toast.LENGTH_SHORT).show();
                }else if(itemsModel.getState()==400) {

                    binding.textView3.setText(code.replace("null",""));
                    //Toast.makeText(UpdateProductActivity.this,  itemsModel.getMessage()+"المنتج غير موجود", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(AddItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        s.subscribe(singleObserver);
    }
}