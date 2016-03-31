package com.example.parser;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This utility class is used to minimize clutter in the main method
 * by offsetting some of the heavy functions into modular type
 * methods.
 * Created by Oscar on 2/10/2016.
 */
public class HtmlUtilities
{
    //Remove attributes from every element in the document, unless it meets specific requirements
    public static void removeIrrelevantAttributes(Document doc)
    {
        for (Element e : doc.getAllElements()) //first pass through
        {   //for each element
            if(e.tagName().equals("a"))
            {   //hyperlink tag found
                hyperlinkOperations(e);
            }
            else
            {   //hyperlink tag not found
                for (Attribute a : e.attributes())
                {   //for each attribute in the element
                    e.removeAttr(a.getKey());
                }
            }
            if(hasBlacklistedKeyword(e))
            {   //Check if the it's a blacklisted tag, and if it is, if it satisfies the requirements for being deleted
                e.remove();
            }
        }
    }

    //Hyperlink operations
    //Helper to removeIrrelevantAttributes(...)
    private static void hyperlinkOperations(Element e)
    {
        for(Attribute a : e.attributes())
        {   //go through each attribute in the 'a' element
            if(!a.getKey().equals("href"))
            {   //skip href tags
                e.removeAttr(a.getKey());
            }
            else
            {   //href tag found, do operations for urls
                if(e.parent().tagName().equals("p"))
                {   //If the 'a' tag parent was a 'p' tag
                    e.unwrap(); //just unwrap it, but keep the text
                }
                else
                {
                    if (a.getValue().contains("https") || a.getValue().contains("http") || a.getValue().contains("mailto"))
                    {   //if "https" or "http" was found in the url of the href
                        if (hasSocialMedia(a.getValue()))
                        {   //if the url has social media
                            e.removeAttr(a.getKey()); //remove the attribute
                        }
                    }
                    else
                    {   //the link is unusable, delete
                        e.removeAttr(a.getKey());
                    }
                }
            }
        }
    }

    //Check if a url is valid, mark for deletion if not
    //Helper to removeIrrelevantAttributes(...)
    public static boolean hasSocialMedia(String url)
    {
        //check for social media
        return (url.contains("facebook") ||
                url.contains("twitter") ||
                url.contains("linkedin") ||
                url.contains("youtube"));
    }

    //Check if an element is blacklisted to be deleted
    //TODO: Preferably should have an array of blacklisted keywords, and loop through to delete.
    //Helper to removeIrrelevantAttributes(...)
    public static boolean hasBlacklistedKeyword(Element e)
    {
        String content = e.text().toLowerCase(); //get the text inside for checking
        //check if this is an 'a' tag, and if it has no attributes
        if(e.tagName().equals("a"))
        {
            if(e.attributes().size() == 0 ||
                    content.contains("give") ||
                    content.contains("alumni") ||
                    content.contains("athletics") ||
                    content.contains("apply") ||
                    content.contains("bookstore") ||
                    content.contains("home"))
            {return true;}
        }
        return false;
    }
    /****************************************************************************************************/
    //Remove irrelevant tags
    public static void removeSpecificTags(ArrayList<Elements> list)
    {
        for(Elements el: list)
        {   //for each tag
            el.forEach(Element::remove); //remove all
        }
    }
    /****************************************************************************************************/
    //Unwrap the tags that you wish to keep the text from
    public static void unwrapSpecificTags(ArrayList<Elements> list)
    {
        for(Elements el: list)
        {   //for each tag
            el.forEach(Element::unwrap);
        }
    }
    /****************************************************************************************************/
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
    /****************************************************************************************************/
    public static void removeEmptyTagPairs(Document doc)
    {
        //remove empty tag pairs
        //for each element in the doc
        doc.select("*")
                .stream()
                .filter(e -> !e.hasText() && e.isBlock())
                .forEach(Element::remove); //remove empty pairs
    }
    /****************************************************************************************************/
    //unwrap nested tags with only one child
    //TODO: Check for lists with only one element, and replace it with a 'p' tag.
    public static void unwrapNestedRedundancies(Document doc)
    {
        //for each element
        doc.getAllElements()//if a div element only has one child
                .stream()
                .filter(e -> (e.children().size() == 1 && e.tagName().equals("div")))
                .forEach(Element::unwrap); //unwrap it
    }
    /****************************************************************************************************/
    //Get the domain from a given url
    public static String getDomainName(String url){
        URI uri;
        try {
            uri = new URI(url);
            return uri.getHost();
        } catch (URISyntaxException e) {
            return null;
        }
    }
    /****************************************************************************************************/
    //Parse the document into headers and contents
    public static void parseDoc(ArrayList<String> headers, Elements el, String path) {
        BufferedWriter bw = null;
        File file;
        try{
            for(Element e : el)
            {
                if(headers.contains(e.tagName()))
                {   //if the current element is one of the headers
                    if(bw!=null)
                    {   //close the file if it's not null and has been created
                        bw.write("</html>");
                        bw.close();
                        System.out.println("\tdone.");
                    }
                    String raw_title = e.unwrap().toString();
                    String title = raw_title.toLowerCase()
                            .replaceAll("[^A-Za-z0-9]", " ") //get rid of all non-alphanumeric chars
                            .replaceAll("\\s+", " ") //change whitespace to a single space
                            .replaceAll(" ", "_") //replace all spaces with an underscore
                            .replace("nsbp", "")
                            .replace("amp", "and");
                    String doc_path = "docs/" + path + "/" + title + ".html";
                    file = new File(doc_path); //specify the path
                    FileWriter fw = null;
                    fw = new FileWriter(file.getAbsoluteFile());
                    bw = new BufferedWriter(fw); //open a buffered writer to write to file
                    System.out.println("\tcreating " + doc_path + "...");
                    bw.write("<html>\n"); //write the html tag
                    bw.write("<h3>" + raw_title + "</h3>" + "\n"); //write the header tag
                }
                else
                {
                    if(bw != null)
                    {   //write misc content to the file
                        bw.write(e.toString() + "\n");
                    }
                }

            }
            if(bw!=null)
            {   //close the last file in the url
                bw.write("</html>");
                bw.close();
                System.out.println("\tdone.");
            }
            System.out.println("\teof...\n\tmoving on...\n");
        } catch(IOException e1){
            e1.printStackTrace();
        }
    }

    //better way to determine if a url is relative or absolute
    public static String ensureAbsoluteURL(String base, String maybeRelative) {
        if (maybeRelative.startsWith("http")) {
            return maybeRelative;
        } else {
            try {
                return new URL(new URL(base), maybeRelative).toExternalForm();
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}
