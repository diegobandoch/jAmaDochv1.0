package utils;

import java.util.Calendar;
import java.util.Date;

public class OperacoesDatas {

    public OperacoesDatas() {
    }

    Calendar cal = Calendar.getInstance();

    public static boolean isDiaMesEAnoIguais(Date data1, Date data2) {

        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();

        cal.setTime(data1);
        cal1.setTime(data2);

        if (cal.get(Calendar.DAY_OF_MONTH) != cal1.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        if (cal.get(Calendar.MONTH) != cal1.get(Calendar.MONTH)) {
            return false;
        }
        if (cal.get(Calendar.YEAR) != cal1.get(Calendar.YEAR)) {
            return false;
        }
        return true;
    }

    public Integer getSemanaDoAno(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
    public Integer getAno(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.YEAR);
    }

    public Integer getMesDoAno(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.MONTH);
    }

    public Calendar getCalendarZerado() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

}
