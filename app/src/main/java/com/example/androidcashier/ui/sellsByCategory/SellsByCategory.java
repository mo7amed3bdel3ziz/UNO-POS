package com.example.androidcashier.ui.sellsByCategory;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.androidcashier.R;
import com.example.androidcashier.databinding.ActivitySellsByCategoryBinding;
import com.example.androidcashier.network.RetrofItItems;
import com.example.androidcashier.pojo.Bluetoothprint.PrintBluetooth;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.SetBillModel;
import com.example.androidcashier.pojo.authModel.ModelStatMg;
import com.example.androidcashier.ui.Categorys;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class SellsByCategory extends AppCompatActivity {
    SharedPreferences sharedPreferenclanguageg;
    ActivitySellsByCategoryBinding binding;
    Dialog dialog;
    String macAddress;
    ProgressDialog progressDoalog;
    PrintBluetooth printBT = new PrintBluetooth();
    ArrayList<ItemsModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_sells_by_category);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sells_by_category);
        sharedPreferenclanguageg = getSharedPreferences("Currency ", MODE_PRIVATE);
        SharedPreferences.Editor editsg = sharedPreferenclanguageg.edit();

        macAddress =
                android.provider.Settings.Secure.
                        getString(this.getApplicationContext().
                                getContentResolver(), "android_id");


        binding.payWay.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  if (isvaled() == true) {
                                                      ItemsModel itemsModel = new ItemsModel(Double.valueOf(binding.cashEditText2.getText().toString()),
                                                              binding.cashEditText.getText().toString(), "258369"
                                                      );
                                                      itemsModel.setContaty(1.0);
                                                      itemsModel.setBalanc(Double.valueOf(binding.cashEditText2.getText().toString()));
                                                      list.add(itemsModel);

                                                      //  OnClikBtnAddPill(view);
                                                      if (list.isEmpty()) {

                                                          Toast.makeText(SellsByCategory.this, "", Toast.LENGTH_SHORT).show();
                                                      } else {
                                                          dialog = new Dialog(SellsByCategory.this);

                                                          // set custom dialog
                                                          dialog.setContentView(R.layout.chos_itemse);
                                                          //  dialog.setContentView(R.layout.chos_itemse);

                                                          // set custom height and width
                                                          dialog.getWindow().setLayout(600, 1000);

                                                          // set transparent background
                                                          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                          // show dialog
                                                          dialog.show();
                                                          Button payWayAndRecordInvoce = dialog.findViewById(R.id.payWayAndRecordInvoce);
                                                          TextView Totalse = dialog.findViewById(R.id.Totalse);
                                                          TextView Totalss = dialog.findViewById(R.id.Totalss);
                                                          // Totalss.setText(Totals.getText().toString() + " LEُ");

                                                          payWayAndRecordInvoce.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View view) {
                                                                  if (list.isEmpty()) {

                                                                      Toast.makeText(SellsByCategory.this, "", Toast.LENGTH_SHORT).show();
                                                                  } else {


                                                                      progressDoalog = new ProgressDialog(SellsByCategory.this);
                                                                      progressDoalog.setMax(100);
                                                                      progressDoalog.setMessage("Its loading....");
                                                                      progressDoalog.setTitle(" جارى تسجيل الفاتوره ");
                                                                      //    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                                                      progressDoalog.show();

                                                                      if (!list.isEmpty()) {
                                                                          Toast.makeText(SellsByCategory.this, "test", Toast.LENGTH_SHORT).show();

                                                                          SetBillModel bill2 = new SetBillModel("1", macAddress, list);
                                                                          Single s  = RetrofItItems.getInstance().getApiCalls().sentInvoice(bill2)
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
                                                                                  dialog = new Dialog(SellsByCategory.this);
                                                                                  // set custom dialog
                                                                                  dialog.setContentView(R.layout.chos_items);
                                                                                  //  dialog.setContentView(R.layout.chos_itemse);

                                                                                  // set custom height and width
                                                                                  dialog.getWindow().setLayout(500, 1350);

                                                                                  // set transparent background
                                                                                  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                                  // ImageView imageView2=dialog.findViewById(R.id.imageView2);

                                                                                  // show dialog
                                                                                  dialog.show();

                                                                                  ImageView imageView2 = dialog.findViewById(R.id.imageView2);
                                                                                  TextView textView13 = dialog.findViewById(R.id.textView13);
                                                                                  textView13.setText(" اجمالى: " + c.getTotalBill() + " " + "ج.م" + "\n" + c.getMessage());

                                                                                  // Button payWayAndRecordInvoce=dialog.findViewById(R.id.payWayAndRecordInvoce);
                                                                                  Toast.makeText(SellsByCategory.this, c.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                  //  Double xx = 0.0;
                                                                                  //  for (int x = 0; x < list.size(); x++) {
                                                                                  //      xx = xx + list.get(x).getBalanc();
                                                                                  //  }

                                                                                  //ItemsModel s=new ItemsModel();
                                                                                  //   list.remove(null);
                                                                                  //  adabter.notifyItemInserted(list.size() - 1);
                                                                                  //  billRecycler.scrollToPosition(list.size());
                                                                                  //   adabter.setList(list);

                                                                                  // PrintBluetooth.printer_id ="Printer001";
                                                                                  PrintBluetooth.printer_id = "InnerPrinter";
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
                                                                                  String s = loopList();
                                                                                  //    Log.d(" loopList",s);

                                                                                  String ss = setpill();
                                                                                  String end = endBill();
                                                                                  String endof = endOfend();
                                                                                  try {
                                                                                      printBT.findBT();
                                                                                      printBT.openBT();

                                                                                      // printBT.printQrCode(textAsBitmap(s,370, 30));

                                                                                      //   printBT.printQrCode(textAsBitmap(setpill(),520,28));


                                                                                      //  printBT.printQrCode(textAsBitmap(s,520, 30));
                                                                                      //  printBT.printQrCode(textAsBitmap(ss,520, 22));
                                                                                      printBT.printQrCode(textAsBitmap(s, 370, 27));
                                                                                      printBT.printQrCode(textAsBitmap(ss, 370, 22));
                                                                                      printBT.printQrCode(print(c.getUrl()));

                                                                                      // printBT.printQrCode(textAsBitmap(s,370, 27));
                                                                                      // printBT.printQrCode(textAsBitmap(ss,370, 22));
                                                                                      // printBT.printQrCode(print( c.getUrl()));


                                                                                      //        printBT.printQrCode(textAsBitmap(s,370, 27));

                                                                                      //     //   printBT.printQrCode(textAsBitmap(setpill(),520,28));
                                                                                      //        printBT.printQrCode(textAsBitmap(ss,370, 22));
                                                                                      //      //  printBT.printQrCode(textAsBitmap(end,370, 27));
                                                                                      //        printBT.printQrCode(textAsBitmap(endof,370, 22));
                                                                                      //  printBT.printQrCode(print( c.getUrl()));
                                                                                      //  printBT.closeBT();
                                                                                  } catch (IOException
                                                                                          | WriterException
                                                                                          ex) {
                                                                                      ex.printStackTrace();
                                                                                  }

                                                                                  MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                                                                  BitMatrix bitMatrix;


                                                                                  {
                                                                                      try {
                                                                                          //   if ( c.getUrl()==null&&c.getUrl().isEmpty()&& c.getUrl().length()==0){}else {}
                                                                                          bitMatrix = multiFormatWriter.encode("c",
                                                                                                  BarcodeFormat.QR_CODE,
                                                                                                  300,
                                                                                                  300);
                                                                                          BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                                                          Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                                                          //  imageView.setImageBitmap(bitmap);

                                                                                          try {
                                                                                              printBT.findBT();
                                                                                              printBT.openBT();
                                                                                              //    printBT.printQrCode(bitmap);
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
                                                                                          Intent i = new Intent(SellsByCategory.this, SellsByCategory.class);
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
                                                                                  Toast.makeText(SellsByCategory.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                                                  PrintBluetooth.printer_id = "InnerPrinter";
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
                                                                                  String s = loopList();


                                                                                  String ss = setpill();
                                                                                  String end = endBill();
                                                                                  String endof = endOfend();
                                                                                  try {
                                                                                      printBT.findBT();
                                                                                      printBT.openBT();
                                                                                      printBT.printQrCode(textAsBitmap(s, 370, 27));
                                                                                      printBT.printQrCode(textAsBitmap(ss, 370, 22));
//
                                                                                      //  printBT.printQrCode(textAsBitmap(s,520, 40));
                                                                                      //  printBT.printQrCode(textAsBitmap(ss,520, 33));

                                                                                      //   printBT.printQrCode(textAsBitmap(setpill(),520,28));
                                                                                      //  printBT.printQrCode(textAsBitmap(ss,520, 33));
                                                                                      //  printBT.printQrCode(textAsBitmap(end,370, 27));
                                                                                      //  printBT.printQrCode(textAsBitmap(endof,370, 22));
                                                                                      printBT.printQrCode(print(" c.getUrl()"));
                                                                                      //  printBT.closeBT();
                                                                                  } catch (IOException
                                                                                          | WriterException
                                                                                          ex) {
                                                                                      ex.printStackTrace();
                                                                                  }

                                                                                  MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                                                                  BitMatrix bitMatrix;


                                                                                  {
                                                                                      try {
                                                                                          //   if ( c.getUrl()==null&&c.getUrl().isEmpty()&& c.getUrl().length()==0){}else {}
                                                                                          bitMatrix = multiFormatWriter.encode("c",
                                                                                                  BarcodeFormat.QR_CODE,
                                                                                                  300,
                                                                                                  300);
                                                                                          BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                                                          Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                                                          //  imageView.setImageBitmap(bitmap);

                                                                                          try {
                                                                                              printBT.findBT();
                                                                                              printBT.openBT();
                                                                                              //    printBT.printQrCode(bitmap);
                                                                                              printBT.closeBT();
                                                                                          } catch (IOException ex) {
                                                                                              ex.printStackTrace();
                                                                                          }

                                                                                      } catch (WriterException ea) {
                                                                                          ea.printStackTrace();
                                                                                      }

                                                                                  }
                                                                                  //  PrintBluetooth.printer_id ="Printer001";
                                                                                  //  java.util.Date today = new java.util.Date();
                                                                                  //  SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                                                                                  //  String print_date = format_date.format(today);
                                                                                  //  String status_barang = "";
                                                                                  //  //    if (radioButtonYa.isChecked()){
                                                                                  //  //        status_barang = " vegetarian";
                                                                                  //  //    }
                                                                                  //  //    else if (radioButtonTidak.isChecked()){
                                                                                  //  //        status_barang = "meet";
                                                                                  //  //    }
                                                                                  //  try {
                                                                                  //      printBT.findBT();
                                                                                  //      printBT.openBT();
//
                                                                                  //      printBT.printQrCode(textAsBitmap(setpill(),520,28));
                                                                                  //     // printBT.printQrCode(textAsBitmap(setpill(),520,28));
//
                                                                                  //      //  printBT.closeBT();
                                                                                  //  }catch(IOException ex){ex.printStackTrace();}
//
                                                                                  //  MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                                                                                  //  BitMatrix bitMatrix;
//
                                                                                  //  {
                                                                                  //      try {
                                                                                  //          bitMatrix = multiFormatWriter.encode("editText",
                                                                                  //                  BarcodeFormat.QR_CODE,
                                                                                  //                  300,
                                                                                  //                  300);
                                                                                  //          BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                                                  //          Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                                                                                  //          //  imageView.setImageBitmap(bitmap);
//
                                                                                  //          try {
                                                                                  //              printBT.findBT();
                                                                                  //              printBT.openBT();
                                                                                  //                printBT.printQrCode(bitmap);
                                                                                  //            //  printBT.closeBT();
                                                                                  //          }catch (IOException ex){ex.printStackTrace();}
//
                                                                                  //      } catch (WriterException ea) {
                                                                                  //          ea.printStackTrace();
                                                                                  //      }
                                                                                  //  }

                                                                                  progressDoalog.dismiss();
                                                                                  Toast.makeText(SellsByCategory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                              }
                                                                          };
                                                                          s.subscribe(singleObserver);
                                                                      }
                                                                  } //Toast.makeText(ItemsActivity.this, "", Toast.LENGTH_SHORT).show();
                                                              }
                                                          });
                                                      }
                                                  } else {
                                                      Toast.makeText(SellsByCategory.this, "ادخل المنتج", Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
        );

        binding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provideSimpleDialogCategory();
            }
        });

        binding.cardView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provideSimpleDialog();
                //  editsg.putString("USD","24.43");
                //  editsg.putString("GBP","28.75");
                //  editsg.putString("CNY","3.45");
                //  editsg.putString("EUR","25.10");
                //  editsg.apply();

                //  Toast.makeText(SellsByCategory.this, "ddd", Toast.LENGTH_SHORT).show();

            }
        });

        binding.cashEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    binding.cashEditText1.setText("0");
                } else {
                    String bayed = binding.cashEditText1.getText().toString();
                    String carncy = binding.textView23.getText().toString();
                    Double dsw = Double.valueOf(bayed) * Double.valueOf(carncy);
                    binding.cashEditText2.setText(dsw.toString());


                }
            }
        });
        //  editsg.putString("lang","en");
        //  editsg.putString("lang","en");
        //  editsg.putString("lang","en");
        //  // editsg.putString("test","2");
        //  editsg.apply();
        //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    void provideSimpleDialog() {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(SellsByCategory.this, "Search...",
                "What are you looking for...?", null, createSampleData(),
                new SearchResultListener<Categorys>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, Categorys item, int position) {
                        Toast.makeText(SellsByCategory.this, item.getValue(), Toast.LENGTH_SHORT).show();
                        binding.textView220.setText(item.getCurrencyName());
                        binding.textView23.setText(item.getValue());
                        binding.cashEditText1.setText("0");
                        binding.cashEditText2.setText("0.0");
                        //   binding.textView22.setText(item.getCurrencyName()+"    ");
                        dialog.dismiss();
                    }

                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    void provideSimpleDialogCategory() {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(SellsByCategory.this, "Search...",
                "What are you looking for...?", null, createSampleDataCategory(),
                new SearchResultListener<Categorys>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, Categorys item, int position) {
                        //  Toast.makeText(SellsByCategory.this, item.getValue(), Toast.LENGTH_SHORT).show();
                        binding.textView20.setText(item.getCurrencyName());
                        //   binding.textView22.setText(item.getCurrencyName()+"    ");
                        dialog.dismiss();
                    }

                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private ArrayList createSampleData() {
        ArrayList<Categorys> categorys = new ArrayList<>();
        //فى حاجه غلط
        categorys.add(new Categorys("USD", "24.43"));
        categorys.add(new Categorys("GBP", "28.75"));
        categorys.add(new Categorys("CNY", "3.45"));
        categorys.add(new Categorys("EUR", "25.10"));
        //  editsg.putString("USD","24.43");
        //  editsg.putString("GBP","28.75");
        //  editsg.putString("CNY","3.45");
        //  editsg.putString("EUR","25.10");

        String USD = sharedPreferenclanguageg.getString("USD", null);
        String GBP = sharedPreferenclanguageg.getString("GBP", null);
        String CNY = sharedPreferenclanguageg.getString("CNY", null);
        String EUR = sharedPreferenclanguageg.getString("EUR", null);
        return categorys;
    }

    private ArrayList createSampleDataCategory() {
        ArrayList<Categorys> categorys = new ArrayList<>();
        categorys.add(new Categorys("statues", "24.43"));
        categorys.add(new Categorys("Gemstones", "28.75"));
        categorys.add(new Categorys("excavations", "3.45"));
        categorys.add(new Categorys("textiles", "25.10"));
        //  editsg.putString("USD","24.43");
        //  editsg.putString("GBP","28.75");
        //  editsg.putString("CNY","3.45");
        //  editsg.putString("EUR","25.10");

        //   String USD = sharedPreferenclanguageg.getString("USD", null);
        //   String GBP = sharedPreferenclanguageg.getString("GBP", null);
        //   String CNY = sharedPreferenclanguageg.getString("CNY", null);
        //   String EUR = sharedPreferenclanguageg.getString("EUR", null);
        return categorys;
    }

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


    public String loopList() {
        java.util.Date today = new java.util.Date();
        SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String print_date = format_date.format(today);

        String n1 = "                 Uno Android Cashier";
        String n2 = "                             Cairo";
        String n3 = "                      CANDY SHOP";
        String msg =
                //n0+"\n"+"\n"+ "\n" +
                n1 + "\n" + n2 + "\n" + n3;
        return msg;

    }

    public String setpill() {
        java.util.Date today = new java.util.Date();
        SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String print_date = format_date.format(today);

        StringBuffer sb = new StringBuffer();
        for (ItemsModel s : list) {
            //  if (s.ItemName.length()>15){
            //    int startRest= s.ItemName.indexOf(" ",15);
            //    String restOfItemName=s.ItemName.substring(startRest);
            //    s.ItemName=s.ItemName.substring(0,startRest);
//
            //    sb.append("  "+s.ItemName+"       "+s.contaty+"           "+s.balanc+"           "+"\n" +
            //            " "+restOfItemName+"\n" +"\n");
            //  }else {
            sb.append("     " + s.ItemName + "           " + s.contaty + "              " + s.balanc + "\n");

            //   }
            //  sb.append("  "+s.ItemName+"           "+s.contaty+"           "+s.balanc +"\n");


        }

        //sb.append("\n");
        sb.append("____________________________________");
        sb.append("\n");
        sb.append("\n");
        sb.append(" Total Items  " + "                           " + binding.cashEditText2.getText());
        sb.append("\n");
        sb.append(" Discount     " + "                                    " + "0.0");
        sb.append("\n");
        sb.append(" Cash Discount  " + "                             " + "0.0");
        sb.append("\n");
        sb.append("____________________________________");
        sb.append("\n");
        // sb.append(" Total Due  "+"                                       "+Totals.getText());
        sb.append("\n");
        sb.append(" Cash Paid    " + "                                    " + "0.0");
        sb.append("\n");
        sb.append(" Credit Cards  " + "                                  " + "0.0");
        sb.append("\n");
        sb.append("____________________________________");
        sb.append("\n");
        sb.append("                      Administrator");
        sb.append("\n");
        sb.append("        " + print_date);
        sb.append("\n");
        sb.append("        " + "                        pos 1");
        sb.append("\n");
        sb.append("____________________________________");

        String n4 = "___________  BILL DETAILS __________";


        String msg = n4 + "\n" + "\n" + sb;


        return msg;
    }

    public String endBill() {
        String n3 = (" Total Due  " + "                             " + "0.0");
        String n5 = (" Cash Paid  " + "                          " + "0.0");
        String n9 = (" Credit Cards  " + "                        " + "0.0");


        String msg =
                //n0+"\n"+"\n"+ "\n" +
                n3 + "\n" + n5 + "\n" + n9;
        return msg;
    }

    public String endOfend() {
        java.util.Date today = new java.util.Date();
        SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String print_date = format_date.format(today);


        String n1 = ("____________________");

        String n2 = ("\n");
        String n3 = ("                                      Administrator");
        String n4 = ("\n");
        String n5 = ("                    " + print_date);
        String n8 = ("\n");
        String n9 = ("                                         POS-01");
        String n10 = ("\n");
        String n11 = ("____________________");
        String msg =
                //n0+"\n"+"\n"+ "\n" +
                n1 + n2 + n3 + n4 + n5 + n8 + n9 + n10 + n11;
        return msg;
    }

    public Bitmap print(String url) throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix;

        bitMatrix = multiFormatWriter.encode(url,
                BarcodeFormat.QR_CODE,
                250,
                250);

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }

    public Boolean isvaled() {

        if (binding.textView20.getText().toString().isEmpty()) {
            binding.textView20.setError("AddCredit");
            return false;
        }

        if (binding.cashEditText1.getText().toString().isEmpty()) {
            binding.cashEditText1.setError("Enter Collect Date");
            return false;
        }


        if (binding.cashEditText.getText().toString().isEmpty()) {
            binding.cashEditText.setError("Enter Description");
            return false;
        }


        {
            return true;
        }
    }
}