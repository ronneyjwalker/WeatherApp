package com.ronakjain.weatherapp.DTO;

public class CurrentConditions {

    private final long datetimeEpoch;
    private final Double temp;
    private final Double feelslike;
    private final Double humidity;
    private final Double windgust;
    private final Double winddir;
    private final Double visibility;
    private final Double cloudcover;
    private final Double uvindex;
    private final String conditions;
    private final String icon;
    private final long sunriseEpoch;
    private final long sunsetEpoch;
    private final Double windspeed;

    public Double getWindspeed() {
        return windspeed;
    }

    public CurrentConditions(long datetimeEpoch, Double temp, Double feelslike, Double humidity, Double windgust,
                             Double winddir, Double visibility, Double cloudcover, Double uvindex,
                             String conditions, String icon, long sunriseEpoch, long sunsetEpoch, Double windspeed) {
        this.datetimeEpoch = datetimeEpoch;
        this.temp = temp;
        this.feelslike = feelslike;
        this.humidity = humidity;
        this.windgust = windgust;
        this.winddir = winddir;
        this.visibility = visibility;
        this.cloudcover = cloudcover;
        this.uvindex = uvindex;
        this.conditions = conditions;
        this.icon = icon;
        this.sunriseEpoch = sunriseEpoch;
        this.sunsetEpoch = sunsetEpoch;
        this.windspeed = windspeed;
    }

    public long getDatetimeEpoch() {
        return datetimeEpoch;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getFeelslike() {
        return feelslike;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getWindgust() {
        return windgust;
    }

    public Double getWinddir() {
        return winddir;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Double getCloudcover() {
        return cloudcover;
    }

    public Double getUvindex() {
        return uvindex;
    }

    public String getConditions() {
        return conditions;
    }

    public String getIcon() {
        return icon;
    }

    public long getSunriseEpoch() {
        return sunriseEpoch;
    }

    public long getSunsetEpoch() {
        return sunsetEpoch;
    }
}
