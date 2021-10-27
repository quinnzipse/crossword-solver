package hw2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Arguments {
    private final Map<String, String> argumentMap;
    private static final String[] REQUIRED_ARGUMENTS = {"-d", "-p", "-v", "-vs", "-vo"};
    private static final String[] DEFAULTS = {"-v", "0", "-vs", "static", "-vo", "static"};
    private static final String USAGE_MESSAGE = "USAGE: Java Solve -d <FILENAME> -p <FILENAME> " +
            "[-v <INT>] [-vs|--variable-selection <static|mrv|deg|mrv+deg>] [-vo|--value-order <static|lcv>]";

    public Arguments(String[] cmdLineArgs) {
        preprocessArgs(cmdLineArgs);
        argumentMap = parse(cmdLineArgs);
        validateArguments();
    }

    private void preprocessArgs(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            String currentArg = arguments[i];
            if (currentArg.equals("--value-order")) {
                arguments[i] = "-vo";
            } else if (currentArg.equals("--variable-selection")) {
                arguments[i] = "-vs";
            }
        }
    }

    private void validateArguments() {
        for (String requiredArgument : REQUIRED_ARGUMENTS) {
            if (!isSet(requiredArgument)) {
                System.err.println(requiredArgument + " is required!");
                throw new IllegalArgumentException(USAGE_MESSAGE);
            }
        }
    }

    public boolean isSet(String s) {
        return argumentMap.containsKey(s);
    }

    public String get(String s) {
        return argumentMap.get(s);
    }

    public static Map<String, String> parse(String[] args) {
        Map<String, String> map = new HashMap<>();
        setDefaultValues(map);
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    map.put(arg, args[i + 1]);
                    i++;
                } else {
                    map.put(arg, arg);
                }
            } else {
                System.err.println("Unexpected Token: " + arg);
                throw new IllegalArgumentException(USAGE_MESSAGE);
            }
        }

        return map;
    }

    private static void setDefaultValues(Map<String, String> map) {
        var iterator = Arrays.stream(DEFAULTS).iterator();
        while (iterator.hasNext()) {
            var key = iterator.next();
            var defaultValue = iterator.next();
            map.put(key, defaultValue);
        }
    }
}
