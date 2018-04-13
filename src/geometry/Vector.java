package geometry;

import java.util.List;

import static java.lang.StrictMath.sqrt;

public class Vector {

    private double[] xs;

    private Vector() {
    }

    public Vector(int n) {
        xs = new double[n];
    }

    public Vector(List<Double> vec) {
        xs = new double[vec.size()];
        for (int i = 0; i < xs.length; i++) {
            xs[i] = vec.get(i);
        }
    }

    public Vector(double... xs) {
        this.xs = xs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (double x : xs) {
            builder.append(x).append(" ");
        }
        return builder.toString();
    }

    public double get(int i) {
        return xs[i];
    }

    public void set(int i, double x) {
        xs[i] = x;
    }

    public int size() {
        return xs.length;
    }

    public double len() {
        double res = 0;
        for (double x : xs) {
            res += x * x;
        }
        return sqrt(res);
    }

    public void normalize() {
        double len = len();
        for (int i = 0; i < xs.length; i++) {
            xs[i] /= len;
        }
    }
}
