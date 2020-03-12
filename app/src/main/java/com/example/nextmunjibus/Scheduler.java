package com.example.nextmunjibus;

import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {


    //private boolean m_IsHoliday = false;
    
    public enum Destination{
        MunjiCampus,
        MainCampus
    }

    private ArrayList<Integer> m_MonTimeSlots, m_TueFriTimeSlots, m_SatTimeSlots, m_SunTimeSlots;

    public Scheduler(){
        CreateDictionaries();
    }

    public int GetNthNearestBus(int a_Nth, int a_TimeInMinutes ,String a_WeekDay){
        int idx = GetUpperLimitIndex(a_TimeInMinutes, m_WeekTimeSlots.get(a_WeekDay));
        return m_WeekTimeSlots.get(a_WeekDay).get(a_Nth + idx);
    }

    private HashMap<String, ArrayList<Integer>> m_WeekTimeSlots;

    private void CreateDictionaries(){
        CreateWeekDayTimeSlotsArray();
        CreateWeekEndTimeSlotsArray();

        m_WeekTimeSlots = new HashMap<String, ArrayList<Integer>>(){
            {
                put("monday", m_MonTimeSlots);
                put("tuesday", m_TueFriTimeSlots);
                put("wednesday", m_TueFriTimeSlots);
                put("thursday", m_TueFriTimeSlots);
                put("friday", m_TueFriTimeSlots);
                put("saturday", m_SatTimeSlots);
                put("sunday", m_SunTimeSlots);
            }
        };
    }

    private void CreateWeekDayTimeSlotsArray(){
        ArrayList<Integer> commonTimeSlots = new ArrayList<>();
        commonTimeSlots.ensureCapacity(27);

        // TODO: Make Array Creation more Compact
        // 7:30 - 9:00, 30 min interval
        for (int i = 0; i < 4; i++){
            commonTimeSlots.add((7 * 60 + 30) + 30 * i);
        }

        // 9:40 - 11:10, 30 min
        for (int i = 0; i < 4; i++){
            commonTimeSlots.add((9 * 60 + 40) + 30 * i);
        }

        // 12:10
        commonTimeSlots.add(12 * 60 + 10);


        // 13:40 - 16:40, 60 min
        for (int i = 0; i < 4; i++){
            commonTimeSlots.add((13 * 60 + 40) + 60 * i);
        }

        // 17:10 - 19:40, 30 min
        for (int i = 0; i < 6; i++){
            commonTimeSlots.add((17 * 60 + 10) + 30 * i);
        }

        //20:40 - 21:40, 30 min
        for (int i = 0; i < 3; i++){
            commonTimeSlots.add((20 * 60 + 40) + 30 * i);
        }

        // 22:40 - 23:40

        for (int i = 0; i < 2; i++){
            commonTimeSlots.add((22 * 60 + 40) + 60 * i);
        }

        m_MonTimeSlots = new ArrayList<>(commonTimeSlots);
        m_TueFriTimeSlots = new ArrayList<>(commonTimeSlots);

        // Tuesday - Friday, 0:40 - 2:40, 60 min

        for (int i = 0; i < 3; i++){
            m_TueFriTimeSlots.add(0, 2 * 60 + 40 - 60 * i);
        }

        // Monday, 0:40 and 2:10
        for (int i = 0; i < 2; i++){
            m_MonTimeSlots.add(0, 2 * 60 + 10 - 90 * i);
        }
    }

    private void CreateWeekEndTimeSlotsArray(){
        ArrayList<Integer> commonTimeSlots = new ArrayList<>();
        commonTimeSlots.ensureCapacity(21);

        // 8:10 - 23:10, 90 min
        for (int i = 0; i < 11; i++) {
            commonTimeSlots.add((8 * 60 + 10) + 90 * i);
        }

        m_SatTimeSlots = new ArrayList<>(commonTimeSlots);
        m_SunTimeSlots = new ArrayList<>(commonTimeSlots);

        // Saturday, 0:40 - 2:40, 60 min
        for (int i = 0; i < 3; i++) {
            m_SatTimeSlots.add(0, 2 * 60 + 40 - 60 * i);
        }

        // Sunday, 0:40 - 2:10
        for (int i = 0; i < 2; i++) {
            m_SunTimeSlots.add(0, 2 * 60 + 10 - 90 * i);
        }
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
