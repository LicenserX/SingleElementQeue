package com.concurrency.producers;

import com.concurrency.news.News;
import com.concurrency.util.Util;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class NewsProducer implements Runnable{

    private final Queue<News<?>> news;

    private News<String> politicalNews;

    private News<Double> stockNews;

    private int timeout;

    private int counter = 0;

    public NewsProducer(Queue<News<?>> news, int timeout) {
        this.news = news;
        this.timeout = timeout;
    }

    private News<?> getRandomNews() {
        boolean isTrue = Util.nextBoolean();

        if (politicalNews == null) {
            createPoliticalNews();
        }
        if (stockNews == null) {
            createStockNews();
        }

        return isTrue ? politicalNews : stockNews;
    }

    private void createStockNews() {
        stockNews = new News<>();
        stockNews.setNews(248.34);
    }

    private void createPoliticalNews() {
        politicalNews = new News<>();
        politicalNews.setNews("Uncle Donald is trying to take over the world!");
    }

    private void print(boolean offer) {
        if (offer) {
            System.out.println(counter++ + " News were added...");
        } else {
            System.out.println(counter++ + " News weren't add...");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(news.offer(getRandomNews()));
        }
    }
}
