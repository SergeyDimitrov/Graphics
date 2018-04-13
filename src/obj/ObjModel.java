package obj;

import geometry.Face;
import geometry.Vector;

import java.util.ArrayList;
import java.util.List;

public class ObjModel {
    private List<Vector> vs;
    private List<Vector> vts;
    private List<Vector> vns;
    private List<Face> fs;

    public ObjModel() {
        vs = new ArrayList<>();
        vts = new ArrayList<>();
        vns = new ArrayList<>();
        fs = new ArrayList<>();
    }

    public void addV(Vector v) {
        vs.add(v);
    }

    public void addVt(Vector vt) {
        vts.add(vt);
    }

    public void addVn(Vector vn) {
        vns.add(vn);
    }

    public void addF(Face f) {
        fs.add(f);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vector v : vs) {
            builder.append("v ").append(v).append("\n");
        }
        builder.append("\n");
        for (Vector v : vts) {
            builder.append("vt ").append(v).append("\n");
        }
        builder.append("\n");
        for (Vector v : vns) {
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

    public Vector getV(int vInd) {
        return vs.get(vInd - 1);
    }

    public Vector getVt(int vtInd) {
        return vts.get(vtInd - 1);
    }
}
