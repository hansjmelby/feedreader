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
        System.out.println("Harest data complete");
        List<Add> result = new ArrayList<>();
        repository.getall().forEach((uuid, add) -> result.add(add));
        long kotlinAdds = result.stream().filter(add -> add.getDesctipion().contains("Kotlin")).count();
        long javaAdds = result.stream().filter(add -> add.getDesctipion().contains("Java")).count();
        System.out.println("Kotin :" +kotlinAdds);
        System.out.println("java :" +javaAdds);
        System.out.println("Program completed. Shutting down");
        System.exit(0);
    }
}
