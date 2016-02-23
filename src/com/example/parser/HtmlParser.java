package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * HTML Parser
 * Created by oscar on 2/9/16.
 */
public class HtmlParser extends HtmlUtilities
{
    private static ArrayList<Elements> tags_to_remove, tags_to_unwrap;
    private static ArrayList<String> urls, headers;
    private static Map<String, String> url_domain;

    public HtmlParser() throws IOException, URISyntaxException, InterruptedException {
        int delay = 3000; //delay for each call in ms
        setUrls();
        setHeaders();
        File dir = new File("docs"); //create a docs directory if it doesn't exist
        if(dir.mkdir()){System.out.println("docs directory created.");}
        // loop through all URL's
        for (String url : urls)
        {
            Document doc = Jsoup.connect(url).get(); //Retrieve HTML from the url
            System.out.println("reading from url " + urls.indexOf(url) + ": " + url + "..."); //Print the index and URL
            String title = doc.title().replaceAll("[^A-Za-z0-9]", "").toLowerCase();
            System.out.println("\ttitle: " + title); //print the title of the page
            setTagsToRemove(doc);
            setTagsToUnwrap(doc);
            removeComments(doc); //remove all comments from the page
            removeSpecificTags(HtmlParser.tags_to_remove); //remove predetermined tags (ex. head, script)
            unwrapSpecificTags(HtmlParser.tags_to_unwrap); //unwrap predetermined tags that you need the text from
            removeIrrelevantAttributes(doc); // remove irrelevant attributes of each element in the html
            removeEmptyTagPairs(doc); //remove empty tag pairs
            unwrapNestedRedundancies(doc); //unwrap nested tags with only one child
            parseDoc(headers, doc.getAllElements(), url_domain.get(url));
            System.out.println("\twaiting " + delay + "ms before proceeding...\n");
            Thread.sleep(delay);
        }
    }

    //Initialize a list of URLs to be called to
    public static void setUrls() throws IOException, URISyntaxException {
        HtmlParser.urls = new ArrayList<>();
        HtmlParser.url_domain = new HashMap<>();
        System.out.println("reading urls from file...");
        urls.addAll(Files.readAllLines(Paths.get("assets/urls.txt")) //read from file
                .stream().filter(line -> line.charAt(0) != '#') //if the line isn't commented out
                .collect(Collectors.toList())); //add to list
        System.out.println("files read.\nurls stored.");
        //Randomize to cover order pattern
        System.out.println("shuffling...");
        long seed = System.nanoTime();
        Collections.shuffle(urls, new Random(seed));
        System.out.println("urls shuffled.");
        System.out.println("mapping the urls to the domains...");
        for(String url : urls)
        {   //Get the domain from the url and map it
            String domain = getDomainName(url).replace(".", "_");
            url_domain.put(url, domain);
        }
        System.out.println("done.");
        System.out.println("creating necessary directories from domains obtained...");
        for(String key : url_domain.keySet())
        {   //print the map and create a directory of the domain if it dne
            String domain = url_domain.get(key).replace(".", "_");
            File dir = new File("docs/" + domain);
            if(dir.mkdir())
            {
                System.out.println(dir.toString() + " created.");
            }
        }
        System.out.println("done.\n");
    }

    //Initialize a list of all tags to be completely removed from the document, along with their child elements
    public static void setTagsToRemove(Document doc)
    {
        HtmlParser.tags_to_remove = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("head");
        tags.add("figure");
        tags.add("script");
        tags.add("form");
        tags.add("img");
        tags.add("header");
        tags.add("nav");
        tags.add("footer");
        tags.add("aside");
        tags.add("br");
        tags_to_remove.addAll(tags.stream()//Get all tags to remove into the elements array list
                .map(doc::select)
                .collect(Collectors.toList()));
    }

    //Initialize a list of all tags to be unwrapped, child elements will be kept intact
    public static void setTagsToUnwrap(Document doc)
    {
        HtmlParser.tags_to_unwrap = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("i");
        tags.add("strong");
        tags.add("em");
        tags.add("abbr");
        tags.add("sup");
        tags_to_unwrap.addAll(tags.stream()//Get all tags to remove into the elements array list
                .map(doc::select)
                .collect(Collectors.toList()));
    }

    //Initialize a list of all headers to be searched for in the document
    public static void setHeaders()
    {
        HtmlParser.headers = new ArrayList<>();
        headers.add("h1");
        headers.add("h2");
        headers.add("h3");
        headers.add("h4");
    }
}