package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * HTML Parser
 * Created by oscar on 2/9/16.
 */
public class Parser extends HtmlUtilities
{
    private static ArrayList<Elements> tags_to_remove, tags_to_unwrap;
    private static ArrayList<String> urls, domains, headers;

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        int delay = 3000; //delay for each call in ms
        setUrls();
        setHeaders();
        // loop through all URL's
        for (String url : urls)
        {
            Document doc = Jsoup.connect(url).get(); //Retrieve HTML from the url
            System.out.println("URL " + urls.indexOf(url) + ": " + url); //Print the index and URL
            String title = doc.title().replaceAll("[^A-Za-z0-9]", "").toLowerCase();
            System.out.println("Title: " + title); //print the title of the page
            setTagsToRemove(doc);
            setTagsToUnwrap(doc);
            removeComments(doc); //remove all comments from the page
            removeSpecificTags(Parser.tags_to_remove); //remove predetermined tags (ex. head, script)
            unwrapSpecificTags(Parser.tags_to_unwrap); //unwrap predetermined tags that you need the text from
            removeIrrelevantAttributes(doc); // remove irrelevant attributes of each element in the html
            removeEmptyTagPairs(doc); //remove empty tag pairs
            unwrapNestedRedundancies(doc); //unwrap nested tags with only one child
            //System.out.println(doc + "\n"); //print the doc for review
            parseDoc(headers, doc.getAllElements());
            if(urls.indexOf(url)!=urls.size()-1)
            {   //If not on the last url, wait 'x' ms before proceeding to reduce stress on the server
                System.out.println("Waiting " + delay + "ms before proceeding...\n");
                Thread.sleep(delay);
            }
        }
    }

    //Initialize a list of URLs to be called to
    public static void setUrls() throws IOException, URISyntaxException {
        Parser.urls = new ArrayList<>();
        Parser.domains = new ArrayList<>();
        urls.addAll(Files.readAllLines(Paths.get("assets/urls.txt")) //read from file
                .stream().filter(line -> line.charAt(0) != '#') //if the line isn't commented out
                .collect(Collectors.toList())); //add to list
        //Randomize to cover order pattern
        long seed = System.nanoTime();
        Collections.shuffle(urls, new Random(seed));
        System.out.println("URLs after being shuffled:");
        urls.forEach(System.out::println); //print the order
        System.out.println();
        for(String url : urls)
        {   //Get the domain from the url and add it to the domains list, if it does not currently exist
            String domain = getDomainName(url);
            if(!domains.contains(domain)){
                domains.add(domain);
            }
        }
        System.out.println("Domains to be visited:");
        domains.forEach(System.out::println);
        System.out.println("\n");
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
        //tags.add("article");
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
        Parser.tags_to_unwrap = new ArrayList<>();
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
        Parser.headers = new ArrayList<>();
        headers.add("h1");
        headers.add("h2");
        headers.add("h3");
        headers.add("h4");
    }
}
