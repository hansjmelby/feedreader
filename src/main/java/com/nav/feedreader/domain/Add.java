package com.nav.feedreader.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Add {



    public Add(UUID id, Date published, String desctipion, Date expires, String source) {
        this.uuid = id;
        this.published = published;
        this.desctipion = desctipion;
        this.expires = expires;
        this.source = source;

    }

    private UUID uuid;
    private Date published;
    private String desctipion;
    private Date expires;
    private String source;
    private Calendar pubDate;

    public UUID getUuid() {
        return uuid;
    }
    public Date getPublished() {
        return published;
    }

    public String getDesctipion() {
        return desctipion;
    }

    public Date getExpires() {
        return expires;
    }

    public String getSource() {
        return source;
    }

    public int  weekNumber () {
        Calendar cal = Calendar.getInstance();
        cal.setTime(published);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}
