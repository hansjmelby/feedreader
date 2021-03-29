package com.nav.feedreader;

import com.nav.feedreader.domain.Add;
import com.nav.feedreader.service.FeedReaderService;

import com.nav.feedreader.service.PageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class FeedreaderApplication implements CommandLineRunner {

    @Autowired
    FeedReaderService feedReaderService;
    @Autowired
    DateService dateService ;
    public static void main(String[] args) {
        SpringApplication.run(FeedreaderApplication.class, args);
    }


    @Autowired
    AddsRepository repository;
    @Override
    public void run(String... args) throws Exception {

        Date endDate = Calendar.getInstance().getTime();
        Date sixMonthAgo = dateService.get6MotnhOldDateFrom(endDate);
        List<Add>  storage = new ArrayList<>();
        boolean allFetched = false;
        PageParser parser = new PageParser();
        System.out.println("Starting data harvest");
        while (!allFetched){
            int page = 0;
            boolean last = false;
            while (!last) {
                String str = feedReaderService.readFeedPage(sixMonthAgo, endDate, page);
                parser.parsePage(str);
                parser.parseAddsInpage();
                parser.getAdds().forEach(add -> repository.persist(add));
                page++;
                last =parser.isLast();
            }
            allFetched=parser.lastPublisghedDateParsed.before(sixMonthAgo);
            endDate = parser.lastPublisghedDateParsed;
        }
        System.out.println("Data harvest complete");

        Map<Integer,List<Add>> javaresult = repository.findByDescription("kotlin")
                .stream()
                .collect(Collectors.groupingBy(Add::weekNumber)
                );
        Map<Integer,List<Add>> kotlinResult = repository.findByDescription("java")
                .stream()
                .collect(Collectors.groupingBy(Add::weekNumber)
                );

        System.out.println("-------------------ANTAL ANONSER PR UKE FOR JAVA -------------");
        javaresult.forEach((integer, adds) -> System.out.println("Uke :"+integer+", "+adds.size()));
        System.out.println("-------------------ANTAL ANONSER PR UKE FOR KOTLIN -------------");
        kotlinResult.forEach((integer, adds) -> System.out.println("Uke :"+integer+", "+adds.size()));
        System.out.println("-------------------TOTAL ANTALL ANSONSER  -------------");
        System.out.println("Kotin :" +repository.findByDescription("kotlin").size());
        System.out.println("java :" +repository.findByDescription("java").size());

        System.out.println("Program completed. Shutting down");
        System.exit(0);
    }
}
