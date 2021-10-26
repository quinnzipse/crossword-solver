package hw2;

import java.awt.geom.Point2D;
import java.util.logging.Level;

abstract public class Board {
    private final char[] board;
    public final int width, height;

    public Board(int width, int height, char[] board) {
        this.width = width;
        this.height = height;
        this.board = board;
    }

    public Point2D getCoordinates(int index) {
        if (index >= width * height) throw new IndexOutOfBoundsException();

        int y = index / width;
        int x = index % width;

        return new Point2D.Float(x, y);
    }

    public int coordinatesToIndex(int x, int y) {
        return y * width + x;
    }

    protected Point2D indexToCoordinates(int i) {
        int y = i / width;
        int x = i % width;

        return new Point2D.Float(x, y);
    }

    public char getAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return '#';
        }

        return board[x + y * width];
    }

    protected void setAt(int x, int y, char c) {
        board[x + y * width] = c;
    }

    public void display() {
        for (int y = 0; y < height; y++) {
            Logger.log(Level.FINE, getRow(y));
        }
    }

    private String getRow(int y) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < width; x++) {
            char value = getAt(x, y);
            if (value == '#') sb.append(" ");
            else sb.append(value);
        }
        return sb.toString();
    }
}