package com.nav.feedreader.service;

import com.nav.feedreader.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FeedReaderService {
    private static final String RESOURCE_URL = "https://arbeidsplassen.nav.no/public-feed/api/v1/ads";
    private static final Integer PAGE_SIZE = 100;
    private RestTemplate restTemplate;
    @Autowired
    public FeedReaderService(RestTemplate template) {

        this.restTemplate = template;

    }

    public String readFeedPage(Date from, Date to, int page){
        Map<String,Object> varabables = new HashMap<String,Object>();
        UriComponentsBuilder builder = null;
        String dateParam = "["+DateService.DATE_FORMAT.format(from)+","+DateService.DATE_FORMAT.format(to)+"]";
             builder = UriComponentsBuilder
                    .fromUriString(RESOURCE_URL)
                    .queryParam("page", page)
                    .queryParam("size", PAGE_SIZE)
                    .queryParam("published", dateParam);
        ResponseEntity<String> response = restTemplate.getForEntity(builder.buildAndExpand().toUri().toString(), String.class,varabables);
        return response.getBody();
    }

}
