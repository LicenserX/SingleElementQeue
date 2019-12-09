package observers.impl;

import observers.Subscriber;

public class PoliticalChannel extends AbstractChannel implements Subscriber {

    private String news;

    public PoliticalChannel(String uniqueChannelName) {
        super(uniqueChannelName);
    }

    public void update(Object news) {
        if (news instanceof String){
            this.news = (String) news;
        }
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
