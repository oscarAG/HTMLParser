package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Do child url operations here
 * Created by oscar on 3/29/16.
 */
public class ChildUrl {
    public static List<UrlClass> getChildUrls(String url, String parent){
        //System.out.println("Press enter to continue..");
        System.out.println("Parent domain: " + parent);
        List<UrlClass> urls = new ArrayList<>();
        Document doc; //Retrieve HTML from the url
        int counter = 0;
        try {
            doc = Jsoup.connect(url).get();
            Elements rawChildUrls = doc.getElementsByTag("a");
            for(Element el : rawChildUrls){
                if(el.hasAttr("href")){
                    //href tag exists
                    String dom = HtmlUtilities.getDomainName(el.attr("abs:href"));
                    if(dom != null){
                        if(dom.equals(parent)){
                            //System.out.println("\tChild with similar domain found.");
                            System.out.println("\t" + el.attr("abs:href"));
                            urls.add(new UrlClass(el.attr("abs:href")));
                            counter++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: READ TIMEOUT: " + url);
        }
        System.out.println("\t" + counter + " child urls found.");
        return urls;
    }
}
