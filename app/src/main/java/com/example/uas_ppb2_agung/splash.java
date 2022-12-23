package com.example.uas_ppb2_agung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class splash extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        Thread timer = new Thread()
        {
            public void run()
            {
                try {
                    sleep(4000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                    Intent m = new Intent(splash.this, MainActivity.class);
                    startActivityForResult(m,0);
                }
            }
        };
        timer.start();
    }
}
