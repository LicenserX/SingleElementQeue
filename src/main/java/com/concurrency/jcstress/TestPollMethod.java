package com.concurrency.jcstress;

import com.concurrency.news.News;
import com.concurrency.queue.SingleElementQueue;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.LL_Result;

import java.util.Queue;

@JCStressTest
@Outcome(id = "News, null", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Reader_1 has taken car from the Queue")
@Outcome(id = "null, News", expect = Expect.ACCEPTABLE, desc = "Reader_2 has taken car from the Queue")
@Outcome(id = "null, null", expect = Expect.ACCEPTABLE, desc = "No elements in the Queue")
@Outcome(id = "News, News", expect = Expect.FORBIDDEN, desc = "Not expected behavior")
@State
public class TestPollMethod {
    Queue<News<?>> singleElementQueue = new SingleElementQueue<>();
    News<String> politicalNews = new News<>();

    @Actor
    public void reader_1(LL_Result oo) {
        oo.r1 = singleElementQueue.poll();
    }

    @Actor
    public void reader_2(LL_Result oo) {
        oo.r2 = singleElementQueue.poll();
    }

    @Actor
    public void writer_1() {
        singleElementQueue.offer(politicalNews);
    }
}
