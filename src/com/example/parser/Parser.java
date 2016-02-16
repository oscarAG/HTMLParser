package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;

/**
 * HTML Parser
 * Created by oscar on 2/9/16.
 */
public class Parser extends HtmlUtilities
{
    public static void main(String[] args) throws IOException
    {
        ArrayList<String> urls = initUrls(); // init list of urls

        // loop through all URL's
        for (String url : urls)
        {
            System.out.println("URL " + urls.indexOf(url) + ": " + url); // current url index
            Document doc = Jsoup.connect(url).get(); // call to the page and get html
            System.out.println("Title: " + doc.title());    //print the title
            removeComments(doc); //remove all comments from the page
            removeIrrelevantTags(doc); //remove predetermined irrelevant tags (ex. head)
            removeIrrelevantAttributes(doc); // remove all irrelevant attributes of each element in the html doc
            removeEmptyTagPairs(doc); //remove empty tag pairs after the doc has been stripped
            unwrapNestedRedundancies(doc); //unwrap nested tags with only one child
            System.out.println(doc); //print the doc after all operations
            System.out.println(); //new line
        }
    }
    
}
