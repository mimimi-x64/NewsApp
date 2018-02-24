package com.example.android.newsapp;

/**
 * Created by phartmann on 20/02/2018.
 */

public class NewsList {

    /* Values */
    private String mTitle;
    private String mSection;
    private String mArticleUrl;

    /* Constructor */
    public NewsList( String mTitle, String mSection, String articleUrl ) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mArticleUrl = articleUrl;
    }

    /* Getters */
    public String getArticleUrl() {
        return mArticleUrl;
    }
    public String getTitle() {
        return mTitle;
    }
    public String getSection() {
        return mSection;
    }
}
