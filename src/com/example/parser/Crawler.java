package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Crawl a website, starting with the head, and moving through all pages, creating an index of urls along the way.
 * Created by Oscar on 2/23/2016.
 */
public class Crawler extends HtmlUtilities
{
    private ArrayList<String> parent_urls;

    public Crawler() throws IOException {
        crawlify();
    }

    private void crawlify() throws IOException {
        setParentUrls(); //init the urls to crawl
        for(String url : parent_urls)
        {
            ArrayList<String> child_urls = new ArrayList<>();
            Document doc = Jsoup.connect(url).get(); //Retrieve HTML from the url
            for(Element e : doc.select("a")) //'a' tags
            {
                if(!hasSocialMedia(e.attr("href")) && !hasBlacklistedKeyword(e))
                {   //if doesn't have social media or blacklisted
                    //good url, check for 'http' or 'https'
                    if(!e.attr("href").contains("http") && !e.attr("href").contains("https"))
                    {   //if it doesn't have 'http' or 'https'
                        child_urls.add(url + e.attr("href"));
                    }
                    else
                    {   //it does
                        child_urls.add(e.attr("href"));
                    }
                }
            }
            //remove irrelevant links from the children
            for(String child : child_urls)
            {

            }
        }
    }

    private void setParentUrls()
    {
        parent_urls = new ArrayList<>();
        parent_urls.add("http://www.utrgv.edu/en-us/");
    }
}
