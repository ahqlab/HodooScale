package com.animal.scale.hodoo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getCurrentDatetime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }
    public static String getCurrentDatetimeSecond() {

        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%02d", calendar.get(Calendar.MONTH) + 1);
    }

    public static String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
    }

    public static long getCurrentMilicecond() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(getCurrentDatetime());
        return date.getTime();
    }

    public static long getMilicecond(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        return date.getTime();
    }

    public static Calendar getCalendatOfDate(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 두 날짜의 주차수 차이를 반환한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static int getWeekNumOfTwoDate( Calendar cal1, Calendar cal2 ) throws Exception
    {
        Calendar calThur1 = getCalThursdayThisWeek( cal1 );
        Calendar calThur2 = getCalThursdayThisWeek( cal2 );

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        if( calThur1.before( calThur2 ) ) {
            startCal.set( calThur1.get( Calendar.YEAR ), calThur1.get( Calendar.MONTH ), calThur1.get( Calendar.DATE ) );
            endCal.set( calThur2.get( Calendar.YEAR ), calThur2.get( Calendar.MONTH ), calThur2.get( Calendar.DATE ) );
        } else {
            startCal.set( calThur2.get( Calendar.YEAR ), calThur2.get( Calendar.MONTH ), calThur2.get( Calendar.DATE ) );
            endCal.set( calThur1.get( Calendar.YEAR ), calThur1.get( Calendar.MONTH ), calThur1.get( Calendar.DATE ) );
        }

        int periodWeek = 0;
        while( endCal.after( startCal ) ) {
            startCal.add( Calendar.DATE, 7);
            periodWeek++;
        }

        return periodWeek;
    }

    /**
     * 해당월의 주차수를 반환한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static int getWeekNumOfMonthTsst( Calendar cal ) throws Exception
    {
        Calendar thisMonthCal = Calendar.getInstance();
        thisMonthCal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), 1 );

        Calendar nextMonthCal = Calendar.getInstance();
        nextMonthCal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ) + 1, 1 );

        Calendar thisMonthFirstThursday = getCalFirstWeekThursdayOfMonthTsst( thisMonthCal );
        Calendar nextMonthFirstThursday = getCalFirstWeekThursdayOfMonthTsst( nextMonthCal );

        int periodWeek = 0;
        while( nextMonthFirstThursday.after( thisMonthFirstThursday ) ) {
            thisMonthFirstThursday.add( Calendar.DATE, 7);
            periodWeek++;
        }

        return periodWeek;
    }

    /**
     * 해당월의 몇번째 주인지를 반환한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static int getCountWeekOfMonthTsst( Calendar cal ) throws Exception
    {
        Calendar thisThursdayCal = getCalThursdayThisWeek( cal );
        Calendar firstThursdayYearCal = getCalFirstWeekThursdayOfMonthTsst( cal );

        int periodWeek = 0;
        while( !thisThursdayCal.before( firstThursdayYearCal ) ) {
            firstThursdayYearCal.add( Calendar.DATE, 7);
            periodWeek++;
        }

        return periodWeek;
    }

    /**
     * 해당년도의 몇번째 주인지를 반환한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static int getCountWeekOfYearTsst( Calendar cal ) throws Exception
    {
        Calendar thisThursdayCal = getCalThursdayThisWeek( cal );
        Calendar firstThursdayYearCal = getCalFirstWeekThursdayOfYearTsst( thisThursdayCal );

        int periodWeek = 0;
        while( !thisThursdayCal.before( firstThursdayYearCal ) ) {
            firstThursdayYearCal.add( Calendar.DATE, 7);
            periodWeek++;
        }

        return periodWeek;
    }

    /**
     * 해당월의 첫주 시작 목요일을 구한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static Calendar getCalFirstWeekThursdayOfMonthTsst( Calendar cal ) throws Exception
    {
        // 해당년의 시작일(1월 1일)을 셋팅하기
        Calendar firstCal = Calendar.getInstance();
        firstCal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), 1 );

        // 기준이 되는 목요일을 구하자.
        firstCal = getCalThursdayThisWeek( firstCal );

        // 기준이 되는 목요일이 전월이라면 7일을 더하자.
        if( cal.get( Calendar.MONTH ) != firstCal.get( Calendar.MONTH ) ) {
            firstCal.add( Calendar.DATE, 7 );
        }

        return firstCal;
    }

    /**
     * 해당월의 마지막주 종료 목요일을 구한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static Calendar getCalLastWeekThursdayOfMonthTsst( Calendar cal ) throws Exception
    {
        // 해당월의 마지막날짜를 셋팅하자
        Calendar lastCal = Calendar.getInstance();
        lastCal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ) + 1, 1 );
        lastCal.add( lastCal.get( Calendar.DATE), -1 );

        // 기준이 되는 목요일을 구하자.
        lastCal = getCalThursdayThisWeek( lastCal );

        // 기준이 되는 목요일이 내년이라면 7일을 빼자.
        if( cal.get( Calendar.MONTH ) != lastCal.get( Calendar.MONTH ) ) {
            cal.add( Calendar.DATE, -7 );
        }

        return cal;
    }

    /**
     * 해당년도의 첫주 시작 목요일을 구한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static Calendar getCalFirstWeekThursdayOfYearTsst( Calendar cal ) throws Exception
    {
        // 해당년의 시작일(1월 1일)을 셋팅하기
        Calendar firstCal = Calendar.getInstance();
        firstCal.set( cal.get( Calendar.YEAR ), 0, 1 );

        // 기준이 되는 목요일을 구하자.
        firstCal = getCalThursdayThisWeek( firstCal );

        // 기준이 되는 목요일이 전년도라면 7일을 더하자.
        if( cal.get( Calendar.YEAR ) != firstCal.get( Calendar.YEAR ) ) {
            firstCal.add( Calendar.DATE, 7 );
        }

        return firstCal;
    }

    /**
     * 해당년도의 마지막주 종료 목요일을 구한다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static Calendar getCalLastWeekThursdayOfYearTsst( Calendar cal ) throws Exception
    {
        // 해당년도의 마지막날짜(12월 31일)을 셋팅하자.
        int year = cal.get( Calendar.YEAR );
        cal.set( year, 11, 31 );

        // 기준이 되는 목요일을 구하자.
        Calendar lastCal = Calendar.getInstance();
        lastCal = getCalThursdayThisWeek( cal );

        // 기준이 되는 목요일이 내년이라면 7일을 빼자.
        if( lastCal.get( Calendar.YEAR ) != year ) {
            lastCal.add( Calendar.DATE, -7 );
        }

        return lastCal;
    }

    /**
     * 한주의 기준이 되는 목요일을 구하자.
     * - 목요일을 기준으로 앞뒤 3일이 한주가 된다.
     *
     * @return int
     * @throws Exception
     * @since 1.0
     */
    public static Calendar getCalThursdayThisWeek( Calendar cal ) throws Exception
    {
        int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );

        Calendar thursdayCal = Calendar.getInstance();
        thursdayCal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), cal.get( Calendar.DATE ) );

        // 일요일은 3일을 빼서 목요일을 구하고
        if( dayOfWeek == 1 ) {
            thursdayCal.add( Calendar.DATE, -3 );
        }
        // 일요일이 아니면 목요일의 숫자 5에서 해당요일의 값을 빼준값을 더한다.
        else {
            thursdayCal.add( Calendar.DATE, 5 - dayOfWeek );
        }

        return thursdayCal;
    }
}
