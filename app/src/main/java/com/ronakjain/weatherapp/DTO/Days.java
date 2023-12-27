package com.ronakjain.weatherapp.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class Days implements Serializable {

    private final long datetimeEpoch;
    private final Double tempmax;
    private final Double tempmin;
    private final Double precipprob;
    private final Double uvindex;
    private final String description;
    private final String icon;
    private final ArrayList<Hours> hours;

    public Days(long datetimeEpoch, Double tempmax, Double tempmin, Double precipprob, Double uvindex, String description, String icon, ArrayList<Hours> hours) {
        this.datetimeEpoch = datetimeEpoch;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
        this.precipprob = precipprob;
        this.uvindex = uvindex;
        this.description = description;
        this.icon = icon;
        this.hours = hours;
    }

    public long getDatetimeEpoch() {
        return datetimeEpoch;
    }

    public Double getTempmax() {
        return tempmax;
    }

    public Double getTempmin() {
        return tempmin;
    }

    public Double getPrecipprob() {
        return precipprob;
    }

    public Double getUvindex() {
        return uvindex;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<Hours> getHours() {
        return hours;
    }
}
