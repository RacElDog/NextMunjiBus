package com.example.nextmunjibus;

import android.icu.util.Calendar;
import android.icu.util.CurrencyAmount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class Scheduler {


    //private boolean m_IsHoliday = false;
    
    public enum Destination{
        MunjiCampus,
        MainCampus
    }

    private ArrayList<Integer> m_WeekDayTimeSlots, m_WeekEndTimeSlots;

    public Scheduler(){
        m_WeekDayTimeSlots = new ArrayList<>(
                Arrays.asList(
                        7 * 60 + 30, 8 * 60 + 30, 9 * 60,
                        9 * 60 + 40, 10 * 60 + 10, 10 * 60 + 40, 11 * 60 + 10,
                        12 * 60 + 10, 13 * 60 + 40, 14 * 60 + 40, 15 * 60 + 40, 16 * 60 + 40,
                        17 * 60 + 10, 17 * 60 + 40, 18 * 60 + 10, 18 * 60 + 40, 19 * 60 + 10, 19 * 60 + 40, 20 * 60 + 40, 21 * 60 + 10, 21 * 60 + 40, 22 * 60 + 10, 22 * 60 + 40,
                        23 * 60 + 40, 24 * 60 + 40, 25 * 60 + 40, 26 * 60 + 40
                )
        );

        m_WeekEndTimeSlots = new ArrayList<>(
                Arrays.asList(
                        8 * 60 + 10, 9 * 60 + 40, 11 * 60 + 10, 12 * 60 + 40, 14 * 60 + 10, 15 * 60 + 40, 17 * 60 + 10,
                        18 * 60 + 40, 20 * 60 + 10, 21 * 60 + 40, 23 * 60 + 10, 24 * 60 + 40, 26 * 60 + 10
                )
        );
    }

    public int GetNthNearestBus(Destination a_Destination ,int a_Nth, int a_TimeInMinutes ,int a_WeekDay){
        if (a_Destination == Destination.MunjiCampus){
            a_TimeInMinutes -= 20;
        }
        ArrayList<Integer> dayTimeSlot;
        switch (a_WeekDay){
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                if (a_TimeInMinutes > 5 * 60){
                    dayTimeSlot = m_WeekEndTimeSlots;
                    break;
                }
                else{
                    dayTimeSlot = m_WeekDayTimeSlots;
                    a_TimeInMinutes += 24 * 60;
                    break;
                }
            case Calendar.MONDAY:
                if (a_TimeInMinutes > 5 * 60){
                    dayTimeSlot = m_WeekDayTimeSlots;
                    break;
                }
                else{
                    dayTimeSlot = m_WeekEndTimeSlots;
                    a_TimeInMinutes += 24 * 60;
                    break;
                }
            default:
                dayTimeSlot = m_WeekDayTimeSlots;
        }
        int idx= GetUpperLimitIndex(a_TimeInMinutes, dayTimeSlot);
        int ret = dayTimeSlot.get(a_Nth + idx);
        if (a_Destination == Destination.MunjiCampus){
            ret += 20;
        }
        return ret;
    }



    private int GetUpperLimitIndex(int a_Target, ArrayList<Integer> a_List){
        int idx = 0;
        for (; idx < a_List.size(); idx++) {
            if (a_List.get(idx) >= a_Target)
                break;
        }
        return idx;
    }
}
