package com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart;

import android.util.Log;

import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayBarDataSet extends BarDataSet {

    private String dateStr;

    public DayBarDataSet(List<BarEntry> yVals, String label, String dateStr) {
        super(yVals, label);
        this.dateStr = dateStr;
    }

    @Override
    public int getColor(int index) {
        if (getEntryForIndex(index).getX() == ((DateUtil.getCalendatOfDate(dateStr).get(Calendar.DAY_OF_WEEK) == 1 ? 8 : DateUtil.getCalendatOfDate(dateStr).get(Calendar.DAY_OF_WEEK)) - 2)) {
            return mColors.get(1);
        }
        return mColors.get(0);
    }
}
