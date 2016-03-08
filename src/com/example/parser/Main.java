package com.example.parser;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oscar on 2/9/16.
 */
public class Main
{
    public static void main(String[] args) {
        //Get all links to the universities in the United States
        Universities uni = new Universities();
        List<UrlClass> url_objects = uni.setUrlObjects(uni.getUniversities());

        while(!CrawlerUtils.isAllVisited(url_objects)) //while all urls haven't been visited
        {
            //find next to call
            UrlClass next = CrawlerUtils.findNextObjectToCall(url_objects);
            if(next != null){
                System.out.println(next.getUrl());
                CrawlerUtils.updateObjects(url_objects, next);
                url_objects.get(url_objects.indexOf(next)).setVisited(true);
            }

            //call
            //mark visited
        }

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
