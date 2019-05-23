package com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart;

import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.Calendar;
import java.util.List;

public class WeekBarDataSet extends BarDataSet {

    private String dateStr;

    public WeekBarDataSet(List<BarEntry> yVals, String label, String dateStr) {
        super(yVals, label);
        this.dateStr = dateStr;
    }

    @Override
    public int getColor(int index) {
        if (getEntryForIndex(index).getX() == (DateUtil.getCalendatOfDate(dateStr).get(Calendar.WEEK_OF_MONTH) - 1)) {
            return mColors.get(1);
        }
        return mColors.get(0);
    }
}
