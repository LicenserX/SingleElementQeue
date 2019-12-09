import consumers.NewsConsumer;
import news.News;
import observers.Subscriber;
import observers.impl.PoliticalChannel;
import observers.impl.StockChannel;
import producers.NewsProducer;
import publisher.NewsAgency;
import queue.SingleElementQueue;

import java.math.BigDecimal;
import java.util.List;
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
        List<String> politicalNewsHistory = ((PoliticalChannel) politicalSubscriber).getAllNews();
        List<BigDecimal> stockNewsHistory = ((StockChannel) stockSubscriber).getAllQuotes();

        System.out.println("USA NEWS: " + politicalNewsHistory);
        System.out.println("CURRENT GAZPROM'S QUOTES: " + stockNewsHistory);
    }
}
