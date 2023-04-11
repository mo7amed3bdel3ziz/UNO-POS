package com.example.androidcashier.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcashier.R;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.network.RetrofitRefranc;
import com.example.androidcashier.pojo.authModel.LoginModel;
import com.example.androidcashier.ui.SelectionActivity;
import com.example.androidcashier.ui.invoice.ItemsActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Login extends AppCompatActivity {
TextView textView14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView14=findViewById(R.id.textView14);

        String macAddress =
                android.provider.Settings.Secure.
                        getString(getBaseContext().getContentResolver(), "android_id");


        Single s=   RetrofItItems.getInstance().getApiCalls().login(macAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        SingleObserver<LoginModel> observer=new SingleObserver<LoginModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull LoginModel loginModel) {
                Toast.makeText(Login.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();

                if (loginModel.getState()==200){
                    textView14.setText(loginModel.getMessage());
                    // Toast.makeText(MainActivity.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(MainActivity.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    // loginModel.getMessage();
                    //in app\
                    //    int s=    loginModel.getEmployeeID();
                    //  Toast.makeText(MainActivity.this, loginModel.getEmployeeID(), Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Login.this, SelectionActivity.class);
                   // i.putExtra("EmployeeID",loginModel.getEmployeeID());
                    //   i.putExtra("EmployeeID",s);
                    startActivity(i);
                    finish();
                }else if (loginModel.getState()==1){
                    //progras
                    Toast.makeText(Login.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    // loginModel.getMessage();
                    textView14.setText(loginModel.getMessage());

                }else if(loginModel.getState()==2){
                    textView14.setText(loginModel.getMessage());
                    Toast.makeText(Login.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    // loginModel.getMessage();
                    Intent i=new Intent(getBaseContext(), CreateAccount.class);
                    startActivity(i);
                    finish();
                }



            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        s.subscribe(observer);

        //   Thread background = new Thread() {
        //       public void run() {
        //           try {
        //               // Thread will sleep for 5 seconds
        //               sleep(3 * 1000);

        //               Intent i=new Intent(getBaseContext(), LoginActivity.class);
        //               startActivity(i);
        //               finish();

        //           } catch (Exception e) {
        //           }
        //       }
        //   };
        //   // start thread
        //   background.start();

    }
}