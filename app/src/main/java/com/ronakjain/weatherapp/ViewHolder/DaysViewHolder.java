package com.ronakjain.weatherapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronakjain.weatherapp.R;

public class DaysViewHolder extends RecyclerView.ViewHolder {

    public TextView date, temp, desc, prec, uvIndex, mornTemp, afterTemp, evenTemp, nightTemp, mornTempText, afterTempText, evenTempText, nightTempText;
    public ImageView icon;

    public DaysViewHolder(View view) {
        super(view);
        date = view.findViewById(R.id.dateDays);
        temp = view.findViewById(R.id.tempDays);
        desc = view.findViewById(R.id.descDays);
        icon = view.findViewById(R.id.daysImageView);
        prec = view.findViewById(R.id.precDays);
        uvIndex = view.findViewById(R.id.uvIndexDays);
        mornTemp = view.findViewById(R.id.mornTempDays);
        afterTemp = view.findViewById(R.id.afterTempDays);
        evenTemp = view.findViewById(R.id.evenTempDays);
        nightTemp = view.findViewById(R.id.nightTempDays);
        mornTempText = view.findViewById(R.id.mornTextDays);
        afterTempText = view.findViewById(R.id.afterTextDays);
        evenTempText = view.findViewById(R.id.evenTextDays);
        nightTempText = view.findViewById(R.id.nightTextDays);
    }
}
