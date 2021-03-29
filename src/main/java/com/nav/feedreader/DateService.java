package com.nav.feedreader;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class DateService {

    //public static final SimpleDateFormat DATE_FORMAT= new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT= new SimpleDateFormat("yyyy-MM-dd");

   public Date get6MotnhOldDateFrom(Date date){
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(date);
       calendar.add(Calendar.MONTH,-6);
       return calendar.getTime();
   }

    /*
    * reurns a search qury in the format [2021-03-01,*] (yyyy-MM-dd')
    * */
    public String getSearchStringFromDate(Date fromDate){

        String parsedFromDate = fromDate!=null?
                DATE_FORMAT.format(fromDate) :"*";
        return "[,"+parsedFromDate+")";
    }
}
