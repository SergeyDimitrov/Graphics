package utils;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by pb on 13.04.2018.
 */
public class RandomUtils {

    private static Random rand = new Random();

    public static int getRandomColorRGB() {
        return abs(rand.nextInt()) % (1 << 24);
    }
}
