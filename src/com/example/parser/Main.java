package com.example.parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oscar on 2/9/16.
 */
public class Main extends HtmlUtilities
{
    public static void main(String[] args)
    {
        //Get all links to the universities in the United States
        Map<String, String> uni_hash = new HashMap<>();
        Universities uni = new Universities();
        uni_hash = uni.getUniversities();


        /*
        //Crawl parent URLs, and obtain all links correlating to financial aid.
        try {
            new Crawler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        /*
        //Parse the URLs
        try {
            new HtmlParser();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
