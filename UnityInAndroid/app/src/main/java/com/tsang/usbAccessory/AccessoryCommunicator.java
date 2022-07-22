package com.tsang.usbAccessory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AccessoryCommunicator {

    private Context context;
    private UsbManager usbManager;
    private ParcelFileDescriptor fileDescriptor;
    private FileDescriptor fd;
    private FileInputStream inStream;
    private FileOutputStream outStream;
    private boolean running;
    private  Handler sendHandler;

    private void receive(final byte[] payload, final int length) {
        onReceive(payload, length);
    }

    public void onReceive(final byte[] payload, final int length) {
    }

    public void send(byte[] payload) {
        Log.e("DEBUG","SEND METHOD NOW RUNNING");
        if (sendHandler != null) {
            Message msg = sendHandler.obtainMessage();
            msg.obj = payload;
            sendHandler.sendMessage(msg);
        }
    }

    public AccessoryCommunicator(final Context context) {
        this.context = context;

        usbManager = (UsbManager)this.context.getSystemService(Context.USB_SERVICE);
        final UsbAccessory[] accessoryList = usbManager.getAccessoryList();

        if (accessoryList == null || accessoryList.length == 0) {
            Log.d("DEBUG","ACCESSORYLIST IS NULL");
        } else {
            Log.d("DEBUG","ACCESSORYLIST NOW RUNNING");
            openAccessory(accessoryList[0]);
        }
    }

    @SuppressLint("HandlerLeak")
    private void openAccessory(UsbAccessory accessory) {
        fileDescriptor = usbManager.openAccessory(accessory);
        if(fileDescriptor != null){
            running = true;
            fd = fileDescriptor.getFileDescriptor();
            inStream = new FileInputStream(fd);
            outStream = new FileOutputStream(fd);
            new CommunicationThread().start();

            sendHandler = new Handler() {
                public void handleMessage(Message msg) {
                    try {
                        outStream.write((byte[]) msg.obj);
                    } catch (final Exception e) {
                        Log.e("DEBUG","USB SEND FAILED");
                    }
                }
            };
        } else {
            Log.e("DEBUG","COUNLD NOT CONNECT");
        }
    }

    public void closeAccessory() {
        running = false;
        try {
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
        } catch (IOException e) {

        }finally {
            fileDescriptor = null;
        }
    }


    private class CommunicationThread extends Thread {
        @Override
        public void run() {
            Log.e("DEBUG","START METHOD IS RUNNING");
            byte[] msg = new byte[Constants.BUFFER_SIZE_IN_BYTES];
            try {

                int len = inStream.read(msg);
                Log.e("DEBUG","TRY SUCCESS" + running);
                while (inStream != null && len > 0 && running) {
                    receive(msg, len);
//                    Thread.sleep(0);
                    len = inStream.read(msg);
                    Log.d("DEBUG","ALL MSG RECEIVE");
                }
                Log.d("DEBUG","ALL instream RECEIVE");
            } catch (final Exception e) {
                Log.e("DEBUG","USB RECEIVE FAILED");
                closeAccessory();
            }
        }
    }

    public class Constants {
        public static final int USB_TIMEOUT_IN_MS = 100;
        public static final int BUFFER_SIZE_IN_BYTES = 25600;
    }
}
