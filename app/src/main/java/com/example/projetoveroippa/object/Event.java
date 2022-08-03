package com.example.projetoveroippa.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Event {
    public String name;
    public String date;
    public String hour;
    public String description;
    public int message;


    public static class myEventComparator implements Comparator<Event> {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        @Override
        public int compare(Event event, Event event1) {

            try {
                Date DateEvent1 = sdf.parse(event.date + " " + event.hour);
                Date DateEvent2 = sdf.parse(event1.date + " " + event1.hour);
                if (DateEvent1.after(DateEvent2)) {
                    return 1;
                } else {
                    return -1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return 0;
        }
    }

}