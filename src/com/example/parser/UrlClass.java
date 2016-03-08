package com.example.parser;


/**
 * This is a custom class that will be used to keep track of when a URL was last visited.
 * Created by oscar on 3/7/16.
 */
public class UrlClass {
    private long time;
    private String url, domain;
    private boolean visited;

    //Time, url, domain
    public UrlClass(long time, String url, String domain)
    {
        this.time = time;
        this.url = url;
        this.domain = domain;
        this.visited = false;
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
}
