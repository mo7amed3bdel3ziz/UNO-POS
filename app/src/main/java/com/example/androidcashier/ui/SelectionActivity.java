package com.example.androidcashier.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.androidcashier.R;
import com.example.androidcashier.databinding.ActivitySelectionBinding;
import com.example.androidcashier.pojo.Bluetoothprint.PrintBluetooth;
import com.example.androidcashier.pojo.Bluetoothprint.printBill;
import com.example.androidcashier.ui.closed.ClosedActivity;
import com.example.androidcashier.ui.crudUI.AddItemActivity;
import com.example.androidcashier.ui.crudUI.UpdateProductActivity;
import com.example.androidcashier.ui.invoice.ItemsActivity;
import com.example.androidcashier.ui.invoice.ReternActivity;
import com.example.androidcashier.ui.sellsByCategory.SellsByCategory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class SelectionActivity extends AppCompatActivity {
ActivitySelectionBinding binding;
    PrintBluetooth printBT = new PrintBluetooth();
    EditText editText ;
    ImageView imageView;
    String[] list_produk = { "Food", "Non Food", "take away", "water", "Makanan Segar", "Stationary" };
    TextInputEditText input_produk;
    EditText in_printer_id;
    Button print_struk;
    RadioButton radioButtonYa,radioButtonTidak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection);

        binding.Returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent is =new Intent(SelectionActivity.this, ReternActivity.class);
              //  is.putExtra("Stat",0);
                startActivity(is);
            }
        });  binding.salesByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent is =new Intent(SelectionActivity.this, SellsByCategory.class);
              //  is.putExtra("Stat",0);
                startActivity(is);
            }
        });
        binding.sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent is =new Intent(SelectionActivity.this, ItemsActivity.class);
//
                 is.putExtra("Stat",1);
                 startActivity(is);
              // PrintBluetooth.printer_id ="Printer001";
              // java.util.Date today = new java.util.Date();
              // SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
              // String print_date = format_date.format(today);
              // String status_barang = "";
            ////   if (radioButtonYa.isChecked()){
            ////       status_barang = " vegetarian";
            ////   }
            ////   else if (radioButtonTidak.isChecked()){
            ////       status_barang = "meet";
            ////   }
              // try {
              //     printBT.findBT();
              //     printBT.openBT();

              //     printBT.printQrCode(textAsBitmap(""+"\n"+"نبيل"+"\n"+"يوسف "+"محمد حمدى",400,33));

              //     //  printBT.closeBT();
              // }catch(IOException ex){ex.printStackTrace();}

              // MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
              // BitMatrix bitMatrix;

              // {
              //     try {
              //         bitMatrix = multiFormatWriter.encode("editText.getText().toString()",
              //                 BarcodeFormat.QR_CODE,
              //                 300,
              //                 300);
              //         BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
              //         Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
              //       //  imageView.setImageBitmap(bitmap);

              //         try {
              //             printBT.findBT();
              //             printBT.openBT();
              //           //  printBT.printQrCode(bitmap);
              //             printBT.closeBT();
              //         }catch (IOException ex){ex.printStackTrace();}

              //     } catch (WriterException e) {
              //         e.printStackTrace();
              //     }
              // }
            }
        });

        binding.addPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is =new Intent(SelectionActivity.this, AddItemActivity.class);

                startActivity(is);

            }
        });

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is =new Intent(SelectionActivity.this, ClosedActivity.class);

                startActivity(is);

            }
        });
        binding.UpdateSall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is =new Intent(SelectionActivity.this, UpdateProductActivity.class);
                startActivity(is);

            }
        });
        //report

        binding.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is =new Intent(SelectionActivity.this, ReportsActivity.class);
                startActivity(is);

            }
        });
    }
    public static Bitmap textAsBitmap(String text, int textWidth, int textSize) {
// Get text dimensions
        TextPaint textPaint = new TextPaint( Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle( Paint.Style.FILL);
        textPaint.setColor( Color.BLACK);
        textPaint.setTextSize(textSize);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

// Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);

// Draw background
        Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle( Paint.Style.FILL);
        paint.setColor( Color.WHITE);
        c.drawPaint(paint);

// Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }
}