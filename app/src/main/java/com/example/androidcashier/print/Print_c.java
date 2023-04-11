package com.example.androidcashier.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;


public class Print_c {

    TextView myLabel;

    // will enable user to enter any text to be printed
    EditText myTextbox;

    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;



       void openBT() throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Bluetooth Opened");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // This is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread( new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()
                            && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length);
                                        final String data = new String(
                                                encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void sendData( Bitmap Logo) throws IOException {

        //  mmOutputStream = btsocket.getOutputStream();
        // byte[] printformat = {0x1B, 0x21, FONT_TYPE};
        //   mmOutputStream.write(printformat);

        String t_line1 = "! 0 vvvvvvv\r\n";
        String t_line2 = "PCX 80 30\r\n";
        String t_line3 = "PRINT\r\n";
        try {

           // Drawable d = ContextCompat.getDrawable(this, R.drawable.dawarlogo3);
         final Bitmap bitmap = Logo;
            //  ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PrintPic printPic = PrintPic.getInstance();
            printPic.init(bitmap);
            byte[] bitmapdata = printPic.printDraw();
            //    String msg = myTextbox.getText().toString();
            //    msg += "\n";
            String n1 =" بسم الله ";
            String n2 ="الحمد لله";
            String n3 ="الله اكبر";
            String n4 ="توكلنا علي الله";
            String msg = n1+"  \n "+n2+"  \n "+n2+"  \n "+n3+"  \n "+n4;
            //

            Bitmap comName = textAsBitmap(msg,580,20);
            printPic.init(comName);
            byte[] bitmapdata_comName = printPic.printDraw();

            Bitmap Taxcard = textAsBitmap("  س.ت: 173847 ب.ض:679/437/597   ",300,30);
            printPic.init(Taxcard);
            byte[] bitmapdata_Taxcard = printPic.printDraw();

            //  mmOutputStream.write(t_line1.getBytes());


            //     mmOutputStream.write(bitmapdata);
            mmOutputStream.write(bitmapdata_comName);
            // mmOutputStream.write(bitmapdata_Taxcard);
            //  mmOutputStream.write(t_line3.getBytes());
            //  mmOutputStream.write(t_line2.getBytes());
        } catch (Exception e) {
        }
        mmOutputStream.flush();


    }
    public void closeBT() throws IOException {
        try {
            stopWorker = true;

            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String fixedLengthString(String str, String Length_Chr, int leng , Boolean FromRight) {

       if(str.contains("لا"))
       {leng=leng+1;}


        for (int i = str.length(); i <= leng; i++)
            if ((FromRight == true)) {

                str = str+Length_Chr;
            }
            else {
                str = Length_Chr+str;

            }


        return str;
    }

    public static String setLength(String str, Integer Length, Boolean FromRight)
    {

        String str2=str.trim();
         String SetLength =str2.trim();


        int L=0;
        L=Length-str2.length();
        char[] chars = new char[L];
        Arrays.fill(chars, '#');
        String text = new String(chars);
        if ((L > 0)) {
            if ((FromRight == true)) {

                SetLength = str2 + text;
            }
            else {
                SetLength = text + str2;
            }
        }
        else if ((L == 0)) {
            SetLength = str2;
        }
        else {
            //  SetLength = str.Substring(0, Length);
            //  Left(str, Length)
        }
        return SetLength;

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
