package com.example.parser;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * This utility class is used to minimize clutter in the main method
 * by offsetting some of the heavy functions into modular type
 * methods.
 * Created by Oscar on 2/10/2016.
 */
public class HtmlUtilities
{
    //Init the list of urls parse from
    public static ArrayList<String> initUrls()
    {
        ArrayList<String> urls = new ArrayList<>(); //array list of URL's
        urls.add("http://example.com/");
        urls.add("http://www.utrgv.edu/en-us/admissions/paying-for-college/financial-aid/index.htm");
        urls.add("http://udallas.edu/offices/hr/employeebenefits/tuition-waiver.php");
        return urls;
    }

    //Init the list header tags
    public static ArrayList<Elements> initTags(Document doc)
    {
        ArrayList<Elements> title_tags = new ArrayList<>();
        title_tags.add(doc.select("h1"));
        title_tags.add(doc.select("h2"));
        title_tags.add(doc.select("h3"));
        return title_tags;
    }

    //Remove attributes from every element in the document
    public static void removeAllAttributes(Elements el)
    {
        for (Element e : el)
        {
            Attributes at = e.attributes();
            for (Attribute a : at)
            {
                e.removeAttr(a.getKey());
            }
        }
    }

}
