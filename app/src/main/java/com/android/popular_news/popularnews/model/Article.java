package com.android.popular_news.popularnews.model;

import com.android.popular_news.popularnews.data.remote.Medium;

/**
 * Created by ovidiu on 12.05.2017.
 */

public class Article {
    private String mTitle;
    private String mUrl;
    private String mAuthor;
    private String mAbstract_section;
    private String mPublishedDate;

    public Article(String mTitle,  String mAbstract_section, String url, String publishedDate,
                   String author){
        this.mTitle = mTitle;
        this.mAbstract_section = mAbstract_section;
        this.mUrl = url;
        mPublishedDate = publishedDate;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getAbstract_section() {
        return mAbstract_section;
    }

    public void setAbstract_section(String abstract_section) {
        mAbstract_section = abstract_section;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
