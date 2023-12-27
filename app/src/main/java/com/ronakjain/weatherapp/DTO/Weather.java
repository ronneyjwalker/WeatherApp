package com.ronakjain.weatherapp.DTO;

import java.util.ArrayList;

public class Weather {

    private final String address;
    private final String timezone;
    private final int tzoffset;
    private final ArrayList<Days> days;
    private final CurrentConditions currentConditions;

    public Weather(String address, String timezone, int tzoffset, ArrayList<Days> days, CurrentConditions currentConditions) {
        this.address = address;
        this.timezone = timezone;
        this.tzoffset = tzoffset;
        this.days = days;
        this.currentConditions = currentConditions;
    }

    public String getAddress() {
        return address;
    }

    String getTimezone() {
        return timezone;
    }

    int getTzoffset() {
        return tzoffset;
    }

    public ArrayList<Days> getDays() {
        return days;
    }

    public CurrentConditions getCurrentConditions() {
        return currentConditions;
    }
}
