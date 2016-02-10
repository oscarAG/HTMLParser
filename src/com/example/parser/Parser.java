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
public class Parser extends HtmlUtilities
{
    public static void main(String[] args) throws IOException
    {
        ArrayList<String> urls = initUrls(); // init list of urls

        for (String url : urls)
        {   // loop through all URL's
            System.out.println("URL " + urls.indexOf(url) + ": " + url); // current url index
            Document doc = Jsoup.connect(url).get();        // call to the page and get html
            removeAllAttributes(doc.getAllElements());      // remove all attributes of each element in the html doc
            System.out.println(doc.title());                //print the title
            ArrayList<Elements> title_tags = initTags(doc); //list of all tags to consider titles
            for(Elements title_type: title_tags)
            {       // loop through tag types in the array list (ex. h1, then h2, then h3)
                for(Element tag: title_type)
                {   // loop through all of a tag type (ex. all of the h1)
                    Elements tag_siblings = tag.siblingElements();  // init the tag's children
                    tag_siblings.forEach(System.out::println);      // loop through the tag, print all children
                }
            }
            System.out.println();
        }
    }
    
}
