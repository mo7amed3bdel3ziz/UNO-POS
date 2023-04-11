package com.example.androidcashier.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidcashier.R;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.network.RetrofitRefranc;
import com.example.androidcashier.pojo.authModel.CreateACC;
import com.example.androidcashier.pojo.authModel.LoginModel;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateAccount extends AppCompatActivity {
    Button confirm;
    EditText nationalID,nameManagerCreateAcc,nameCreateAcc,emailAddress,phoneCreateAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        confirm=findViewById(R.id.confirm);

     //  nationalID          =findViewById(R.id.nationalID);
        nameManagerCreateAcc=findViewById(R.id.nameManagerCreateAcc);
        nameCreateAcc       =findViewById(R.id.nameCreateAcc);
     //   emailAddress         =findViewById(R.id.emailAddress);
        phoneCreateAcc      =findViewById(R.id.phoneCreateAcc);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent i=new Intent(LoginActivity.this, HomeMain.class);
                // startActivity(i);
                createAcc();
            }
        });
    }
    public void createAcc(){
        String  nameManagerCreateAccs =    nameManagerCreateAcc.getText().toString();
        String   nameCreateAccs         =    nameCreateAcc.getText().toString();
        String    phoneCreateAccs       =    phoneCreateAcc.getText().toString();




        String macAddress =
                android.provider.Settings.Secure.
                        getString(this.getApplicationContext().getContentResolver(), "android_id");


        //    Toast.makeText(LoginActivity.this, macAddress, Toast.LENGTH_SHORT).show();


        HashMap<String,String> Login=new HashMap<>();
        Login.put("ClientName",  nameCreateAccs ) ;


        Login.put("AndroidID",      macAddress  );


        Login.put("CompanyName",  nameManagerCreateAccs ) ;

        Login.put("Phone", phoneCreateAccs )  ;





        Log.d("testAtt",macAddress);
        Log.d("testAtt",nameCreateAccs);

        Log.d("testAtt",phoneCreateAccs);
        Log.d("testAtt",nameManagerCreateAccs);

    //   CreateACC acc=new CreateACC();



        Single create=    RetrofItItems.getInstance().getApiCalls().creatAcc(Login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        SingleObserver<LoginModel> observerCreate=new SingleObserver<LoginModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull LoginModel loginModel) {
                Log.d("testAtt",loginModel.getMessage());
                Toast.makeText(CreateAccount.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();

                if (loginModel.getState()==200){
                    Toast.makeText(CreateAccount.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(CreateAccount.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    // loginModel.getMessage();
                    //in app

                }else if (loginModel.getState()==400){
                    //progras
                    Toast.makeText(CreateAccount.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(CreateAccount.this,loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    // loginModel.getMessage();


                }



            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("testAtt",e.getMessage());

                Toast.makeText(CreateAccount.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        create.subscribe(observerCreate);

    }
}