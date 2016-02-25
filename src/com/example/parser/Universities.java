package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for obtaining a list of all universities
 * in the US.
 * Created by oscar on 2/25/16.
 */
public class Universities extends HtmlUtilities
{
    private Map<String, String> universities = new HashMap<>(); //map for the univ's and urls

    /**
     * Constructor
     */
    public Universities()
    {
        setUniversities();
    }

    /**
     * Access a url containing all schools and their corresponding URL's in the US
     */
    public void setUniversities()
    {
        String universities_url = "https://www.utexas.edu/world/univ/alpha/"; //url with all schools in US
        System.out.println("Connecting to " + universities_url + " & grabbing HTML...");
        Document doc; //Retrieve HTML from the url
        try {
            doc = Jsoup.connect(universities_url).get();
            System.out.println("Parsing...");
            removeComments(doc); //strip comments
            Elements div_contents = doc.getElementsByAttributeValue("class", "institution");
            System.out.println(div_contents.size() + " URLs found...\nPlacing elements into a Map...");
            for(Element e : div_contents)
            {
                //System.out.println("Key: \"" + e.text() + "\"\nData: " + e.attr("href"));
                universities.put(e.attr("href"),e.text());
            }
            System.out.println(universities.size() + " key-value sets placed into map.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get hash map containing the university link, and name
     */
    public Map<String, String> getUniversities()
    {
        return universities;
    }

    /**
     * Print the map to review
     */
    public void printMap(){
        for (Map.Entry<String,String> entry : universities.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " - " + value);
        }
    }
}
