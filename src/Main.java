import obj.ObjModel;
import obj.ObjUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Никита\\Desktop\\african_head.obj");
        ObjModel model = ObjUtils.parse(file);
    }
}
