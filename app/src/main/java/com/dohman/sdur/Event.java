package com.dohman.sdur;

/**
 * Created by Dohman on 2018-03-21.
 */

public class Event {
    private int id;
    private String namn;
    private String infotext;
    private String datum;
    private String tid;
    private String plats;
    private String laenk;

    public Event() {
    }

    public Event(String namn, String infotext, String datum, String tid, String plats, String laenk) {
        this.namn = namn;
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

    public String getNamn() {
        return namn;
    }

    public String getInfotext() {
        return infotext;
    }

    public String getDatum() {
        return datum;
    }

    public String getTid() {
        return tid;
    }

    public String getPlats() {
        return plats;
    }

    public String getLaenk() {
        return laenk;
    }
}
