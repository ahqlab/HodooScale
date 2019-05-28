package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Statistics;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.MathUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class WeightStatisticsPresenter implements WeightStatistics.Presenter {

    WeightStatistics.View view;

    WeightStatisticsModel model;

    BarChart chart;

    private Context mContext;

    private SharedPrefManager sharedPrefManager;

    public WeightStatisticsPresenter(WeightStatistics.View view, BarChart chart) {
        this.view = view;
        this.chart = chart;
        this.model = new WeightStatisticsModel();
    }

    @Override
    public void initLoadData(Context context) {
        mContext = context;
        model.initLoadData(context);
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    @Override
    public void getDailyStatisticalData(int type, final String date) {
        model.getDailyStatisticalData(type, date, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d == null)
                    return;
                if (d.size() > 0) {
                    /* 임시 일본어 처리(s) */
                    String[] ko = {"월", "화", "수", "목", "금", "토", "일"};
                    String[] ja = {"月", "火", "水", "木", "金", "土", "日"};
                    String[] en = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                    Locale locale = mContext.getResources().getConfiguration().locale;
                    String localeStr = locale.getLanguage();
                    /* 임시 일본어 처리(e) */
                    if (localeStr.equals("ja")) {
                        for (Statistics value : d) {
                            for (int i = 0; i < ko.length; i++) {
                                if (value.getTheDay().equals(ko[i])) {
                                    value.setTheDay(ja[i]);
                                }
                            }
                        }

                    } else if (localeStr.equals("ko")) {
                        List<String> temp = new ArrayList<>();
                        Collections.addAll(temp, ko);

                        Iterator<String> iterator = temp.iterator();

                        /* 있는 요일 삭제 */
                        while (iterator.hasNext()) {
                            String target = iterator.next();
                            for (int j = 0; j < d.size(); j++) {
                                if (target.equals(d.get(j).getTheDay())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }

                        /* 없는 요일 데이터 넣기 */
                        for (int i = 0; i < temp.size(); i++) {
                            Statistics statistics = new Statistics();
                            statistics.setTheDay(temp.get(i));
                            d.add(statistics);
                        }

                        /* 데이터 정렬 */
                        for (int i = 0; i < ko.length; i++) {
                            for (int j = 0; j < d.size(); j++) {
                                if (ko[i].equals(d.get(j).getTheDay())) {
                                    if (i == j) break;
                                    Statistics tempData = d.get(i);
                                    d.set(i, d.get(j));
                                    d.set(j, tempData);
                                    break;
                                }
                            }
                        }

                        //일요일 ~ 토요일 셋팅

//                        List<Statistics> tempList = new ArrayList<>();
//                        tempList.addAll(d);
//                        for (int i = 0; i < tempList.size() - 1; i++) {
//                            int target = 0;
//                            for (int j = 0; j < ko.length; j++) {
//                                if( ko[j].equals(tempList.get(i).getTheDay()) ) {
//                                    target = j + 1;
//                                    break;
//                                }
//                            }
//                            if ( ko.length == target )
//                                continue;
//                            if ( ko[target].equals(tempList.get(i + 1).getTheDay()) )
//                                continue;
//                            Statistics statistics = new Statistics();
//                            statistics.setTheDay( ko[target] );
//                            tempList.add(i + 1, statistics);
//                        }
//                        d.clear();
//                        d.addAll(tempList);


                    } else if (localeStr.equals("en")) {
                        List<String> temp = new ArrayList<>();
                        Collections.addAll(temp, ko);

                        Iterator<String> iterator = temp.iterator();

                        /* 있는 요일 삭제 */
                        while (iterator.hasNext()) {
                            String target = iterator.next();
                            for (int j = 0; j < d.size(); j++) {
                                if (target.equals(d.get(j).getTheDay())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }

                        /* 없는 요일 데이터 넣기 */
                        for (int i = 0; i < temp.size(); i++) {
                            Statistics statistics = new Statistics();
                            statistics.setTheDay(temp.get(i));
                            d.add(statistics);
                        }

                        /* 데이터 정렬 */
                        for (int i = 0; i < ko.length; i++) {
                            for (int j = 0; j < d.size(); j++) {
                                if (ko[i].equals(d.get(j).getTheDay())) {
                                    if (i == j) break;
                                    Statistics tempData = d.get(i);
                                    d.set(i, d.get(j));
                                    d.set(j, tempData);
                                    break;
                                }
                            }
                        }

                        /* 데이터 정렬 */
                        for (int i = ko.length; i > 0; i--) {
                            for (int j = 0; j < i - 1; j++) {
                                if (ko[j].equals(d.get(j + 1).getTheDay())) {
                                    if (i == j) break;
                                    Statistics tempData = d.get(j);
                                    d.set(j, d.get(j + 1));
                                    d.set(j + 1, tempData);
                                    break;
                                }
                            }
                        }

                        for (Statistics value : d) {
                            for (int i = 0; i < ko.length; i++) {
                                if (value.getTheDay().equals(ko[i])) {
                                    value.setTheDay(en[i]);
                                }
                            }
                        }
                    }
                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, date, "Day");
                } else {
                    chart.clear();
                }
            }

            @Override
            public void doPreExecute() {
            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getWeeklyStatisticalData(int type, final String dateStr, final String year, final String month) {
        model.getWeeklyStatisticalData(type, year, month, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if ( d == null )
                    return;
                if (d.size() > 0) {
                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    List<String> list = new ArrayList<>();

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
                    Date date = null;
                    try {
                        date = formatter.parse(year + month);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    String[] weeks = new String[0];
                    try {
                        weeks = new String[DateUtil.getWeekNumOfMonthTsst(cal)];
                        Log.e("HJLEE", "주차는 : " + DateUtil.getWeekNumOfMonthTsst(cal) + "month : " + month + " year + month : " + year + month);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < weeks.length; i++)
                        list.add(String.valueOf(i + 1));

                    Iterator<String> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        String target = iterator.next();
                        for (int i = 0; i < d.size(); i++) {
                            if (target.equals(d.get(i).getTheWeek())) {
                                iterator.remove();
                                break;
                            }
                        }
                    }

                    /* 데이터 추가 */
                    for (int i = 0; i < list.size(); i++) {
                        Statistics statistics = new Statistics();
                        statistics.setTheWeek(list.get(i));
                        d.add(statistics);
                    }

                    /* 데이터 정렬 */
                    for (int i = d.size(); i > 0; i--) {
                        for (int j = 0; j < i - 1; j++) {
                            if (Integer.parseInt(d.get(j).getTheWeek()) > Integer.parseInt(d.get(j + 1).getTheWeek())) {
                                Statistics temp = d.get(j);
                                d.set(j, d.get(j + 1));
                                d.set(j + 1, temp);
                            }
                        }
                    }

                    chart.notifyDataSetChanged();
                    setStatisticalData(d, dateStr, "Week");
                } else {
                    chart.clear();
                }
            }

            @Override
            public void doPreExecute() {
            }

            @Override
            public void doCancelled() {

            }
        });
    }

    private int getWeekCount(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String dateStr = sdf.format(date);
//        String dateStr = "201906";
        Calendar nowCal = Calendar.getInstance();
        Calendar lastCal = Calendar.getInstance();


        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(4, 6));

        nowCal.set(year, month - 1, 1);
        lastCal.set(year, month, 0);

        int lastDate = lastCal.get(Calendar.DATE);
        int monthWeek = nowCal.get(Calendar.DAY_OF_WEEK);

        int weekSeq = ((lastDate + monthWeek - 1) / 7) + 1;
        return weekSeq;
    }

    @Override
    public void getMonthlyStatisticalData(int type, final String month) {
        model.getMonthlyStatisticalData(type, month, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d == null)
                    return;
                if (d.size() > 0) {

                    String[] ko;
                    String[] ja;
                    String[] en;
                    if (Integer.parseInt(month) <= 6) {
                        ko = new String[]{"01", "02", "03", "04", "05", "06", "07"};
                        ja = new String[]{"01", "02", "03", "04", "05", "06", "07"};
                        en = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
                    } else {
                        ko = new String[]{"01", "02", "08", "09", "10", "11", "12"};
                        ja = new String[]{"06", "07", "08", "09", "10", "11", "12"};
                        en = new String[]{"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    }
                    Locale locale = mContext.getResources().getConfiguration().locale;
                    String localeStr = locale.getLanguage();
                    /* 임시 일본어 처리(e) */
                    if (localeStr.equals("ja")) {
                        for (Statistics value : d) {
                            for (int i = 0; i < ko.length; i++) {
                                if (value.getTheMonth().equals(ko[i])) {
                                    value.setTheMonth(ja[i]);
                                }
                            }
                        }
                    } else if (localeStr.equals("ko")) {
                        List<String> temp = new ArrayList<>();
                        Collections.addAll(temp, ko);

                        Iterator<String> iterator = temp.iterator();

                        /* 있는 요일 삭제 */
                        while (iterator.hasNext()) {
                            String target = iterator.next();
                            for (int j = 0; j < d.size(); j++) {
                                if (target.equals(d.get(j).getTheMonth())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }

                        /* 없는 요일 데이터 넣기 */
                        for (int i = 0; i < temp.size(); i++) {
                            Statistics statistics = new Statistics();
                            statistics.setTheMonth(temp.get(i));
                            d.add(statistics);
                        }

                        /* 데이터 정렬 */
                        for (int i = 0; i < ko.length; i++) {
                            for (int j = 0; j < d.size(); j++) {
                                if (ko[i].equals(d.get(j).getTheMonth())) {
                                    if (i == j) break;
                                    Statistics tempData = d.get(i);
                                    d.set(i, d.get(j));
                                    d.set(j, tempData);
                                    break;
                                }
                            }
                        }
                    } else if (localeStr.equals("en")) {
                        List<String> temp = new ArrayList<>();
                        Collections.addAll(temp, ko);

                        Iterator<String> iterator = temp.iterator();

                        /* 있는 요일 삭제 */
                        while (iterator.hasNext()) {
                            String target = iterator.next();
                            for (int j = 0; j < d.size(); j++) {
                                if (target.equals(d.get(j).getTheMonth())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }

                        /* 없는 요일 데이터 넣기 */
                        for (int i = 0; i < temp.size(); i++) {
                            Statistics statistics = new Statistics();
                            statistics.setTheMonth(temp.get(i));
                            d.add(statistics);
                        }

                        /* 데이터 정렬 */
                        for (int i = 0; i < ko.length; i++) {
                            for (int j = 0; j < d.size(); j++) {
                                if (ko[i].equals(d.get(j).getTheMonth())) {
                                    if (i == j) break;
                                    Statistics tempData = d.get(i);
                                    d.set(i, d.get(j));
                                    d.set(j, tempData);
                                    break;
                                }
                            }
                        }
                        for (Statistics value : d) {
                            for (int i = 0; i < ko.length; i++) {
                                if (value.getTheMonth().equals(ko[i])) {
                                    value.setTheMonth(en[i]);
                                }
                            }
                        }
                    }


                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, month, "Month");
                } else {
                    chart.clear();
                }
            }

            @Override
            public void doPreExecute() {
            }

            @Override
            public void doCancelled() {

            }
        });
    }

   /* @Override
    public void getStatisticalDataByYear(int type, String date) {
        model.getStatisticalDataByYear(type, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {
                    if (chart.getDayData() != null) {
                        chart.getDayData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, "Year");
                } else {
                    chart.clear();
                }
            }

            @Override
            public void doPreExecute() {
            }

            @Override
            public void doCancelled() {

            }
        });
    }*/

    private void setStatisticalData(List<Statistics> d, String type) {
        List<Statistics> list = new ArrayList<Statistics>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < d.size(); i++) {
            yVals.add(new BarEntry(i, d.get(i).getAverage()));
        }

        if (type.matches("Day")) {
            model.setupChart(chart, model.getDayData(yVals), d, type, null);
        } else if (type.matches("Week")) {
            model.setupChart(chart, model.getWeekData(yVals, ""), d, type, null);
        } else if (type.matches("Month")) {
            model.setupChart(chart, model.getMonthData(yVals, null), d, type, null);
        }
    }

    private void setStatisticalData(List<Statistics> d, String date, String type) {
        List<Statistics> list = new ArrayList<Statistics>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < d.size(); i++) {
           /* if (sharedPrefManager.getIntExtra(SharedPrefVariable.UNIT_STR) == 1) {
                d.get(i).setAverage();
            }*/
           Log.e("HJLEE", "(float) MathUtil.cmToInch(d.get(i).getAverage()) : " + (float) MathUtil.cmToInch(d.get(i).getAverage()));
            yVals.add(new BarEntry(i, sharedPrefManager.getIntExtra(SharedPrefVariable.UNIT_STR) == 1 ? (float) MathUtil.kgTolb(d.get(i).getAverage()) :  d.get(i).getAverage()));
        }

        if (type.matches("Day")) {
            model.setupChart(chart, model.getDayData(yVals, date), d, type, date);
        } else if (type.matches("Week")) {
            model.setupChart(chart, model.getWeekData(yVals, date), d, type, date);
        } else if (type.matches("Month")) {
            model.setupChart(chart, model.getMonthData(yVals, date), d, type, date);
        }
    }

    public static String getDayOfHan(int dayOfWeek) {
        //System.err.println(dayOfWeek);
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        } else if (dayOfWeek == -1) {
            dayOfWeek = 6;
        } else if (dayOfWeek == -2) {
            dayOfWeek = 5;
        } else if (dayOfWeek == -3) {
            dayOfWeek = 4;
        } else if (dayOfWeek == -4) {
            dayOfWeek = 3;
        } else if (dayOfWeek == -5) {
            dayOfWeek = 2;
        } else if (dayOfWeek == -6) {
            dayOfWeek = 1;
        }

        String day = "";
        switch (dayOfWeek) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;
        }
        return day;
    }

    @Override
    public void initChart() {
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
    }
}
