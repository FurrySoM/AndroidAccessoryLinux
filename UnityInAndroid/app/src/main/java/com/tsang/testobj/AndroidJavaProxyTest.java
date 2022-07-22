package com.tsang.testobj;

import android.content.Intent;

public interface AndroidJavaProxyTest {
    void success(String payload);
    void onReceive(byte[] payload, int length);
}
