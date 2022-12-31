package com.space.starwars.utils;

/**
 * @author Fabio Barros
 * @version 1.0 30/12/22
 */
public final class FilmUtils {

    public static String getFilmIdFromUrl(String url){
        String [] urlSplitted = url.split("/");

        return urlSplitted[urlSplitted.length - 1];
    }

}
