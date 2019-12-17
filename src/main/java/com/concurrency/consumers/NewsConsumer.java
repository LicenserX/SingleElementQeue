package com.concurrency.consumers;

import com.concurrency.news.News;
import com.concurrency.publisher.NewsAgency;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class NewsConsumer implements Runnable {

    private final Queue<News<?>> news;

    private final NewsAgency newsAgency;

    private final int timeout;

    private int counter = 0;

    public NewsConsumer(Queue<News<?>> news, NewsAgency newsAgency, int timeout) {
        this.news = news;
        this.newsAgency = newsAgency;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
                System.out.println(counter + " Try to update com.concurrency.news...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            News<?> updatedNews = news.poll();
            print(updatedNews);
            newsAgency.notifyObservers(updatedNews);
        }
    }

    private void print(News<?> news) {
        if (news != null) {
            System.out.println(counter++ + " " +  news.getNews().toString());
        } else {
            System.out.println(counter++ + " No com.concurrency.news");
        }
    }
}
