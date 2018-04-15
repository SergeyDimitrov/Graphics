package geometry;

import java.util.List;

import static java.lang.Math.sqrt;

public class Vector<T extends Number> {

    private T[] xs;

    public Vector(int n) {
        xs = (T[]) new Number[n];
    }

    public Vector(List<T> vec) {
        xs = (T[]) new Number[vec.size()];
        for (int i = 0; i < xs.length; i++) {
            xs[i] = vec.get(i);
        }
    }

    public Vector(T... xs) {
        this.xs = xs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T x : xs) {
            builder.append(x).append(" ");
        }
        return builder.toString();
    }

    public T get(int i) {
        return xs[i];
    }

    public void set(int i, T x) {
        xs[i] = x;
    }

    public int size() {
        return xs.length;
    }

    public double len() {
        double res = 0;
        for (T x : xs) {
            res += x.doubleValue() * x.doubleValue();
        }
        return sqrt(res);
    }

    public void normalize() {
        double len = len();
        for (int i = 0; i < xs.length; i++) {
            Double newVal = xs[i].doubleValue() / len;
            xs[i] = (T) newVal;
        }
    }
}
