package com.example.bai2;

public class Event {
    private String name;
    private String place;
    private String time;
    private boolean check;

    public Event() {

    }

    public Event(String name, String place, String time) {
        this.name = name;
        this.place = place;
        this.time = time;
        this.check = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getCheck() {
        return check;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
