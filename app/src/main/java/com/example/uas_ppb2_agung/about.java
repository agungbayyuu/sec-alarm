package com.example.uas_ppb2_agung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class about extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_about);
    }
}
