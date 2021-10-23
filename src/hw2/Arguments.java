package hw2;

import java.util.HashMap;
import java.util.Map;

public class Arguments {
    // Read in command line arguments into a map
    public static Map<String, String> parse(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                if (i + 1 < args.length) {
                    map.put(arg, args[i + 1]);
                } else {
                    map.put(arg, "");
                }
            }
        }
        return map;
    }
}
