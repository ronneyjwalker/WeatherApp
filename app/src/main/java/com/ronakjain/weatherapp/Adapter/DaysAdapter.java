package com.ronakjain.weatherapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronakjain.weatherapp.DTO.Days;
import com.ronakjain.weatherapp.DaysActivity;
import com.ronakjain.weatherapp.R;
import com.ronakjain.weatherapp.ViewHolder.DaysViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaysAdapter extends RecyclerView.Adapter<DaysViewHolder> {

    private boolean fahrenheit;
    private final List<Days> daysList;
    private final DaysActivity daysAct;

    public DaysAdapter(List<Days> daysList, DaysActivity daysAct, boolean fahrenheit) {
        this.daysList = daysList;
        this.daysAct = daysAct;
        this.fahrenheit = fahrenheit;
    }

    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.days_entry, parent, false);

        return new DaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {

        Days day = daysList.get(position);
        holder.date.setText(getFormattedDate(day.getDatetimeEpoch()));
        holder.icon.setImageResource(daysAct.getImageIcon(day.getIcon()));
        holder.desc.setText(day.getDescription());
        holder.temp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C") + "/%.0f°" + (fahrenheit ? "F" : "C"),
                day.getTempmax(), day.getTempmin()));
        holder.prec.setText(String.format(Locale.getDefault(), "(%.0f%% precip.)", day.getPrecipprob()));
        holder.uvIndex.setText(String.format(Locale.getDefault(), "UV Index: %.0f", day.getUvindex()));
        holder.mornTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"), day.getHours().get(8).getTemp()));
        holder.afterTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"), day.getHours().get(13).getTemp()));
        holder.evenTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"), day.getHours().get(17).getTemp()));
        holder.nightTemp.setText(String.format("%.0f°" + (fahrenheit ? "F" : "C"), day.getHours().get(23).getTemp()));
    }


    @Override
    public int getItemCount() {
        return daysList.size();
    }

    private String getFormattedDate(long datetimeEpoch) {
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat dayDate = new SimpleDateFormat(" EEEE, MM/dd", Locale.getDefault());
        String dayDateStr = dayDate.format(dateTime);
        return dayDateStr;
    }

}
