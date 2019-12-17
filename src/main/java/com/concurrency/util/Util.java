package com.concurrency.util;

import java.util.concurrent.ThreadLocalRandom;

public final class Util {
    private Util() {}

    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
