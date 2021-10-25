package hw2;

import java.util.logging.Level;

public class Logger {
    private static Level level;

    public static void setupLogger(String loggingLevel) {
        level = switch (loggingLevel.charAt(0)) {
            case '1' -> Level.FINER;
            case '2' -> Level.FINEST;
            case '0' -> Level.FINE;
            default -> throw new IllegalStateException("Unexpected verbosity value: " + loggingLevel.charAt(0));
        };
    }

    public static void log(Level messageLevel, String message) {
        if (messageLevel.intValue() >= level.intValue()) {
            System.out.println(message);
        }
    }
}
