package com.example.nextmunjibus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.text.format.DateFormat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public TextView m_TimeTextView;
    private Scheduler m_Scheduler;
    private Scheduler.Destination m_Destination = Scheduler.Destination.MainCampus;
    private Handler m_Handler;
    private int m_WaitFor, m_Seconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_TimeTextView = findViewById(R.id.TimeTextView);
        m_Scheduler = new Scheduler();
        m_Handler = new Handler();
        InitializeTimer(Scheduler.Destination.MunjiCampus);
    }
    @Override
    protected void onResume() {
        super.onResume();
        InitializeTimer(m_Destination);
    }

    Runnable UpdateRemainingTime = new Runnable() {
        @Override
        public void run() {
            if (m_WaitFor > 1){
                final String message = "Next In\n" + Integer.toString(m_WaitFor) + "\n minutes";
                m_TimeTextView.setText(message);
                m_WaitFor--;
                m_Handler.postDelayed(this, 60000);
            }
            else if (m_WaitFor == 1 && m_Seconds > 1) {
                final String message = Integer.toString(m_Seconds) + "\n Seconds";
                m_TimeTextView.setText(message);
                m_Seconds--;
                m_Handler.postDelayed(this, 1000);
            }
            else if (m_WaitFor == 1 && m_Seconds == 1){
                final String message = "Arrived";
                m_TimeTextView.setText(message);
                m_Handler.postDelayed(this, 5000);
                m_Seconds--;
            }
            else {
                InitializeTimer(m_Destination);
            }
        }
    };
    
    public void ChangeDestination(View a_View){
        a_View.setScaleX(a_View.getScaleX() * -1);
        if (m_Destination == Scheduler.Destination.MunjiCampus){
            m_Destination = Scheduler.Destination.MainCampus;
        }
        else
            m_Destination = Scheduler.Destination.MunjiCampus;
        InitializeTimer(m_Destination);
    }

    private void InitializeTimer(Scheduler.Destination a_Destination){
        try{
            m_Handler.removeCallbacks(UpdateRemainingTime);
        }
        catch (Exception e){
            Log.i("Initialize Timer", "Remove Failed");
            e.printStackTrace();
        }

        Date date = Calendar.getInstance(TimeZone.getDefault()).getTime();
        Log.i("Initialize timer date", date.toString());
        int hours = Integer.parseInt(DateFormat.format("HH", date).toString());
        int minutes = Integer.parseInt(DateFormat.format("mm", date).toString());

        Log.i("Initialize timer time", hours + ":" + minutes);

        int minutesElapsed = hours * 60 + minutes;

        int nextBus = m_Scheduler.GetNthNearestBus(a_Destination,0, minutesElapsed, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        Log.i("Initialize timer wait for", String.valueOf(nextBus));

        m_WaitFor = nextBus - minutesElapsed;
        m_Seconds = 60;

        m_Handler.post(UpdateRemainingTime);
    }
}
