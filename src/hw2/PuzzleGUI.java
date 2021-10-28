package hw2;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PuzzleGUI {
    private final JLabel[] labels;
    private final int delay;
    private final JFrame frame;

    public PuzzleGUI(CrosswordPuzzle puzzle, int delay) {
        this.delay = delay;

        frame = new JFrame("Crossword Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(puzzle.width * 70, puzzle.height * 70);

        labels = new JLabel[puzzle.height * puzzle.width];
        frame.setLayout(new GridLayout(puzzle.height, puzzle.width));
        for (int i = 0; i < puzzle.height * puzzle.width; i++) {
            var coordinates = puzzle.getCoordinates(i);
            var key = puzzle.getAt((int) coordinates.getX(), (int) coordinates.getY());

            JLabel blank = new JLabel(String.valueOf(key), JLabel.CENTER);
            if (key == '#') {
                blank.setOpaque(true);
                blank.setBackground(Color.BLACK);
            }

            blank.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
            blank.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
            frame.add(blank);
            labels[i] = blank;
        }

        frame.setVisible(true);

    }

    public void update(CrosswordPuzzle puzzle, Word word, Assignment assignment) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(delay);
        if (word == null)
            frame.setTitle("Crossword Puzzle | SOLVED | All " + assignment.size() + " words assigned.");
        else
            frame.setTitle("Crossword Puzzle | Trying: " + word + " | " + assignment.size() + " words assigned.");
        for (int x = 0; x < puzzle.width * puzzle.height; x++) {
            var coords = puzzle.getCoordinates(x);
            char value = puzzle.getAt((int) coords.getX(), (int) coords.getY());
            labels[x].setText(String.valueOf(value));
        }
    }
}
