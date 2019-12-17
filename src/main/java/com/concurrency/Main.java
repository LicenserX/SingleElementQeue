package com.concurrency;

import com.concurrency.consumers.NewsConsumer;
import com.concurrency.news.News;
import com.concurrency.observers.Subscriber;
import com.concurrency.observers.impl.PoliticalChannel;
import com.concurrency.observers.impl.StockChannel;
import com.concurrency.producers.NewsProducer;
import com.concurrency.publisher.NewsAgency;
import com.concurrency.queue.SingleElementQueue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Queue<News<?>> singleElementQueue = new SingleElementQueue<>();
        Subscriber politicalSubscriber = new PoliticalChannel("Very cool political com.concurrency.news");
        Subscriber stockSubscriber = new StockChannel("Exact stock com.concurrency.news");
        NewsAgency newsAgency = new NewsAgency();

        newsAgency.addObserver(politicalSubscriber);
        newsAgency.addObserver(stockSubscriber);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new NewsProducer(singleElementQueue, 200));
        service.submit(new NewsConsumer(singleElementQueue, newsAgency, 200));
        service.shutdown();


        TimeUnit.MILLISECONDS.sleep(2000);
        List<String> politicalNewsHistory = ((PoliticalChannel) politicalSubscriber).getAllNews();
        List<BigDecimal> stockNewsHistory = ((StockChannel) stockSubscriber).getAllQuotes();

        System.out.println("USA NEWS: " + politicalNewsHistory);
        System.out.println("CURRENT GAZPROM'S QUOTES: " + stockNewsHistory);
    }
}
