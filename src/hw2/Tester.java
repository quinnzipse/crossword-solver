package hw2;

public class Tester {
    public static void main(String[] args) {
        PuzzleKey pk = PuzzleKey.createFromFile(args[0]);
        assert pk != null;
        pk.printWordList();
    }
}
