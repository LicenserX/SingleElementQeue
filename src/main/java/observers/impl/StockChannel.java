package observers.impl;

import observers.Subscriber;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class StockChannel extends AbstractChannel implements Subscriber {

//    TODO ask about storing BigDecimal
    private final List<BigDecimal> quotes = new ArrayList<>();

    public StockChannel(String uniqueChannelName) {
        super(uniqueChannelName);
    }

    @Override
    public void update(Object news) {
        if (news instanceof Double) {
            synchronized (quotes){
                quotes.add(new BigDecimal((Double) news).round(MathContext.DECIMAL64));
            }
        }
    }

    public void setQuote(Double quotes) {
        synchronized (this.quotes) {
            this.quotes.add(new BigDecimal(quotes).round(MathContext.DECIMAL64));
        }
    }

    public BigDecimal getLastQuote() {
        synchronized (this.quotes){
            return quotes.get(quotes.size()-1);
        }
    }

    public List<BigDecimal> getAllQuotes() {
        synchronized (this.quotes){
            return new ArrayList<>(quotes);
        }
    }




}
