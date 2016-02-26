package com.example.parser;

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
        //uni.printMap();
        uni_hash = uni.getUniversities(); //hash map of all parent urls

        //Todo: Add the parent urls to a queue
        //Crawl the parent URLs

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
