package com.example.lyz.uniquefilm.Information;

import java.util.ArrayList;

/**
 * Created by lyz on 18-1-3.
 */

public class CinemaInfo {

    public String uid;
    public String name;
    public String telephone;
    public String address;
    public String rate;
    public locate location;
    public ArrayList<timetable> time_table;

    public class locate{
        public String lng;
        public String lat;
    }

    public class timetable{
        public String time;
        public String date;
        public String lan;
        public String type;
        public String price;
    }

    public String getName() {
        return name;
    }

    public ArrayList<timetable> getTime_table() {
        return time_table;
    }

    public String getAddress() {

        return address;
    }
}
