package com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart;

import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthBarDataSet extends BarDataSet {

    private String month;

    public MonthBarDataSet(List<BarEntry> yVals, String label, String month) {
        super(yVals, label);
        this.month = month;
    }

    @Override
    public int getColor(int index) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (getEntryForIndex(index).getX() == (Integer.parseInt(month) - 1)) {
            return mColors.get(1);
        }
        return mColors.get(0);
    }
}
