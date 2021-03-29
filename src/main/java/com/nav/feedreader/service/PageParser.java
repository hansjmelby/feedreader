package com.nav.feedreader.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nav.feedreader.domain.Add;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PageParser {
    List<Add> pageAdds= new ArrayList<>();
    AddsParser parser  = new AddsParser();
    JsonObject page;
    public String totelEments;
    public String  pageNumer;
    public String pageSize;
    public String totalPages;
    public String first;
    public String last;
    public Date lastPublisghedDateParsed;

    public void parsePage(String json) {

        page =  JsonParser.parseString(json).getAsJsonObject();
        totelEments = page.get("totalElements").getAsString();
        pageNumer = page.get("pageNumber").getAsString();
        pageSize = page.get("pageSize").getAsString();
        totalPages = page.get("totalPages").getAsString();
        first = page.get("first").getAsString();
        last = page.get("last").getAsString();
    }
    public JsonArray getAddsOnPage(JsonObject page){
        return page.get("content").getAsJsonArray();

    }

    public void parseAddsInpage(){
        getAddsOnPage(page).forEach(jsonElement -> {
            Add add = parser.parseAdd(jsonElement.getAsJsonObject());
            pageAdds.add(add);
            lastPublisghedDateParsed=add.getPublished();
        });
    }
    public List<Add> getAdds(){
        return pageAdds;
    }

    public Boolean isLast(){
        return Boolean.valueOf(last);
    }

}
