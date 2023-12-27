package com.ronakjain.weatherapp.DTO;

import java.io.Serializable;

public class Hours implements Serializable {

    private final long datetimeEpoch;
    private final Double temp;
    private final String conditions;
    private final String icon;

    public Hours(long datetimeEpoch, Double temp, String conditions, String icon) {
        this.datetimeEpoch = datetimeEpoch;
        this.temp = temp;
        this.conditions = conditions;
        this.icon = icon;
    }

    public long getDatetimeEpoch() {
        return datetimeEpoch;
    }

    public Double getTemp() {
        return temp;
    }

    public String getConditions() {
        return conditions;
    }

    public String getIcon() {
        return icon;
    }
}
