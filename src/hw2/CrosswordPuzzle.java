package hw2;

import java.util.Set;

public class CrosswordPuzzle extends Puzzle {

    public CrosswordPuzzle(int width, int height, char[] board) {
        super(width, height, board);
    }

    public void fillWords(Assignment assignment) {
        Set<Word> words = assignment.keySet();
        for (Word word : words) {
            word.setPuzzleValue(this, assignment.get(word));
        }
    }
}
