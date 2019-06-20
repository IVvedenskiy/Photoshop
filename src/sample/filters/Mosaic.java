package sample.filters;

import java.util.LinkedList;

public class Mosaic {
    public int[][] process(int[][] pixels) {
        int normalHeight = pixels.length;
        int normalWidth = pixels[0].length;
        int[][] newPixels = new int[normalHeight][normalWidth];
        for (int i = 0; i < normalHeight; i++) {
            System.arraycopy(pixels[i], 0, newPixels[i], 0, normalWidth);
        }

        int radius = 4;

        for (int i = 0; i < normalHeight - radius; i += radius) {
            for (int j = 0; j < normalWidth - radius; j += radius) {
                setMosaic(
                        newPixels,
                        i,
                        j,
                        radius
                );
            }
        }
        return newPixels;
    }

    private void setMosaic(int[][] pixels, int i, int j, int radius) {
        LinkedList<Integer> list = new LinkedList<>();

        for (int k = i, p = 0; k < i + radius; k++, p++) {
            for (int l = j, q = 0; l < j + radius; l++, q++) {
                list.add(pixels[k][l]);
            }
        }
        list.sort(Integer::compareTo);

        Integer integer = list.get(list.size() / 2);
        for (int k = i, p = 0; k < i + radius; k++, p++) {
            for (int l = j, q = 0; l < j + radius; l++, q++) {
                pixels[k][l] = integer;
            }
        }
    }

}
