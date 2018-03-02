package com.example.dawakpharmacy.model;

public class Location {

    private double lat;
    private double lang;

    public Location(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }
}
