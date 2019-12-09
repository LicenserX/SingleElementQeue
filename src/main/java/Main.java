import consumers.NewsConsumer;
import news.News;
import observers.Subscriber;
import observers.impl.PoliticalChannel;
import observers.impl.StockChannel;
import producers.NewsProducer;
import publisher.NewsAgency;
import queue.SingleElementQueue;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Queue<News<?>> singleElementQueue = new SingleElementQueue<>();
        Subscriber politicalSubscriber = new PoliticalChannel("Very cool political news");
        Subscriber stockSubscriber = new StockChannel("Exact stock news");
        NewsAgency newsAgency = new NewsAgency();

        newsAgency.addObserver(politicalSubscriber);
        newsAgency.addObserver(stockSubscriber);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new NewsProducer(singleElementQueue));
        service.submit(new NewsConsumer(singleElementQueue, newsAgency));
        service.shutdown();


        TimeUnit.MILLISECONDS.sleep(2000);
        String news1 = ((PoliticalChannel) politicalSubscriber).getNews();
        Double news2 = ((StockChannel) stockSubscriber).getQuotes();

        System.out.println("USA NEWS: " + news1);
        System.out.println("CURRENT GAZPROM'S QUOTES: " + news2);
    }
}
