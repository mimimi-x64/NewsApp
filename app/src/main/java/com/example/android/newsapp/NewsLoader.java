package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by phartmann on 21/02/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsList>> {

    /* Query URL */
    private String mUrl;

    /* Constructor to initialize Loader and pass Url to query */
    public NewsLoader( Context context, String url ) {
        super(context);
        mUrl = url;
    }

    /* Thread in background */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List <NewsList> loadInBackground() {
        /* Check if Url is Valid */
        if(mUrl == null){
            return null;
        }
        /* Do http request, process, decode, extract, etc */
        List<NewsList> newsLists = QueryNews.fetchData(mUrl);
        return newsLists;
    }
}
