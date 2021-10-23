package hw2;

import java.awt.geom.Line2D;

public class Word {
    private final String name;
    private final int length;
    private final Line2D line;
    private String[] domain;
    private final Direction direction;

    public Word(String name, int length, Line2D line, Direction direction) {
        this.name = name;
        this.length = length;
        this.line = line;
        this.direction = direction;
    }

    public void print() {
        System.out.printf("%s %s Len-%d @ (%.0f,%.0f)->(%.0f,%.0f) domain %s\n", name,
                (direction == Direction.ACROSS ? "Across" : "Down"),
                length, line.getX1(), line.getY1(), line.getX2(), line.getY2(), domain == null ? "null" : "set!");
    }

    public int getLength() {
        return length;
    }

    public void setDomain(String[] domain) {
        // Domain is effectively final
        if (domain != null) {
            this.domain = domain;
        }
    }
}
