package com.dohman.sdur;

/**
 * Created by Dohman on 2018-03-21.
 */

public class Event {
    private int id;
    private String name;
    private String text;
    private String date;
    private String clock;
    private String place;
    private String link;

    public Event() {
    }

    public Event(String name, String text, String date, String clock, String place, String link) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.clock = clock;
        this.place = place;
        this.link = link;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
