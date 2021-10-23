package hw2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PuzzleKey extends Puzzle {
    private final Word[] list;

    private PuzzleKey(int width, int height, char[] board) {
        super(width, height, board);
        list = getWordList();
    }

    public void printWordList() {
        Arrays.stream(list).forEach(Word::print);
    }

    public static PuzzleKey createFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            int width = scanner.nextInt();
            int height = scanner.nextInt();

            char[] board = new char[width * height];
            for (int i = 0; i < width * height; i++) {
                board[i] = scanner.next().charAt(0);
            }

            return new PuzzleKey(width, height, board);
        } catch (IOException e) {
            System.out.println("File not found");
            return null;
        }
    }

    public CrosswordPuzzle createBlankPuzzle() {
        char[] board = getBoard();

        for (int i = 0; i < board.length; i++) {
            if (board[i] != '#') board[i] = ' ';
        }

        return new CrosswordPuzzle(getWidth(), getHeight(), board, this);
    }

    public void setDomains(Dictionaries dictionaries) {
        for (Word word : list) {
            int length = word.getLength();
            word.setDomain(dictionaries.get(length));
        }
    }

    private Word[] getWordList() {
        ArrayList<Word> wordList = new ArrayList<>();

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (isDownWord(x, y)) wordList.add(addWord(x, y, Direction.DOWN));
                if (isAcrossWord(x, y)) wordList.add(addWord(x, y, Direction.ACROSS));
            }
        }

        return wordList.toArray(new Word[0]);
    }

    private Word addWord(int x, int y, Direction direction) {
        String name = get(x, y) + "";
        Point2D start = new Point2D.Float(x, y), end = getEndCoords(x, y, direction);
        Line2D line = new Line2D.Float(start, end);
        int length;

        if (Direction.ACROSS == direction) length = (int) (line.getX2() - line.getX1());
        else length = (int) (line.getY2() - line.getY1());

        return new Word(name, length, line, direction);
    }

    private boolean isDownWord(int x, int y) {
        return isNumber(x, y) && (isBlack(x, y - 1) && !isBlack(x, y + 1));
    }

    private boolean isAcrossWord(int x, int y) {
        return isNumber(x, y) && (isBlack(x - 1, y) && !isBlack(x + 1, y));
    }

    private boolean isBlack(int x, int y) {
        return get(x, y) == '#';
    }

    private boolean isNumber(int x, int y) {
        return '0' <= get(x, y) && get(x, y) <= '9';
    }
}
