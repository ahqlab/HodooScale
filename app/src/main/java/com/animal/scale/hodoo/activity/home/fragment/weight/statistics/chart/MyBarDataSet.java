package com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart;

import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.Calendar;
import java.util.List;

public class MyBarDataSet extends BarDataSet {

    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        Calendar calendar = Calendar.getInstance();
        if (getEntryForIndex(index).getX() == (calendar.get(Calendar.DAY_OF_WEEK) - 2)) {
            return mColors.get(1);
        }
        return mColors.get(0);
    }
}
