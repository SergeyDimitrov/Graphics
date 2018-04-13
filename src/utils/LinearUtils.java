package utils;

import geometry.Vector;

/**
 * Created by pb on 13.04.2018.
 */
public class LinearUtils {

    public static Vector sub(Vector a, Vector b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException();
        }
        Vector res = new Vector(a.size());
        for (int i = 0; i < res.size(); i++) {
            res.set(i, a.get(i) - b.get(i));
        }
        return res;
    }

    private static Vector cross3(Vector a, Vector b) {
        return new Vector(a.get(1) * b.get(2) - a.get(2) * b.get(1),
                -(a.get(0) * b.get(2) - a.get(2) * b.get(0)),
                a.get(0) * b.get(1) - a.get(1) * b.get(0));
    }

    public static Vector cross(Vector a, Vector b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException();
        }

        if (a.size() == 3) {
            return cross3(a, b);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static double scalar(Vector a, Vector b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException();
        }
        double res = 0;
        for (int i = 0; i < a.size(); i++) {
            res += a.get(i) * b.get(i);
        }
        return res;
    }

}
