package hw2;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Tester {
    public static void main(String[] args) {
        Arguments arguments = parseArguments(args);

        String loggingLevel = arguments.get("-v");
        setupLogger(loggingLevel);

        String puzzleFileName = arguments.get("-p");
        PuzzleKey puzzleKey = readPuzzle(puzzleFileName);

        String dictionaryFileName = arguments.get("-d");
        Dictionaries dictionaries = readDictionaries(dictionaryFileName);

        CSP csp = new CSP(puzzleKey, dictionaries);

        CrosswordPuzzle puzzle = csp.solve();
        if (puzzle != null) {
            puzzle.print();
        } else {
            System.out.println("No solution found");
        }
    }

    private static void setupLogger(String loggingLevel) {
        Level level = switch (loggingLevel.charAt(0)) {
            case '1' -> Level.FINER;
            case '2' -> Level.FINEST;
            case '0' -> Level.FINE;
            default -> throw new IllegalStateException("Unexpected verbosity value: " + loggingLevel.charAt(0));
        };

        Logger log = Logger.getLogger("customLogger");
        ConsoleHandler handler = new ConsoleHandler();
        log.addHandler(handler);
        handler.setFormatter(new SimpleFormatter());
        log.setLevel(level);
    }

    private static Dictionaries readDictionaries(String fileName) {
        Dictionaries domains = Dictionaries.createFromFile(fileName);
        if (domains == null) {
            System.err.println("Cannot read puzzle. Quitting.");
            System.exit(1);
        }

        return domains;
    }

    private static PuzzleKey readPuzzle(String filename) {
        PuzzleKey pk = PuzzleKey.createFromFile(filename);
        if (pk == null) {
            System.err.println("Cannot read puzzle. Quitting.");
            System.exit(1);
        }

        return pk;
    }

    private static Arguments parseArguments(String[] args) {
        try {
            return new Arguments(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
