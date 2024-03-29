package sample.bmp;

public class Bmp {
    private int bitsCount;
    private int width;
    private int height;
    private int size;
    private byte[] image;
    private int[][] pixels;
    private int offset;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getBitsCount() {
        return bitsCount;
    }

    public void setBitsCount(int bitsCount) {
        this.bitsCount = bitsCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int[][] getPixels() {
        return pixels;
    }

    public void setPixels(int[][] pixels) {
        this.pixels = pixels;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
