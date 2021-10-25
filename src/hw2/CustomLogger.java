package hw2;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CustomLogger {
    private static Logger logger;

    public static void setupLogger(String loggingLevel) {
        Level level = switch (loggingLevel.charAt(0)) {
            case '1' -> Level.FINER;
            case '2' -> Level.FINEST;
            case '0' -> Level.FINE;
            default -> throw new IllegalStateException("Unexpected verbosity value: " + loggingLevel.charAt(0));
        };

        logger = Logger.getLogger("customLogger");
        ConsoleHandler handler = new ConsoleHandler();
        logger.addHandler(handler);
        handler.setFormatter(new SimpleFormatter());
        logger.setLevel(level);
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
