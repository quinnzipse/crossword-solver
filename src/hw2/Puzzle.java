package hw2;

import java.awt.geom.Point2D;

abstract public class Puzzle {
    private final char[] board;
    private final int width, height;

    public Puzzle(int width, int height, char[] board) {
        this.width = width;
        this.height = height;
        this.board = board;
    }

    public Point2D getCoordinates(int index) {
        int y = index / width;
        int x = index % width;

        return new Point2D.Float(x, y);
    }

    public char get(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return '#';
        }

        return board[x + y * width];
    }

    public void print() {
        for (int y = 0; y < height; y++) {
            String row = getRow(y);
            System.out.println(row);
        }
    }

    public void set(int x, int y, char c) {
        board[x + y * width] = c;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected char[] getBoard() {
        return board;
    }

    public Point2D getEndCoords(int x, int y, Direction direction) {
        if (direction == Direction.ACROSS) {
            for (; x < width; x++) {
                if (get(x, y) == '#') break;
            }
        } else {
            for (; y < height; y++) {
                if (get(x, y) == '#') break;
            }
        }

        return new Point2D.Float(x, y);
    }

    private String getRow(int y) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < width; x++) sb.append(get(x, y)).append(" ");
        return sb.toString();
    }

    public void setValue(Point2D start, Direction direction, String value) {
        int x = (int) start.getX(), y = (int) start.getY();

        if (direction == Direction.DOWN) {
            for (int i = 0; i < value.length(); i++) {
                set(x, y + i, value.charAt(i));
            }
        } else {
            for (int i = 0; i < value.length(); i++) {
                set(x + i, y, value.charAt(i));
            }
        }
    }
}