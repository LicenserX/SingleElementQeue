package com.concurrency.jcstress;

import com.concurrency.news.News;
import com.concurrency.queue.SingleElementQueue;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.Queue;

@JCStressTest
@Outcome(id = "0, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Car in the Queue")
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "Car still in the Queue")
@Outcome(id = "1, 0", expect = Expect.FORBIDDEN, desc = "Not expected behavior")
@Outcome(id = "0, 0", expect = Expect.FORBIDDEN, desc = "Not expected behavior")
@State
public class TestTotalOrder {
    Queue<News<?>> singleElementQueue = new SingleElementQueue<>();
    News<String> politicalNews = new News<>();

    @Actor
    public void writer_1(II_Result i) {
        i.r1 = singleElementQueue.size();
        singleElementQueue.offer(politicalNews);
        i.r2 = singleElementQueue.size();
    }

    @Actor
    public void writer_2(II_Result i) {
        i.r1 = singleElementQueue.size();
        singleElementQueue.offer(politicalNews);
        i.r2 = singleElementQueue.size();
    }
}
