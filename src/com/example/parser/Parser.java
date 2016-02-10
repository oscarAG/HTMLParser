package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

/**
 * HTML Parser
 * Created by oscar on 2/9/16.
 */
public class Parser {
    public static void main(String[] args) throws IOException
    {
        ArrayList<String> urls = new ArrayList<>(); //array list of URL's
        urls.add("http://example.com/");
        urls.add("http://www.utrgv.edu/en-us/admissions/paying-for-college/financial-aid/index.htm");
        urls.add("http://udallas.edu/offices/hr/employeebenefits/tuition-waiver.php");

        for (String url : urls)
        { //loop through all URL's
            System.out.println("URL " + urls.indexOf(url) + ": " + url); // current index
            Document doc = Jsoup.connect(url).get();        // get html from url
            System.out.println(doc.title());
            ArrayList<Elements> els = new ArrayList<>();    // array of tags to be ripped
            els.add(doc.select("h1"));
            els.add(doc.select("h2"));
            els.add(doc.select("h3"));
            for(Elements e: els)
            {   //loop through tag types in the array list
                for(Element e_sub: e)
                {   //loop through all of a tag type (ex. all of the h1)
                    Elements parent_sibs = e_sub.siblingElements(); //init the tag's children
                    parent_sibs.forEach(System.out::println); //loop through the tag, print all children
                }
            }
        }
    }
}
