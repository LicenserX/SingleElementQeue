package observers.impl;

import observers.Subscriber;

public class StockChannel extends AbstractChannel implements Subscriber {

    private Double quote;

    public StockChannel(String uniqueChannelName) {
        super(uniqueChannelName);
    }

    @Override
    public void update(Object news) {
        if (news instanceof Double) {
            quote = (Double) news;
        }
    }

    public void setQuotes(Double quotes) {
        this.quote = quotes;
    }

    public Double getQuotes() {
        return quote;
    }
}
