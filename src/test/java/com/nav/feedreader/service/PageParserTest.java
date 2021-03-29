package com.nav.feedreader.service;


import com.nav.feedreader.domain.Add;
import org.junit.jupiter.api.Test;


import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


public class PageParserTest {

    @Test
    public void testAtParsingSkjerUtenFeil() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("page.json");
    String json = new String(inputStream.readAllBytes());
    PageParser parser = new PageParser();
    parser.parsePage(json);
    assertEquals("5000",parser.totelEments);
    assertEquals("false",parser.first);
    assertEquals("false",parser.last);
    }
    @Test
    public void testAtParsingAvAddsReturnererKorrektantallElementer() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("page.json");
    String json = new String(inputStream.readAllBytes());
    PageParser parser = new PageParser();
    parser.parsePage(json);
    parser.parseAddsInpage();
    assertEquals(1,parser.getAdds().size());
    }
    @Test
    public void testAddBlirPoulertKorrekt() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("page.json");
        String json = new String(inputStream.readAllBytes());
        PageParser parser = new PageParser();
        parser.parsePage(json);
        parser.parseAddsInpage();
        Add add = parser.getAdds().get(0);
        assertNotNull(add.getPublished());
        assertEquals("08c7888c-5b8e-4d01-aab2-00971f0ba50d",add.getUuid().toString());
        assertNotNull(add.getDesctipion());
        assertNotNull(add.getSource());

    }
}
