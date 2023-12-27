package com.ronakjain.weatherapp.Adapter;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronakjain.weatherapp.DTO.Hours;
import com.ronakjain.weatherapp.MainActivity;
import com.ronakjain.weatherapp.R;
import com.ronakjain.weatherapp.ViewHolder.HoursViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoursAdapter extends RecyclerView.Adapter<HoursViewHolder> {

    public boolean fahrenheit;
    private final List<Hours> hoursList;
    private final MainActivity mainAct;

    public HoursAdapter(List<Hours> hoursList, MainActivity mainAct, boolean fahrenheit) {
        this.hoursList = hoursList;
        this.mainAct = mainAct;
        this.fahrenheit = fahrenheit;
    }

    @NonNull
    @Override
    public HoursViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hours_entry, parent, false);

        return new HoursViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursViewHolder holder, int position) {

        Hours hour = hoursList.get(position);
        holder.day.setText(getFormattedDate(hour.getDatetimeEpoch(), 1));
        holder.time.setText(getFormattedDate(hour.getDatetimeEpoch(), 2));
        holder.temp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"),
                hour.getTemp()));
        holder.desc.setText(hour.getConditions());
        holder.icon.setImageResource(mainAct.getImageIcon(hour.getIcon()));
    }


    @Override
    public int getItemCount() {
        return hoursList.size();
    }

    private String getFormattedDate(long datetimeEpoch, int type) {
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
        SimpleDateFormat day = new SimpleDateFormat("EEEE", Locale.getDefault());
        String timeOnlyStr = timeOnly.format(dateTime);
        String dayStr = day.format(dateTime);
        dayStr = DateUtils.isToday(datetimeEpoch * 1000) ? "Today" : dayStr;
        return type == 1 ? dayStr : timeOnlyStr;
    }
}
