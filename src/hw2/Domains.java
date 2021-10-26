package hw2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Domains extends HashMap<Integer, String[]> {
    public static Domains createFromFile(String filename) {
        try {
            HashMap<Integer, List<String>> domains = new HashMap<>();

            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                String domainValue = scanner.nextLine();
                int key = domainValue.length();

                if (domains.containsKey(key)) {
                    List<String> domain = domains.get(key);
                    domain.add(domainValue);
                } else {
                    ArrayList<String> domain = new ArrayList<>();
                    domain.add(domainValue);
                    domains.put(key, domain);
                }
            }

            return convertFormat(domains);
        } catch (IOException e) {
            return null;
        }
    }

    private static Domains convertFormat(HashMap<Integer, List<String>> dictionaries) {
        Domains result = new Domains();

        for (int length : dictionaries.keySet()) {
            List<String> dictionary = dictionaries.get(length);
            result.put(length, dictionary.toArray(new String[0]));
        }

        return result;
    }
}
