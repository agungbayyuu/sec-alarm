package com.example.uas_ppb2_agung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toolbar;

public class help extends Activity {
    ImageView toolbar_about;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_help);
        toolbar_about = findViewById(R.id.help_back);
        toolbar_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(help.this, MainActivity.class);
                startActivity(a);
            }
        });
    }
}
