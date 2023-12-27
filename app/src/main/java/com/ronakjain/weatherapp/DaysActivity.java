package com.ronakjain.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronakjain.weatherapp.Adapter.DaysAdapter;
import com.ronakjain.weatherapp.DTO.Days;

import java.util.ArrayList;
import java.util.List;

public class DaysActivity extends AppCompatActivity {

    private boolean fahrenheit;
    private DaysAdapter daysAdapter;
    private List<Days> daysList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        Intent intent = getIntent();
        if (intent.hasExtra("DAYS")) {
            daysList = (ArrayList<Days>) intent.getSerializableExtra("DAYS");
            fahrenheit = intent.getBooleanExtra("UNITS", false);
            setTitle(intent.getStringExtra("ADDRESS") + " 15 Day");
        }

        recyclerView = findViewById(R.id.daysRecycler);
        daysAdapter = new DaysAdapter(daysList, this, fahrenheit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(daysAdapter);
        daysAdapter.notifyItemInserted(daysList.size());
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
}