package hw2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Dictionaries extends HashMap<Integer, String[]> {
    public static Dictionaries createFromFile(String filename) {
        try {
            HashMap<Integer, List<String>> dictionaries = new HashMap<>();
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                int length = line.length();

                if (dictionaries.containsKey(length)) {
                    List<String> dictionary = dictionaries.get(length);
                    dictionary.add(line);
                } else {
                    ArrayList<String> dictionary = new ArrayList<>();
                    dictionary.add(line);
                    dictionaries.put(length, dictionary);
                }
            }

            return convertFormat(dictionaries);
        } catch (IOException e) {
            return null;
        }
    }

    private static Dictionaries convertFormat(HashMap<Integer, List<String>> dictionaries) {
        Dictionaries result = new Dictionaries();

        for (int length : dictionaries.keySet()) {
            List<String> dictionary = dictionaries.get(length);
            result.put(length, dictionary.toArray(new String[0]));
        }

        return result;
    }
}
