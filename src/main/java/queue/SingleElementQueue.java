package queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SingleElementQueue<T> {

    private volatile T element;

    private final ReentrantLock lock;

    private final Condition notEmpty;

    public SingleElementQueue(){
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
    }

    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }

    public boolean offer(T e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (element == null){
                element = e;
                return true;
            } else {
                notEmpty.await();
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public T poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            T e = element;
            element = null;
            return e;
        } finally {
            lock.unlock();
        }
    }



}
