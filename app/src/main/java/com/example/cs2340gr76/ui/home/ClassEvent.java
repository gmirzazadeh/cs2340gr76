package com.example.cs2340gr76.ui.home;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClassEvent {
    public static ArrayList<ClassEvent> eventsList = new ArrayList<>();

    public static ArrayList<ClassEvent> eventsForDate(LocalDate date)
    {
        ArrayList<ClassEvent> events = new ArrayList<>();

        for(ClassEvent event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;

    public ClassEvent(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
