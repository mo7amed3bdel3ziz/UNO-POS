package com.example.androidcashier.ui.invoice;

import static java.security.spec.MGF1ParameterSpec.SHA256;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcashier.OnClicCuantaty;
import com.example.androidcashier.R;
import com.example.androidcashier.adabters.AdabterInvoice;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.Bluetoothprint.PrintBluetooth;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.SetBillModel;
import com.example.androidcashier.pojo.authModel.ModelStatMg;
import com.example.androidcashier.ui.scanner.capture;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemsActivity extends AppCompatActivity {
    TextView textview;
    ArrayList<ItemsModel> arrayList;
    ArrayList<ItemsModel> list = new ArrayList<>();
    Dialog dialog;
    AdabterInvoice adabter;
    RecyclerView billRecycler;
    OutputStream mmOutputStream;
    TextView farora, Totals;
    ImageButton btn_qr;
    Button payWay, SearcheCode;
    String barcode = "";
    RadioButton radiosaling, radioReturn;
    // ItemTouchHelper helper;
    ProgressDialog progressDoalog;
    int dataDoc;
    //////////////
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    String printerMsg = "";

    InputStream mmInputStream;
    Thread workerThread;
    int BluetoothStutes = 0;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;

    String Print_pill_Return = "";
    String pill_Return = "";
    String pill = "";
    String Printpill = "";
    volatile boolean stopWorker;
    private final static int sale = 1;
    String macAddress;
    PrintBluetooth printBT = new PrintBluetooth();
    EditText editText;
    ImageView imageView;
    String[] list_produk = {"Food", "Non Food", "take away", "water", "Makanan Segar", "Stationary"};
    TextInputEditText input_produk;
    EditText in_printer_id;
    Button print_struk;
    RadioButton radioButtonYa, radioButtonTidak;
    TextView textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        billRecycler = findViewById(R.id.billRecycler);
        farora = findViewById(R.id.farora);
        Totals = findViewById(R.id.Totals);
        payWay = findViewById(R.id.payWay);
        btn_qr = findViewById(R.id.btn_qr);
        textview = findViewById(R.id.testView);
        textView7 = findViewById(R.id.textView7);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PrintBluetooth.printer_id = "Printer001";
                java.util.Date today = new java.util.Date();
                SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                String print_date = format_date.format(today);
                String status_barang = "";
                //    if (radioButtonYa.isChecked()){
                //        status_barang = " vegetarian";
                //    }
                //    else if (radioButtonTidak.isChecked()){
                //        status_barang = "meet";
                //    }
                try {
                    printBT.findBT();
                    printBT.openBT();

                    printBT.printQrCode(textAsBitmap(setpill(), 520, 28));
                    printBT.printQrCode(print("dk"));

                    //  printBT.closeBT();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix;

                {
                    try {
                        //   if ( c.getUrl()==null&&c.getUrl().isEmpty()&& c.getUrl().length()==0){}else {}
                        bitMatrix = multiFormatWriter.encode("editText.getText().toString()",
                                BarcodeFormat.CODE_128,
                                300,
                                300);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);

                        try {
                            printBT.findBT();
                            printBT.openBT();
                            printBT.printQrCode(print("dk"));
                            printBT.closeBT();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        //  list.add(new ItemsModel(20.5,"مياه اقوى معدانيه",20.5));
        //  list.add(new ItemsModel(20.5,"مياه اقوى معدانيه",20.5));
        //  list.add(new ItemsModel(20.5,"مياه اقوى معدانيه",20.5));


        //  findBT();
        macAddress =
                android.provider.Settings.Secure.
                        getString(this.getApplicationContext().
                                getContentResolver(), "android_id");
        // radioReturn=findViewById(R.id.radioReturn);
        // radiosaling=findViewById(R.id.radiosaling);
        SearcheCode = findViewById(R.id.SearcheCode);


        //  dataDoc = getIntent().getExtras().getInt("Stat",1);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@androidx.annotation.NonNull RecyclerView recyclerView, @androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull RecyclerView.ViewHolder target) {
                //  Toast.makeText(ItemsActivity.this, "Swipe to delete", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(billRecycler, "Deleted item", Snackbar.LENGTH_SHORT).setAction(list.get(viewHolder.getAdapterPosition()).getItemName(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
                list.remove(viewHolder.getAdapterPosition());
                adabter.notifyDataSetChanged();
                Double xx = 0.0;
                for (int x = 0; x < list.size(); x++) {
                    xx = xx + list.get(x).getBalanc();
                }
                // Double  sa =  Double.valueOf(Totals.getText().toString().trim());
                // Double xz=sa+s;
                // Totals.setText(xz+"");
                Totals.setText(xx + "");
                adabter.notifyItemInserted(list.size() - 1);
                billRecycler.scrollToPosition(list.size());
                billRecycler.setAdapter(adabter);

            }
        }).attachToRecyclerView(billRecycler);
        SearcheCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(textview.getText().toString());

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
                //  OnClikBtnAddPill(view);
                if (list.isEmpty()) {

                    Toast.makeText(ItemsActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    dialog = new Dialog(ItemsActivity.this);

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

                                Toast.makeText(ItemsActivity.this, "", Toast.LENGTH_SHORT).show();
                            } else {


                                progressDoalog = new ProgressDialog(ItemsActivity.this);
                                progressDoalog.setMax(100);
                                progressDoalog.setMessage("Its loading....");
                                progressDoalog.setTitle(" جارى تسجيل الفاتوره ");
                                //    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDoalog.show();

                                if (!list.isEmpty()) {
                                    Toast.makeText(ItemsActivity.this, "test", Toast.LENGTH_SHORT).show();

                                    SetBillModel bill2 = new SetBillModel("1", macAddress, list);
                                    Single s = RetrofItItems.getInstance().getApiCalls().sentInvoice(bill2)
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
                                            dialog = new Dialog(ItemsActivity.this);
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
                                            Toast.makeText(ItemsActivity.this, c.getMessage(), Toast.LENGTH_SHORT).show();
                                            //  Double xx = 0.0;
                                            //  for (int x = 0; x < list.size(); x++) {
                                            //      xx = xx + list.get(x).getBalanc();
                                            //  }

                                            //ItemsModel s=new ItemsModel();
                                            //   list.remove(null);
                                            //  adabter.notifyItemInserted(list.size() - 1);
                                            //  billRecycler.scrollToPosition(list.size());
                                            //   adabter.setList(list);

                                            PrintBluetooth.printer_id = "Printer001";
                                            java.util.Date today = new java.util.Date();
                                            SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                                            String print_date = format_date.format(today);
                                            String status_barang = "";
                                            //    if (radioButtonYa.isChecked()){
                                            //        status_barang = " vegetarian";
                                            //    }
                                            //    else if (radioButtonTidak.isChecked()){
                                            //        status_barang = "meet";
                                            //    }
                                            try {
                                                printBT.findBT();
                                                printBT.openBT();

                                                printBT.printQrCode(textAsBitmap(setpill(), 520, 28));

                                                //  printBT.closeBT();
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }

                                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                            BitMatrix bitMatrix;

                                            {
                                                try {
                                                    //   if ( c.getUrl()==null&&c.getUrl().isEmpty()&& c.getUrl().length()==0){}else {}
                                                    bitMatrix = multiFormatWriter.encode("editText.getText().toString()",
                                                            BarcodeFormat.CODE_128,
                                                            300,
                                                            300);
                                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                      imageView.setImageBitmap(bitmap);

                                                    try {
                                                        printBT.findBT();
                                                        printBT.openBT();
                                                        printBT.printQrCode(bitmap);
                                                        printBT.closeBT();
                                                    } catch (IOException ex) {
                                                        ex.printStackTrace();
                                                    }

                                                } catch (WriterException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                            //
                                            imageView2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent i = new Intent(ItemsActivity.this, ItemsActivity.class);
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
                                            PrintBluetooth.printer_id = "Printer001";
                                            java.util.Date today = new java.util.Date();
                                            SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                                            String print_date = format_date.format(today);
                                            String status_barang = "";
                                            //    if (radioButtonYa.isChecked()){
                                            //        status_barang = " vegetarian";
                                            //    }
                                            //    else if (radioButtonTidak.isChecked()){
                                            //        status_barang = "meet";
                                            //    }
                                            try {
                                                printBT.findBT();
                                                printBT.openBT();

                                                printBT.printQrCode(textAsBitmap(setpill(), 520, 28));
                                                // printBT.printQrCode(textAsBitmap(setpill(),520,28));

                                                //  printBT.closeBT();
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }

                                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                            BitMatrix bitMatrix;

                                            {
                                                try {
                                                    bitMatrix = multiFormatWriter.encode("editText",
                                                            BarcodeFormat.QR_CODE,
                                                            300,
                                                            300);
                                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                    //  imageView.setImageBitmap(bitmap);

                                                    try {
                                                        printBT.findBT();
                                                        printBT.openBT();
                                                        printBT.printQrCode(bitmap);
                                                        printBT.closeBT();
                                                    } catch (IOException ex) {
                                                        ex.printStackTrace();
                                                    }

                                                } catch (WriterException ea) {
                                                    ea.printStackTrace();
                                                }

                                            }
                                            progressDoalog.dismiss();
                                            Toast.makeText(ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        ItemsActivity.this
                );
                intentIntegrator.setPrompt("For flash use Volump up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.initiateScan();
            }
        });
        arrayList = new ArrayList<>();
        // list.add(new ItemsModel(5,"Name()","adaBarcode()"));

        adabter = new AdabterInvoice(this, new OnClicCuantaty() {
            @Override
            public void cliceCuantaty(int pos, Double s) {
                Double xx = 0.0;
                for (int x = 0; x < list.size(); x++) {
                    xx = xx + list.get(x).getBalanc();
                }
                // Double  sa =  Double.valueOf(Totals.getText().toString().trim());
                // Double xz=sa+s;
                // Totals.setText(xz+"");
                Totals.setText(xx + "");
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adabter.setList(list);
        billRecycler.setLayoutManager(linearLayoutManager);
        billRecycler.setAdapter(adabter);

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


            }
        });

        farora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentIntegrator = IntentIntegrator.parseActivityResult(

                requestCode, resultCode, data
        );
        if (intentIntegrator.getContents() != null) {

            Single s = RetrofItItems.getInstance().getApiCalls().getItemsQr(intentIntegrator.getContents())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            SingleObserver<ItemsModel> singleObserver = new SingleObserver<ItemsModel>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ItemsModel itemsModel) {
                    if (itemsModel.getState() == 200) {
                        Toast.makeText(ItemsActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();

                        list.add(new ItemsModel(itemsModel.getSelling_Price(),
                                itemsModel.getItemName(), itemsModel.getBarcode()));
                        //   Double s =0.0;
                        Double s = Double.valueOf(Totals.getText().toString().trim());
                        Double xz = s + itemsModel.getSelling_Price();
                        Totals.setText(xz + "");
                        adabter.notifyItemInserted(list.size() - 1);
                        billRecycler.scrollToPosition(list.size());
                        billRecycler.setAdapter(adabter);
                    } else {
                        Toast.makeText(ItemsActivity.this, itemsModel.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Toast.makeText(ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };
            s.subscribe(singleObserver);
            // Toast.makeText(ItemsActivity.this, intentIntegrator.getContents(), Toast.LENGTH_SHORT).show();

        }

    }

    public void getItem(String code) {

        Single s = RetrofItItems.getInstance().getApiCalls().getItemsQr(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        SingleObserver<ItemsModel> singleObserver = new SingleObserver<ItemsModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull ItemsModel itemsModel) {
                //    textview.setText("");
                if (itemsModel.getState() == 200) {
                    Toast.makeText(ItemsActivity.this, itemsModel.getItemName(), Toast.LENGTH_SHORT).show();

                    list.add(new ItemsModel(itemsModel.getSelling_Price(),
                            itemsModel.getItemName(), itemsModel.getBarcode()));
                    //   Double s =0.0;
                    Double s = Double.valueOf(Totals.getText().toString().trim());
                    Double xz = s + itemsModel.getSelling_Price();
                    Totals.setText(xz + "");
                    adabter.notifyItemInserted(list.size() - 1);
                    billRecycler.scrollToPosition(list.size());
                    billRecycler.setAdapter(adabter);
                } else {
                    Toast.makeText(ItemsActivity.this, itemsModel.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        s.subscribe(singleObserver);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (e.getAction() == KeyEvent.ACTION_DOWN) {
            // getItem(e.toString().trim());
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("TAG", "dispatchKeyEvent: " + e.toString());
            char pressedKey = (char) e.getUnicodeChar();
            barcode += pressedKey;
        }
        if (e.getAction() == KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            // Toast.makeText(getApplicationContext(),
            //                 "barcode--->>>" + barcode, Toast.LENGTH_LONG)
            //         .show();
            Toast.makeText(this, barcode.trim(), Toast.LENGTH_SHORT).show();
            //   textview.setText(barcode.trim());
            Log.d("TAG", barcode.trim());

            getItem(barcode.trim());
            barcode = "";
            //String curText =  textview.getText();
            textview.setText("");
            textview.clearFocus();

        }

        return super.dispatchKeyEvent(e);
    }

    public String setpill() {

        StringBuffer sb = new StringBuffer();

        for (ItemsModel s : list) {
            //     String x= s.ItemName+"      "+s.contaty+""+"\n";
            //    bill= s.ItemName+"      "+s.contaty+""+"\n";
            sb.append(s.ItemName + "          " + s.contaty + "            " + s.balanc + "\n");


        }
        sb.append("\n");
        sb.append(" الاجمالى " + "             " + "0.0");

        String n1 = "    اسم المحل:   ";
        String n2 = "رقم التيليفون:  ";
        String n5 = "رقم الفاتوره :  ";
        java.util.Date today = new java.util.Date();
        SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String print_date = format_date.format(today);
        String n3 = print_date;
        String n4 = "__________________الفاتوره_________________";
        String n6 = "الصنف                           الكميه                  السعر ";
        String n7 = "______________________________________";

        String msg =
                //" "+ sb;
                n3 + "\n" + "\n" + n1 + "\n" + n2 + "\n" + n5 + "\n" + n4 + "\n" + "\n" + n6 + "\n" + "\n" + sb;


        return msg;
    }

    //    void findBT() {
//
//        try {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//            if (mBluetoothAdapter == null) {
//                //myLabel.setText("No bluetooth adapter available");
//            }
//
//            if (!mBluetoothAdapter.isEnabled()) {
//                Intent enableBluetooth = new Intent(
//                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                startActivityForResult(enableBluetooth, 0);
//            }
//
//            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
//                    .getBondedDevices();
//            if (pairedDevices.size() > 0) {
//                for (BluetoothDevice device : pairedDevices) {
//
//                    // MP300 is the name of the bluetooth printer device
//
//                    if (device.getName().equals(ContactResult.PrintrName)) {
//                        mmDevice = device;
//                        break;
//                    }
//                }
//            }
//            // myLabel.setText("Bluetooth Device Found");
//            openBT();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    void openBT() throws IOException {
//        try {
//            // Standard SerialPortService ID
//            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
//            mmSocket.connect();
//            mmOutputStream = mmSocket.getOutputStream();
//            mmInputStream = mmSocket.getInputStream();
//
//            beginListenForData();
//            BluetoothStutes=1;
//            printerMsg="الطابعة جاهزه " +
//                    "للطباعة";
//            //myLabel.setText("Bluetooth Opened");
//
//        } catch (NullPointerException e) {
//
//            e.printStackTrace();
//        } catch (Exception e) {
//
//
//            e.printStackTrace();
//
//        }
//    }
//
//    void beginListenForData() {
//        try {
//            final Handler handler = new Handler();
//
//            // This is the ASCII code for a newline character
//            final byte delimiter = 10;
//
//            stopWorker = false;
//            readBufferPosition = 0;
//            readBuffer = new byte[1024];
//
//            workerThread = new Thread(new Runnable() {
//                public void run() {
//                    while (!Thread.currentThread().isInterrupted()
//                            && !stopWorker) {
//
//                        try {
//
//                            int bytesAvailable = mmInputStream.available();
//                            if (bytesAvailable > 0) {
//                                byte[] packetBytes = new byte[bytesAvailable];
//                                mmInputStream.read(packetBytes);
//                                for (int i = 0; i < bytesAvailable; i++) {
//                                    byte b = packetBytes[i];
//                                    if (b == delimiter) {
//                                        byte[] encodedBytes = new byte[readBufferPosition];
//                                        System.arraycopy(readBuffer, 0,
//                                                encodedBytes, 0,
//                                                encodedBytes.length);
//                                        final String data = new String(
//                                                encodedBytes, "US-ASCII");
//                                        readBufferPosition = 0;
//
//                                        handler.post(new Runnable() {
//                                            public void run() {
//                                                //    myLabel.setText(data);
//                                            }
//                                        });
//                                    } else {
//                                        readBuffer[readBufferPosition++] = b;
//                                    }
//                                }
//                            }
//
//                        } catch (IOException ex) {
//                            stopWorker = true;
//                        }
//
//                    }
//                }
//            });
//
//            workerThread.start();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void OnClikBtnAddPill(final View view) {
//
//        Toast.makeText(this, "okk", Toast.LENGTH_SHORT).show();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder( ItemsActivity.this );
//     //   builder.setTitle( "رسالة تاكيد تعديل الدفع !" );
//     //   String msg ="هل انت متاكد من ان" +"\n"+"الـكـاش : " + Pill_Class.Cash.toString() +" جنية "+"\n"+ "الاجـل : " + Pill_Class.DeferredPayment.toString()  +" جنية "+"\n"+" خصم الفاتورة : " +Pill_Class.PillDiscount.toString()+" جنية " ;
//      //  builder.setMessage( msg );
//        builder.setCancelable( false );
//        builder.setPositiveButton( "نـــعــم", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //==========================
//
//                // inflate the layout of the popup window
//                LayoutInflater inflater = (LayoutInflater)
//                        getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = inflater.inflate(R.layout.pop_print, null);
//                final TextView   _Confirm =(TextView) popupView.findViewById(R.id.txt_Confirm);
//                final TextView   _Cancel =(TextView) popupView.findViewById(R.id.txt_Cancel);
//                final TextView   _Open_print =(TextView) popupView.findViewById(R.id.txt_Open_print);
//                final TextView   _Close_print =(TextView) popupView.findViewById(R.id.txt_Close_print);
//                final TextView   _txt_Msg =(TextView) popupView.findViewById(R.id.txt_Msg);
//                final TextView   _ConfirmWithPrint =(TextView) popupView.findViewById(R.id.txt_ConfirmWithPrint);
//                // create the popup window
//                _txt_Msg.setTextColor(getResources().getColor(R.color.black));
//                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//
//                _ConfirmWithPrint.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(printerMsg=="الطابعة جاهزه للطباعة") {
//                            print(1);
//                            popupWindow.dismiss();
//                        }
//                        else {_txt_Msg.setText("يجب فتح الطابعة اولا");}
//
//                    }
//                });
//
//                _Confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        print(0);
//                        popupWindow.dismiss();
//
//                    }
//                });
//
//                _Cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        popupWindow.dismiss();
//
//                    }
//                });
//                _Open_print.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        findBT();
//                        _txt_Msg.setText(printerMsg);
//
//                    }
//                });
//                _Close_print.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        try {
//                            closeBT();
//                            _txt_Msg.setText("تم ايقاف الطابعة");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        popupWindow.dismiss();
//
//                    }
//                });
//
//
//                // show the popup window
//                // which view you pass in doesn't matter, it is only used for the window tolken
//                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//                // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        // popupWindow.dismiss();
//                        return true;
//                    }
//                });
//
//                //         Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
//            }
//        } );
//
//        builder.setNegativeButton( "لاء", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //  msgFlag=0;
//                Toast.makeText( getApplicationContext(), "لم يتم تعديل", Toast.LENGTH_SHORT ).show();
//            }
//        } );
//
//        builder.show();
//
//
//    }
//    void closeBT() throws IOException {
//        try {
//            stopWorker = true;
//
//            mmOutputStream.close();
//            mmInputStream.close();
//            mmSocket.close();
//            //  myLabel.setText("Bluetooth Closed");
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    void print(int WithoutPrint)
//    {
//        Thread runt = new Thread(){
//            public void  run() {
//
//
//                String StartLine="*";
//                String StartCom="|";
//              // TableLayout t_Return = (TableLayout) findViewById(R.id.tableReturns);
//              // TableLayout t1 = (TableLayout) findViewById(R.id.table);
//
//
//              // //loop through table and get the Child count
//              // for(int i =0,j = t_Return.getChildCount(); i< j ;i++) {
//
//              //     //get Child views of the table with table.getChildAt(int index), this returns a view
//              //     //cast it to a TextView
//              //     View vi = t_Return.getChildAt(i);
//              //     TableRow r = (TableRow) vi;
//              //     //in this row (row i) of the table get the child element(column) where the first column would have a value of 0
//              //     if(i>1) {
//              //         int SR_No=i-1;
//
//
//              //         TextView Item = (TextView) r.getChildAt(0);
//              //         TextView ItemID = (TextView) r.getChildAt(2);
//              //         TextView Size = (TextView) r.getChildAt(3);
//              //         TextView ToalItemPrise = (TextView) r.getChildAt(5);
//
//              //         Print_pill_Return = Print_pill_Return+StartLine+
//              //                 Print_c.fixedLengthString(String.valueOf(SR_No),"0",1,false)+
//              //                 StartCom+Print_c.fixedLengthString(Item.getText().toString().trim()," ",14,true)+
//              //                 StartCom+Print_c.fixedLengthString(Size.getText().toString().trim()," ",4,true)+
//              //                 StartCom+Print_c.fixedLengthString(ToalItemPrise.getText().toString().trim()," ",4,true);
//
//
//
//
//
//              //         pill_Return = pill_Return+StartLine+
//              //                 SR_No+
//              //                 StartCom+Item.getText().toString()+
//              //                 StartCom+Size.getText().toString()+
//              //                 StartCom+ToalItemPrise.getText().toString()+
//              //                 StartCom+ItemID.getText().toString();
//
//              //     }
//              // }
//
//
//
//
//
//
//
//
//
//
//
//              // //loop through table and get the Child count
//              // for(int i =0,j = t1.getChildCount(); i< j ;i++) {
//
//              //     //get Child views of the table with table.getChildAt(int index), this returns a view
//              //     //cast it to a TextView
//              //     View vi = t1.getChildAt(i);
//              //     TableRow r = (TableRow) vi;
//
//
//
//              //     //in this row (row i) of the table get the child element(column) where the first column would have a value of 0
//              //     if(i>0) {
//              //         int SR_No=i;//-1;
//              //         TextView Item = (TextView) r.getChildAt(0);
//              //         TextView count = (TextView) r.getChildAt(2);
//              //         TextView Size = (TextView) r.getChildAt(4);
//              //         TextView ItemPrise = (TextView) r.getChildAt(6);
//              //         TextView ItemDescount = (TextView) r.getChildAt(8);
//              //         TextView ToalItemPrise = (TextView) r.getChildAt(10);
//              //         TextView ItemID = (TextView) r.getChildAt(11);
//              //         TextView SupplerID = (TextView) r.getChildAt(12);
//              //         TextView  Item_Supply_Price = (TextView) r.getChildAt(13);
//
//
//
//
//
//              //         Printpill=Printpill+StartLine+
//              //                 Print_c.fixedLengthString(String.valueOf(SR_No),"0",1,false)+
//              //                 StartCom+Print_c.fixedLengthString(Item.getText().toString().trim()," ",14,true)+
//              //                 StartCom+Print_c.fixedLengthString(count.getText().toString().trim()," ",4,true)+
//              //                 StartCom+Print_c.fixedLengthString(Size.getText().toString().trim()," ",4,true)+
//              //                 StartCom+Print_c.fixedLengthString(ItemPrise.getText().toString().trim()," ",5,true)+
//              //                 StartCom+Print_c.fixedLengthString(ItemDescount.getText().toString().trim()," ",4,true)+
//              //                 StartCom+Print_c.fixedLengthString(ToalItemPrise.getText().toString().trim()," ",8,true);
//
//              //         pill = pill+StartLine+
//              //                 SR_No+
//              //                 StartCom+Item.getText().toString()+
//              //                 StartCom+count.getText().toString()+
//              //                 StartCom+Size.getText().toString()+
//              //                 StartCom+ItemPrise.getText().toString()+
//              //                 StartCom+ItemDescount.getText().toString()+
//              //                 StartCom+ToalItemPrise.getText().toString()+
//              //                 StartCom+ItemID.getText().toString()+
//              //                 StartCom+SupplerID.getText().toString()+
//              //                 StartCom+ Item_Supply_Price.getText().toString();
//              //     }
//              // }
//              // cweb.setpill(pill,pill_Return);
//
//            }
//        };
//        runt.start();
//        try {
//            runt.join();
//
//            Toast.makeText(this,ContactResult.ErrorMessag,Toast.LENGTH_LONG).show();
//
//            if (  BluetoothStutes==1 &&WithoutPrint==1)
//
//            {
//
//                sendData(Printpill,Print_pill_Return);
//            }
//            else{ }
//
//
//
//
//            clearControls();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    void sendData(String Pill_Detelss,String pill_Return) throws IOException {
//
//        try {
//
//
//
//
//            //     Drawable d = ContextCompat.getDrawable(this, R.drawable.dawarlogo3a);
//            //     Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
//            PrintPic printPic = PrintPic.getInstance();
//            //     printPic.init(bitmap);
//            //     byte[] bitmapdata = printPic.printDraw();
////
//            String BillNo =  Print_c.fixedLengthString( ContactResult.ErrorMessag.replace("تم تسجيل الفاتورة بنجاح رقم الفاتورة هو ","")," ",7,true) ;
//            String CompName ="               شـــركة ألـــبــان الــدوار";
//            String S_B =" ب ض:597-437-679     س ت:173847 ";
//            BillNo ="             فاتورة رقم "+BillNo;
//            String Name =Print_c.fixedLengthString("الـمـنـدوب"," ",10,false)+":"+ContactResult.UserName;
//            String SalesMobile=" رقم المندوب :01007456521 ";
//            String Customer_Name =Print_c.fixedLengthString("الـعـمـيـل"," ",10,false)+":"+Pill_Class.Cus_Name;
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Calendar c = Calendar.getInstance();
//            String Date =Print_c.fixedLengthString("الـتـاريـخ"," ",10,false)+":"+ df.format(c.getTime());
//            String F_line =Print_c.fixedLengthString("","ـ",50,false);
//            String Retern_pil="";
//            if(pill_Return.length()>35)
//            { Retern_pil="مرتجعات"+"\n"+F_line+pill_Return+"\n"+F_line;}
//
//
//            Double Old_RemainingAmount=Double.valueOf(Pill_Class.collection.trim()).doubleValue();
//            Double TotalAmoun=Double.valueOf(Pill_Class.TotalPriceAfterDiscount.trim()).doubleValue();
//            Double Current_Balance=Double.valueOf(Pill_Class.TotalPriceAfterDiscount.trim()).doubleValue()- Double.valueOf(Pill_Class.Cash.trim()).doubleValue();
//            Double BillAmount = TotalAmoun - Old_RemainingAmount;
//            Double _Cash=Double.valueOf(Pill_Class.Cash.trim()).doubleValue();
//            Double _Deferred =0.0;
//            Double Icollection = 0.0;
//
//            if (_Cash ==0)
//            {
//                Icollection = 0.0;
//            }
//            else if (_Cash == Old_RemainingAmount)
//            {
//                Icollection = _Cash;
//                _Cash = 0.0;
//
//            }
//            else if (_Cash > Old_RemainingAmount)
//            {
//                Icollection = Old_RemainingAmount;
//                _Cash = _Cash - Old_RemainingAmount;
//            }
//            else if (_Cash < Old_RemainingAmount)
//            {
//                Icollection = _Cash;
//                _Cash = 0.0;
//            }
//
//            _Deferred = BillAmount - _Cash;
//            String _Old_RemainingAmount=Print_c.fixedLengthString(" المديونية السابقة"," ",37,true)+":"+ Double.toString(Old_RemainingAmount);
//            String _Current_Balance=Print_c.fixedLengthString(" المديونية الحالية"," ",37,true)+":"+ Double.toString(Current_Balance);
//            String TotalAmountBeforDiscount=Print_c.fixedLengthString(" اجمالي الفاتورة قبل الخصم والمرتجعات"," ",37,true)+":"+ Pill_Class.TotalPriceBeforDiscount;
//            String TotalAmountAfterDiscount=Print_c.fixedLengthString(" صافي الفاتورة بعد الخصم والمرتجعات"," ",37,true)+":"+ Double.toString(BillAmount);
//            String Cash=Print_c.fixedLengthString(" كـــاش"," ",37,true)+":"+ Double.toString(_Cash);
//            String DeferredPayment=Print_c.fixedLengthString(" أجـــــــل"," ",37,true)+":"+ Double.toString(_Deferred);
//            String collection=Print_c.fixedLengthString(" تــحــصــيــل"," ",37,true)+":"+ Double.toString(Icollection);
//            String ReturnAmount=Print_c.fixedLengthString(" الـمـرتـجـعـات"," ",37,true)+":"+ Pill_Class.ReturnAmount;
//            String PillDiscount=Print_c.fixedLengthString(" خصم اجمالي الفاتورة"," ",37,true)+":"+ Pill_Class.PillDiscount;
//            String collection_summry="";
//            if (Old_RemainingAmount>0)
//            { collection_summry="       ========== تـحـصـيـل ==========      "
//                    +"\n"+_Old_RemainingAmount
//                    +"\n"+collection
//                    +"\n"+_Current_Balance;}
//            String msg =CompName+"\n"+S_B+"\n"+ BillNo+"\n"+Name+"\n"+SalesMobile+"\n"+Customer_Name+"\n"+Date+"\n"+F_line+Pill_Detelss+F_line+Retern_pil
//
//                    +"\n"+TotalAmountBeforDiscount
//                    +"\n"+ReturnAmount
//                    +"\n"+PillDiscount
//                    +"\n"+TotalAmountAfterDiscount
//                    +"\n"+DeferredPayment
//                    +"\n"+Cash
//                    +  "\n"+collection_summry;
//
//
//
//
//
//            // Bitmap Pill_Detels = textAsBitmap(msg.replace("*"," \n "),580,20);
//            // Bitmap Pill_Detels = textAsBitmap(msg.replace("*","\n"),580,16);
//            Bitmap Pill_Detels = textAsBitmap(msg.replace("*","\n"),370,10);
//            //for our machen smol restet
//            printPic.init(Pill_Detels);
//            byte[] bitmapdata_Pill_Detels = printPic.printDraw();
//
//
//
//
//            //    mmOutputStream.write(bitmapdata);
//            mmOutputStream.write(bitmapdata_Pill_Detels);
//            if (Current_Balance>0)
//
//            {
//                String t_line2 ="\n"+"\n"+"\n"+"\n";
//                mmOutputStream.write(t_line2.getBytes());
//                mmOutputStream.write(bitmapdata_Pill_Detels);
//            }
//            //  mmOutputStream.write(bitmapdata_Taxcard);
//            //  mmOutputStream.write(bitmapdata_BillNo);
//            //  mmOutputStream.write(t_line3.getBytes());
//            //  mmOutputStream.write(t_line2.getBytes());
//        } catch (Exception e) {
//        }
//        mmOutputStream.flush();
//
//        //   closeBT();
//    }
//    public Bitmap textAsBitmap(String text, int textWidth, int textSize) {
//// Get text dimensions
//        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
//                | Paint.LINEAR_TEXT_FLAG);
//        textPaint.setStyle(Paint.Style.FILL);
//
//
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.kawkabregular);
//        textPaint.setTypeface(typeface);
//        textPaint.setColor(Color.BLACK);
//        textPaint.setTextSize(textSize);
//        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
//                textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
//
//// Create bitmap and canvas to draw to
//        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
//        Canvas c = new Canvas(b);
//
//// Draw background
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
//                | Paint.LINEAR_TEXT_FLAG);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//
//        paint.setTypeface(typeface);
//
//
//        c.drawPaint(paint);
//
//// Draw text
//        c.save();
//        c.translate(0, 0);
//        mTextLayout.draw(c);
//        c.restore();
//
//        return b;
//    }
//    void clearControls()
//    {
//        Printpill="*م |الصنف          |العدد|الوزن|السعر |الخصم|الاجمالي*"  ;//          +Print_c.fixedLengthString("","ـ",50,false);
//        Print_pill_Return="*م |الصنف          |الوزن|السعر *" ;   //   + Print_c.fixedLengthString("","ـ",50,false);
//        pill_Return="";
//        pill="";
//
//
//
//
//
//
//        // reset Pill_class
//
//        Pill_Class.Item_id=0;
//        Pill_Class.Item_Name="";
//        Pill_Class.Itemsize="1";
//        Pill_Class.Item_Selling_Price="0";
//        Pill_Class.Item_Supply_Price="0" ;
//        Pill_Class. Net_Item_prise="0";
//        Pill_Class.lineTotal_Price="0";
//        Pill_Class.TotalPriceBeforDiscount="0";
//        Pill_Class.PillDiscount="0";
//        Pill_Class.TotalPriceAfterDiscount="0";
//        Pill_Class.Cash="0";
//        Pill_Class.DeferredPayment="0";
//        Pill_Class.ReturnAmount="0";
//        Pill_Class.collection="0";
//
//
//
//        ContactResult.Supplier_Name=new String[] {"اختار مورد", "لاتوجد نتائج"};
//        ContactResult.Supplier_id=new String[] {"0"};
//        ContactResult.Items_Group_Name=new String[] {"اختار مجموعة اصناف", "لاتوجد نتائج"};;
//        ContactResult.Items_Group_id=new String[] {"0"};
//
//
//
//
//
//        ContactResult.Customer_id2=0;
//        ContactResult.Supplier_id2=0;
//        ContactResult.Group_items_id2=0;
//
//
//
//
//
//
//
//    }
    public static Bitmap textAsBitmap(String text, int textWidth, int textSize) {
// Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

// Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);

// Draw background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        c.drawPaint(paint);

// Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }
    public Bitmap print(String url) throws WriterException {
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        BitMatrix bitMatrix;

        bitMatrix = multiFormatWriter.encode( url,
                BarcodeFormat.CODE_128,
                250,
                250);

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
        return  bitmap;
    }
}