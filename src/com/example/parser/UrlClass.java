package com.example.parser;


import java.util.List;

/**
 * This is a custom class that will be used to keep track of when a URL was last visited.
 * Created by oscar on 3/7/16.
 */
public class UrlClass {
    private long time;
    private String url, domain;
    private boolean visited;

    //Time, url, domain
    public UrlClass(String url)
    {
        this.time = System.currentTimeMillis();
        this.visited = false;
        this.url = url;
        this.domain = HtmlUtilities.getDomainName(url);
    }

    public long getTime(){return time;}
    public String getUrl(){return url;}
    public String getDomain(){return domain;}
    public void setVisited(boolean blah)
    {
        this.visited = blah;
    }
    public boolean isVisited()
    {
        return this.visited;
    }
    public void resetTime(){time = System.currentTimeMillis();}

    public static boolean allVisited(List<UrlClass> list){
        for(UrlClass obj : list)
        {
            if(!obj.isVisited()){
                return false;
            }
        }
        return true;
    }

    public static void resetTimers(String domain, List<UrlClass> list){
        int counter = 0;
        for(UrlClass obj : list){
            if(!obj.isVisited() && obj.getDomain().equals(domain)){
                obj.resetTime();
                counter++;
            }

        }
        if(counter > 0){
            System.out.println(counter + " " + domain + " URL(s) in queue.");
        }
    }

    public static int remaining(List<UrlClass> list){
        int remaining = 0;
        for(UrlClass obj : list){
            if(!obj.isVisited()){
                remaining++;
            }
        }
        return remaining;
    }
}
