package utils;

import geometry.Face;
import geometry.PolygonPoint;
import geometry.Vector;
import obj.ObjModel;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    private static double getX(int x) {
        return (x - IMAGE_WIDTH / 2) * 3. / IMAGE_WIDTH;
    }

    private static int getX(double x) {
        return (int) (x * IMAGE_WIDTH / 3.) + IMAGE_WIDTH / 2;
    }

    private static double getY(int y) {
        return (y - IMAGE_HEIGHT / 2) * 3. / IMAGE_HEIGHT;
    }

    private static int getY(double y) {
        return (int) (y * IMAGE_HEIGHT / 3.) + IMAGE_HEIGHT / 2;
    }

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

    private static void fillPolygon(ObjModel model, List<PolygonPoint> polygon, BufferedImage image, double intensity,
                                    BufferedImage texture, double[][] zBuf) {

        int xMax = Integer.MIN_VALUE;
        int xMin = Integer.MAX_VALUE;
        int yMax = xMax;
        int yMin = xMin;

        for (PolygonPoint v : polygon) {
            int vInd = v.getVInd();
            Vector<Double> point = model.getV(vInd);
            int x = getX(point.get(0));
            int y = getY(point.get(1));
            xMax = max(x, xMax);
            xMin = min(x, xMin);
            yMax = max(y, yMax);
            yMin = min(y, yMin);
        }

        int vInd = polygon.get(0).getVInd();
        int vtInd = polygon.get(0).getVtInd();
        Vector<Double> point = model.getV(vInd);
        double x0 = point.get(0);
        double y0 = point.get(1);
        double z0 = point.get(2);
        double xt0 = model.getVt(vtInd).get(0);
        double yt0 = model.getVt(vtInd).get(1);

        vInd = polygon.get(1).getVInd();
        vtInd = polygon.get(1).getVtInd();
        point = model.getV(vInd);
        double x1 = point.get(0);
        double y1 = point.get(1);
        double z1 = point.get(2);
        double xt1 = model.getVt(vtInd).get(0);
        double yt1 = model.getVt(vtInd).get(1);

        vInd = polygon.get(2).getVInd();
        vtInd = polygon.get(2).getVtInd();
        point = model.getV(vInd);
        double x2 = point.get(0);
        double y2 = point.get(1);
        double z2 = point.get(2);
        double xt2 = model.getVt(vtInd).get(0);
        double yt2 = model.getVt(vtInd).get(1);

        for (int xPix = xMin; xPix <= xMax; xPix++) {
            for (int yPix = yMin; yPix <= yMax; yPix++) {
                // Barycentric coordinates
                double x = getX(xPix);
                double y = getY(yPix);
                double l0Num = (y - y2) * (x1 - x2) - (x - x2) * (y1 - y2);
                double l0Den = (y0 - y2) * (x1 - x2) - (x0 - x2) * (y1 - y2);
                double l1Num = (y - y0) * (x2 - x0) - (x - x0) * (y2 - y0);
                double l1Den = (y1 - y0) * (x2 - x0) - (x1 - x0) * (y2 - y0);
                double l2Num = (y - y1) * (x0 - x1) - (x - x1) * (y0 - y1);
                double l2Den = (y2 - y1) * (x0 - x1) - (x2 - x1) * (y0 - y1);

                if (l0Num * l0Den < 0 || l1Num * l1Den < 0 || l2Num * l2Den < 0) {
                    continue;
                }
                double z = l0Num * z0 / l0Den  + l1Num * z1 / l1Den + l2Num * z2 / l2Den;

                if (z > zBuf[xPix][yPix]) {
                    zBuf[xPix][yPix] = z;
                    double textX = xt0 * l0Num / l0Den +
                            xt1 * l1Num / l1Den +
                            xt2 * l2Num / l2Den;

                    double textY = yt0 * l0Num / l0Den +
                            yt1 * l1Num / l1Den +
                            yt2 * l2Num / l2Den;

                    int textPixelX = (int) (textX * texture.getWidth());
                    int textPixelY = (int) (textY * texture.getHeight());
                    Color primColor = new Color(texture.getRGB(textPixelX, textPixelY));
                    Color colorToSet = new Color((int) (primColor.getRed() * intensity),
                            (int) (primColor.getGreen() * intensity),
                            (int) (primColor.getBlue() * intensity));
                    setPixel(xPix, yPix, image, colorToSet.getRGB());
                }
            }
        }
    }

    public static void render(ObjModel model, BufferedImage texture, BufferedImage image) {
        double[][] zBuf = new double[IMAGE_HEIGHT][IMAGE_WIDTH];
        for (double[] aZBuf : zBuf) {
            Arrays.fill(aZBuf, Double.NEGATIVE_INFINITY);
        }
        for (Face f : model.getFaces()) {
            List<PolygonPoint> polygon = f.getPolygonPoints();

            Vector<Double> n = cross(sub(model.getV(polygon.get(2).getVInd()), model.getV(polygon.get(0).getVInd())), sub(model.getV(polygon.get(1).getVInd()), model.getV(polygon.get(0).getVInd())));
            n.normalize();
            Vector<Double> light = new Vector<>(0., 0., -1.);
            double intensity = scalar(n, light);
            if (intensity <= 0) {
                continue;
            }
            fillPolygon(model, polygon, image, intensity, texture, zBuf);
        }
    }

    public static BufferedImage create() {
        BufferedImage image = new BufferedImage(RenderUtils.IMAGE_WIDTH, RenderUtils.IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < RenderUtils.IMAGE_WIDTH; i++) {
            for (int j = 0; j < RenderUtils.IMAGE_HEIGHT; j++) {
                image.setRGB(i, j, new Color(0, 255, 0).getRGB());
            }
        }
        return image;
    }
}
