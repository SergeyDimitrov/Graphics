package obj;

import geometry.Face;
import geometry.PolygonPoint;
import geometry.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ObjUtils {

    public static ObjModel parse(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringTokenizer tok;
        String line;
        ObjModel model = new ObjModel();
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) continue;
            tok = new StringTokenizer(line);
            String identifier = tok.nextToken();
            boolean isVector = identifier.equals("v") || identifier.equals("vn") || identifier.equals("vt");
            boolean isFace = identifier.equals("f");
            if (isVector) {
                List<Double> vectorList = new ArrayList<>();
                while (tok.hasMoreTokens()) {
                    vectorList.add(Double.parseDouble(tok.nextToken()));
                }
                Vector vec = new Vector(vectorList);
                switch (identifier) {
                    case "v":
                        model.addV(vec);
                        break;
                    case "vt":
                        model.addVt(vec);
                        break;
                    case "vn":
                        model.addVn(vec);
                        break;
                }
            }
            if (isFace) {
                List<PolygonPoint> f = new ArrayList<>();
                while (tok.hasMoreTokens()) {
                    StringTokenizer inTok = new StringTokenizer(tok.nextToken(), "/");
                    ArrayList<Integer> polyPointList = new ArrayList<>();
                    while (inTok.hasMoreTokens()) {
                        polyPointList.add(Integer.parseInt(inTok.nextToken()));
                    }
                    f.add(new PolygonPoint(polyPointList));
                }
                model.addF(new Face(f));
            }
        }
        return model;
    }
}
