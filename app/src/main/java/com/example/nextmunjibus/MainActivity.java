package com.example.nextmunjibus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.widget.TextView;
import android.text.format.DateFormat;

import java.sql.Time;
import java.util.Date;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity {
    public TextView m_TimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_TimeTextView = findViewById(R.id.TimeTextView);
        UpdateTime.start();
    }

    Thread UpdateTime = new Thread(){
        int waitAmount = 1000;
        @Override
        public void run() {
            while(!isInterrupted()){
                try{
                    Thread.sleep(waitAmount);
                    Date date = Calendar.getInstance(TimeZone.getDefault()).getTime();

                    final String message = DateFormat.format("w", date).toString();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run(){
                            m_TimeTextView.setText(message);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
