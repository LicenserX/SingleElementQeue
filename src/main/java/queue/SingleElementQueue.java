package queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SingleElementQueue<T>  extends AbstractQueue<T>
        implements BlockingQueue<T> {

    private T element;

    private final ReentrantLock lock;

    private final Condition notEmpty;

    private final Condition notFull;


    public SingleElementQueue(){
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }

    public boolean offer(T e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (element != null){
                return false;
            }
            element = e;
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void put(T e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (element != null){
                notFull.await();
            }
            element = e;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T e, long timeout, TimeUnit unit) throws InterruptedException {
        checkNotNull(e);
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (element != null) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = notFull.awaitNanos(nanos);
            }
            element = e;
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (element == null){
                notEmpty.await();
            }
            T e = element;
            element = null;
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (element == null) {
                if (nanos <= 0) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            T e = element;
            element = null;
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    public T poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (element == null){
                return null;
            }
            T e = element;
            element = null;
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    public T peek() {
        return null;
    }

    public int size() {
        return element == null ? 0 : 1;
    }










    public Iterator<T> iterator() {
        return null;
    }

    public int remainingCapacity() {
        return 0;
    }

    public int drainTo(Collection<? super T> c) {
        return 0;
    }

    public int drainTo(Collection<? super T> c, int maxElements) {
        return 0;
    }


}
