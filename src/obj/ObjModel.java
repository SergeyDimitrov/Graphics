package obj;

import geometry.Face;
import geometry.Vector;

import java.util.ArrayList;
import java.util.List;

public class ObjModel {
    private List<Vector<Double>> vs;
    private List<Vector<Double>> vts;
    private List<Vector<Double>> vns;
    private List<Face> fs;

    public ObjModel() {
        vs = new ArrayList<>();
        vts = new ArrayList<>();
        vns = new ArrayList<>();
        fs = new ArrayList<>();
    }

    public void addV(Vector<Double> v) {
        vs.add(v);
    }

    public void addVt(Vector<Double> vt) {
        vts.add(vt);
    }

    public void addVn(Vector<Double> vn) {
        vns.add(vn);
    }

    public void addF(Face f) {
        fs.add(f);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vector<Double> v : vs) {
            builder.append("v ").append(v).append("\n");
        }
        builder.append("\n");
        for (Vector<Double> v : vts) {
            builder.append("vt ").append(v).append("\n");
        }
        builder.append("\n");
        for (Vector<Double> v : vns) {
            builder.append("vn ").append(v).append("\n");
        }
        builder.append("\n");
        for (Face f : fs) {
            builder.append("f ").append(f).append("\n");
        }
        return builder.toString();
    }

    public List<Face> getFaces() {
        return fs;
    }

    public Vector<Double> getV(int vInd) {
        return vs.get(vInd - 1);
    }

    public Vector<Double> getVt(int vtInd) {
        return vts.get(vtInd - 1);
    }
}
