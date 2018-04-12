package utils;

import geometry.Face;
import geometry.PolygonPoint;
import geometry.Vector;
import obj.ObjModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.white;
import static java.lang.Math.abs;

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

    public static void render(ObjModel model, BufferedImage image) {
        for (Face f : model.getFaces()) {
            List<PolygonPoint> polygon = f.getPolygonPoints();
            List<Vector> points = new ArrayList<>();
            for (PolygonPoint p : polygon) {
                Vector v = model.getV(p.getV());
                int x = (int) (v.get(0) * IMAGE_WIDTH / 3.) + IMAGE_WIDTH / 2;
                int y = (int) (v.get(1) * IMAGE_HEIGHT / 3.) + IMAGE_HEIGHT / 2;
                setPixel(x, y, image, white.getRGB(), false);
                points.add(new Vector(x, y));
            }
            for (int j = 0; j < points.size(); j++) {
                lineBr(points.get(j).get(0), points.get(j).get(1),
                        points.get((j + 1) % points.size()).get(0), points.get((j + 1) % points.size()).get(1),
                        image, white.getRGB());
            }
        }
    }
}
