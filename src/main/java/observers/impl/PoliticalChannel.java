package observers.impl;

import observers.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class PoliticalChannel extends AbstractChannel implements Subscriber {

    private final List<String> newsHistory = new ArrayList<>();

    public PoliticalChannel(String uniqueChannelName) {
        super(uniqueChannelName);
    }

//    TODO need synchronized?
    public void update(Object news) {
        if (news instanceof String){
            synchronized (newsHistory){
                newsHistory.add((String)news);
            }
        }
    }

    public List<String> getAllNews() {
        synchronized (newsHistory) {
            return newsHistory;
        }
    }

    public String getLastNews() {
        synchronized (newsHistory) {
            return newsHistory.get(newsHistory.size() - 1);
        }
    }

    public void setNews(String news) {
        synchronized (newsHistory){
            newsHistory.add(news);
        }
    }
}
