package com.concurrency.news;

import java.io.Serializable;

public class News<T> implements Serializable {

    private T news;

    public T getNews() {
        return news;
    }

    public void setNews(T news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "News";
    }
}
