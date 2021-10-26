package hw2;

import java.security.InvalidParameterException;
import java.util.Set;

public class CrosswordPuzzle extends Board {
    public CrosswordPuzzle(int width, int height, char[] board) {
        super(width, height, board);
    }

    public void fillWords(Assignment assignment) {
        if (assignment == null) return;

        Set<Word> words = assignment.keySet();
        for (Word word : words) {
            String value = assignment.get(word);
            setWord(word, value);
        }
    }

    private void setWord(Word w, String value) {
        if (w == null)
            throw new InvalidParameterException("Cannot set Word (null)");
        if (value == null)
            throw new InvalidParameterException("Cannot set Word (" + w + ") to null");
        if (value.length() > w.length)
            throw new InvalidParameterException("Value (" + value + ") is too long for word " + w);

        if (w.direction == Direction.DOWN) {
            setDownWord(w, value);
        } else {
            setAcrossWord(w, value);
        }
    }

    private void setAcrossWord(Word w, String value) {
        int x = (int) w.getStartingPoint().getX(),
                y = (int) w.getStartingPoint().getY();

        for (int i = 0; i < value.length(); i++) {
            setAt(x + i, y, value.charAt(i));
        }
    }

    private void setDownWord(Word w, String value) {
        int x = (int) w.getStartingPoint().getX(),
                y = (int) w.getStartingPoint().getY();

        for (int i = 0; i < value.length(); i++) {
            setAt(x, y + i, value.charAt(i));
        }
    }
}
