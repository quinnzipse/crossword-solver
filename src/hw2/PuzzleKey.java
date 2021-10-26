package hw2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PuzzleKey extends Board {
    public final Word[] wordList;

    private PuzzleKey(int width, int height, char[] board, Map<Integer, Integer> indexToNumber) {
        super(width, height, board);
        wordList = generateWordList(indexToNumber);
    }

    public static PuzzleKey createFromFile(String filename) {
        try {
            Map<Integer, Integer> indexToNumber = new HashMap<>();
            Scanner scanner = new Scanner(new File(filename));

            int width = scanner.nextInt();
            int height = scanner.nextInt();

            char[] board = new char[width * height];

            for (int i = 0; i < width * height; i++) {
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    board[i] = '@';
                    // TODO: If time, create words here!
                    indexToNumber.put(i, number);
                } else {
                    board[i] = scanner.next().charAt(0);
                }
            }

            return new PuzzleKey(width, height, board, indexToNumber);
        } catch (IOException e) {
            System.out.println("File not found");
            return null;
        }
    }

    public CrosswordPuzzle createBlankPuzzle() {
        char[] board = new char[width * height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = coordinatesToIndex(x, y);
                if (isBlack(x, y)) board[index] = '#';
            }
        }

        return new CrosswordPuzzle(width, height, board);
    }

    private Word[] generateWordList(Map<Integer, Integer> wordNumbersByIndex) {
        ArrayList<Word> wordList = new ArrayList<>();

        for (int index : wordNumbersByIndex.keySet()) {
            Point2D coords = indexToCoordinates(index);
            int x = (int) coords.getX(), y = (int) coords.getY(),
                    wordNumber = wordNumbersByIndex.get(index);

            if (isDownWord(x, y)) wordList.add(generateWord(x, y, Direction.DOWN, wordNumber));
            if (isAcrossWord(x, y)) wordList.add(generateWord(x, y, Direction.ACROSS, wordNumber));
        }

        return wordList.toArray(new Word[0]);
    }

    // TODO: Simplify this even more by using direction instead of calculating endCoordinates?
    private Word generateWord(int x, int y, Direction direction, int wordNumber) {
        Point2D start = new Point2D.Float(x, y),
                end = getEndCoordinates(x, y, direction);
        Line2D line = new Line2D.Float(start, end);

        return new Word(wordNumber, line);
    }

    private Point2D getEndCoordinates(int x, int y, Direction direction) {
        if (direction == Direction.ACROSS) {
            for (; x < width; x++) {
                if (isBlack(x, y)) break;
            }
        } else {
            for (; y < height; y++) {
                if (isBlack(x, y)) break;
            }
        }

        return new Point2D.Float(x, y);
    }

    private boolean isDownWord(int x, int y) {
        return isNumber(x, y) && (isBlack(x, y - 1) && !isBlack(x, y + 1));
    }

    private boolean isAcrossWord(int x, int y) {
        return isNumber(x, y) && (isBlack(x - 1, y) && !isBlack(x + 1, y));
    }

    private boolean isBlack(int x, int y) {
        return getAt(x, y) == '#';
    }

    public boolean isBlack(Point2D coordinates) {
        return isBlack((int) coordinates.getX(), (int) coordinates.getY());
    }

    private boolean isNumber(int x, int y) {
        return getAt(x, y) == '@';
    }
}


