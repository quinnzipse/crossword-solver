package hw2;

public class Tester {
    public static void main(String[] args) {
        Arguments arguments = parseArguments(args);

        String puzzleFileName = arguments.get("-p");
        PuzzleKey puzzleKey = readPuzzle(puzzleFileName);

        String dictionaryFileName = arguments.get("-d");
        Dictionaries dictionaries = readDictionaries(dictionaryFileName);

        CSP csp = new CSP(puzzleKey, dictionaries);

        CrosswordPuzzle puzzle = csp.solve();
        puzzle.print();
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
