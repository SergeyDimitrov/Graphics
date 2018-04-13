package utils;

import geometry.Face;
import geometry.PolygonPoint;
import geometry.Vector;
import obj.ObjModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;
import static utils.LinearUtils.*;

/**
 * Created by pb on 12.04.2018.
 */
public class RenderUtils {
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 800;

    private static void setPixel(int x, int y, BufferedImage image, int color, boolean inv) {
        if (inv) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        image.setRGB(x, IMAGE_HEIGHT - 1 - y, color);
    }

    private static void setPixel(int x, int y, BufferedImage image, int color) {
        setPixel(x, y, image, color, false);
    }

    private static void lineBr(int x0, int y0, int x1, int y1, BufferedImage image, int color) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int tmp;
        boolean inv = false;
        if (abs(dy) > abs(dx)) {
            inv = true;
            tmp = x0;
            x0 = y0;
            y0 = tmp;
            tmp = x1;
            x1 = y1;
            y1 = tmp;
            tmp = dx;
            dx = dy;
            dy = tmp;
        }

        if (x1 < x0) {
            tmp = x1;
            x1 = x0;
            x0 = tmp;
            tmp = y1;
            y1 = y0;
            y0 = tmp;
        }

        if (dx == 0) {
            tmp = y1;
            y1 = y0;
            y0 = tmp;
            for (int y = y0; y <= y1; y++) {
                setPixel(x0, y, image, color, inv);
            }
            return;
        }

        float k = abs(1.0f * dy / dx);
        float offset = 0;
        int y = y0;

        for (int x = x0; x <= x1; x++) {
            setPixel(x, y, image, color, inv);
            offset += k;
            if (offset >= 0.5f) {
                y += (y1 > y0 ? 1 : -1);
                offset -= 1;
            }
        }
    }

    private static void lineBr(double x0, double y0, double x1, double y1, BufferedImage image, int color) {
        lineBr((int) x0, (int) y0, (int) x1, (int) y1, image, color);
    }

    private static void fillPolygon(List<Vector> polygon, BufferedImage image, int color, double[][] zBuf) {
        if (polygon.size() != 3) {
            return;
        }

        int xMax = Integer.MIN_VALUE;
        int xMin = Integer.MAX_VALUE;
        int yMax = xMax;
        int yMin = xMin;
        for (Vector v : polygon) {
            int x = (int) v.get(0);
            int y = (int) v.get(1);
            xMax = max(x, xMax);
            xMin = min(x, xMin);
            yMax = max(y, yMax);
            yMin = min(y, yMin);
        }

        int x0 = (int) polygon.get(0).get(0);
        int y0 = (int) polygon.get(0).get(1);
        int x1 = (int) polygon.get(1).get(0);
        int y1 = (int) polygon.get(1).get(1);
        int x2 = (int) polygon.get(2).get(0);
        int y2 = (int) polygon.get(2).get(1);

        for (int x = xMin; x <= xMax; x++) {
            int l = -1;
            int r = -1;
            for (int y = yMin; y <= yMax; y++) {
                boolean inside = true;
                int l0 = (y - y2) * (x1 - x2) - (x - x2) * (y1 - y2);
                int ll0 = (y0 - y2) * (x1 - x2) - (x0 - x2) * (y1 - y2);
                int l1 = (y - y0) * (x2 - x0) - (x - x0) * (y2 - y0);
                int ll1 = (y1 - y0) * (x2 - x0) - (x1 - x0) * (y2 - y0);
                int l2 = (y - y1) * (x0 - x1) - (x - x1) * (y0 - y1);
                int ll2 = (y2 - y1) * (x0 - x1) - (x2 - x1) * (y0 - y1);
                if (l0 * ll0 < 0 || l1 * ll1 < 0 || l2 * ll2 < 0) {
                    inside = false;
                }
//                for (int k = 0; k < 3; k++) {
//                    int prev = k - 1;
//                    int next = k + 1;
//                    if (prev == -1) prev = 2;
//                    if (next == 3) next = 0;
//                    double lamNum = ((y - polygon.get(k).get(1)) * (polygon.get(prev).get(0) - polygon.get(k).get(0))
//                            - (x - polygon.get(k).get(0)) * (polygon.get(prev).get(1) - polygon.get(k).get(1)));
//
//                    double lamDen = ((y - polygon.get(next).get(1)) * (polygon.get(prev).get(0) - polygon.get(k).get(0))
//                            - (x - polygon.get(next).get(0)) * (polygon.get(prev).get(1) - polygon.get(k).get(1)));
//
//                    double lam = lamNum / lamDen;
//
//                    if (lam < 0) {
//                        inside = false;
//                    }
//                }
                if (inside) {
                    setPixel(x, y, image, color);
                }
            }
        }
    }

    public static void render(ObjModel model, BufferedImage image) {
        double[][] zBuf = new double[IMAGE_HEIGHT][IMAGE_WIDTH];
        for (int i = 0; i < zBuf.length; i++) {
            Arrays.fill(zBuf, Double.MIN_VALUE);
        }
        for (Face f : model.getFaces()) {
            List<PolygonPoint> polygon = f.getPolygonPoints();
            List<Vector> points = new ArrayList<>();
            List<Vector> pp = new ArrayList<>();
            for (PolygonPoint p : polygon) {
                Vector v = model.getV(p.getV());
                int x = (int) (v.get(0) * IMAGE_WIDTH / 3.) + IMAGE_WIDTH / 2;
                int y = (int) (v.get(1) * IMAGE_HEIGHT / 3.) + IMAGE_HEIGHT / 2;
                points.add(new Vector(x, y));
                pp.add(v);
            }

            Vector n = cross(sub(pp.get(2), pp.get(0)), sub(pp.get(1), pp.get(0)));
            n.normalize();
            Vector light = new Vector(0, 0, -1);
            double intensity = scalar(n, light);
            if (intensity <= 0) {
                continue;
            }
            int color = new Color((int) (255 * intensity), (int) (255 * intensity), (int) (255 * intensity)).getRGB();
            for (int j = 0; j < points.size(); j++) {
                lineBr(points.get(j).get(0), points.get(j).get(1),
                        points.get((j + 1) % points.size()).get(0), points.get((j + 1) % points.size()).get(1),
                        image, color);
            }
            fillPolygon(points, image, color, zBuf);
        }
    }


}
