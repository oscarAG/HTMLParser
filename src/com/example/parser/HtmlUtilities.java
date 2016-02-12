package com.example.parser;

import org.jsoup.nodes.*;
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
    //TODO:Get all URLS from a text file instead, and init array dynamically
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
        // Get all elements of each tag
        ArrayList<Elements> hierarchy = new ArrayList<>();
        hierarchy.add(doc.select("h1"));
        hierarchy.add(doc.select("div"));
        hierarchy.add(doc.select("h2"));
        hierarchy.add(doc.select("h3"));
        return hierarchy;
    }

    //Remove attributes from every element in the document
    //TODO: Keep URLS for crawling
    public static void removeIrrelevantAttributes(Document doc)
    {
        for (Element e : doc.getAllElements()) //first pass through
        {   //for each element
            for (Attribute a : e.attributes())
            {   //for each attribute in the element
                if(!a.getKey().equals("href"))
                {   //skip href tags
                    e.removeAttr(a.getKey());
                }
                else
                {   //href tag found, do operations for urls
                    if(a.getValue().contains("https") || a.getValue().contains("http"))
                    {   //if "https" or "http" was found in the url of the href
                        if(!verifyUrl(a.getValue()))
                        {   //if the url is bad
                            e.removeAttr(a.getKey()); //remove
                        }
                    }
                    else
                    {   //the link is unusable, delete
                        e.removeAttr(a.getKey());
                    }
                }
            }
            //check if this is an 'a' tag, and if it has no attributes
            if(e.tagName().equals("a") && e.attributes().size() == 0)
            {
                e.remove();
            }
        }
    }

    //Remove irrelevant tags
    public static void removeIrrelevantTags(Document doc)
    {
        ArrayList<Elements> tags_to_remove = new ArrayList<>();
        tags_to_remove.add(doc.select("head"));
        tags_to_remove.add(doc.select("figure"));
        tags_to_remove.add(doc.select("script"));
        tags_to_remove.add(doc.select("form"));
        tags_to_remove.add(doc.select("img"));
        tags_to_remove.add(doc.select("article"));
        for(Elements el: tags_to_remove)
        {   //for each tag
            el.forEach(Element::remove); //remove all
        }
    }

    //Remove comments
    public static void removeComments(Node node)
    {
        for (int i = 0; i < node.childNodes().size();)
        {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }

    //Check if a url is valid, mark for deletion if not
    public static boolean verifyUrl(String url)
    {
        //check for social media
        return !(url.contains("facebook") || url.contains("twitter") || url.contains("linkedin") ||
                 url.contains("youtube"));
    }

    public static void removeEmptyTagPairs(Document doc)
    {
        //remove empty tag pairs
        for(Element e: doc.getAllElements())
        {   //for each element in the doc
            if (!e.hasText() && e.isBlock()) {
                e.remove();
            }
        }
    }

}
