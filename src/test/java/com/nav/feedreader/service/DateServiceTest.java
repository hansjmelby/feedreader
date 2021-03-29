package com.nav.feedreader.service;


import com.nav.feedreader.DateService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DateServiceTest {

    DateService dateService = new DateService();
    @Test
    public void WhenDateInSendtInReturn6DateSixMonthBackInTime() throws ParseException {
        Date date =DateService.DATE_FORMAT.parse("2009-07-05");
        Date response = dateService.get6MotnhOldDateFrom(date);
        System.out.println(DateService.DATE_FORMAT.format(response));
        assertTrue("2009-01-05".equals(DateService.DATE_FORMAT.format(response)));
    }

    @Test
    public void WhenDateInSendtInReturnCorrectFormatedSeachParameter () throws ParseException {
        Date date =DateService.DATE_FORMAT.parse("2019-06-01");
        System.out.println(dateService.getSearchStringFromDate(date));
        assertTrue("[,2019-06-01)".equals(dateService.getSearchStringFromDate(date)));

    }


}
