package com.example.bai2;

public class Event {
    private int id;
    private String name;
    private String place;
    private String dateTime;
    private boolean check;

    public Event(String name, String place, String dateTime) {
        this.name = name;
        this.place = place;
        this.dateTime = dateTime;
        this.check = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}