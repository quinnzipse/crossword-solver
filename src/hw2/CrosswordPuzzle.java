package hw2;

public class CrosswordPuzzle extends Puzzle {
    private final PuzzleKey key;

    public CrosswordPuzzle(int width, int height, char[] board, PuzzleKey key) {
        super(width, height, board);
        this.key = key;
    }
}
