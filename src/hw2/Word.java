package hw2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class Word {
    private final int length;
    private final Line2D line;
    private String[] domain;
    private final Direction direction;

    public Word(int length, Line2D line, Direction direction) {
        this.length = length;
        this.line = line;
        this.direction = direction;
    }

    public void print() {
        System.out.printf("(%.0f,%.0f)->(%.0f,%.0f) %s Len-%d domain %s\n",
                line.getX1(), line.getY1(), line.getX2(), line.getY2(),
                (direction == Direction.ACROSS ? "Across" : "Down"),
                length, domain == null ? "null" : "set!");
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

    public int getIndexAt(Point2D coordinates) {
        if (!containsPoint(coordinates)) {
            System.out.printf("Line (%d, %d)->(%d, %d) %s contains (%d, %d)\n",
                    (int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2(),
                    direction == Direction.ACROSS ? "Across" : "Down",
                    (int) coordinates.getX(), (int) coordinates.getY());
            return -1;
        }

        System.out.printf("Line (%d, %d)->(%d, %d) %s contains (%d, %d)\n",
                (int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2(),
                direction == Direction.ACROSS ? "Across" : "Down",
                (int) coordinates.getX(), (int) coordinates.getY());

        int xDiff = (int) (line.getX1() - coordinates.getX());
        int yDiff = (int) (line.getY1() - coordinates.getY());
        return xDiff + yDiff;
    }

    private boolean containsPoint(Point2D coordinate) {
        Set<Point2D> allPoints = new HashSet<>();
        int startX = (int) line.getX1(), startY = (int) line.getY1();

        if (direction == Direction.ACROSS) {
            for (int i = 0; i < length; i++) allPoints.add(new Point2D.Float(startX + i, startY));
        } else {
            for (int i = 0; i < length; i++) allPoints.add(new Point2D.Float(startX, startY + 1));
        }
        System.out.print("COORDS: ");
        allPoints.forEach(r -> System.out.printf("(%.0f, %.0f) ", r.getX(), r.getY()));
        System.out.println();
        return allPoints.contains(coordinate);
    }

    public String getValue() {
        return null;
    }
}
