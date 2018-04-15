package utils;

import geometry.Vector;

/**
 * Created by pb on 13.04.2018.
 */
public class LinearUtils {

    public static Vector<Double> sub(Vector<Double> a, Vector<Double> b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException();
        }
        Vector<Double> res = new Vector<>(a.size());
        for (int i = 0; i < res.size(); i++) {
            res.set(i, a.get(i) - b.get(i));
        }
        return res;
    }

    private static Vector<Double> cross3(Vector<Double> a, Vector<Double> b) {
        return new Vector<>(a.get(1) * b.get(2) - a.get(2) * b.get(1),
                -(a.get(0) * b.get(2) - a.get(2) * b.get(0)),
                a.get(0) * b.get(1) - a.get(1) * b.get(0));
    }

    public static Vector<Double> cross(Vector<Double> a, Vector<Double> b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException();
        }

        if (a.size() == 3) {
            return cross3(a, b);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static double scalar(Vector<Double> a, Vector<Double> b) {
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
