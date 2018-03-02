package com.example.dawakpharmacy.model;

import com.google.firebase.database.Exclude;

public class Pharmacy {

    private String id;
    private String name;
    private String email;
    private String password;
    private String openDate;
    private String closeDate;
    private String phone;
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(double lat, double lang) {
        this.location = new Location(lat, lang);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", phone='" + phone + '\'' +
                ", location=" + location +
                '}';
    }
}
