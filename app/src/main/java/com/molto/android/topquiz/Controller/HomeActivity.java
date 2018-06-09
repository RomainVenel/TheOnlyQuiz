package com.molto.android.topquiz.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.molto.android.topquiz.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Romain on 05/06/2018.
 */

public class HomeActivity extends AppCompatActivity {

    private ProgressDialog nDialog;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tx = (TextView)findViewById(R.id.activity_home_first_txt);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/home.TTF");

        tx.setTypeface(custom_font);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
