package com.example.nextmunjibus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.text.format.DateFormat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public TextView m_TimeTextView;

    private Scheduler m_Scheduler;

    private Scheduler.Destination m_Destination = Scheduler.Destination.MainCampus;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_TimeTextView = findViewById(R.id.TimeTextView);
        m_Scheduler = new Scheduler();
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

                    String a = DateFormat.format("HH", date).toString();
                    int hours = Integer.parseInt(DateFormat.format("HH", date).toString());
                    int minutes = Integer.parseInt(DateFormat.format("mm", date).toString());
                    int seconds = Integer.parseInt(DateFormat.format("ss", date).toString());

                    int minutesElapsed = hours * 60 + minutes;

                    int nextBus = m_Scheduler.GetNthNearestBus(0, minutesElapsed, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                    int nextBusHours = nextBus / 60;
                    int nextBusMinutes = nextBus - nextBusHours * 60;
                    final String message = "Next In\n" + Integer.toString(nextBus - minutesElapsed) + "\nminutes";

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
    
    public void DestinationChange(View a_View){
        a_View.setScaleX(a_View.getScaleX() * -1);
    }
}
