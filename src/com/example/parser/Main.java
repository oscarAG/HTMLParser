package com.example.parser;

import java.util.List;

/**
 * Main class of the program.
 * Created by oscar on 2/9/16.
 */
public class Main
{
    private static final int DELAY = 5000; //set delay time for a url domain to be visited again

    public static void main(String[] args)
    {
        //Get all links to the universities in the United States
        Universities uni = new Universities();
        uni.setUniversities(); //init a list of all university urls
        List<UrlClass> url_objects = uni.getUniversityObjects(); //get a list of UrlClass objects
        int marked = 0; //keep track of how many url objects have been marked as visited
        long timer = System.currentTimeMillis(); //timer to print some information every few seconds
        long start_time = System.currentTimeMillis();

        System.out.println("Visiting URLs...");
        while(!UrlClass.allVisited(url_objects))
        {   //while all urls haven't been visited
            for(UrlClass obj : url_objects)
            {   //go through the list
                if(!obj.isVisited() && (System.currentTimeMillis() - obj.getTime() > DELAY))
                {   //if a url is ready to be visited
                    //visit
                    obj.setVisited(true);
                    //reset timer for all with same domain
                    UrlClass.resetTimers(obj.getDomain(), url_objects);
                    //update counter
                    marked++;
                }
            }

            //print out the remaining number every 5 secs
            if(System.currentTimeMillis() - timer > DELAY)
            {   //if it's been more than 5 seconds since the last updated number print
                System.out.println("\n" + UrlClass.remaining(url_objects) + " total remaining..."); //print
                System.out.println("Waiting " + DELAY + " ms before continuing...\n");
                timer = System.currentTimeMillis(); //reset timer
            }
        }
        System.out.println(marked + " objects marked as visited."); //print
        System.out.println("Crawling completed in " + ((System.currentTimeMillis() - start_time) / 1000) + " seconds.");
        /*
        //Parse the URLs
        try {
            new HtmlParser();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
