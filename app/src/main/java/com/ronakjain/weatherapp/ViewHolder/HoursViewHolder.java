package com.ronakjain.weatherapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronakjain.weatherapp.R;

public class HoursViewHolder extends RecyclerView.ViewHolder {

    public TextView day, time, temp, desc;
    public ImageView icon;

    public HoursViewHolder(View view) {
        super(view);
        day = view.findViewById(R.id.dayHours);
        time = view.findViewById(R.id.timeHours);
        temp = view.findViewById(R.id.tempHours);
        desc = view.findViewById(R.id.descHours);
        icon = view.findViewById(R.id.iconHours);
    }
}
