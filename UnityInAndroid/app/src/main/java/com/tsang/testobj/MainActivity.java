package com.tsang.testobj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tsang.usbAccessory.AccessoryCommunicator;
import com.tsang.usbAccessory.BaseChatActivity;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends BaseChatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AccessoryCommunicator communicator;
    String inputString = "send success";
    private long account = 0;

    AndroidJavaProxyTest mCallback;

//    AndroidJavaProxyTest mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        communicator = new AccessoryCommunicator(this) {
//            @Override
//            public void onReceive(byte[] payload, int length) {
//                try{
//                    receivePayload = payload;
////                    Log.i("DEBUG","onReceive: " + new String(receivePayload, 0, length));
////                    Log.i("DEBUG","onReceive: " + (account++) +"length: " + length);
////                    printLineToUI("host> " + new String(payload, 0, length));
//                } catch (final Exception e) {
//                    Log.e("DEBUG","onReveive Method FAILED");
//                }
//                CallUnityShow("Text (TMP)","GetBytesUnity","");
//                Log.d("UNITY","receivePayload is :" + receivePayload);
//            }
//        };
//        sendString(inputString);

    }

    public void click(View view) {

        Intent intent = new Intent (this,TestUnityActivity.class);
        startActivity(intent);
//        sendString(inputString);
    }

    public void CallUnityShow(String s1, String s2, String s3) {
        UnityPlayer.UnitySendMessage(s1, s2, s3);
    }

//    protected void sendString(String string) {
//        communicator.send(string.getBytes());
//    }

//    public void setCallback(AndroidJavaProxyTest callback){
//        Log.d("@@@", "UnityBatteryEventCallback setCallback start ");
//        mCallback = callback;
//        Log.d("@@@", "UnityBatteryEventCallback setCallback end ");
//        mCallback.success(receivePayload);
//    }

}