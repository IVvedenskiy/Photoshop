package sample.bmp;

import java.io.FileOutputStream;
import java.io.IOException;

public class BmpWriter {

    public static void saveBMP(int[][] pixels, String path) throws IOException {

        LittleEndianDataOutput out = new LittleEndianDataOutput(new FileOutputStream(path));
        int height = pixels.length;
        int width = pixels[0].length;
        int rowSize = (width * 3 + 3) / 4 * 4;  // 3 bytes per pixel in RGB888, round up to multiple of 4
        int imageSize = rowSize * height;

        // BITMAPFILEHEADER
        out.writeBytes(new byte[]{'B', 'M'});  // FileType
        out.writeInt32(14 + 40 + imageSize);   // FileSize
        out.writeInt16(0);                     // Reserved1
        out.writeInt16(0);                     // Reserved2
        out.writeInt32(14 + 40);               // BitmapOffset

        // BITMAPINFOHEADER
        out.writeInt32(40);                        // Size
        out.writeInt32(width);                     // Width
        out.writeInt32(height);                    // Height
        out.writeInt16(1);                         // Planes
        out.writeInt16(24);                        // BitsPerPixel
        out.writeInt32(0);                         // Compression
        out.writeInt32(imageSize);                 // SizeOfBitmap
        out.writeInt32(0);  // HorzResolution
        out.writeInt32(0);    // VertResolution
        out.writeInt32(0);                         // ColorsUsed
        out.writeInt32(0);                         // ColorsImportant

        byte[] row = new byte[rowSize];
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width * 3; x += 3) {
                int color = pixels[y][x / 3];
                row[x] = (byte) (color);  // Blue
                row[x + 1] = (byte) (color >>> 8);  // Green
                row[x + 2] = (byte) (color >>> 16);  // Red
            }
            out.writeBytes(row);
        }
    }
}
