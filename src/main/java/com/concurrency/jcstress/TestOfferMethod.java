package com.concurrency.jcstress;

import com.concurrency.news.News;
import com.concurrency.queue.SingleElementQueue;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.util.Queue;

@JCStressTest
@Outcome(id = "true, false", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Writer_1 has added news in the Queue")
@Outcome(id = "false, true", expect = Expect.ACCEPTABLE, desc = "Writer_2 has added news in the Queue")
@Outcome(id = "false, false", expect = Expect.FORBIDDEN, desc = "Not expected behavior")
@Outcome(id = "true, true", expect = Expect.FORBIDDEN, desc = "Not expected behavior")
@State
public class TestOfferMethod {
    Queue<News<?>> singleElementQueue = new SingleElementQueue<>();
    News<String> politicalNews = new News<>();

    @Actor
    public void writer_1(ZZ_Result bb) {
        bb.r1 = singleElementQueue.offer(politicalNews);
    }

    @Actor
    public void writer_2(ZZ_Result bb) {
        bb.r2 = singleElementQueue.offer(politicalNews);
    }
}
