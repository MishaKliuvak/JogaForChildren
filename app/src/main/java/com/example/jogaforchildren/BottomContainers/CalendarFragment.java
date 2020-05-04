package com.example.jogaforchildren.BottomContainers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.jogaforchildren.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    private View v;
    private CalendarView calendarView;

    public CalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        //CalendarView calendarView = v.findViewById(R.id.calendar);
        CalendarView materialCalendarView = v.findViewById(R.id.calendar);
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 4, 4);
        events.add(new EventDay(calendar, R.drawable.buttonstyle));

        materialCalendarView.setEvents(events);

        return v;
    }
}
