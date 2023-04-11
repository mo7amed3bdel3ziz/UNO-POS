package com.example.androidcashier.ui.invoice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcashier.OnClicCuantaty;
import com.example.androidcashier.R;
import com.example.androidcashier.adabters.AdabterInvoice;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.SetBillModel;
import com.example.androidcashier.pojo.SetBillRutern;
import com.example.androidcashier.pojo.authModel.ModelStatMg;
import com.example.androidcashier.ui.scanner.capture;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReternActivity extends AppCompatActivity {
    TextView textview;
    ArrayList<ReturnModel> arrayList;
    ArrayList<ReturnModel> list=new ArrayList<>();
    Dialog dialog;
    RetuenAdabter adabter;
    RecyclerView retuenRecycler;
    TextView farora,Totals;
    ImageButton btn_qr;
    Button payWay,SearcheCode;
    String barcode = "";
    RadioButton radiosaling,radioReturn;
    // ItemTouchHelper helper;
    ProgressDialog progressDoalog;
    String macAddress;
    int dataDoc;
   private final static int rutern=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retern);

        retuenRecycler=findViewById(R.id.billRecycler1);
        farora=findViewById(R.id.farora1);
        Totals=findViewById(R.id.Totals1);
        payWay=findViewById(R.id.payWay1);
        btn_qr=findViewById(R.id.btn_qr1);
        textview=findViewById(R.id.testView1);


        // radioReturn=findViewById(R.id.radioReturn);
        // radiosaling=findViewById(R.id.radiosaling);
        SearcheCode=findViewById(R.id.SearcheCode1);
        //  Intent x=getIntent().getExtras();
        //  int r=2;
        macAddress =
                android.provider.Settings.Secure.
                        getString(this.getApplicationContext().
                                getContentResolver(), "android_id");

        // Observable.create(new ObservableOnSubscribe<Object>() {
        //     @Override
        //     public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {

        //         textview.addTextChangedListener(new TextWatcher() {
        //             @Override
        //             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //             }

        //             @Override
        //             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //                 if (charSequence!=null&&charSequence.length()!=0){
        //                     emitter.onNext(charSequence);

        //                 }
        //             }

        //             @Override
        //             public void afterTextChanged(Editable editable) {

        //             }
        //         });
        //     }
        // }).debounce(1, TimeUnit.SECONDS)
        //         //.doOnNext(s->Log.d("aplsa",s.toString()))
        //         .subscribe(s->

        //                 getItem(s.toString().trim()) );

    //    dataDoc = getIntent().getExtras().getInt("Stat",1);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@androidx.annotation.NonNull RecyclerView recyclerView, @androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull RecyclerView.ViewHolder target) {
                //  Toast.makeText(ItemsActivity.this, "Swipe to delete", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(retuenRecycler,"Deleted item",Snackbar.LENGTH_SHORT).setAction(list.get(viewHolder.getAdapterPosition()).getItemName(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
                list.remove(viewHolder.getAdapterPosition());
                adabter.notifyDataSetChanged();
                Double xx=0.0;
                for (int x=0;x<list.size();x++){
                    xx=xx+list.get(x).getBalanc();
                }
                // Double  sa =  Double.valueOf(Totals.getText().toString().trim());
                // Double xz=sa+s;
                // Totals.setText(xz+"");
                Totals.setText(xx+"");
                adabter.notifyItemInserted(list.size() - 1);
                retuenRecycler.scrollToPosition(list.size());
                retuenRecycler.setAdapter(adabter);

            }
        }).attachToRecyclerView(retuenRecycler);
        SearcheCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem( textview.getText().toString());

            }
        });


        //  radioReturn.setVisibility(View.GONE);
        //  radiosaling.setVisibility(View.GONE);
        //  if(radioReturn.isChecked()){
        //      Toast.makeText(this, "مرتجع", Toast.LENGTH_SHORT).show();
        //  }else {
        //      Toast.makeText(this, "بيع", Toast.LENGTH_SHORT).show();

        //  }

        // textview.setVisibility(View.GONE);

        payWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (list.isEmpty()){

                    Toast.makeText(ReternActivity.this, "", Toast.LENGTH_SHORT).show();
                }else {
                    dialog = new Dialog(ReternActivity.this);

                    // set custom dialog
                    dialog.setContentView(R.layout.chos_itemse);
                    //  dialog.setContentView(R.layout.chos_itemse);

                    // set custom height and width
                    dialog.getWindow().setLayout(600, 1350);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();
                    Button payWayAndRecordInvoce = dialog.findViewById(R.id.payWayAndRecordInvoce);
                    TextView Totalse = dialog.findViewById(R.id.Totalse);
                    TextView Totalss = dialog.findViewById(R.id.Totalss);
                    Totalss.setText(Totals.getText().toString() + " LEُ");

                    payWayAndRecordInvoce.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (list.isEmpty()) {

                                Toast.makeText(ReternActivity.this, "", Toast.LENGTH_SHORT).show();
                            } else {


                                progressDoalog = new ProgressDialog(ReternActivity.this);
                                progressDoalog.setMax(100);
                                progressDoalog.setMessage("Its loading....");
                                progressDoalog.setTitle(" جارى تسجيل الفاتوره ");
                                //    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDoalog.show();
                                //   new Thread(new Runnable() {
                                //       @Override
                                //       public void run() {
                                //           try {
                                //               while (progressDoalog.getProgress() <= progressDoalog
                                //                       .getMax()) {
                                //                   Thread.sleep(200);
                                //                   handle.sendMessage(handle.obtainMessage());
                                //                   if (progressDoalog.getProgress() == progressDoalog
                                //                           .getMax()) {
                                //                       progressDoalog.dismiss();
                                //                   }
                                //               }
                                //           } catch (Exception e) {
                                //               e.printStackTrace();
                                //           }
                                //       }
                                //   }
                                //   ).start();
                                if (!list.isEmpty()) {
                                    Toast.makeText(ReternActivity.this, "test", Toast.LENGTH_SHORT).show();
                                    SetBillRutern setBillModel=new SetBillRutern("1",macAddress,list);
                                    Single s = RetrofItItems.getInstance().getApiCalls().senReturn(setBillModel)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread());
                                    SingleObserver<ModelStatMg> singleObserver = new SingleObserver<ModelStatMg>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {
                                        }

                                        @Override
                                        public void onSuccess(@NonNull ModelStatMg c) {
                                            progressDoalog.dismiss();
                                            dialog.dismiss();
                                            dialog = new Dialog(ReternActivity.this);
                                            // set custom dialog
                                            dialog.setContentView(R.layout.chos_items);
                                            //  dialog.setContentView(R.layout.chos_itemse);

                                            // set custom height and width
                                            dialog.getWindow().setLayout(700, 1350);

                                            // set transparent background
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            // ImageView imageView2=dialog.findViewById(R.id.imageView2);

                                            // show dialog
                                            dialog.show();

                                            ImageView imageView2 = dialog.findViewById(R.id.imageView2);
                                            TextView textView13 = dialog.findViewById(R.id.textView13);
                                            textView13.setText(" اجمالى: " + c.getTotalBill() + " " + "ج.م" + "\n" + c.getMessage());

                                            // Button payWayAndRecordInvoce=dialog.findViewById(R.id.payWayAndRecordInvoce);
                                            Toast.makeText(ReternActivity.this, c.getMessage(), Toast.LENGTH_SHORT).show();
                                            //  Double xx = 0.0;
                                            //  for (int x = 0; x < list.size(); x++) {
                                            //      xx = xx + list.get(x).getBalanc();
                                            //  }

                                            //ItemsModel s=new ItemsModel();
                                            //   list.remove(null);
                                            //  adabter.notifyItemInserted(list.size() - 1);
                                            //  billRecycler.scrollToPosition(list.size());
                                            //   adabter.setList(list);
                                            //
                                            imageView2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent i = new Intent(ReternActivity.this, ReternActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            });
                                            //   billRecycler.setAdapter(adabter);
                                            // Intent i=new Intent(ItemsActivity.this,ItemsActivity.class);
                                            // startActivity(i);
                                            Totalse.setText(" اجمالى: " + c.getTotalBill() + " " + "ج.م" + "\n" + c.getMessage());
                                            // Toast.makeText(ItemsActivity.this,xx+"", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            progressDoalog.dismiss();
                                            Toast.makeText(ReternActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    s.subscribe(singleObserver);
                                }
                            } //Toast.makeText(ItemsActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //  Single s= RetrofItItems.getInstance().getApiCalls().getItemsQr("622300876366")
        //          . subscribeOn(Schedulers.computation())
        //          . observeOn(AndroidSchedulers.mainThread());
        //  SingleObserver <ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
        //      @Override
        //      public void onSubscribe(@NonNull Disposable d) {

        //      }

        //      @Override
        //      public void onSuccess(@NonNull ItemsModel itemsModel) {
        //          Toast.makeText(ItemsActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();

        //      }

        //      @Override
        //      public void onError(@NonNull Throwable e) {
        //          Toast.makeText(ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        //      }
        //  };
        //  s.subscribe(singleObserver);

        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(
                        ReternActivity.this
                );
                intentIntegrator.setPrompt("For flash use Volump up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.initiateScan();
            }
        });
        arrayList=new ArrayList<>();
        // list.add(new ItemsModel(5,"Name()","adaBarcode()"));

        adabter=new RetuenAdabter(this, new OnClicCuantaty() {
            @Override
            public void cliceCuantaty(int pos, Double s) {
                Double xx=0.0;
                for (int x=0;x<list.size();x++){
                    xx=xx+list.get(x).getBalanc();
                }
                // Double  sa =  Double.valueOf(Totals.getText().toString().trim());
                // Double xz=sa+s;
                // Totals.setText(xz+"");
                Totals.setText(xx+"");
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adabter.setList(list);
        retuenRecycler.setLayoutManager(linearLayoutManager);
        retuenRecycler.setAdapter(adabter);

        //  Observable TotalItems= RetrofItItems.getInstance().getApiCalls().getItems()
        //          . subscribeOn(Schedulers.io())
        //          . observeOn(AndroidSchedulers.mainThread());

        //  @SuppressLint("WrongViewCast")
        //  Observer<Task>taskObserver=new Observer<Task>() {
        //      @Override
        //      public void onSubscribe(@NonNull Disposable d) {

        //      }

        //      @Override
        //      public void onNext(@NonNull Task task) {


        //          arrayList=task.getItems();
        //          Log.d("logggg",task.getItems().get(0).ItemName);
        //      }

        //      @Override
        //      public void onError(@NonNull Throwable e) {

        //          Toast.makeText(ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        //      }

        //      @Override
        //      public void onComplete() {
        //          Toast.makeText(ItemsActivity.this, "complet", Toast.LENGTH_SHORT).show();

        //      }
        //  };
        //  TotalItems.subscribe(taskObserver);
        // assign variable


        // initialize array list
        //  arrayList=new ArrayList<>();

        //  // set value in array list
        //  arrayList.add("DSA Self Paced");
        //  arrayList.add("Complete Interview Prep");
        //  arrayList.add("Amazon SDE Test Series");
        //  arrayList.add("Compiler Design");
        //  arrayList.add("Git & Github");
        //  arrayList.add("Python foundation");
        //  arrayList.add("Operating systems");
        //  arrayList.add("Theory of Computation");

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize dialog
                // dialog=new Dialog(ItemsActivity.this);

                // // set custom dialog
                // dialog.setContentView(R.layout.dialog_searchable_spinner);

                // // set custom height and width
                // dialog.getWindow().setLayout(1100,1100);

                // // set transparent background
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // // show dialog
                // dialog.show();

                // // Initialize and assign variable
                // EditText editText=dialog.findViewById(R.id.edit_text);
                // ListView listView=dialog.findViewById(R.id.list_view);

                // // Initialize array adapter

                ///  ArrayAdapter<ItemsModel> adapterlist=new ArrayAdapter<ItemsModel>(ItemsActivity.this, android.R.layout.simple_list_item_1,arrayList);
                // ArrayAdapter<ItemsModel> adapterlist=new ArrayAdapter<ItemsModel>(ItemsActivity.this, android.R.layout.simple_list_item_1,arrayList);

                // // set adapter
                // listView.setAdapter(adapterlist);

                // editText.addTextChangedListener(new TextWatcher() {
                //     @Override
                //     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //       //  Toast.makeText(ItemsActivity.this, s+"s34", Toast.LENGTH_SHORT).show();

                //     }

                //     @Override
                //     public void onTextChanged(CharSequence s, int start, int before, int count) {
                //         adapterlist.getFilter().filter(s);
                //       //  adapterlist.filterList()

                //         Toast.makeText(ItemsActivity.this, s, Toast.LENGTH_SHORT).show();
                //     }

                //     @Override
                //     public void afterTextChanged(Editable s) {
                //       //  Toast.makeText(ItemsActivity.this, s+"ss", Toast.LENGTH_SHORT).show();

                //     }
                // });

                // listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //     @Override
                //     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //         // when item selected from list
                //         // set selected item on textView
                //         textview.setText(adapterlist.getItem(position).getItemName());
                //        // Toast.makeText(ItemsActivity.this,adapter.getItem(position).Record_ID, Toast.LENGTH_SHORT).show();
                //         list.add(new ItemsModel(adapterlist.getItem(position).getSelling_Price(),adapterlist.getItem(position).getItemName(),adapterlist.getItem(position).getBarcode()));
                //         // Dismiss dialog
                //       //  adabter.setList(list);
                //         adabter.notifyItemInserted(list.size()-1);
                //         billRecycler.scrollToPosition(list.size());
                //         billRecycler.setAdapter(adabter);
                //         dialog.dismiss();
                //     }
                // });
            }
        });

        farora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Intent i=new Intent(ItemsActivity.this,AddItemActivity.class);
                //  startActivity(i);


                //   if (!list.isEmpty()) {
                //       Toast.makeText(ItemsActivity.this, "test", Toast.LENGTH_SHORT).show();
                //       Single s = RetrofItItems.getInstance().getApiCalls().sentInvoice(list)
                //               .subscribeOn(Schedulers.io())
                //               .observeOn(AndroidSchedulers.mainThread());
                //       SingleObserver<ModelStatMg> singleObserver = new SingleObserver<ModelStatMg>() {
                //           @Override
                //           public void onSubscribe(@NonNull Disposable d) {

                //           }

                //           @Override
                //           public void onSuccess(@NonNull ModelStatMg c) {
                //               Toast.makeText(ItemsActivity.this, c.getMessage(), Toast.LENGTH_SHORT).show();

                //               Double xx = 0.0;
                //               for (int x = 0; x < list.size(); x++) {
                //                   xx = xx + list.get(x).getBalanc();
                //               }

                //               Totals.setText(c.getTotalBill()+" "+"ج.م");
                //               Toast.makeText(ItemsActivity.this,xx+"", Toast.LENGTH_SHORT).show();

                //           }

                //           @Override
                //           public void onError(@NonNull Throwable e) {
                //               Toast.makeText(ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                //           }
                //       };
                //       s.subscribe(singleObserver);
                //   }


                //  Double d=  list.get(0).getBalanc();
                //  Double dx=  list.get(1).getBalanc();
                //    Toast.makeText(ItemsActivity.this, dx+""+d+"", Toast.LENGTH_SHORT).show();
                //  Double xx=0.0;
                //  for (int x=0;x<list.size();x++){
                //      xx=xx+list.get(x).getBalanc();
                //  }
                //  Toast.makeText(ItemsActivity.this, xx+"", Toast.LENGTH_SHORT).show();
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
            SingleObserver <ItemsModel> singleObserver=new SingleObserver<ItemsModel>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ItemsModel itemsModel) {
                    if (itemsModel.getState()==200) {
                        Toast.makeText(ReternActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();

                        list.add(new ReturnModel(itemsModel.getSelling_Price(),
                                itemsModel.getItemName(), itemsModel.getBarcode()));
                        //   Double s =0.0;
                        Double s = Double.valueOf(Totals.getText().toString().trim());
                        Double xz = s + itemsModel.getSelling_Price();
                        Totals.setText(xz + "");
                        adabter.notifyItemInserted(list.size() - 1);
                        retuenRecycler.scrollToPosition(list.size());
                        retuenRecycler.setAdapter(adabter);
                    }else {
                        Toast.makeText(ReternActivity.this,  itemsModel.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Toast.makeText(ReternActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };
            s.subscribe(singleObserver);
            // Toast.makeText(ItemsActivity.this, intentIntegrator.getContents(), Toast.LENGTH_SHORT).show();

        }

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
                //    textview.setText("");
                if (itemsModel.getState()==200) {
                    Toast.makeText(ReternActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();

                    list.add(new ReturnModel(itemsModel.getSelling_Price(),
                            itemsModel.getItemName(), itemsModel.getBarcode()));
                    //   Double s =0.0;
                    Double s = Double.valueOf(Totals.getText().toString().trim());
                    Double xz = s + itemsModel.getSelling_Price();
                    Totals.setText(xz + "");
                    adabter.notifyItemInserted(list.size() - 1);
                    retuenRecycler.scrollToPosition(list.size());
                    retuenRecycler.setAdapter(adabter);
                }else {
                    Toast.makeText(ReternActivity.this,  itemsModel.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(ReternActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        s.subscribe(singleObserver);

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
            //String curText =  textview.getText();
            textview.setText("");
            textview.clearFocus();

        }

        return super.dispatchKeyEvent(e);
    }

}