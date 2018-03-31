package com.dohman.sdur;

/**
 * Created by Dohman on 2018-03-21.
 */

public class Event {
    private int id;
    private String name;
    private String infotext;
    private String datum;
    private String tid;
    private String plats;
    private String laenk;

    public Event() {
    }

    public Event(String name, String infotext, String datum, String tid, String plats, String laenk) {
        this.name = name;
        this.infotext = infotext;
        this.datum = datum;
        this.tid = tid;
        this.plats = plats;
        this.laenk = laenk;
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

    public String getInfotext() {
        return infotext;
    }

    public void setInfotext(String infotext) {
        this.infotext = infotext;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPlats() {
        return plats;
    }

    public void setPlats(String plats) {
        this.plats = plats;
    }

    public String getLaenk() {
        return laenk;
    }

    public void setLaenk(String laenk) {
        this.laenk = laenk;
    }
}
