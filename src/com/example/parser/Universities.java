package com.example.parser;

import com.sun.media.sound.UlawCodec;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for obtaining a list of all universities
 * in the US.
 * Created by oscar on 2/25/16.
 */
public class Universities
{
    private List<String> universities = new ArrayList<>(); //list for the urls
    private List<UrlClass> university_objects = new ArrayList<>();

    /**
     * Constructor
     */
    public Universities()
    {

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
            HtmlUtilities.removeComments(doc); //strip comments
            Elements div_contents = doc.getElementsByAttributeValue("class", "institution");
            System.out.println(div_contents.size() + " URLs found...\nPlacing elements into a List...");
            int duplicates = 0;
            for(Element e : div_contents)
            {
                if(universities.size() != 0){
                    if(!universities.contains(e.attr("href"))){
                        universities.add(e.attr("href"));
                    }
                    else{
                        duplicates++;
                    }
                }
                else
                {
                    universities.add(e.attr("href"));
                }
            }
            System.out.println(universities.size() + " URLs placed into list.\n" + duplicates + " duplicates URLs found/ignored.");
            System.out.println("Creating a List of UrlClass objects...");
            setUrlObjects(universities);
            System.out.println(university_objects.size() + " UrlClass objects in List.\n");
        } catch (IOException e) {
            System.out.println("Something went wrong. Check your internet connection.");
        }
    }

    /**
     * Get list containing university links
     */
    public List<String> getUniversities()
    {
        return universities;
    }

    private void setUrlObjects(List<String> list){
        for(String str : list){
            university_objects.add(new UrlClass(str));
        }
    }

    public List<UrlClass> getUniversityObjects(){
        return university_objects;
    }
}
