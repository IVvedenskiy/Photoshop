package sample.filters;

import java.awt.*;

public class Stamping {
    public int[][] process(int[][] pixels) {
        int normalHeight = pixels.length;
        int normalWidth = pixels[0].length;
        int radius = 1;
        int[][] newPixels = new int[normalHeight][normalWidth];

        int[][] stampingKernel = getStampingKernel();

        int[][] extendMatrix = extendMatrix(pixels, radius);

        for (int i = 0; i < normalHeight; i++) {
            for (int j = 0; j < normalWidth; j++) {
                newPixels[i][j] = getNewPixel(
                        extendMatrix,
                        stampingKernel,
                        i + radius,
                        j + radius
                );
            }
        }
        return newPixels;
    }

    private int getNewPixel(int[][] extendMatrix, int[][] stampingKernel, int i, int j) {
        double sumR = 0;
        double sumG = 0;
        double sumB = 0;
        int radius = 1;
        for (int k = i - radius, p = 0; k < i + radius + 1; k++, p++) {
            for(int l = j - radius, q = 0; l < j + radius + 1; l++, q++) {
                Color color = new Color(extendMatrix[k][l]);
                sumR += (color.getRed() * stampingKernel[p][q]);
                sumG += (color.getGreen() * stampingKernel[p][q]);
                sumB += (color.getBlue() * stampingKernel[p][q]);
            }
        }
        return new Color(
                getValidColorValue((int) Math.round(sumR)+128),
                getValidColorValue((int) Math.round(sumG)+128),
                getValidColorValue((int) Math.round(sumB)+128)).getRGB();
    }

    private int getValidColorValue(int color) {
        if (color > 255) {
            return 255;
        } else if (color < 0) {
            return 0;
        } else {
            return color;
        }
    }

    private int[][] getStampingKernel() {
        return new int[][] {{0, -1, 0},{-1, 0, 1},{0,1,0}};
    }

    public static int[][] extendMatrix(int[][] matrix, int sideSizeExtension) {
        int matrixHeight = matrix.length;
        int matrixWidth = matrix[0].length;
        int[][] extendedMatrix = new int[matrixHeight + sideSizeExtension * 2][matrixWidth + sideSizeExtension * 2];
        int oldI = 0;
        int oldJ = 0;
        for (int i = 0; i < extendedMatrix.length; i++) {
            if(i <= sideSizeExtension){
                oldI = 0;
            } else if(i >= sideSizeExtension + matrixHeight) {
                oldI = matrixHeight - 1;
            } else {
                oldI++;
            }
            for (int j = 0; j < extendedMatrix[i].length; j++) {
                if(j <= sideSizeExtension){
                    oldJ = 0;
                } else if(j >= sideSizeExtension + matrixWidth) {
                    oldJ = matrixWidth - 1;
                } else {
                    oldJ++;
                }
                extendedMatrix[i][j] = matrix[oldI][oldJ];
            }
        }
        return extendedMatrix;
    }
}
