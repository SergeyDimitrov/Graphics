import obj.ObjModel;
import obj.ObjUtils;
import utils.RenderUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("african_head.obj");
        ObjModel model = ObjUtils.parse(file);
        BufferedImage image = new BufferedImage(RenderUtils.IMAGE_WIDTH, RenderUtils.IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        RenderUtils.render(model, image);
        ImageIO.write(image, "png", new File("output.png"));
    }
}
