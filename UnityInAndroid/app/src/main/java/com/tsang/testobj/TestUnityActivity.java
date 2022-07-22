package com.tsang.testobj;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tsang.usbAccessory.AccessoryCommunicator;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class TestUnityActivity extends UnityPlayerActivity {

    AndroidJavaProxyTest mCallback;
    String stras = "";

    String string = "hello world";
    //Convert to byte[]
    byte[] receivePayload;
    String str;

    public AccessoryCommunicator communicator;
    private long account = 0;

    String new_str = new String();

    String inputString = "send success";


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_unity);
        LinearLayout ll_unity_container = findViewById(R.id.ll_unity_container);
        View unity_view = mUnityPlayer.getView();
        ll_unity_container.addView(unity_view);

        Button change = findViewById(R.id.change);


        communicator = new AccessoryCommunicator(this) {
            @Override
            public void onReceive(byte[] payload, int length) {
                try{
//                    receivePayload = payload;
                    str = new String(payload,0 ,length);
                    stras = stras.concat(str);

//                    Log.d("DEBUG","str : " + str);
//                    CallUnityShow("Text (TMP)","GetBytesUnity","");
//                    Log.i("DEBUG","onReceive: " + new String(receivePayload, 0, length));
                    Log.i("DEBUG","onReceive: " + (account++) +"length: " + length);
                    Log.i("DEBUG",stras);
//                    printLineToUI("host> " + new String(payload, 0, length));
                } catch (final Exception e) {
                    Log.e("DEBUG", "onReveive Method FAILED");
                }
//                Log.d("DEBUG","receivePayload is :" + receivePayload);
            }
        };
        sendString(inputString);
//        CallUnityShow("Text (TMP)","GetBytesUnity","");





        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CallUnityShow("Text (TMP)","ShowText","test_data");
                CallUnityShow("Text (TMP)","GetBytesUnity","");
////                Log.d("DEBUG", "bytes is : "+receivePayload);
////                CallUnityShow("pointcloudrender","ShowDot","(2,2,2)");
//                sendString(inputString);
            }
        });
    }


    public void click(View view){
        callMainActivity();
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        // 添加返回键返回 MainActivity
        if(i == KeyEvent.KEYCODE_BACK){
            callMainActivity();
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void callMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void CallUnityShow(String s1, String s2, String s3) {
        UnityPlayer.UnitySendMessage(s1, s2, s3);
    }

    public void setCallback(AndroidJavaProxyTest callback){
        Log.d("@@@", "UnityBatteryEventCallback setCallback start ");
        mCallback = callback;
        Log.d("@@@", "UnityBatteryEventCallback setCallback end ");
        mCallback.success(stras);
    }

    protected void sendString(String string) {
        communicator.send(string.getBytes());
    }



}
