package sample.jpeg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JpegEncoder {
    public static void saveJpeg(int[][] matrix, int width, int height, String path) throws IOException, IOException {
        BufferedImage image = JpegEncoder.encode(matrix, width, height);
        File file = new File(path);
        ImageIO.write(image, "jpg", file);
    }

    public static BufferedImage encode(int[][] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                image.setRGB(j, i, pixels[i][j]);
            }
        }
        return  image;
    }
}