package com.example.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class Parser extends HtmlUtilities
{
    public static void main(String[] args)
    {
        //Parse the URLs
        try {
            HtmlParser parser = new HtmlParser();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
