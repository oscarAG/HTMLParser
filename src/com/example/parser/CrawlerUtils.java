package com.example.parser;

import java.util.List;

/**
 * Created by Oscar on 3/8/2016.
 */
public class CrawlerUtils {
    public static boolean isAllVisited(List<UrlClass> list)
    {
        for (UrlClass url_obj : list)
        {
            if(!url_obj.isVisited())
                return false;
        }
        return true;
    }

    public static UrlClass findNextObjectToCall(List<UrlClass> list)
    {
        for(UrlClass u : list)
        {
            if(System.currentTimeMillis() - u.getTime() > 5000)
            {   //it's been more than 5 seconds since the last call
                return u;
            }
        }
        return null;
    }

    public static void updateObjects(List<UrlClass> list, UrlClass u){
        for(UrlClass j : list)
        {
            if(j.getDomain().equals(u.getDomain()) && !j.getUrl().equals(u.getUrl())){
                list.get(list.indexOf(j)).resetTime();
            }
        }
    }
}
