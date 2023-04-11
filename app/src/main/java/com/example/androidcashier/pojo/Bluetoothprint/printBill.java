package com.example.androidcashier.pojo.Bluetoothprint;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.androidcashier.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class printBill {
    RadioButton radioButtonYa,radioButtonTidak;
    PrintBluetooth printBT = new PrintBluetooth();
  //  PrintBluetooth.printer_id = "Printer001"
    java.util.Date today = new java.util.Date();
    SimpleDateFormat format_date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    String print_date = format_date.format(today);
    String status_barang = "";
    public  void  print(){
        if (radioButtonYa.isChecked()){
            status_barang = " vegetarian";
        }
        else if (radioButtonTidak.isChecked()){
            status_barang = "meet";
        }
        try {
            printBT.findBT();
            printBT.openBT();
            printBT.printStruk("يسينخ",status_barang,print_date);
            printBT.closeBT();
        }catch(IOException ex){ex.printStackTrace();}

        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        BitMatrix bitMatrix;

        {
            try {
                bitMatrix = multiFormatWriter.encode("editText.getText().toString()",
                        BarcodeFormat.QR_CODE,
                        300,
                        300);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
               // imageView.setImageBitmap(bitmap);

                try {
                    printBT.findBT();
                    printBT.openBT();
                    printBT.printQrCode(bitmap);
                    printBT.closeBT();
                }catch (IOException ex){ex.printStackTrace();}

            } catch (WriterException e) {
                e.printStackTrace();
            }

        }

    }


}
