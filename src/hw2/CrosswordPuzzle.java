package hw2;

import java.util.Set;

public class CrosswordPuzzle extends Puzzle {
    private final PuzzleKey key;

    public CrosswordPuzzle(int width, int height, char[] board, PuzzleKey key) {
        super(width, height, board);
        this.key = key;
    }

    public void fillWords(Assignment assignment) {
        Set<Word> words = assignment.keySet();
        for (Word word : words) {
            word.setPuzzleValue(this, assignment.get(word));
        }
    }
}
