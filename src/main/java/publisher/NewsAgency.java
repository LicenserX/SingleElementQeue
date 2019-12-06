package publisher;

import news.News;
import observers.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class NewsAgency {

    private final ReentrantLock lock;

    public NewsAgency() {
        this.lock = new ReentrantLock();
    }

    private List<Subscriber> channels = new ArrayList<>();

    public void addObserver(Subscriber channel) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            channels.add(channel);
        } finally {
            lock.unlock();
        }
    }

    public void removeObserver(Subscriber channel) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            channels.remove(channel);
        } finally {
            lock.unlock();
        }
    }

    public void notifyObservers(final News<?> news) {
        if (news == null){
            System.out.println("null in notifyObservers");
        }
//        if (news instanceof String){
//            System.out.println("string");
//        }
//        if (news instanceof Double){
//            System.out.println("Double");
//        }
//        if (news instanceof Object){
//            System.out.println("Object");
//        }

        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
//            channels.forEach(n -> n.update(news));
            for (Subscriber c : channels ) {
                if(news!= null ){
                    c.update(news.getNews());
                }

            }
        } finally {
            lock.unlock();
        }
    }
}


