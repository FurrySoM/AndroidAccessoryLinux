package com.tsang.usbAccessory;


import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseChatActivity extends AppCompatActivity {
    TextView contentText;

    protected void printLineToUI(final String line) {
        Log.d("DEBUG","printLineToUI is working" + line);
//        contentText.setText(contentText.getText() + "\n" + line);
    }



}
