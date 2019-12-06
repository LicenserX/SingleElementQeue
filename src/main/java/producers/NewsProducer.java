package producers;

import news.News;
import util.Util;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class NewsProducer implements Runnable{

    private final Queue<News<?>> news;

    News<String> politicalNews;

    News<Double> stockNews;

    private int counter = 0;

    public NewsProducer(Queue<News<?>> news) {
        this.news = news;
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
        stockNews.setNews(99.0354);
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
        while (counter < 25) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(news.offer(getRandomNews()));
        }
    }
}
