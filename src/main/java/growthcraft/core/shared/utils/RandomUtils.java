package growthcraft.core.shared.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private RandomUtils() {
    }

    public static boolean thresh(Random random, float thresh) {
        return random.nextInt(2000) < (int) (thresh * 2000);
    }

    public static int range(Random random, int min, int max) {
        if (min == max) return min;
        return min + random.nextInt(max - min);
    }

    public static <T> T sample(Random random, List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
