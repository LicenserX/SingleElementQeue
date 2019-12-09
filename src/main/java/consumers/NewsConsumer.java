package consumers;

import news.News;
import publisher.NewsAgency;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class NewsConsumer implements Runnable {

    private Queue<News<?>> news;

    private NewsAgency newsAgency;

    private int counter = 0;

    public NewsConsumer(Queue<News<?>> news, NewsAgency newsAgency) {
        this.news = news;
        this.newsAgency = newsAgency;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println(counter + " Try to update news...");
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
            System.out.println(counter++ + " No news");
        }
    }
}
