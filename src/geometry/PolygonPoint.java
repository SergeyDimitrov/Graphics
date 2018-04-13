package geometry;

import java.util.List;

public class PolygonPoint {
    private int vInd;
    private int vtInd;
    private int vnInd;

    private PolygonPoint() {
        vInd = vtInd = vnInd = -1;
    }

    public PolygonPoint(List<Integer> polyPointList) {
        this();
        vInd = polyPointList.get(0);
        if (polyPointList.size() == 2) {
            vnInd = polyPointList.get(1);
        } else if (polyPointList.size() == 3) {
            vtInd = polyPointList.get(1);
            vnInd = polyPointList.get(2);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(vInd);
        if (vnInd != -1) {
            builder.append("/");
            if (vtInd != -1) {
                builder.append(vtInd);
                builder.append("/");
            }
            builder.append(vnInd);
        }
        return builder.toString();
    }

    public int getVind() {
        return vInd;
    }

    public int getVtInd() {
        return vtInd;
    }
}
