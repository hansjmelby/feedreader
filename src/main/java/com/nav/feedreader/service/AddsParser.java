package com.nav.feedreader.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nav.feedreader.AddsRepository;
import com.nav.feedreader.domain.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AddsParser {
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    AddsRepository repo;

    public JsonArray getAddsOnPage(JsonObject page){
        return page.get("content").getAsJsonArray();

    }
    public Add parseAdd(JsonObject oneAdd)  {
        UUID uuid = UUID.fromString(oneAdd.get("uuid").getAsString());
        Date  published = null;
        String desctipion = oneAdd.get("description").getAsString();
        Date  expires = null;
        String source = oneAdd.toString();
        try{
            published = dateFormat.parse(oneAdd.get("published").getAsString().substring(0,10));
            expires =dateFormat.parse(oneAdd.get("expires").getAsString().substring(0,10));
        }
        catch (ParseException e ){
            e.printStackTrace();
        }
        return new Add(uuid,published,desctipion,expires,source);
    }
}
