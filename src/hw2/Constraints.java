package hw2;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Constraints extends ArrayList<Constraint> {
    public static Constraints generateConstraints(PuzzleKey puzzleKey) {
        Word[] words = puzzleKey.getWordList();
        Constraints constraints = new Constraints();
        int puzzleLength = puzzleKey.getHeight() * puzzleKey.getWidth();

        for (int i = 0; i < puzzleLength; i++) {
            Point2D coordinates = puzzleKey.getCoordinates(i);

            if (puzzleKey.isBlack(coordinates)) continue;

            List<Word> intersections = Arrays.stream(words)
                    .filter(word -> word.getIndexAt(coordinates) != -1)
                    .collect(Collectors.toList());

            if (intersections.size() == 2) {
                Word word1 = intersections.get(0),
                        word2 = intersections.get(1);

                constraints.add(new Constraint(word1, word2, coordinates));
            }
        }

        return constraints;
    }
}