package com.ronakjain.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronakjain.weatherapp.Adapter.HoursAdapter;
import com.ronakjain.weatherapp.ApiService.WeatherDownloader;
import com.ronakjain.weatherapp.DTO.CurrentConditions;
import com.ronakjain.weatherapp.DTO.Days;
import com.ronakjain.weatherapp.DTO.Hours;
import com.ronakjain.weatherapp.DTO.Weather;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private TextView currDate, currTemp, feelsLike, weatherDesc, windDesc, humidity, uvIndex;
    private TextView mornTemp, afterTemp, evenTemp, nightTemp;
    private TextView mornTempText, afterTempText, evenTempText, nightTempText;
    private TextView sunrise, sunset;

    private TextView visibility;
    private ImageView weatherIcon;

    private HoursAdapter hoursAdapter;

    private String address;
    private SharedPreferences.Editor editor;
    private List<Hours> hoursList = new ArrayList<>();
    private ArrayList<Days> daysArrayList = new ArrayList<>();

    private boolean fahrenheit = true;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
        setDataInvisibility(true);
    }

    public void initializeView() {

        currDate = findViewById(R.id.dateAndTime);
        currTemp = findViewById(R.id.degreeTemp);
        feelsLike = findViewById(R.id.feelsLikeTemp);
        weatherDesc = findViewById(R.id.cloudText);
        windDesc = findViewById(R.id.windText);
        humidity = findViewById(R.id.humidity);
        uvIndex = findViewById(R.id.uvIndex);
        mornTemp = findViewById(R.id.mornTemp);
        afterTemp = findViewById(R.id.afterTemp);
        evenTemp = findViewById(R.id.evenTemp);
        nightTemp = findViewById(R.id.nightTemp);
        mornTempText = findViewById(R.id.mornText);
        afterTempText = findViewById(R.id.afterText);
        evenTempText = findViewById(R.id.ecenText);
        nightTempText = findViewById(R.id.nightText);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        visibility = findViewById(R.id.visibility);
        recyclerView = findViewById(R.id.hoursRecyler);
        weatherIcon = findViewById(R.id.tempImageView);
        swiper = findViewById(R.id.swiper);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        if (!sharedPref.contains("FAHRENHEIT")) {
            editor.putBoolean("FAHRENHEIT", true);
            editor.apply();
        }
        if (!sharedPref.contains("ADDRESS")) {
            editor.putString("ADDRESS", "Chicago, Illinois");
            editor.apply();
        }

        fahrenheit = sharedPref.getBoolean("FAHRENHEIT", true);
        address = sharedPref.getString("ADDRESS", "Chicago, Illinois");

        WeatherDownloader.downloadWeather(this, address, fahrenheit);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        recyclerView = findViewById(R.id.hoursRecyler);
        hoursAdapter = new HoursAdapter(hoursList, this, fahrenheit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(hoursAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu, menu);
        menu.getItem(0).setIcon(fahrenheit ? R.drawable.units_f : R.drawable.units_c);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!checkNetworkConnection()) {
            Toast.makeText(this, "This action can not be perform due to no internet connection", Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }

        if (item.getItemId() == R.id.daysMenu) {
            if (daysArrayList.isEmpty()) {
                Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            Intent intent = new Intent(this, DaysActivity.class);
            intent.putExtra("DAYS", (Serializable) daysArrayList);
            intent.putExtra("UNITS", fahrenheit);
            intent.putExtra("ADDRESS", address);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.unitsMenu) {
            if (daysArrayList.isEmpty()) {
                Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            fahrenheit = !fahrenheit;
            item.setIcon(fahrenheit ? R.drawable.units_f : R.drawable.units_c);
            editor.putBoolean("FAHRENHEIT", fahrenheit);
            editor.apply();
            WeatherDownloader.downloadWeather(this, address, fahrenheit);
            swiper.setRefreshing(true);
            return true;
        } else if (item.getItemId() == R.id.locationMenu) {
            showLocationDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLocationDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        builder.setPositiveButton("OK", (dialog, id) -> {

            if (!checkNetworkConnection()) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            EditText et = view.findViewById(R.id.editTextTextPersonName);
            if (et.getText().toString().isEmpty()) {
                Toast.makeText(this, "City name can't be empty, enter valid city name.", Toast.LENGTH_SHORT).show();
            } else {
                address = et.getText().toString();
                WeatherDownloader.downloadWeather(this, address, fahrenheit);
            }
        });
        builder.setNegativeButton("CANCEL", (dialog, id) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_500));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_500));
    }

    public void updateData(Weather weather) {

        swiper.setRefreshing(false);
        if (!checkNetworkConnection()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            if (currDate.getText().toString().isEmpty()) {
                currDate.setText("No internet connection");
                currDate.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            setDataInvisibility(true);
            editor.remove("ADDRESS");
            editor.apply();
            daysArrayList.clear();
            hoursList.clear();
            return;
        }

        setDataInvisibility(false);
        setTitle(weather.getAddress());
        CurrentConditions currentConditions = weather.getCurrentConditions();
        daysArrayList = weather.getDays();
        ArrayList<Hours> curHours = daysArrayList.get(0).getHours();
        currDate.setText(String.format("%s", getFormattedDate(currentConditions.getDatetimeEpoch(), 1)));
        currTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"),
                currentConditions.getTemp()));
        feelsLike.setText(String.format("Feels Like: %.0f°" + (fahrenheit ? "F" : "C"),
                currentConditions.getFeelslike()));
        weatherDesc.setText(String.format(Locale.getDefault(), "%s (%.0f%% clouds)", currentConditions.getConditions()
                , currentConditions.getCloudcover()));
        windDesc.setText(String.format(Locale.getDefault(), "Winds: %s at %.0f %s gusting to %.0f %s", getDirection(currentConditions.getWinddir())
                , currentConditions.getWindspeed(), (fahrenheit ? "mph" : "kmph"), currentConditions.getWindgust(), (fahrenheit ? "mph" : "kmph")));
        humidity.setText(String.format(Locale.getDefault(), "Humidity: %.0f%%", currentConditions.getHumidity()));
        uvIndex.setText(String.format(Locale.getDefault(), "UV Index: %.0f", currentConditions.getUvindex()));
        visibility.setText(String.format(Locale.getDefault(), "Visibility: %.0f %s", currentConditions.getVisibility(),
                (fahrenheit ? "mi" : "km")));
        mornTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"),
                curHours.get(8).getTemp()));
        afterTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"),
                curHours.get(13).getTemp()));
        evenTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"),
                curHours.get(17).getTemp()));
        nightTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"),
                curHours.get(23).getTemp()));
        sunrise.setText(String.format("Sunrise: %s", getFormattedDate(currentConditions.getSunriseEpoch(), 2)));
        sunset.setText(String.format("Sunset: %s", getFormattedDate(currentConditions.getSunsetEpoch(), 2)));
        int iconId = getImageIcon(currentConditions.getIcon());
        weatherIcon.setImageResource(iconId);
        hoursList.clear();
        ArrayList<Hours> newList = new ArrayList<>();
        List<Hours> hoursStream = daysArrayList.get(0).getHours().stream().filter(x ->
                x.getDatetimeEpoch() > currentConditions.getDatetimeEpoch()).collect(Collectors.toList());
        newList.addAll(hoursStream);
        newList.addAll(daysArrayList.get(1).getHours());
        newList.addAll(daysArrayList.get(2).getHours());
        newList.addAll(daysArrayList.get(3).getHours());
        hoursList.addAll(newList);
        hoursAdapter.fahrenheit = fahrenheit;
        hoursAdapter.notifyItemInserted(hoursList.size());
        hoursAdapter.notifyDataSetChanged();
        recyclerView.setBackgroundColor(Color.argb(40, 255, 255, 255));
        editor.putString("ADDRESS", address);
        editor.apply();
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private String getFormattedDate(long datetimeEpoch, int type) {
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat fullDate =
                new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String fullDateStr = fullDate.format(dateTime);
        String timeOnlyStr = timeOnly.format(dateTime);
        return type == 1 ? fullDateStr : timeOnlyStr;
    }

    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }

    public int getImageIcon(String icon) {
        icon = icon.replace("-", "_");
        int iconID =
                getResources().getIdentifier(icon, "drawable", getPackageName());
        if (iconID == 0) {
            iconID = 233;
        }
        return iconID;
    }

    private void refreshData() {
        if (!checkNetworkConnection()) {
            swiper.setRefreshing(false);
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            WeatherDownloader.downloadWeather(this, address, fahrenheit);
        }
    }

    private void setDataInvisibility(Boolean flag) {

        int vis = !flag ? View.VISIBLE : View.INVISIBLE;
        currDate.setVisibility(vis);
        currTemp.setVisibility(vis);
        feelsLike.setVisibility(vis);
        weatherDesc.setVisibility(vis);
        windDesc.setVisibility(vis);
        humidity.setVisibility(vis);
        uvIndex.setVisibility(vis);
        mornTemp.setVisibility(vis);
        afterTemp.setVisibility(vis);
        evenTemp.setVisibility(vis);
        nightTemp.setVisibility(vis);
        mornTempText.setVisibility(vis);
        afterTempText.setVisibility(vis);
        evenTempText.setVisibility(vis);
        nightTempText.setVisibility(vis);
        sunrise.setVisibility(vis);
        sunset.setVisibility(vis);
        visibility.setVisibility(vis);
        weatherIcon.setVisibility(vis);
        recyclerView.setVisibility(vis);
        if (flag) {
            setTitle(R.string.app_name);
        } else {
            setTitle(address);
        }
    }
}