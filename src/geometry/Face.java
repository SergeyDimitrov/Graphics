package geometry;

import java.util.List;

public class Face {
    private List<PolygonPoint> f;

    private Face() {
    }

    public Face(List<PolygonPoint> f) {
        this.f = f;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PolygonPoint p : f) {
            builder.append(p).append(" ");
        }
        return builder.toString();
    }

    public List<PolygonPoint> getPolygonPoints() {
        return f;
    }
}
