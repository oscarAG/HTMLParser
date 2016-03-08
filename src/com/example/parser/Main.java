package com.example.parser;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oscar on 2/9/16.
 */
public class Main extends HtmlUtilities
{
    public static void main(String[] args) {
        //Get all links to the universities in the United States
        Universities uni = new Universities();
        //uni.printMap();
        Map<String, String> uni_hash = uni.getUniversities(); //hash map of all parent urls
        List<UrlClass> url_objects = setUrlObjects(uni_hash);
        while(!isAllVisited(url_objects))
        {   //while all urls havent been visited
            for (UrlClass url_obj : url_objects)
            {   //loop through the list of url objects
                if(!url_obj.isVisited())
                {   //if the url object hasn't been visited
                    //check if the domain has been visited
                    if(System.currentTimeMillis() - url_obj.getTime() < 10000)
                    {   //still waiting
                        //todo: if it's still waiting to be visited
                        //      continue
                    }
                    else
                    {   //ready
                        //todo: else it's ready
                        //      get all child urls & add to the list
                        //      mark visited
                        System.out.println(url_obj.getUrl() + " visited.");
                        url_obj.setVisited(true);
                    }
                }
                //System.out.println(url_obj.getTime() + " - " + url_obj.getDomain() + " - " + url_obj.getUrl() + "\n");
            }
        }

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

    private static List<UrlClass> setUrlObjects(Map<String, String> hash){
        List<UrlClass> url_objects = new ArrayList<>();
        try {
            for (Map.Entry<String,String> entry : hash.entrySet()) {
                String key = entry.getKey();
                url_objects.add(new UrlClass(System.currentTimeMillis(), key, Universities.getDomainName(key)));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return url_objects;
    }

    private static boolean isAllVisited(List<UrlClass> list)
    {
        for (UrlClass url_obj : list)
        {
            if(!url_obj.isVisited())
                return false;
        }
        return true;
    }

    private void setDomains(Map<String, String> hash)
    {
        List<UrlClass> url_objs = new ArrayList<>();
        for (Map.Entry<String,String> entry : hash.entrySet()) {
            String value = entry.getValue();

        }
    }
}
