package geometry;

import java.util.List;

public class Vector {

    private double[] xs;

    private Vector() {
    }

    public Vector(double[] xs) {
        this.xs = xs;
    }

    public Vector(List<Double> vec) {
        xs = new double[vec.size()];
        for (int i = 0; i < xs.length; i++) {
            xs[i] = vec.get(i);
        }
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
}
