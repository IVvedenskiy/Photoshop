package sample.filters;

import java.awt.*;
import java.util.LinkedList;

public class Mosaic {
    public int[][] process(int[][] pixels) {
        int normalHeight = pixels.length;
        int normalWidth = pixels[0].length;
        int radius = 1;
        int[][] newPixels = new int[normalHeight][normalWidth];


        int[][] extendMatrix = extendMatrix(pixels, radius);

        for (int i = 0; i < normalHeight; i++) {
            for (int j = 0; j < normalWidth; j++) {
                getNewPixel(
                        extendMatrix,
                        i + radius,
                        j + radius
                );
            }
        }
        return newPixels;
    }

    private void getNewPixel(int[][] extendMatrix, int i, int j) {
        int radius = 4;
        LinkedList<Integer> list = new LinkedList<>();

        for (int k = i - radius, p = 0; k < i + radius + 1; k++, p++) {
            for(int l = j - radius, q = 0; l < j + radius + 1; l++, q++) {
                list.add(extendMatrix[k][l]);
            }
        }
        list.sort(Integer::compareTo);
        for (Integer element: list) {
            element = list.get(list.size()/2);
        }
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
