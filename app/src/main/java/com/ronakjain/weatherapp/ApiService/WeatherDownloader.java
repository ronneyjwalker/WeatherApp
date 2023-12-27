package com.ronakjain.weatherapp.ApiService;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ronakjain.weatherapp.DTO.CurrentConditions;
import com.ronakjain.weatherapp.DTO.Days;
import com.ronakjain.weatherapp.DTO.Hours;
import com.ronakjain.weatherapp.DTO.Weather;
import com.ronakjain.weatherapp.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherDownloader {

    private static MainActivity mainActivity;

    private static final String TAG = "WeatherDownloader ";
    private static String weatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private static final String myAPIKey = "4SJE6J2NZR5JQFANCLYKR6XXL";

    private static RequestQueue queue;
    private static Weather weatherObj;

    public static void downloadWeather(MainActivity mainActivityIn,
                                       String city, boolean fahrenheit) {

        mainActivity = mainActivityIn;

        queue = Volley.newRequestQueue(mainActivity);

        String newURL = weatherURL + city;
        Uri.Builder buildURL = Uri.parse(newURL).buildUpon();

        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("unitGroup", (fahrenheit ? "us" : "metric"));
        buildURL.appendQueryParameter("key", myAPIKey);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener =
                response -> parseJSON(response.toString());

        Response.ErrorListener error =
                error1 -> mainActivity.updateData(null);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);

        queue.add(jsonObjectRequest);
    }


    private static void parseJSON(String s) {

        try {
            JSONObject jsonMainObject = new JSONObject(s);

            JSONArray jsonDays = jsonMainObject.getJSONArray("days");
            ArrayList<Days> daysArrayList = new ArrayList<Days>();
            for (int i=0;i<jsonDays.length();i++){
                JSONObject jsonDayObject = jsonDays.getJSONObject(i);
                long datetimeEpoch = jsonDayObject.getLong("datetimeEpoch");
                Double tempmax = jsonDayObject.getDouble("tempmax");
                Double tempmin = jsonDayObject.getDouble("tempmin");
                Double precipprob = jsonDayObject.getDouble("precipprob");
                Double uvindex = jsonDayObject.getDouble("uvindex");
                String description = jsonDayObject.getString("description");
                String icon = jsonDayObject.getString("icon");

                ArrayList<Hours> hoursArrayList = new ArrayList<Hours>();
                JSONArray jsonHours = jsonDayObject.getJSONArray("hours");
                for (int j=0;j<jsonHours.length();j++) {
                    JSONObject jsonHourObject = jsonHours.getJSONObject(j);
                    long datetimeEpochHour = jsonHourObject.getLong("datetimeEpoch");
                    Double tempHour = jsonHourObject.getDouble("temp");
                    String descriptionHour = jsonHourObject.getString("conditions");
                    String iconHour = jsonHourObject.getString("icon");

                    Hours hour = new Hours(datetimeEpochHour,tempHour,descriptionHour,iconHour);
                    hoursArrayList.add(hour);
                }

                Days day = new Days(datetimeEpoch,tempmax,tempmin,precipprob,uvindex,description,icon,hoursArrayList);
                daysArrayList.add(day);
            }

            int tzoffset = jsonMainObject.getInt("tzoffset");
            String address = jsonMainObject.getString("address");
            String timezone = jsonMainObject.getString("timezone");

            JSONObject jsonCurrentConditions = jsonMainObject.getJSONObject("currentConditions");
            long datetimeEpoch = jsonCurrentConditions.getLong("datetimeEpoch");
            Double temp = jsonCurrentConditions.getDouble("temp");
            Double feelslike = jsonCurrentConditions.getDouble("feelslike");
            Double humidity = jsonCurrentConditions.getDouble("humidity");
            Double windspeed = jsonCurrentConditions.getDouble("windspeed");
            Double windgust = windspeed;
            if (jsonCurrentConditions.get("windgust") == "null")
                windgust = jsonCurrentConditions.getDouble("windgust");
            Double winddir = jsonCurrentConditions.getDouble("winddir");
            Double visibility = jsonCurrentConditions.getDouble("visibility");
            Double cloudcover = jsonCurrentConditions.getDouble("cloudcover");
            Double uvindex = jsonCurrentConditions.getDouble("uvindex");
            String conditions = jsonCurrentConditions.getString("conditions");
            String icon = jsonCurrentConditions.getString("icon");
            long sunriseEpoch = jsonCurrentConditions.getLong("sunriseEpoch");
            long sunsetEpoch = jsonCurrentConditions.getLong("sunsetEpoch");

            CurrentConditions currentConditions = new CurrentConditions(datetimeEpoch,temp,feelslike,humidity,windgust
                    ,winddir,visibility,cloudcover,uvindex,conditions,icon,sunriseEpoch,sunsetEpoch, windspeed);

            weatherObj = new Weather(address,timezone,tzoffset,daysArrayList,currentConditions);
            mainActivity.updateData(weatherObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
