package hw2.value;
import hw2.Assignment;
import hw2.Word;

public abstract class ValueOrderer {
    abstract public String[] order(Word word);

    public static ValueOrderer getValueOrderer(ValueOrder valueOrder, Assignment assignment) {
        return switch (valueOrder) {
            case STATIC -> new Static();
            case LEAST_CONSTRAINING_VALUE -> new LCV(assignment);
        };
    }

    public static ValueOrder getOrderByString(String orderCode) {
        return switch (orderCode) {
            case "static" -> ValueOrder.STATIC;
            case "lcv" -> ValueOrder.LEAST_CONSTRAINING_VALUE;
            default -> throw new IllegalArgumentException("Invalid argument value order: " + orderCode);
        };
    }
}
