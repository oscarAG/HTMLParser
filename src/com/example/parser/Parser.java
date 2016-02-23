package com.example.parser;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * HTML Parser
 * Created by oscar on 2/9/16.
 */
public class Parser extends HtmlUtilities
{
    public static void main(String[] args)
    {
        //Crawl parent URLs, and obtain all links correlating to financial aid.
        try {
            new Crawler();
        } catch (IOException e) {
            e.printStackTrace();
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
