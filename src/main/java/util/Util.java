package util;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
