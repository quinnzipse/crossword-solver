package hw2;

import hw2.value.ValueOrder;
import hw2.value.ValueOrderer;
import hw2.variable.VariableOrder;
import hw2.variable.VariableOrderer;

import java.util.InputMismatchException;
import java.util.logging.Level;

public class Solve {
    public static void main(String[] args) {
        Arguments arguments = parseArguments(args);

        String loggingLevel = arguments.get("-v");
        Logger.setupLogger(loggingLevel);

        String puzzleFileName = arguments.get("-p");
        Logger.log(Level.FINER, String.format("Reading puzzle from [%s]", puzzleFileName));
        PuzzleKey puzzleKey = readPuzzle(puzzleFileName);

        String dictionaryFileName = arguments.get("-d");
        Logger.log(Level.FINER, String.format("Reading dictionary from [%s]", dictionaryFileName));
        Domains domains = getDomainsFromDictionary(dictionaryFileName);

        String valueOrderString = arguments.get("-vo");
        ValueOrder valueOrder = ValueOrderer.getOrderByString(valueOrderString);

        String variableSelectionString = arguments.get("-vs");
        VariableOrder variableOrder = VariableOrderer.getOrderByString(variableSelectionString);

        int guiDelay = getGUIDelay(arguments.get("-gui"));

        CSP csp = new CSP(puzzleKey, domains);
        CSPSolver cspSolver = new CSPSolver(csp, valueOrder, variableOrder, guiDelay);

        CrosswordPuzzle puzzle = cspSolver.solve();
        if (puzzle != null) {
            puzzle.display();
        } else {
            Logger.log(Level.FINE, "No solution found");
        }
    }

    private static int getGUIDelay(String guiDelay) {
        int delay = -1;

        if (guiDelay != null) {
            try {
                delay = Integer.parseInt(guiDelay);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid GUI delay: " + guiDelay);
            }
        }

        return delay;
    }

    private static Domains getDomainsFromDictionary(String fileName) {
        Domains domains = Domains.createFromFile(fileName);
        if (domains == null) {
            System.err.println("Cannot read domains. Quitting.");
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
