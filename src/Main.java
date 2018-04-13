import obj.ObjModel;
import obj.ObjUtils;
import utils.RenderUtils;
import utils.TargaReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File objFile = new File("african_head.obj");
        ObjModel model = ObjUtils.parse(objFile);
        BufferedImage image = new BufferedImage(RenderUtils.IMAGE_WIDTH, RenderUtils.IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        BufferedImage texture = TargaReader.getImage("african_head_diffuse.tga");
        if (texture == null) {
            return;
        }
        RenderUtils.render(model, texture, image);
        ImageIO.write(image, "png", new File("output.png"));

    }
}
