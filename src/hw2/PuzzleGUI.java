package hw2;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PuzzleGUI {
    private final JLabel[] labels;
    private final int delay;
    private final JFrame frame;

    public PuzzleGUI(PuzzleKey puzzleKey, int delay) {
        this.delay = delay;

        frame = new JFrame("Crossword Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(puzzleKey.width * 70, puzzleKey.height * 70);

        labels = new JLabel[puzzleKey.height * puzzleKey.width];
        frame.setLayout(new GridLayout(puzzleKey.height, puzzleKey.width));
        for (int i = 0; i < puzzleKey.height * puzzleKey.width; i++) {
            var coordinates = puzzleKey.getCoordinates(i);
            var key = puzzleKey.getAt((int) coordinates.getX(), (int) coordinates.getY());

            JLabel blank = new JLabel(String.valueOf(key), JLabel.CENTER);
            blank.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));

            if (key == '#') {
                blank.setOpaque(true);
                blank.setBackground(Color.BLACK);
                blank.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
                frame.add(blank);
            } else if (puzzleKey.isNumber((int) coordinates.getX(), (int) coordinates.getY())) {
                JComponent container = new JPanel(new GridLayout(2, 1));
                container.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));

                JLabel superScript = new JLabel(String.valueOf(puzzleKey.getNumber(i)), JLabel.LEFT);
                superScript.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                container.add(superScript);
                container.add(blank);

                frame.add(container);
            } else {
                blank.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
                frame.add(blank);
            }

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
