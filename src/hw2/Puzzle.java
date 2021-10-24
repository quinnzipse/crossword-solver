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

    public char get(int index) {
        Point2D coordinates = getCoordinates(index);
        return get((int) coordinates.getX(), (int) coordinates.getY());
    }

    public char get(int x, int y) {
        try {
            return board[x + y * width];
        } catch (IndexOutOfBoundsException e) {
            return '#';
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

//    public String getColumn(int x) {
//        StringBuilder sb = new StringBuilder();
//        for (int y = 0; y < height; y++) sb.append(get(x, y));
//        return sb.toString();
//    }
//
//    public String getRow(int y) {
//        StringBuilder sb = new StringBuilder();
//        for (int x = 0; x < width; x++) sb.append(get(x, y));
//        return sb.toString();
//    }
//
//    public void setColumn(int x, String s) {
//        if (s.length() > height) return;
//        for (int i = 0; i < s.length(); i++) {
//            board[x + i * board.length] = s.charAt(i);
//        }
//    }
//
//    public void setRow(int y, String s) {
//        if (s.length() > width) return;
//        for (int i = 0; i < s.length(); i++) {
//            board[i + y * board.length] = s.charAt(i);
//        }
//    }
}