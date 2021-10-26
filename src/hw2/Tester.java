package hw2;

import java.util.logging.Level;

public class Tester {
    public static void main(String[] args) {
        Arguments arguments = parseArguments(args);

        String loggingLevel = arguments.get("-v");
        Logger.setupLogger(loggingLevel);

        String puzzleFileName = arguments.get("-p");
        Logger.log(Level.FINER, String.format("Reading puzzle from [%s]", puzzleFileName));
        PuzzleKey puzzleKey = readPuzzle(puzzleFileName);

        String dictionaryFileName = arguments.get("-d");
        Logger.log(Level.FINER, String.format("Reading dictionary from [%s]", dictionaryFileName));
        Dictionaries dictionaries = readDictionaries(dictionaryFileName);

        String valueOrderString = arguments.get("-vo");
        ValueOrderer.Order valueOrder = ValueOrderer.getOrderByString(valueOrderString);

        String variableSelectionString = arguments.get("-vs");
        VariableOrderer.Order variableOrder = VariableOrderer.getOrderByString(variableSelectionString);

        CSP csp = new CSP(puzzleKey, dictionaries);
        CSPSolver cspSolver = new CSPSolver(csp, valueOrder, variableOrder);

        CrosswordPuzzle puzzle = cspSolver.solve();
        if (puzzle != null) {
            puzzle.display();
        } else {
            Logger.log(Level.FINE, "No solution found");
        }
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
