package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * HTML Parser
 * Created by oscar on 2/9/16.
 */
public class Parser extends HtmlUtilities
{
    private static ArrayList<Elements> tags_to_remove, tags_to_unwrap;
    private static ArrayList<String> urls;

    public static void main(String[] args) throws IOException
    {
        setUrls();
        // loop through all URL's
        for (String url : urls)
        {
            Document doc = Jsoup.connect(url).get(); //Retrieve HTML from the url
            System.out.println("URL " + urls.indexOf(url) + ": " + url); //Print the index and URL
            System.out.println("Title: " + doc.title()); //print the title of the page
            setTagsToRemove(doc);
            setTagsToUnwrap(doc);
            removeComments(doc); //remove all comments from the page
            removeSpecificTags(Parser.tags_to_remove); //remove predetermined tags (ex. head, script)
            unwrapSpecificTags(Parser.tags_to_unwrap); //unwrap predetermined tags that you need the text from
            removeIrrelevantAttributes(doc); // remove irrelevant attributes of each element in the html
            removeEmptyTagPairs(doc); //remove empty tag pairs
            unwrapNestedRedundancies(doc); //unwrap nested tags with only one child
            System.out.println(doc + "\n"); //print the doc for review
        }
    }

    //Initialize a list of URLs to be called to
    //TODO:Get all URLS from a text file instead, and init array dynamically
    public static void setUrls()
    {
        Parser.urls = new ArrayList<>(); //array list of URL's
        urls.add("http://www.utrgv.edu/en-us/admissions/paying-for-college/financial-aid/index.htm");
        urls.add("http://www.utrgv.edu/en-us/admissions/paying-for-college/scholarships/index.htm");
        //urls.add("http://www.utrgv.edu/en-us/admissions/paying-for-college/cost-of-attendance/index.htm");
        //urls.add("http://www.utrgv.edu/en-us/admissions/paying-for-college/tuition-fees/index.htm");
    }

    //Initialize a list of all tags to be completely removed from the document, along with their child elements
    public static void setTagsToRemove(Document doc)
    {
        Parser.tags_to_remove = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("head");
        tags.add("figure");
        tags.add("script");
        tags.add("form");
        tags.add("img");
        tags.add("article");
        tags.add("header");
        tags.add("nav");
        tags.add("footer");
        tags.add("br");
        tags_to_remove //Get all tags to remove into the elements array list
                .addAll(tags.stream()
                .map(doc::select)
                .collect(Collectors.toList()));
    }

    //Initialize a list of all tags to be unwrapped, child elements will be kept intact
    public static void setTagsToUnwrap(Document doc)
    {
        Parser.tags_to_unwrap = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("i");
        tags.add("strong");
        tags.add("em");
        tags.add("abbr");
        tags_to_unwrap //Get all tags to remove into the elements array list
                .addAll(tags.stream()
                .map(doc::select)
                .collect(Collectors.toList()));
    }

}
