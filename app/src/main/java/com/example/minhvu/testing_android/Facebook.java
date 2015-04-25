package com.example.minhvu.testing_android;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.FacebookSdk;

/**
 * Created by minhvu on 4/23/15.
 */
public class Facebook extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
